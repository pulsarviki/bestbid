import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import javax.servlet.annotation.WebFilter;

@WebFilter("/*")
public class LoginFilter implements Filter {

	public void init(FilterConfig arg0) throws ServletException {}

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);
        String loginURI = request.getContextPath() + "/LoginPage";
				String registerURI = request.getContextPath() + "/RegisterPage";
				String homeURI = request.getContextPath() + "/HomePage";

        boolean loggedIn = session != null && session.getAttribute("username") != null;
        boolean loginRequest = request.getRequestURI().equals(loginURI);
				boolean registerRequest = request.getRequestURI().equals(registerURI);

				if (loggedIn || loginRequest || registerRequest) {
            chain.doFilter(request, response);
        } else {
            response.sendRedirect(loginURI);
        }
    }

	public void destroy() {}

}
