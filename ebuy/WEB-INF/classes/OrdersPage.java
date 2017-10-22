import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Map.Entry;
import javax.xml.parsers.ParserConfigurationException;
import java.net.URL;
import org.xml.sax.SAXException;

public class OrdersPage extends HttpServlet {

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


	public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {

		  PageContent contentManager = new PageContent();

		  HttpSession session = request.getSession();
      String contentStr="";
		  String usertype=(String)session.getAttribute("usertype");
			String username=(String)session.getAttribute("username");
			//contentManager.setContent("This is Home Page you are logged in! "+username+usertype);
      System.out.println("username is "+username);
		  contentManager.setHeader(usertype,username,contentManager.getProductsCount(session));

			//---------Product Display-------------

			PrintWriter out = response.getWriter();
			//HashMap<String, Order> ordersFromDb = (HashMap<String, Order>) PageContent.readFromFile("orders");
			HashMap<String, Order> ordersFromDb = (HashMap<String, Order>) Order.getAllOrders();
			//prinProducttMap(products);

      if(ordersFromDb != null && !ordersFromDb.isEmpty()) {
        contentStr = contentStr + "<table>"+
        "  <tr>"+
        "    <th>Order Id</th>"+
        "    <th>Order Date</th>"+
        "    <th>Order Status</th>"+
        "  </tr>";
        for(Entry<String, Order> m :ordersFromDb.entrySet()){
          System.out.println(m.getKey());
          Order o = m.getValue();
          System.out.println("Name "+o.getShipName());
          //currprice = c.getPrice() - c.getDiscount()-c.getRdiscount() + c.getRwarranty();
          //totalprice = totalprice + c.getPrice() - c.getDiscount()-c.getRdiscount() + c.getRwarranty();
            //		  		contentStr = contentStr +buildString(m, contentStr);
            if(o.getBuyer().equalsIgnoreCase(username)){
           contentStr = contentStr +
            "  <tr>"+
            "    <td><a href=\"/ebuy/OrderDetailsPage?orderid="+o.getId()+"\">"+o.getId()+"</a></td>"+
            "    <td>"+o.getOrderDate()+"</td>"+
            "    <td>"+o.getStatus()+"</td>"+
            "  </tr>";
         }
				 else if(usertype.equalsIgnoreCase("SALESMAN"))
				 {
					 contentStr = contentStr +
            "  <tr>"+
            "    <td><a href=\"/ebuy/OrderDetailsPage?orderid="+o.getId()+"\">"+o.getId()+"</a></td>"+
            "    <td>"+o.getOrderDate()+"</td>"+
            "    <td>"+o.getStatus()+"</td>"+
            "  </tr>";
				 }
        }
        contentStr=contentStr+"</table>";

      }


      if((ordersFromDb == null || ordersFromDb.isEmpty() || contentStr.equalsIgnoreCase("")))
      {
        contentManager.setContent("<br/><div style=\"padding-left:50px\">There are no orders placed! Please place an order first!</div>") ;
      }
      else{
        contentStr="<div class=\"other_menu\">Orders for your account</div>"+contentStr;
      }

      contentManager.setContent(contentStr);
      out.println(contentManager.getPageContent());

	  }


}
