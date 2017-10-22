import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Map.Entry;
import java.text.SimpleDateFormat;
import java.text.DateFormat;


public class DisplayReviews extends HttpServlet {

PageContent contentManager = new PageContent();


  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
    HttpSession session = request.getSession();
    PrintWriter out = response.getWriter();
    String usertype=(String)session.getAttribute("usertype");
    String username=(String)session.getAttribute("username");
    String productname = request.getParameter("productname");
    HashMap<String, Product> products = (HashMap<String, Product>) PageContent.readFromFile("products");
    Product cur_product=products.get(productname);
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    HashMap<String, Product> orderproducts = (HashMap<String, Product>) session.getAttribute("cart");
	HashMap<String, Accessory> cartacc = (HashMap<String, Accessory>)session.getAttribute("cartacc");
  HashMap<String, Users> usersFromDb = Users.loadUsers();
  Users curr_user = usersFromDb.get(username);

  HashMap<String, ArrayList<Review>> reviewHashmap = MongoDBDataStoreUtilities.selectReviewsDyanamically(true,productname);
  ArrayList<Review> arr = reviewHashmap.get(productname);


    String contentStr = "";
    	//prinProducttMap(orderproducts);

      contentStr=contentStr+"<div class=\"other_menu\">Product Reviews</div>";

      for (Review g: arr){

        contentStr = contentStr + "<table style=\"width:100%\">";
          contentStr = contentStr +
           "  <tr>"+
           "    <th style=\"width:50%\">Reviewer Name:</th>"+
           "    <td style=\"width:50%\">"+g.getUserName()+"</td>"+
           "  </tr>"+
           "  <tr>"+
           "    <th style=\"width:50%\">Review Date:</th>"+
           "    <td style=\"width:50%\">"+dateFormat.format(g.getReviewDate())+"</td>"+
           "  </tr>"+

           "  <tr>"+
           "    <th style=\"width:50%\">Review Rating:</th>"+
           "    <td style=\"width:50%\">"+g.getReviewRating()+"</td>"+
           "  </tr>"+

           "  <tr>"+
           "    <th style=\"width:50%\">Product Name:</th>"+
           "    <td style=\"width:50%\">"+g.getReviewText()+"</td>"+
           "  </tr>";

           contentStr=contentStr+"</table><br/><br/>";
      }


  contentManager.setHeader(usertype,username,contentManager.getProductsCount(session));
	contentManager.setContent(contentStr);

    out.println(contentManager.getPageContent());
  }
}
