package controller;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.RefreshToken;
import beans.User;
import dao.DaoRefreshToken;
import dao.DaoUser;
import requests.AccountReq;
import utils.IdUtil;
import utils.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestParam;
@Controller
public class LoginController {

    @Autowired
    private DaoUser daoUser;

    @Autowired
    private DaoRefreshToken daoRefreshToken;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);


	@GetMapping("/login")
	public String showLoginForm() {
	    return "authentication/login";
	}
	
	@PostMapping("/login")
	public String processLogin(
	        @RequestParam("username") String username,
	        @RequestParam("password") String password,
	        HttpSession session,
	        HttpServletResponse response,
	        Model model) {

	    logger.info("🔥 Đang vào API login");
	    User user = daoUser.findByUsername(username);

	    if (user != null && passwordEncoder.matches(password, user.getPassword())) {
	        // 1. Tạo Access Token
	        String accessToken = JwtUtil.generateAccessToken(user);

	        // 2. Tạo Refresh Token
	        String refreshTokenStr = JwtUtil.generateRefreshToken();
	        Date expiryDate = JwtUtil.getRefreshTokenExpiry();
	        logger.debug("-- expiryDate: {}", expiryDate.toString());
	        logger.debug("-- accessToken: {}", accessToken);
	        logger.debug("-- refreshTokenStr: {}", refreshTokenStr);

	        // 3. Lưu refresh token vào DB
	        RefreshToken refreshToken = new RefreshToken();
	        refreshToken.setId(IdUtil.generateId());
	        refreshToken.setUserId(user.getId());
	        refreshToken.setToken(refreshTokenStr);
	        refreshToken.setExpiryDate(expiryDate);
	        refreshToken.setStatus(true);
	        refreshToken.setCreatedDate(new Date());
	        refreshToken.setUpdatedDate(new Date());
	        daoRefreshToken.save(refreshToken);

	        // 4. Lưu access token vào session hoặc cookie
	        session.setAttribute("accessToken", accessToken);

	        // (Tuỳ chọn) Gửi refresh token qua cookie HttpOnly
	        Cookie cookie = new Cookie("refreshToken", refreshTokenStr);
	        cookie.setHttpOnly(true);
	        cookie.setPath("/");
	        cookie.setMaxAge(7 * 24 * 60 * 60); // 7 ngày
	        response.addCookie(cookie);

	        session.setAttribute("loggedInUser", user);
	        session.setAttribute("role", user.getRole()); // Gán role vào session

	        return "redirect:/";
	    } else {
	        model.addAttribute("error", "Sai tài khoản hoặc mật khẩu");
	        return "authentication/login";
	    }
	}
	
	@PostMapping("/refresh-token")
	@ResponseBody
	public String refreshToken(@CookieValue(value = "refreshToken", required = false) String refreshTokenCookie) {
		logger.debug("++ API /refresh-token");
	    if (refreshTokenCookie == null) {
	        return "Không tìm thấy refresh token";
	    }

	    RefreshToken refreshToken = daoRefreshToken.findByToken(refreshTokenCookie);

	    // Kiểm tra refresh token có hợp lệ không
	    if (refreshToken == null || !refreshToken.isStatus() || refreshToken.getExpiryDate().before(new Date())) {
	        return "Refresh token không hợp lệ hoặc đã hết hạn";
	    }

	    // Lấy user từ refresh token
	    User user = daoUser.getUserById(refreshToken.getUserId());
	    if (user == null) {
	        return "Người dùng không tồn tại";
	    }

	    // Tạo Access Token mới
	    String newAccessToken = JwtUtil.generateAccessToken(user);

	    // Trả về access token mới
	    return newAccessToken;
	}

	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
	    session.invalidate();
	    return "redirect:/login";
	}
    

    
    @GetMapping("/register")
    public String Register(Model model) {
    	model.addAttribute("accountReq", new AccountReq());
    	return "authentication/register";
    }
    
    @PostMapping("/register")
    public String Register2(Model model) {
    	model.addAttribute("accountReq", new AccountReq());
    	return "authentication/register";
    }


  
}
