import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Map.Entry;


public class MyAccountPage extends HttpServlet {

  private PageContent contentManager;
  private HashMap<String, Users> users;

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
      System.out.println("\t type : "+c.getAddress());
      System.out.println("\t type : "+c.getCredNo());
    }
  }

  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException
      {

        PrintWriter out = response.getWriter();
        response.setContentType("text/html");

		  HttpSession session = request.getSession();
      String contentStr="";
		  String usertype=(String)session.getAttribute("usertype");
			String username=(String)session.getAttribute("username");
			//contentManager.setContent("This is Home Page you are logged in! "+username+usertype);
		  contentManager.setHeader(usertype,username,contentManager.getProductsCount(session));

      Users curr_user=users.get(username);
      prinUsertMap(users);
    //----------------------------Users------------------------------------

    if(curr_user != null || !users.isEmpty()) {

        // for(Entry<String, Accessory> ma :c.getAccessories().entrySet()){
	 		// 			Accessory a = ma.getValue();
        //     cat_acc_link.put(a.getName(),c.getCategory());
        // }
		  		//		  		contentStr = contentStr +buildString(m, contentStr);

        contentStr = contentStr + "<table>"+
        "  <tr>"+
        "    <td>Username:</td>"+
        "    <td>"+curr_user.getName()+"</td>"+
        "  </tr>"+
        "  <tr>"+
        "    <td>Usertype:</td>"+
        "    <td>"+curr_user.getUtype()+"</td>"+
        "  </tr>"+
        "  <tr>"+
        "    <td>Address:</td>"+
        "    <td>"+((curr_user.getAddress() == null) ? "" : curr_user.getAddress())+"</td>"+
        "  </tr>"+
        "  <tr>"+
        "    <td>Credit Card:</td>"+
        "    <td>"+((curr_user.getCredNo() == null) ? "" : curr_user.getCredNo())+"</td>"+
        "  </tr>"+
        "  </table>";


            String headval= "<div class=\"other_menu\">Account Information</div>";
            contentManager.setContent(headval+contentStr);
            out.println(contentManager.getPageContent());
	  	}




  }
}
