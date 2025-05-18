package controller;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.RefreshToken;
import beans.User;
import dao.DaoRefreshToken;
import dao.DaoUser;
import mailservice.MailService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);


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

		LOGGER.info("🔥 Đang vào API login");
	    User user = daoUser.findByUsername(username);
	    LOGGER.debug("Username: {}", username);
	    LOGGER.debug("Password: {}", password);

	    if (user != null && passwordEncoder.matches(password, user.getPassword())) {
	        // 1. Tạo Access Token
	        String accessToken = JwtUtil.generateAccessToken(user);

	        // 2. Tạo Refresh Token
	        String refreshTokenStr = JwtUtil.generateRefreshToken();
	        Date expiryDate = JwtUtil.getRefreshTokenExpiry();
	        LOGGER.debug("-- expiryDate: {}", expiryDate.toString());
	        LOGGER.debug("-- accessToken: {}", accessToken);
	        LOGGER.debug("-- refreshTokenStr: {}", refreshTokenStr);

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

	        // 4. Lưu Access Token vào HttpOnly cookie
	        Cookie accessTokenCookie = new Cookie("accessToken", accessToken);
	        accessTokenCookie.setHttpOnly(true);
	        accessTokenCookie.setPath("/");
	        accessTokenCookie.setMaxAge(5 * 60); // 5 phút
	        response.addCookie(accessTokenCookie);

	        // (Tuỳ chọn) Gửi refresh token qua cookie HttpOnly
	        Cookie cookie = new Cookie("refreshToken", refreshTokenStr);
	        cookie.setHttpOnly(true);
	        cookie.setPath("/");
	        cookie.setMaxAge(7 * 24 * 60 * 60); // 7 ngày
	        response.addCookie(cookie);

	        session.setAttribute("loggedInUser", user);
	        session.setAttribute("role", user.getRole()); // Gán role vào session
	        session.setAttribute("userId",  user.getId());
	        return "redirect:/";
	    } else {
	        model.addAttribute("error", "Sai tài khoản hoặc mật khẩu");
	        return "authentication/login";
	    }
	}
	
//	@PostMapping("/refresh-token")
//	@ResponseBody
//	public String refreshToken(@CookieValue(value = "refreshToken", required = false) String refreshTokenCookie) {
//		LOGGER.debug("++ API /refresh-token");
//	    if (refreshTokenCookie == null) {
//	        return "Không tìm thấy refresh token";
//	    }
//
//	    RefreshToken refreshToken = daoRefreshToken.findByToken(refreshTokenCookie);
//
//	    // Kiểm tra refresh token có hợp lệ không
//	    if (refreshToken == null || !refreshToken.isStatus() || refreshToken.getExpiryDate().before(new Date())) {
//	        return "Refresh token không hợp lệ hoặc đã hết hạn";
//	    }
//
//	    // Lấy user từ refresh token
//	    User user = daoUser.getUserById(refreshToken.getUserId());
//	    if (user == null) {
//	        return "Người dùng không tồn tại";
//	    }
//
//	    // Tạo Access Token mới
//	    String newAccessToken = JwtUtil.generateAccessToken(user);
//
//	    // Trả về access token mới
//	    return newAccessToken;
//	}
//	
	@GetMapping("/refresh-token")
	public String refreshTokenPage(@CookieValue(value = "refreshToken", required = false) String refreshTokenCookie,
	                               HttpServletResponse response,
	                               HttpServletRequest request,
	                               HttpSession session,
	                               @RequestParam(value = "redirect", required = false, defaultValue = "/") String redirectUrl) {
		LOGGER.debug("-- call api:/refresh-token");
		LOGGER.debug("-- refreshTokenCookie: ", refreshTokenCookie);
		LOGGER.debug("--redirectUrl: {} ",redirectUrl);
	    if (refreshTokenCookie == null) {
	        return "redirect:/login";
	    }

	    
	    RefreshToken refreshToken = daoRefreshToken.findByToken(refreshTokenCookie);

	    if (refreshToken == null || !refreshToken.isStatus() || refreshToken.getExpiryDate().before(new Date())) {
	        return "redirect:/login";
	    }

	    User user = daoUser.getUserById(refreshToken.getUserId());
	    if (user == null) {
	        return "redirect:/login";
	    }

	    // Tạo lại accessToken
	    String newAccessToken = JwtUtil.generateAccessToken(user);
	    Cookie newAccessTokenCookie = new Cookie("accessToken", newAccessToken);
	    newAccessTokenCookie.setHttpOnly(true);
	    newAccessTokenCookie.setPath("/");
	    newAccessTokenCookie.setMaxAge(5 * 60); // 5 phút
	    response.addCookie(newAccessTokenCookie);

	    // Cập nhật session thông tin user (nếu cần)
	    session.setAttribute("loggedInUser", user);
	    session.setAttribute("role", user.getRole());
        session.setAttribute("userId",  user.getId());

	 // Xử lý để tránh lặp contextPath
	    String contextPath = request.getContextPath();
	    if (redirectUrl.startsWith(contextPath)) {
	        redirectUrl = redirectUrl.substring(contextPath.length());
	    }
	    LOGGER.debug("-- new redirectUrl sau khi remove contextPath: {}", redirectUrl);

	    return "redirect:" + redirectUrl;
	}

	
	@GetMapping("/logout")
	public String logout(HttpSession session, HttpServletResponse response,
			HttpServletRequest request) {
	    session.invalidate();

	    LOGGER.debug("--CAll API: /logout");
	    // 1. Xoá refresh token trong database
	    Cookie[] cookies = request.getCookies();
	    if (cookies != null) {
	    	LOGGER.debug("-----1");
	        for (Cookie cookie : cookies) {
	        	LOGGER.debug("-----2");
	        	LOGGER.debug("---name: {}", cookie.getName());
	            if ("refreshToken".equals(cookie.getName())) {
	            	LOGGER.debug("-----3");
	                String refreshTokenCookie = cookie.getValue();
	        	    LOGGER.debug("----------- refreshTokenStr: {}", refreshTokenCookie);

	                if (refreshTokenCookie != null && !refreshTokenCookie.isEmpty()) {
	                    RefreshToken refreshToken = daoRefreshToken.findByToken(refreshTokenCookie);
	                    if (refreshToken != null) {
	            	    	daoRefreshToken.deleteToken(refreshTokenCookie);
	                    }
	                }
	            }
	        }
	    }
	    
	 
	    Cookie accessTokenCookie = new Cookie("accessToken", null);
	    accessTokenCookie.setMaxAge(0);
	    accessTokenCookie.setPath("/");
	    response.addCookie(accessTokenCookie);

	    Cookie refreshTokenCookie = new Cookie("refreshToken", null);
	    refreshTokenCookie.setMaxAge(0);
	    refreshTokenCookie.setPath("/");
	    response.addCookie(refreshTokenCookie);

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
    
    @GetMapping("/forgotPassword")
    public String ShowForgotPasswordForm() {
        return "authentication/forgotPassword"; 
    }
    
    @PostMapping("/forgotPassword")
    public String handleForgotPassword(
            @RequestParam("email") String email,
            HttpSession session,
            RedirectAttributes redirectAttributes,
            Model model) {
    	System.out.println("Session ID: " + session.getId());


        User user = daoUser.findByEmail(email);

        if (user == null) {
            model.addAttribute("message", "Email không tồn tại trong hệ thống. Vui lòng thử lại.");
            return "authentication/forgotPassword";
        }

        String otp = String.valueOf((int)(Math.random() * 900000) + 100000);
        session.setAttribute("otp", otp);
        session.setAttribute("otpEmail", email);

        try {
            MailService mailService2 = new MailService();
            mailService2.sendOtpEmail(email, otp);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "Gửi email thất bại. Vui lòng thử lại sau.");
            return "authentication/forgotPassword";
        }

        // Gửi 1 thông tin nhỏ đi cùng redirect để biết rằng đã gửi thành công
        redirectAttributes.addFlashAttribute("otpSent", true);

        return "redirect:/verifyOtp"; 
    }

    
    @GetMapping("/verifyOtp")
    public String showVerifyOtpPage(HttpSession session, Model model) {
    	System.out.println("Session ID: " + session.getId());

        String otp = (String) session.getAttribute("otp");

        if (otp == null) {
            model.addAttribute("error", "OTP đã hết hạn hoặc không tồn tại.");
            return "authentication/forgotPassword";
        }
        return "authentication/verifyOtp";
    }

    
    @PostMapping("/verifyOtp")
    public String verifyOtp(
            @RequestParam("otp") String otpInput,
            HttpSession session,
            Model model) {

        String otp = (String) session.getAttribute("otp");

        if (otp == null) {
            model.addAttribute("error", "OTP đã hết hạn hoặc không tồn tại.");
            return "authentication/verifyOtp";
        }

        if (!otp.equals(otpInput)) {
            model.addAttribute("error", "OTP không chính xác.");
            return "authentication/verifyOtp";
        }

        // OTP đúng: Cho phép reset mật khẩu
        return "redirect:/resetPassword";  // Chuyển đến trang resetPassword
    }
    
    @GetMapping("/resetPassword")
    public String showResetPasswordPage(HttpSession session, Model model) {
    	LOGGER.debug("------------------Calling api GET : /resetPassword");

        // Kiểm tra xem session có chứa thông tin OTP và email không
        String otp = (String) session.getAttribute("otp");
        String email = (String) session.getAttribute("otpEmail");

        // Nếu OTP hoặc email không tồn tại trong session, trả về trang forgotPassword
        if (otp == null || email == null) {
            model.addAttribute("error", "Đã xảy ra lỗi. Vui lòng thử lại.");
            return "authentication/forgotPassword";
        }

        // Nếu OTP và email hợp lệ, trả về trang resetPassword
        return "authentication/resetPassword";
    }


    
    @PostMapping("/resetPassword")
    public String resetPassword(
            @RequestParam("password") String password,
            @RequestParam("confirmPassword") String confirmPassword,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

    	LOGGER.debug("------------------Calling api POST: /resetPassword");
    	LOGGER.debug("password: {}", password);
    	LOGGER.debug("confirmPassword: {}", confirmPassword);
        // Kiểm tra nếu mật khẩu và xác nhận mật khẩu không khớp
        if (!password.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "Mật khẩu và xác nhận mật khẩu không khớp.");
            return "redirect:/resetPassword"; // 🔥 Redirect về form resetPassword, tránh lỗi reload form POST
        }

        // Lấy email từ session
        String email = (String) session.getAttribute("otpEmail");
        if (email == null) {
            redirectAttributes.addFlashAttribute("error", "Đã xảy ra lỗi. Vui lòng thử lại.");
            return "redirect:/forgotPassword"; // 🔥 Redirect về forgotPassword
        }

        // Tìm người dùng từ email
        User user = daoUser.findByEmail(email);
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy người dùng với email này.");
            return "redirect:/forgotPassword"; // 🔥 Redirect về forgotPassword
        }

        // Mã hóa mật khẩu mới
        String encodedPassword = passwordEncoder.encode(password);
    	LOGGER.debug("encodedPassword: {}", encodedPassword);

        // Cập nhật mật khẩu mới cho người dùng
        user.setPassword(encodedPassword);
        daoUser.update(user);  // Lưu người dùng với mật khẩu mới vào cơ sở dữ liệu

     // Xóa OTP và email khỏi session (không xóa toàn bộ session)
        session.removeAttribute("otp");
        session.removeAttribute("otpEmail");

        return "redirect:/login";
    }





  
}
