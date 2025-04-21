package interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HttpSession session = request.getSession(false);
        String uri = request.getRequestURI();

        // Bỏ qua các đường dẫn không cần kiểm tra (như login, static file)
        if (uri.contains("/login") || uri.contains("/register") || uri.contains("/resources") || uri.contains("/css") || uri.contains("/js")) {
            return true;
        }

        if (session != null && session.getAttribute("loggedInUser") != null) {
            return true; // đã login
        }

        response.sendRedirect(request.getContextPath() + "/login");
        return false;
    }
}
