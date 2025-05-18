package interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.User;
import controller.LoginController;
import dao.DaoUser;
import utils.JwtUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
public class AuthInterceptor implements HandlerInterceptor {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthInterceptor.class);


    @Autowired
    private DaoUser daoUser;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String uri = request.getRequestURI();
        LOGGER.debug("-- URI: {}", uri );

        // Bỏ qua các URI không cần bảo vệ
        if (uri.contains("/login") || uri.contains("/register") || uri.contains("/resources")
        		|| uri.contains("/forgotPassword")
        	    || uri.contains("/authentication/forgotPassword")
        		|| uri.contains("/resetPassword")  
        	    || uri.contains("/resetPasswordSuccess")  // 👈 THÊM DÒNG NÀY
        	    || uri.contains("/verifyOtp")  
                || uri.contains("/css") || uri.contains("/js") || uri.contains("/refresh-token")) {
        	LOGGER.debug("True1");
            return true;
        }

     // Lấy accessToken từ Cookie
        String accessToken = getCookieValue(request, "accessToken");
        LOGGER.debug("--accessToken: {}", accessToken);
        if (accessToken != null && JwtUtil.validateToken(accessToken)) {
            // Token hợp lệ, giải mã để lấy userId
            String userId = JwtUtil.getUserIdFromToken(accessToken);
            if (userId != null) {
                User user = daoUser.getUserById(userId);
                if (user != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("loggedInUser", user);
                    session.setAttribute("role", user.getRole());
                    LOGGER.debug("User info set in session: {}", user.getUsername());
                }
            }
            LOGGER.debug("True2");
            return true;
        }

        // Nếu accessToken không hợp lệ, thử dùng refreshToken
        String refreshToken = getCookieValue(request, "refreshToken");
        LOGGER.debug("--refreshToken: {}", refreshToken);

        if (refreshToken != null) {
            // Gọi nội bộ endpoint để làm mới token
            // Cách đơn giản nhất là redirect qua /refresh-token (để controller xử lý)
            response.sendRedirect(request.getContextPath() + "/refresh-token?redirect=" + uri);
            return false;
        }

        // Không có token nào hợp lệ => về login
        response.sendRedirect(request.getContextPath() + "/login");
        return false;
    }

    private String getCookieValue(HttpServletRequest request, String name) {
        if (request.getCookies() == null) return null;
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals(name)) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
