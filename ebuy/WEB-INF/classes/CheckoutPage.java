import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Map.Entry;


public class CheckoutPage extends HttpServlet {

PageContent contentManager = new PageContent();

  protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HashMap<String, Users> usersFromDb = null;
		HashMap<String, Order> ordersFromDb = null;
    PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
    String usertype=(String)session.getAttribute("usertype");
    String username=(String)session.getAttribute("username");
		String act = request.getParameter("act");
    String orderid = request.getParameter("orderid");
    Order orderObj=null;
		Order orderFromSession = (Order)session.getAttribute(orderid);

		if(act != null && act.equals("remove")) {
			//ordersFromDb = (HashMap<String, Order>) contentManager.readFromFile("orders");
      ordersFromDb = Order.getAllOrders();

			if(ordersFromDb == null || ordersFromDb.isEmpty()) {
				ordersFromDb = new HashMap<String, Order>();
			} else {

				ordersFromDb.remove(orderid);
        Order.removeOrder(orderid);
			}
			//contentManager.writeToFile(ordersFromDb, "orders");

			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/OrdersPage"));
			System.out.println("Removed ordered product");

		} else if(act != null && act.equals("cancel")) {
			//ordersFromDb = (HashMap<String, Order>) contentManager.readFromFile("orders");
      ordersFromDb = Order.getAllOrders();

			if(ordersFromDb == null || ordersFromDb.isEmpty()) {
				ordersFromDb = new HashMap<String, Order>();
			} else {
				orderObj = ordersFromDb.get(orderid);
				orderObj.setStatus("CANCELLED");

				ordersFromDb.put(orderid, orderObj);
			}
			//contentManager.writeToFile(ordersFromDb, "orders");
      Order.updateOrderPriceStatus(orderObj.getId(),orderObj.getPrice(),orderObj.getStatus());

      contentManager.setHeader(usertype,username,contentManager.getProductsCount(session));
      contentManager.setContent("<br/><div style=\"padding-left:50px\">Your order is now "+orderObj.getStatus()+"!</div>");
      out.println(contentManager.getPageContent());
			System.out.println("Canceled ordered product");

		} else if(act != null && act.equals("update")) {
			String orderstatus = request.getParameter("orderstatus");
			//ordersFromDb = (HashMap<String, Order>) contentManager.readFromFile("orders");
      ordersFromDb = (HashMap<String, Order>) Order.getAllOrders();

			String totalpriceString = request.getParameter("totalprice");

			double totalprice = Double.parseDouble(totalpriceString) ;

			if(ordersFromDb == null || ordersFromDb.isEmpty()) {
				ordersFromDb = new HashMap<String, Order>();
			} else {
				orderObj = ordersFromDb.get(orderid);
				orderObj.setStatus(orderstatus);
				orderObj.setPrice(totalprice);
				ordersFromDb.put(orderid, orderObj);
			}
			//contentManager.writeToFile(ordersFromDb, "orders");
      Order.updateOrderPriceStatus(orderObj.getId(),orderObj.getPrice(),orderObj.getStatus());

			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/OrdersPage"));
			System.out.println("update ordered product");

		} else {

			String name = request.getParameter("name");
      String assumeduser = request.getParameter("assumeduser");
      System.out.println("Got Name "+name);
			String address = request.getParameter("address");
			String cardno = request.getParameter("cardno");
      System.out.println("Got Card "+cardno);
      Users userObj=null;
			String totalpriceString = request.getParameter("totalprice");

			double totalprice = Double.parseDouble(totalpriceString) ;

			//usersFromDb = (HashMap<String, Users>) contentManager.readFromFile("users");
      usersFromDb = Users.loadUsers();
      if(assumeduser==null){
			 userObj = usersFromDb.get(username);
      }
      else{
         userObj = usersFromDb.get(assumeduser);
      }
			// if(usersFromDb == null || usersFromDb.isEmpty()) {
			// 	usersFromDb = new HashMap<String, Users>();
			// 	userObj =new Users();
			// 	userObj.setName(name);
      //
			// } else {
			// 	userObj = usersFromDb.get(name);
			// 	if(userObj== null || usersFromDb.isEmpty()) {
			// 		userObj =new Users();
			// 		userObj.setName(name);
			// 	}
			// }

			userObj.setAddress(address);
			userObj.setCredNo(cardno);
			//prinUsertMap(usersFromDb);
      if(assumeduser==null){
			usersFromDb.put(username, userObj);
    }
    else{
      usersFromDb.put(assumeduser, userObj);
    }
			contentManager.setUsers(usersFromDb);

			//contentManager.writeToFile(usersFromDb, "users");
      Users.updateUserAddressAndCredNo(userObj.getName(),userObj.getAddress(),userObj.getCredNo());

			// ORDER
			//ordersFromDb = (HashMap<String, Order>) contentManager.readFromFile("orders");
      ordersFromDb = Order.getAllOrders();

			orderObj = null;

			if(ordersFromDb == null || ordersFromDb.isEmpty()) {
				ordersFromDb = new HashMap<String, Order>();
				orderObj = orderFromSession;
				// orderObj.setId(1);
				// ordername = ordername + "-"+ orderObj.getId();
				// orderObj.setName(ordername);

				// orderObj.setBuyer(name);
			} else {
				orderObj = ordersFromDb.get(orderid);
				if(orderObj== null || ordersFromDb.isEmpty()) {
					orderObj = orderFromSession;
					// orderObj.setId(ordersFromDb.size()+1);
					// ordername = ordername + "-"+ orderObj.getId();
          //
					// orderObj.setName(ordername);
					//
					// orderObj.setBuyer(name);
				} else {
					orderid = orderObj.getId();
				}

			}

			orderObj.setPrice(totalprice);
      orderObj.setOrderDate(new Date());
      if(assumeduser==null){
			orderObj.setBuyer(username);
    }
    else{
      orderObj.setBuyer(assumeduser);
    }

      orderObj.setShipName(name);
      orderObj.setShipAddress(address);
			ordersFromDb.put(orderid, orderObj);
			contentManager.setOrders(ordersFromDb);

			//contentManager.writeToFile(ordersFromDb, "orders");
      Order.insertOrder(orderObj);
      ordersFromDb = Order.getAllOrders();
      session.setAttribute("cart",null);
      session.setAttribute("cartacc",null);
      contentManager.setHeader(usertype,username,contentManager.getProductsCount(session));
    	contentManager.setContent("<br/><div>Your order is placed on "+orderObj.getOrderDate()+" ! Your confirmation number is: "+orderObj.getId()+"</div>");

      out.println(contentManager.getPageContent());
			//response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/CheckoutPage"));

		}
	}


  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
    HttpSession session = request.getSession();
    PrintWriter out = response.getWriter();
    String usertype=(String)session.getAttribute("usertype");
    String username=(String)session.getAttribute("username");

    HashMap<String, Product> orderproducts = (HashMap<String, Product>) session.getAttribute("cart");
	HashMap<String, Accessory> cartacc = (HashMap<String, Accessory>)session.getAttribute("cartacc");
  HashMap<String, Users> usersFromDb = Users.loadUsers();

    Order orderDetails= new Order();
    String ordername = "Order: ";
    double totalprice = 0.0;
    double currprice = 0.0;
    orderDetails.setId(Order.orderIdGenerator());

    String contentStr = "";
    if((orderproducts == null || orderproducts.isEmpty()) && (cartacc == null || cartacc.isEmpty())) {
    	System.out.println("NO product");
    } else {
      contentStr="<div class=\"other_menu\">Order Id Number: "+orderDetails.getId()+"</div>";
    	System.out.println("Session product");
    	//prinProducttMap(orderproducts);

    if(orderproducts != null && !orderproducts.isEmpty()) {
      contentStr = contentStr + "<table>"+
      "  <tr>"+
      "    <th>Item</th>"+
      "    <th>Price</th>"+
      "  </tr>";
    	for(Entry<String, Product> m :orderproducts.entrySet()){
		  	System.out.println(m.getKey());
		  	Product c = m.getValue();
        currprice = c.getPrice() - c.getDiscount()-c.getRdiscount() + c.getRwarranty();
        totalprice = totalprice + c.getPrice() - c.getDiscount()-c.getRdiscount() + c.getRwarranty();
		  		//		  		contentStr = contentStr +buildString(m, contentStr);
	  		 contentStr = contentStr +
          "  <tr>"+
          "    <td>"+c.getName()+"</td>"+
          "    <td>"+currprice+"</td>"+
          "  </tr>";
	  		 ordername = ordername + " :Product:: " + m.getKey();

	  	}

    }

    if(cartacc != null && !cartacc.isEmpty()) {

		for(Entry<String, Accessory> ma :cartacc.entrySet()){
			Accessory a = ma.getValue();
			currprice = a.getPrice() - a.getDiscount();
			totalprice = totalprice + a.getPrice() - a.getDiscount();
      ordername = ordername + " :Product:: " + a.getName();
      contentStr = contentStr +
       "  <tr>"+
       "    <td>"+a.getName()+"</td>"+
       "    <td>"+currprice+"</td>"+
       "  </tr>";

		}

    }
     orderDetails.setDescription(ordername);
    	orderDetails.setPrice(totalprice);

      contentStr = contentStr + "  <tr>"+
      "    <td><strong>Total</strong></td>"+
      "    <td><strong>"+totalprice+"</strong></td>"+
      "  </tr>"+
      "</table>";

      contentStr=contentStr+"<div class=\"other_menu\">Buyer details</div>";

      String userSelect = "";

      if(usertype.equalsIgnoreCase("SALESMAN")){
        userSelect="<span>Customer:</span>"+"<br/>";
      userSelect=userSelect+"<select name=\"assumeduser\" value=\"\">";
    for(Entry<String, Users> m :usersFromDb.entrySet()){
      userSelect=userSelect+"<option value=\""+m.getKey()+"\">"+m.getKey()+"</option>";
    }
    userSelect=userSelect+ "</select>";
      }

    	contentStr = contentStr +
    			"      <div id=\"container\">"+
    			"        <div id=\"wrapper\">"+
    			"        <div class=\"formcontainer\">"+
    			"          <div id=\"alert\"></div>"+
    						"<form action=\"/ebuy/CheckoutPage\" method=\"post\" class=\"bossform\">"+
                userSelect+
                "<br/>"+
    			"              <span>Name:</span>"+
          "<br/>"+
    			"              <input type=\"text\" name=\"name\"/>"+
          "<br/>"+

    			"              <span>Address:</span>"+
          "<br/>"+
    			"              <input type=\"text\" name=\"address\"/>"+
          "<br/>"+
    			"              <span>Card No:</span>"+
          "<br/>"+
    			"              <input type=\"text\" name=\"cardno\" />"+
          "<br/>"+
    						"  <input type=\"hidden\" name=\"orderid\" value= \""+orderDetails.getId()+"\">"+
					 		"  <input type=\"hidden\" name=\"totalprice\" value=\""+totalprice+"\">"+

    			"             <button type=\"submit\" value=\"Check Out\" style=\"float: right; margin-right: 90px;\" class=\"contact\">Check Out</button> "+
    						"</form>"+
    			"          </div>"+
    			"        </div>"+
    			"        </div>";


    }

    orderDetails.setProducts(orderproducts);
    orderDetails.setAccessories(cartacc);
    session.setAttribute(orderDetails.getId(),orderDetails);

  contentManager.setHeader(usertype,username,contentManager.getProductsCount(session));
	contentManager.setContent(contentStr);

    out.println(contentManager.getPageContent());
  }
}
