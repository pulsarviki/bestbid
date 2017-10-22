import java.io.*;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.servlet.*;
import javax.servlet.http.*;

public class RegisterPage extends HttpServlet {

  private PageContent toolkit;
  private  HashMap<String, Users> userslist;

  public void init() throws ServletException {
    toolkit = new PageContent();
    //userslist = (HashMap<String, Users>) PageContent.readFromFile("users");
    userslist = Users.loadUsers();
  }

  protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// get request parameters for userID and password
		String user = request.getParameter("username");
		String pwd = request.getParameter("password");
		String rpwd = request.getParameter("rpassword");
		String usertype = request.getParameter("utype");
    String address = request.getParameter("address");
    String zip = request.getParameter("zip");
    String credno = request.getParameter("credno");
    String credexp = request.getParameter("credexp");
    String credcvv = request.getParameter("credcvv");
    String act = request.getParameter("act");
		System.out.println("YOO.." + user + pwd);

    if(userslist == null || userslist.isEmpty()) {
      userslist = new HashMap<String, Users>();
    }

    if(act!=null && act.equalsIgnoreCase("CreateUser")){
      if(!pwd.equals(rpwd) ) {
  			System.out.println("Password mismatch..");
  			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/RegisterPage?"+"password-mismatch-TRY-AGAIN"));
  		} else if(user == null || user == "") {
  			System.out.println("Username required..");
  			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/RegisterPage?"+"Username-required-TRY-AGAIN"));
  		} else if(userslist.get(user)!=null) {
  			System.out.println("Username exists..");
  			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/RegisterPage?"+"Username-exists-TRY-AGAIN"));
  		} else {
  			System.out.println("User-registered-WELCOME..");
  			Users userObj = new Users(user, pwd);
  			userObj.setName(user);
  			userObj.setUtype(usertype);
        userObj.setAddress(address);
        userObj.setPassword(pwd);
        userObj.setCredExp(credexp);
        userObj.setcredCvv(credcvv);
        userObj.setZip(zip);

        userslist.put(user,userObj);
        //Users.dumpUsers(userslist);
        Users.insertUser(userObj);
  			userslist = Users.loadUsers();
        response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/HomePage"));
    }
  }
    else{
		if(!pwd.equals(rpwd) ) {
			System.out.println("Password mismatch..");
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/RegisterPage?"+"password-mismatch-TRY-AGAIN"));
		} else if(user == null || user == "") {
			System.out.println("Username required..");
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/RegisterPage?"+"Username-required-TRY-AGAIN"));
		} else if(userslist.get(user)!=null) {
			System.out.println("Username exists..");
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/RegisterPage?"+"Username-exists-TRY-AGAIN"));
		} else {
			System.out.println("User-registered-WELCOME..");
			Users userObj = new Users(user, pwd);
      userObj.setName(user);
      userObj.setUtype(usertype);
      userObj.setAddress(address);
      userObj.setPassword(pwd);
      userObj.setCredExp(credexp);
      userObj.setcredCvv(credcvv);
      userObj.setZip(zip);

      userslist.put(user,userObj);
      Users.insertUser(userObj);
      //Users.dumpUsers(userslist);

			userslist = Users.loadUsers();

      HttpSession session = request.getSession();
      session.setAttribute("username", user);
      session.setAttribute("utype", usertype);

      // Configure: setting session to expiry in 30 mins
      // session.setMaxInactiveInterval(30*60);
      // Cookie userName = new Cookie("user", user);
      // userName.setMaxAge(30*60);
      // response.addCookie(userName);

      if(usertype.equalsIgnoreCase("BUYER"))
      {
        response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/HomePage"));
      }
      else if(usertype.equalsIgnoreCase("SELLER"))
      {
        response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/HomePage"));
      }
      else if(usertype.equalsIgnoreCase("ADMIN"))
      {
        response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/HomePage"));
      }
		}
  }

	}

  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
	PrintWriter out = response.getWriter();
	//store.setBasicWithCSS("login");
	String message = request.getParameter("message");
  String act = request.getParameter("act");
  HttpSession session = request.getSession();
  String usertype=(String)session.getAttribute("usertype");
  String username=(String)session.getAttribute("username");
  //contentManager.setContent("This is Home Page you are logged in! "+username+usertype);
  if(username!=null && usertype!=null)
  {
  toolkit.setHeader(usertype,username,toolkit.getProductsCount(session));
  }

  if(act==null)
  {
    act = "";
  }

  if(message==null){
    message="";
  }

  System.out.println("-----------------------------" + message);

	String content = "<div class=\"ribbon\"></div><div class=\"login\">"+
	"<h1>New User?</h1>"+
  	"<p style=\"color:red\">"+message+"</p>"+
	"<form action=\"RegisterPage\" method=\"post\">"+
    		"<div class=\"input\">"+
        "      <div class=\"blockinput\">"+
    		"        <i class=\"icon-envelope-alt\"></i><input type=\"text\" name=\"username\" placeholder=\"Username\">"+
    		"      </div>"+
        "      <div class=\"blockinput\">"+
    		"        <i class=\"icon-unlock\"></i><input type=\"Password\" placeholder=\"Password\" name=\"password\">"+
    		"      </div>"+
    		"      <div class=\"blockinput\">"+
    		"        <i class=\"icon-unlock\"></i><input type=\"password\" placeholder=\"Reenter Password\" name=\"rpassword\">"+
    		"      </div><br><br>"+
        // "      <div class=\"blockinput\">"+
    		// "        <i class=\"icon-envelope-alt\"></i><input type=\"text\" name=\"credno\" placeholder=\"Cred. Card No\">"+
    		// "      </div>"+
        // "      <div class=\"blockinput\">"+
    		// "        <i class=\"icon-envelope-alt\"></i><input type=\"text\" name=\"credcvv\" placeholder=\"Cred. Card Cvv\">"+
    		// "      </div>"+
        // "      <div class=\"blockinput\">"+
    		// "        <i class=\"icon-envelope-alt\"></i><input type=\"text\" name=\"credexp\" placeholder=\"Cred. Card Exp\">"+
    		// "      </div>"+
		"      <div class=\"blockinput\">"+
		"        <textarea rows=\"4\" class=\"icon-envelope-alt\" name=\"address\" placeholder=\"Address\"></textarea>"+
		"      </div>"+
		"      <div class=\"blockinput\">"+
		"        <i class=\"icon-unlock\"></i><input type=\"text\" placeholder=\"zip\" name=\"Zip\">"+
		"      </div>"+

    "  <input type=\"hidden\" name=\"act\" value= \""+act+"\">"+
		"      <div class=\"blockinput\">"+
		"        <i class=\"icon-unlock\"></i>"+
				"<select name=\"utype\">"+
        "    <option value=\"BUYER\">BUYER</option>"+
				"    <option value=\"SELLER\">SELLER</option>"+
				"    <option value=\"ADMIN\">ADMIN</option>"+
				"  </select>"+
		"      </div>"+
		"    </div>"+
		"<button type=\"submit\" value=\"Register\">Register</button>"+
	"  </form></div>";

	// HttpSession session = request.getSession();
	// String usertype = (String)session.getAttribute("usertype");
	//
	// store.setHeader(usertype);
    toolkit.setContent(content);
    out.println(toolkit.getPageContent());
  }
}
