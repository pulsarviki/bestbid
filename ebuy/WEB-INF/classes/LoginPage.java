import java.io.*;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.servlet.*;
import javax.servlet.http.*;

public class LoginPage extends HttpServlet {

  private PageContent contentManager;
  private HashMap<String, Users> users;
  //private  HashMap<String, User> usersFromDb = new HashMap<String, User>();

  public void init() throws ServletException {
    users = Users.loadUsers();
    contentManager = new PageContent();
  }

  public void prinUsertMap(HashMap<String, Users> mapInFile) throws ServletException {

	  for(Entry<String, Users> m :mapInFile.entrySet()){
		    System.out.println(m.getKey());
			Users c = m.getValue();
			System.out.println("\t Name : "+c.getName());
			System.out.println("\t type : "+c.getUtype());
	  }
  }


	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

        String user = request.getParameter("username");
		    String pwd = request.getParameter("password");
        String utype = request.getParameter("utype");
        users = Users.loadUsers();
        prinUsertMap(users);

        Users userObj = users.get(user);
        System.out.println("Entering  if -----");
        if(!users.containsKey(user)) {
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/RegisterPage?message=User not found, Try Again!"));
        	} else if(!(userObj.getPassword()).equals(pwd)) {
        		response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/LoginPage?message=Incorrect password, Try Again!."));
        	} else if(!(userObj.getUtype()).equals(utype)) {
        		response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/LoginPage?message=Incorrect user type, Try Again!."));
        	} else {
            System.out.println("Entering  else -----");
      			HttpSession session = request.getSession();
      			session.setAttribute("username", user);
      			session.setAttribute("utype", utype);

      			// Configure: setting session to expiry in 30 mins
      			// session.setMaxInactiveInterval(30*60);
      			// Cookie userName = new Cookie("user", user);
      			// userName.setMaxAge(30*60);
      			// response.addCookie(userName);
            System.out.println("Redirecting  now-----");
            if(utype.equalsIgnoreCase("BUYER"))
            {
              response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/HomePage"));
            }
            else if(utype.equalsIgnoreCase("SELLER"))
            {
              response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/ManageProducts"));
            }
            else if(utype.equalsIgnoreCase("ADMIN"))
            {
              response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/RetailerUpdate"));
            }
            else{
              System.out.println("Redirected now-----");
		        }
          }

	}

  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {

	PrintWriter out = response.getWriter();
  response.setContentType("text/html");
  contentManager = new PageContent();

  String message = request.getParameter("message");
  if(message==null){
    message="";
  }
	System.out.println("----------------x-------------" + message);

  String adhoc_content = "<div id=\"content\">"+
	"<div class=\"login\">"+
	"<h1>Please login to continue.</h1>"+
	"<p style=\"color:red\">"+message+"</p>"+
	"<form action=\"LoginPage\" method=\"post\">"+
    		"<div class=\"input\">"+
		"      <div class=\"blockinput\">"+
		"        <i class=\"icon-envelope-alt\"></i><input type=\"text\" name=\"username\" placeholder=\"Username\">"+
		"      </div><br>"+
		"      <div class=\"blockinput\">"+
		"        <i class=\"icon-unlock\"></i><input type=\"password\" placeholder=\"Password\" name=\"password\">"+
		"      </div><br>"+

		"      <div class=\"blockinput\">"+
		"        <i class=\"icon-unlock\"></i>"+
				"<select name=\"utype\">"+
				"    <option value=\"BUYER\">BUYER</option>"+
				"    <option value=\"SELLER\">SELLER</option>"+
				"    <option value=\"ADMIN\">ADMIN</option>"+
				"  </select>"+
		"      </div>"+

		"    </div>"+
		"<button type=\"submit\" value=\"Login\">Login</button>"+
		"<br/>"+
		"<a href=\"#\">Forgot Password?</a>"+
	"  </form></div>"+
	"        </div>";

  contentManager.setContent(adhoc_content);
  //Send error message as parameter for incorrect username,passord or user type
    out.println(contentManager.getPageContent());
  }
}
