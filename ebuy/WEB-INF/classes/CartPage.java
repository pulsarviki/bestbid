import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Map.Entry;


public class CartPage extends HttpServlet {

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

  protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// HttpSession session = request.getSession();
		// HashMap<String, Product> cartproducts = (HashMap<String, Product>)session.getAttribute("cart");
    // HashMap<String, String> cat_acc_link = (HashMap<String, String>)session.getAttribute("cat_acc_link");
	  // HashMap<String, Accessory> cartacc = (HashMap<String, Accessory>) session.getAttribute("cartacc");
    // System.out.println("hello doPost cart");
		// String act = request.getParameter("act");
		// String name = request.getParameter("name");
		// String priceString = request.getParameter("price");
		// String discountString = request.getParameter("discount");
    // String rdiscountString = request.getParameter("rdiscount");
		// String warrantyString = request.getParameter("rwarranty");
    // String categoryString = request.getParameter("category");
    //
		// if(act != null && act.equals("remove")) {
		// 	if(cartproducts == null || cartproducts.isEmpty()) {
		// 		cartproducts = new HashMap<String, Product>();
		// 	}
		// 	cartproducts.remove(name);
		// 	session.setAttribute("cart", cartproducts);
		// 	System.out.println("remove product");
    //
		// } else if (act != null && act.equals("removeacc")) {
    //   if(cat_acc_link == null || cat_acc_link.isEmpty()) {
		// 		cat_acc_link = new HashMap<String, String>();
		// 	}
		// 	if(cartacc == null || cartacc.isEmpty()) {
		// 		cartacc = new HashMap<String, Accessory>();
		// 	} else {
    //
		// 		String accname = request.getParameter("accname");
		// 		cartacc.remove(accname);
    //     cat_acc_link.remove(accname);
		// 	}
		// 	session.setAttribute("cartacc", cartacc);
    //   session.setAttribute("cat_acc_link", cat_acc_link);
		// 	System.out.println("remove accessory.");
    //
		// }  else if (act != null && act.equals("addacc")) {
    //   if(cat_acc_link == null || cat_acc_link.isEmpty()) {
		// 		cat_acc_link = new HashMap<String, String>();
		// 	}
		// 	if(cartacc == null || cartacc.isEmpty()) {
		// 		cartacc = new HashMap<String, Accessory>();
		// 	}
		// 	String accname = request.getParameter("accname");
		// 	double price = Double.parseDouble(priceString) ;
		// 	double discount = Double.parseDouble(discountString);
    //   cat_acc_link.put(accname,categoryString);
    //   session.setAttribute("cat_acc_link", cat_acc_link);
    //
		// 	Accessory a = new Accessory(accname,price, discount);
    //
		// 	 cartacc.put(accname, a);
    //
		// 	session.setAttribute("cartacc", cartacc);
		// 	System.out.println("Add acc");
    //
		// } else if (act != null && act.equals("removewarr")) {
    //   if(cat_acc_link == null || cat_acc_link.isEmpty()) {
		// 		cat_acc_link = new HashMap<String, String>();
		// 	}
    //   if(cartproducts == null || cartproducts.isEmpty()) {
		// 		cartproducts = new HashMap<String, Product>();
		// 	}
    //
		// 	double price = Double.parseDouble(priceString) ;
		// 	double discount = Double.parseDouble(discountString);
    //   double warranty = Double.parseDouble(warrantyString) ;
		// 	double rdiscount = Double.parseDouble(rdiscountString);
    //
    //   if(warranty>0)
    //   {
    //     session.setAttribute("oldwarrantyval"+name,warranty);
    //   }
    //
		// 	Product cartp = new Product(name, price, discount,categoryString,rdiscount,0);
    //
		// 	cartproducts.put(name, cartp);
		// 	session.setAttribute("cart", cartproducts);
    //
		// } else if (act != null && act.equals("addwarr")) {
    //   if(cat_acc_link == null || cat_acc_link.isEmpty()) {
		// 		cat_acc_link = new HashMap<String, String>();
		// 	}
    //   if(cartproducts == null || cartproducts.isEmpty()) {
		// 		cartproducts = new HashMap<String, Product>();
		// 	}
    //
		// 	double price = Double.parseDouble(priceString) ;
		// 	double discount = Double.parseDouble(discountString);
    //   double warranty = Double.parseDouble(warrantyString) ;
		// 	double rdiscount = Double.parseDouble(rdiscountString);
    //
    //   if(warranty<=0)
    //   {
    //     warranty = (double)session.getAttribute("oldwarrantyval"+name);
    //   }
    //
		// 	Product cartp = new Product(name, price, discount,categoryString,rdiscount,warranty);
    //   session.setAttribute("oldwarrantyval"+name,0);
		// 	cartproducts.put(name, cartp);
		// 	session.setAttribute("cart", cartproducts);
    //
		// }
    // else {
		// 	if(cartproducts == null || cartproducts.isEmpty()) {
		// 		cartproducts = new HashMap<String, Product>();
		// 	}
    //
		// 	double price = Double.parseDouble(priceString) ;
		// 	double discount = Double.parseDouble(discountString);
    //   double warranty = Double.parseDouble(warrantyString) ;
		// 	double rdiscount = Double.parseDouble(rdiscountString);
    //
		// 	Product cartp = new Product(name, price, discount,categoryString,rdiscount,warranty);
		// 	cartproducts.put(name, cartp);
		// 	session.setAttribute("cart", cartproducts);
		// }


    String bid_value = request.getParameter("bid_value");
    int product_id = Integer.parseInt(request.getParameter("product_id"));
    System.out.println("value of int "+product_id);
    Product.updateBidValue(bid_value, product_id);

		response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/HomePage?Bid-Value-Set"));
	}


  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException
      {
      System.out.println("hello   doget cart");
		  contentManager = new PageContent();
      PrintWriter out = response.getWriter();
		  HttpSession session = request.getSession();

		  String usertype=(String)session.getAttribute("utype");
			String username=(String)session.getAttribute("username");
			contentManager.setContent("This is Home Page you are logged in! "+username+usertype);
		  contentManager.setHeader(usertype,username,contentManager.getProductsCount(session));

    //----------------------------Cart Items=------------------------------------

    // HashMap<String, Product> cartproducts = (HashMap<String, Product>) session.getAttribute("cart");
    // HashMap<String, Accessory> cartacc = (HashMap<String, Accessory>) session.getAttribute("cartacc");
    // HashMap<String, String> cat_acc_link = (HashMap<String, String>)session.getAttribute("cat_acc_link");
    String product_id = request.getParameter("product_id");
    HashMap<String, Product> m = (HashMap<String, Product>) Product.loadProducts();
    System.out.println("vaue of hashmap products "+m);
    String contentStr = "";



    if(m == null || m.isEmpty()) {
    	System.out.println("No products found");
    } else {
    	System.out.println("Session product");

      Product c = m.get(product_id);
      System.out.println("product details "+c);
    	// prinProducttMap(cartproducts);
      //
    	// for(Entry<String, Product> m :cartproducts.entrySet()){
		  // 	System.out.println(m.getKey());

        // for(Entry<String, Accessory> ma :c.getAccessories().entrySet()){
	 		// 			Accessory a = ma.getValue();
        //     cat_acc_link.put(a.getName(),c.getCategory());
        // }
		  		//		  		contentStr = contentStr +buildString(m, contentStr);

        contentStr = contentStr +
        "<div class=\"eStore-product\">"+
        "   <div class=\"eStore-thumbnail\">"+
        "   <a href=\"/ProductPage?productname="+c.getId()+"\" rel=\"lightbox["+c.getCategory()+"]\" title=\""+c.getCategory()+"\">"+
        "	<img class=\"thumb-image\" src=\"/images/"+c.getCategory()+".jpg\" alt=\""+c.getCategory()+"\">"+
        "	</a>"+
        "	</div>"+
        "   <div class=\"eStore-product-description\">"+
        "      <div class=\"eStore-product-name\"><strong>Product</strong>:<a href=\"/ebuy/ProductPage?productname="+c.getId()+"\"> "+c.getId()+"</a></div>"+
        "      <div class=\"eStore_price\">"+
        "	  <span class=\"eStore_price_label\">"+
        "	  Category: </span><span class=\"eStore_price_value\">"+c.getCategory()+"</span>"+
        "	  </div>"+
        // "	  <div class=\"eStore_price\">"+
        // "	  <span class=\"eStore_price_label\">"+
        // "	  Special discount: </span><span class=\"eStore_price_value\">$ "+c.getRdiscount()+"</span>"+
        // "	  </div>"+
        "	  <div class=\"eStore_price\">"+
        "	  <span class=\"eStore_price_label\">"+
        "	  Warranty Offered: </span><span class=\"eStore_price_value\">$ "+c.getRwarranty()+"</span>"+
        "	  </div>"+
        // "	  <div class=\"eStore_price\">"+
        // "	  <span class=\"eStore_price_label\">"+
        // "	  Discount: </span><span class=\"eStore_price_value\">$ "+c.getDiscount()+"</span>"+
        // "	  </div>"+
        "	  <div class=\"eStore_price\">"+
        "	  <span class=\"eStore_price_label\">"+
        "	  Bid Value: </span><span class=\"eStore_price_value\">$ "+c.getPrice()+"</span>"+
        "	  </div>"+
        // "      <form method=\"post\" action=\"/ebuy/CartPage?act=remove\" style=\"display:inline\">"+
        // "  <input type=\"hidden\" name=\"name\" value= \""+c.getName()+"\">"+
        //        "  <input type=\"hidden\" name=\"price\" value=\""+c.getPrice()+"\">"+
        //      "  <input type=\"hidden\" name=\"rdiscount\" value= \""+c.getRdiscount()+"\">"+
        //      "  <input type=\"hidden\" name=\"rwarranty\" value=\""+c.getRwarranty()+"\">"+
        //        "  <input type=\"hidden\" name=\"discount\" value=\""+c.getDiscount()+"\">"+
        //        "  <input type=\"hidden\" name=\"category\" value=\""+c.getCategory()+"\">"+
        //      "  <button type = \"submit\" value= \"Remove From Cart\" class=\"addtocart\">Remove From Cart</button>"+
        //      "</form>"+
        //      "      <form method=\"post\" action=\"/ebuy/CartPage?act=removewarr\" style=\"display:inline\">"+
        //      "  <input type=\"hidden\" name=\"name\" value= \""+c.getName()+"\">"+
        //             "  <input type=\"hidden\" name=\"price\" value=\""+c.getPrice()+"\">"+
        //           "  <input type=\"hidden\" name=\"rdiscount\" value= \""+c.getRdiscount()+"\">"+
        //           "  <input type=\"hidden\" name=\"rwarranty\" value=\""+c.getRwarranty()+"\">"+
        //             "  <input type=\"hidden\" name=\"discount\" value=\""+c.getDiscount()+"\">"+
        //             "  <input type=\"hidden\" name=\"category\" value=\""+c.getCategory()+"\">"+
        //           "  <button type = \"submit\" value= \"Remove From Cart\" class=\"addtocart\">Remove Warranty</button>"+
        //           "</form>"+
        //           "      <form method=\"post\" action=\"/ebuy/CartPage?act=addwarr\" style=\"display:inline\">"+
        //           "  <input type=\"hidden\" name=\"name\" value= \""+c.getName()+"\">"+
        //                  "  <input type=\"hidden\" name=\"price\" value=\""+c.getPrice()+"\">"+
        //                "  <input type=\"hidden\" name=\"rdiscount\" value= \""+c.getRdiscount()+"\">"+
        //                "  <input type=\"hidden\" name=\"rwarranty\" value=\""+c.getRwarranty()+"\">"+
        //                  "  <input type=\"hidden\" name=\"discount\" value=\""+c.getDiscount()+"\">"+
        //                  "  <input type=\"hidden\" name=\"category\" value=\""+c.getCategory()+"\">"+
        //                "  <button type = \"submit\" value= \"Remove From Cart\" class=\"addtocart\">Add Warranty</button>"+
        //                "</form>"+
        "</div>"+
        "</div>";


        contentStr = contentStr +
        "<form method=\"post\" action=\"/ebuy/CartPage\" style=\"display:inline\">"+
        "<input type=\"hidden\" name=\"product_id\" value=\""+c.getId()+"\">"+
        "<input type=\"text\" name=\"bid_value\" >"+
        "  <button type = \"submit\" value= \"Bid Set\" class=\"addtocart\">Set Bid</button>"+
        "</form>";
	  	// }





    }



    // if(cartacc == null || cartacc.isEmpty()) {
    // 	System.out.println("No products found");
    // } else {
    //   String acc_header = "<div class=\"other_menu\">Accessories</div>";
    //   contentStr = contentStr + acc_header;
		// for(Entry<String, Accessory> ma :cartacc.entrySet()){
		// 	Accessory a = ma.getValue();
    //   contentStr = contentStr +
    //   "<div class=\"eStore-product\">"+
    //   "   <div class=\"eStore-thumbnail\">"+
    //   "   <a href=\"/ProductPage?productname="+a.getName()+"\" rel=\"lightbox["+cat_acc_link.get(a.getName())+"]\" title=\""+cat_acc_link.get(a.getName())+"\">"+
    //   "	<img class=\"thumb-image\" src=\"/images/"+cat_acc_link.get(a.getName())+"-acc.jpg\" alt=\""+cat_acc_link.get(a.getName())+"\">"+
    //   "	</a>"+
    //   "	</div>"+
    //   "   <div class=\"eStore-product-description\">"+
    //   "      <div class=\"eStore-product-name\"><strong>Product</strong>:<a href=\"/ebuy/ProductPage?productname="+a.getName()+"\"> "+a.getName()+"</a></div>"+
    //   "      <div class=\"eStore_price\">"+
    //   "	  <span class=\"eStore_price_label\">"+
    //   "	  Price: </span><span class=\"eStore_price_value\">"+a.getPrice()+"</span>"+
    //   "	  </div>"+
    //   "	  <div class=\"eStore_price\">"+
    //   "	  <span class=\"eStore_price_label\">"+
    //   "	  Discount: </span><span class=\"eStore_price_value\">$ "+a.getDiscount()+"</span>"+
    //   "	  </div>"+
    //   "<form action=\"/ebuy/CartPage?act=removeacc\" method = \"post\">"+
    //   "  <input type=\"hidden\" name=\"accname\" value= \""+a.getName()+"\">"+
    //   "  <input type=\"hidden\" name=\"price\" value=\""+a.getPrice()+"\">"+
    //   "  <input type=\"hidden\" name=\"discount\" value=\""+a.getDiscount()+"\">"+
    //   "  <button type = \"submit\" value= \"Remove\" >Remove</button>"+
    //   "</form>"+
    //   "</div>"+
    //   "</div>";
    //
    //
		// }

    // }

    // String checkout_but = "  <button type = \"button\" style=\"float:right\" value= \"Checkout\" onclick=\"document.location.href='/ebuy/HomePage';\">Place Bid</button>";
    String headval= "<div class=\"other_menu\">My Cart</div>";
    // if(contentManager.getProductsCount(session)>0)
    // {
      contentManager.setContent(headval+contentStr);
    // }
    // else
    // {
    //   contentManager.setContent(headval+"<br/><div style=\"padding-left:150px\">There are no products added to your cart!</div>");
    // }

    out.println(contentManager.getPageContent());
  }
}
