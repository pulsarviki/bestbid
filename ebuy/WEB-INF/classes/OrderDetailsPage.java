import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Map.Entry;


public class OrderDetailsPage extends HttpServlet {

  private PageContent contentManager = new PageContent();

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

  // protected void doPost(HttpServletRequest request,
	// 		HttpServletResponse response) throws ServletException, IOException {
	// 	HttpSession session = request.getSession();
	// 	HashMap<String, Product> cartproducts = (HashMap<String, Product>)session.getAttribute("cart");
  //   HashMap<String, String> cat_acc_link = (HashMap<String, String>)session.getAttribute("cat_acc_link");
	//   HashMap<String, Accessory> cartacc = (HashMap<String, Accessory>) session.getAttribute("cartacc");
  //
	// 	String act = request.getParameter("act");
	// 	String name = request.getParameter("name");
	// 	String priceString = request.getParameter("price");
	// 	String discountString = request.getParameter("discount");
  //   String rdiscountString = request.getParameter("rdiscount");
	// 	String warrantyString = request.getParameter("rwarranty");
  //   String categoryString = request.getParameter("category");
  //
	// 	if(act != null && act.equals("remove")) {
	// 		if(cartproducts == null || cartproducts.isEmpty()) {
	// 			cartproducts = new HashMap<String, Product>();
	// 		}
	// 		cartproducts.remove(name);
	// 		session.setAttribute("cart", cartproducts);
	// 		System.out.println("remove product");
  //
	// 	} else if (act != null && act.equals("removeacc")) {
  //     if(cat_acc_link == null || cat_acc_link.isEmpty()) {
	// 			cat_acc_link = new HashMap<String, String>();
	// 		}
	// 		if(cartacc == null || cartacc.isEmpty()) {
	// 			cartacc = new HashMap<String, Accessory>();
	// 		} else {
  //
	// 			String accname = request.getParameter("accname");
	// 			cartacc.remove(accname);
  //       cat_acc_link.remove(accname);
	// 		}
	// 		session.setAttribute("cartacc", cartacc);
  //     session.setAttribute("cat_acc_link", cat_acc_link);
	// 		System.out.println("remove accessory.");
  //
	// 	}  else if (act != null && act.equals("addacc")) {
  //     if(cat_acc_link == null || cat_acc_link.isEmpty()) {
	// 			cat_acc_link = new HashMap<String, String>();
	// 		}
	// 		if(cartacc == null || cartacc.isEmpty()) {
	// 			cartacc = new HashMap<String, Accessory>();
	// 		}
	// 		String accname = request.getParameter("accname");
	// 		double price = Double.parseDouble(priceString) ;
	// 		double discount = Double.parseDouble(discountString);
  //     cat_acc_link.put(accname,categoryString);
  //     session.setAttribute("cat_acc_link", cat_acc_link);
  //
	// 		Accessory a = new Accessory(accname,price, discount);
  //
	// 		 cartacc.put(accname, a);
  //
	// 		session.setAttribute("cartacc", cartacc);
	// 		System.out.println("Add acc");
  //
	// 	} else if (act != null && act.equals("removewarr")) {
  //     if(cat_acc_link == null || cat_acc_link.isEmpty()) {
	// 			cat_acc_link = new HashMap<String, String>();
	// 		}
  //     if(cartproducts == null || cartproducts.isEmpty()) {
	// 			cartproducts = new HashMap<String, Product>();
	// 		}
  //
	// 		double price = Double.parseDouble(priceString) ;
	// 		double discount = Double.parseDouble(discountString);
  //     double warranty = Double.parseDouble(warrantyString) ;
	// 		double rdiscount = Double.parseDouble(rdiscountString);
  //
  //     if(warranty>0)
  //     {
  //       session.setAttribute("oldwarrantyval"+name,warranty);
  //     }
  //
	// 		Product cartp = new Product(name, price, discount,categoryString,rdiscount,0);
  //
	// 		cartproducts.put(name, cartp);
	// 		session.setAttribute("cart", cartproducts);
  //
	// 	} else if (act != null && act.equals("addwarr")) {
  //     if(cat_acc_link == null || cat_acc_link.isEmpty()) {
	// 			cat_acc_link = new HashMap<String, String>();
	// 		}
  //     if(cartproducts == null || cartproducts.isEmpty()) {
	// 			cartproducts = new HashMap<String, Product>();
	// 		}
  //
	// 		double price = Double.parseDouble(priceString) ;
	// 		double discount = Double.parseDouble(discountString);
  //     double warranty = Double.parseDouble(warrantyString) ;
	// 		double rdiscount = Double.parseDouble(rdiscountString);
  //
  //     if(warranty<=0)
  //     {
  //       warranty = (double)session.getAttribute("oldwarrantyval"+name);
  //     }
  //
	// 		Product cartp = new Product(name, price, discount,categoryString,rdiscount,warranty);
  //     session.setAttribute("oldwarrantyval"+name,0);
	// 		cartproducts.put(name, cartp);
	// 		session.setAttribute("cart", cartproducts);
  //
	// 	}
  //   else {
	// 		if(cartproducts == null || cartproducts.isEmpty()) {
	// 			cartproducts = new HashMap<String, Product>();
	// 		}
  //
	// 		double price = Double.parseDouble(priceString) ;
	// 		double discount = Double.parseDouble(discountString);
  //     double warranty = Double.parseDouble(warrantyString) ;
	// 		double rdiscount = Double.parseDouble(rdiscountString);
  //
	// 		Product cartp = new Product(name, price, discount,categoryString,rdiscount,warranty);
	// 		cartproducts.put(name, cartp);
	// 		session.setAttribute("cart", cartproducts);
	// 	}
	// 	response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/CartPage"));
	// }


  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException
      {
      HashMap<String, Order> ordersFromDb = null;
      //ordersFromDb = (HashMap<String, Order>) contentManager.readFromFile("orders");
      ordersFromDb = Order.getAllOrders();

		  contentManager = new PageContent();
      PrintWriter out = response.getWriter();
		  HttpSession session = request.getSession();
      String orderid=request.getParameter("orderid");
      Order custOrder;

		  String usertype=(String)session.getAttribute("usertype");
			String username=(String)session.getAttribute("username");
			//contentManager.setContent("This is Home Page you are logged in! "+username+usertype);
		  contentManager.setHeader(usertype,username,contentManager.getProductsCount(session));

    //----------------------------Order Details------------------------------------

    String contentStr = "";

    if(ordersFromDb!=null || !ordersFromDb.isEmpty())
    {
      custOrder=ordersFromDb.get(orderid);
      if(custOrder!=null && usertype.equalsIgnoreCase("SALESMAN"))
      {
        contentStr=contentStr + "<form action=\"/ebuy/CheckoutPage?act=update\" method=\"post\" style=\"display:inline\">"+
        "<table>"+
        "  <tr>"+
        "    <th>Order Id:</th>"+
        "    <th>"+custOrder.getId()+"</th>"+
        "  </tr>"+
        "  <tr>"+
        "    <td>Billed To:</td>"+
        "    <td>"+custOrder.getBuyer()+"</td>"+
        "  </tr>"+
        "  <tr>"+
        "    <td>Status:</td>"+
        "    <td>"+
        "<select name=\"orderstatus\" value=\""+custOrder.getStatus()+"\">"+
        "<option value=\"PLACED\">PLACED</option>"+
        "<option value=\"PROCESSED\">PROCESSED</option>"+
        "<option value=\"DELIVERED\">DELIVERED</option>"+
        "<option value=\"CANCELLED\">CANCELLED</option>"+
        "</select>"+
        "  </tr>"+
        "  <tr>"+
        "    <td>Price Total:</td>"+
        "    <td><input type=\"text\" name=\"totalprice\" value= \""+custOrder.getPrice()+"\"></td>"+
        "  </tr>"+
        "  <tr>"+
        "    <td>Order Date:</td>"+
        "    <td>"+custOrder.getOrderDate()+"</td>"+
        "  </tr>"+
        "  <tr>"+
        "    <td>Description:</td>"+
        "    <td>"+custOrder.getDescription()+"</td>"+
        "  </tr>"+
        "  </table>";

        contentStr=contentStr + "  <input type=\"hidden\" name=\"orderid\" value= \""+custOrder.getId()+"\">"+
          "  <input type=\"hidden\" name=\"act\" value=\"remove\">"+
      "<button type=\"submit\" value=\"Delete Order\" style=\"margin-left:200px;\">Update Order</button> "+
            "</form>";

        contentStr=contentStr + "<form action=\"/ebuy/CheckoutPage?act=remove\" method=\"post\" style=\"display:inline\">"+
         "  <input type=\"hidden\" name=\"orderid\" value= \""+custOrder.getId()+"\">"+
          "  <input type=\"hidden\" name=\"act\" value=\"remove\">"+
      "<button type=\"submit\" value=\"Delete Order\" >Delete Order</button> "+
            "</form>";


      }
      else if(custOrder!=null)
      {

        contentStr = contentStr + "<table>"+
        "  <tr>"+
        "    <th>Order Id:</th>"+
        "    <th>"+custOrder.getId()+"</th>"+
        "  </tr>"+
        "  <tr>"+
        "    <td>Billed To:</td>"+
        "    <td>"+custOrder.getBuyer()+"</td>"+
        "  </tr>"+
        "  <tr>"+
        "    <td>Status:</td>"+
        "    <td>"+custOrder.getStatus()+"</td>"+
        "  </tr>"+
        "  <tr>"+
        "    <td>Price Total:</td>"+
        "    <td>"+custOrder.getPrice()+"</td>"+
        "  </tr>"+
        "  <tr>"+
        "    <td>Order Date:</td>"+
        "    <td>"+custOrder.getOrderDate()+"</td>"+
        "  </tr>"+
        "  <tr>"+
        "    <td>Description:</td>"+
        "    <td>"+custOrder.getDescription()+"</td>"+
        "  </tr>"+
        "  </table>";


        if(custOrder.getStatus().equalsIgnoreCase("PLACED"))
        {
          contentStr=contentStr + "<form action=\"/ebuy/CheckoutPage?act=cancel\" method=\"post\">"+
              "  <input type=\"hidden\" name=\"orderid\" value= \""+custOrder.getId()+"\">"+
            "  <input type=\"hidden\" name=\"act\" value=\"cancel\">"+
        "<button type=\"submit\" value=\"Cancel Order\" style=\"margin-left: 250px;\">Cancel Order</button> "+
              "</form>";
      }
      }
    }

//    String checkout_but = "  <button type = \"button\" style=\"float:right\" value= \"Checkout\" onclick=\"document.location.href='/ebuy/CheckoutPage';\">Cancel Order</button>";
    String headval= "<div class=\"other_menu\">Order Details</div>";
    contentManager.setContent(headval+contentStr);
    out.println(contentManager.getPageContent());
  }
}
