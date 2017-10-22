import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Map.Entry;
import javax.xml.parsers.ParserConfigurationException;
import java.net.URL;
import org.xml.sax.SAXException;

public class RetailerUpdate extends HttpServlet {

	private PageContent contentManager;

	public void init() throws ServletException {


  }

	public void prinProducttMap(HashMap<String, Product> mapInFile) throws ServletException {

      for(Entry<String, Product> m :mapInFile.entrySet()){
			  	System.out.println(m.getKey());
			Product c = m.getValue();
			System.out.println("\t Name : "+c.getName());
			System.out.println("\t Price : "+c.getPrice());
			System.out.println("\t Accessories : ");
			HashMap<String,Accessory> accessories = c.getAccessories();
			for(Entry<String, Accessory> ma :accessories.entrySet()){
				System.out.println("\t\t\t" + ma.getKey());
				Accessory a = ma.getValue();
				System.out.println("\t\t\t\t Name : "+a.getName());
				System.out.println("\t\t\t\t Price : "+a.getPrice());
			}

	   }
	 }

   public void doPost(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {

        contentManager = new PageContent();

       HttpSession session = request.getSession();

        HashMap<String, Product> products = (HashMap<String, Product>) PageContent.readFromFile("products");
       String usertype=(String)session.getAttribute("usertype");
       String username=(String)session.getAttribute("username");
        contentManager.setHeader(usertype,username,contentManager.getProductsCount(session));
        String act=request.getParameter("act");
        PrintWriter out= response.getWriter();

        if(act.equalsIgnoreCase("update"))
        {
          String productname=request.getParameter("productname");
          String rwarranty=request.getParameter("rwarranty");
          Product curr_prod=products.get(productname);
          curr_prod.setRwarranty(Double.parseDouble(rwarranty));
          products.put(productname,curr_prod);
          PageContent.writeToFile(products, "products");
          response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/RetailerUpdate?act=success"));
        }

      }


	public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {

		  PageContent contentManager = new PageContent();

		  HttpSession session = request.getSession();
      String contentStr="";
		  String usertype=(String)session.getAttribute("usertype");
			String username=(String)session.getAttribute("username");
      String act=request.getParameter("act");
      String cat=request.getParameter("cat");
			//contentManager.setContent("This is Home Page you are logged in! "+username+usertype);
      System.out.println("username is "+username);
		  contentManager.setHeader(usertype,username,contentManager.getProductsCount(session));

			//---------Product Display-------------

			PrintWriter out = response.getWriter();
			HashMap<String, Product> products = (HashMap<String, Product>) PageContent.readFromFile("products");
			//prinProducttMap(products);

      if(products != null && !products.isEmpty()) {
        contentStr = contentStr + "<form action=\"/ebuy/RetailerUpdate?act=update\" method=\"post\" class=\"bossform\">"+
        "<table>"+
        "  <tr>"+
        "    <th>Product Name</th>"+
        "    <th>Warranty</th>"+
        "  </tr>";

           contentStr = contentStr +
            "  <tr>"+
            "    <td>"+
            "<span>Product:</span>"+
              "<select name=\"productname\" value=\"\">";
            for(Entry<String, Product> m :products.entrySet()){
              if(cat!=null && cat.equalsIgnoreCase(m.getValue().getCategory())){
              contentStr=contentStr+"<option value=\""+m.getKey()+"\">"+m.getKey()+"</option>";
            }
            else if(cat==null)
            {
              contentStr=contentStr+"<option value=\""+m.getKey()+"\">"+m.getKey()+"</option>";
            }
            }
            contentStr=contentStr+ "</select>"+
            "</td>"+
            "    <td><input type=\"text\" name=\"rwarranty\" value=\"\"></td>"+
            "  </tr>";
        contentStr=contentStr+"</table><button type=\"submit\" value=\"Update\" style=\"float: right; margin-right: 270px;\" >Update</button></form>";

      }


      if((products == null || products.isEmpty() ))
      {
        contentManager.setContent("<br/><div style=\"padding-left:50px\">There are no products! Please add a product first!</div>") ;
      }
      else{
        contentStr="<div class=\"other_menu\">Products</div>"+contentStr;
      }

      if(act!=null && act.equalsIgnoreCase("success"))
      {
        contentStr=contentStr+"<br/>Warranty updated successfully!";
      }

      contentManager.setContent(contentStr);
      out.println(contentManager.getPageContent());

	  }


}
