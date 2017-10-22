import java.io.IOException;

import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutPage extends HttpServlet {

          private PageContent contentManager;

          public void init() throws ServletException {
            contentManager = new PageContent();
          }

          protected void doGet(HttpServletRequest request, HttpServletResponse response)
                                throws ServletException, IOException {
            response.setContentType("text/html");
            PrintWriter out=response.getWriter();
            HttpSession session=request.getSession();
            session.invalidate();
            contentManager.setContent("You are successfully logged out!");
            // out.print("<b>You are successfully logged out!<b>");
            // out.print("<br><br><u><a href=\"/csj/home\">Go To Home Page</a></u>");
            out.println(contentManager.getPageContent());
            out.close();

    }
}
