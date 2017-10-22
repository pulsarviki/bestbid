import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Map.Entry;
import java.text.SimpleDateFormat;
import java.text.DateFormat;


public class TrendingPage extends HttpServlet {

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

  HashMap<String, Integer> reviewHashmap1 = MongoDBDataStoreUtilities.topLikedProducts();
  HashMap<String, Integer> reviewHashmap2 = MongoDBDataStoreUtilities.topZipCodes();
  HashMap<String, Integer> reviewHashmap3 = MongoDBDataStoreUtilities.topProducts();

    String contentStr = "";
    	//prinProducttMap(orderproducts);

      contentStr=contentStr+"<div class=\"other_menu\">Top 5 most liked products</div>";

      contentStr = contentStr + "<table>"+
      "  <tr>"+
      "    <th>Product Name</th>"+
      "  </tr>";

      for(Entry<String, Integer> m :reviewHashmap1.entrySet()){
        System.out.println(m.getKey());
         contentStr = contentStr +
          "  <tr>"+
          "    <td><a href=\"/ebuy/ProductPage?productname="+m.getKey()+"\">"+m.getKey()+"</a></td>"+
          "  </tr>";

      }
      contentStr=contentStr+"</table><br/><br/>";


      contentStr=contentStr+"<div class=\"other_menu\">Top zip codes with most products sold</div>";

      contentStr = contentStr + "<table>"+
      "  <tr>"+
      "    <th>Zip Code</th>"+
      "  </tr>";

      for(Entry<String, Integer> m :reviewHashmap2.entrySet()){
        System.out.println(m.getKey());
         contentStr = contentStr +
          "  <tr>"+
          "    <td>"+m.getKey()+"</td>"+
          "  </tr>";

      }
      contentStr=contentStr+"</table><br/><br/>";

      contentStr=contentStr+"<div class=\"other_menu\">Top 5 most sold products</div>";

      contentStr = contentStr + "<table>"+
      "  <tr>"+
      "    <th>Product Name</th>"+
      "  </tr>";

      for(Entry<String, Integer> m :reviewHashmap3.entrySet()){
        System.out.println(m.getKey());

          contentStr = contentStr +
           "  <tr>"+
           "    <td><a href=\"/ebuy/ProductPage?productname="+m.getKey()+"\">"+m.getKey()+"</a></td>"+
           "  </tr>";

      }
      contentStr=contentStr+"</table><br/><br/>";


  contentManager.setHeader(usertype,username,contentManager.getProductsCount(session));
	contentManager.setContent(contentStr);

    out.println(contentManager.getPageContent());
  }
}
