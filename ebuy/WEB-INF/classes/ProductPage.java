import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Map.Entry;
import javax.xml.parsers.ParserConfigurationException;
import java.net.URL;
import org.xml.sax.SAXException;

public class ProductPage extends HttpServlet {

	private PageContent contentManager;

	public void init() throws ServletException {
	  SAXProductHandler s = new SAXProductHandler();
      HashMap<String, Product> products = null;
	try {
// 		URL url = getClass().getResource("ProductCatalog.xml");
// 		System.out.println("Found ProductCatalog.xml--"+url.getPath());
// //		File file = new File(url.getPath());
// 		products = s.readDataFromXML(url.getPath());
// 		System.out.println("_--------------------");
// 		PageContent.writeToFile(products, "products");
	} catch (Exception e) {
		System.out.println("Something went wrong!");
		e.printStackTrace();
	}

  }

	public void prinProducttMap(HashMap<String, Product> mapInFile) throws ServletException {

      for(Entry<String, Product> m :mapInFile.entrySet()){
			  	System.out.println(m.getKey());
			Product c = m.getValue();
			System.out.println("\t Name : "+c.getName());
			System.out.println("\t Price : "+c.getPrice());
			System.out.println("\t Accessories : ");
			//HashMap<String,Accessory> accessories = c.getAccessories();
			//for(Entry<String, Accessory> ma :accessories.entrySet()){
				//System.out.println("\t\t\t" + ma.getKey());
				//Accessory a = ma.getValue();
				//System.out.println("\t\t\t\t Name : "+a.getName());
				//System.out.println("\t\t\t\t Price : "+a.getPrice());
			}

	   }


	public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {

		  contentManager = new PageContent();
			Product c;
		  HttpSession session = request.getSession();

		  String usertype=(String)session.getAttribute("usertype");
			String username=(String)session.getAttribute("username");
			//contentManager.setContent("This is Home Page you are logged in! "+username+usertype);
		  contentManager.setHeader(usertype,username,contentManager.getProductsCount(session));

			//---------Product Display-------------

			PrintWriter out = response.getWriter();
			HashMap<String, Product> products = (HashMap<String, Product>) PageContent.readFromFile("products");
			prinProducttMap(products);

			String productname = request.getParameter("productname");
			// String category = "ALL PRODUCTS";
			// boolean isCategory = false;
			// if(cat == null || cat == "") {
			// 	isCategory = false;
			// 	category = "ALL PRODUCTS";
			// } else if(cat.toUpperCase().equals("TV")){
			// 	isCategory = true;
			// 	category = "TV";
			// } else if(cat.toUpperCase().equals("TABLETS")){
			// 	isCategory = true;
			// 	category = "TABLETS";
			// }  else if(cat.toUpperCase().equals("LAPTOPS")){
			// 	isCategory = true;
			// 	category = "LAPTOPS";
			// } else if(cat.toUpperCase().equals("SMARTPHONES")){
			// 	isCategory = true;
			// 	category = "SMARTPHONES";
			// } else if(cat.toUpperCase().equals("OTHERS")){
			// 	isCategory = true;
			// 	category = "OTHERS";
			// }

			String contentStr = "";


			if(products == null || products.isEmpty()) {
				System.out.println("NO products found!");
			} else {
						c=products.get(productname);

		 //for(Entry<String, Product> m :products.entrySet()){
					System.out.println("Each product--------"+c.getName());
					//Product c = m.getValue();
					//if(isCategory == false || (isCategory == true && category.equalsIgnoreCase(c.getCategory()))) {
						//		  		contentStr = contentStr +buildString(m, contentStr);
						 contentStr = "<div class=\"eStore-product\">"+
"   <div class=\"eStore-thumbnail\">"+
"   <a href=\"javascript:popImage('/images/"+c.getCategory()+".jpg','"+c.getCategory()+"')\" rel=\"lightbox["+c.getCategory()+"]\" title=\""+c.getCategory()+"\">"+
"	<img class=\"thumb-image\" src=\"/images/"+c.getCategory()+".jpg\" alt=\""+c.getCategory()+"\">"+
"	</a>"+
"	</div>"+
"   <div class=\"eStore-product-description\">"+
"      <div class=\"eStore-product-name\"><strong>Product</strong>:"+productname+"</div>"+
"      <div class=\"eStore_price\">"+
"	  <span class=\"eStore_price_label\">"+
"	  Category: </span><span class=\"eStore_price_value\">"+c.getCategory()+"</span>"+
"	  </div>"+
"	  <div class=\"eStore_price\">"+
"	  <span class=\"eStore_price_label\">"+
"	  Special discount: </span><span class=\"eStore_price_value\">$ "+c.getRdiscount()+"</span>"+
"	  </div>"+
"	  <div class=\"eStore_price\">"+
"	  <span class=\"eStore_price_label\">"+
"	  Warranty Offered: </span><span class=\"eStore_price_value\">$ "+c.getRwarranty()+"</span>"+
"	  </div>"+
"	  <div class=\"eStore_price\">"+
"	  <span class=\"eStore_price_label\">"+
"	  Discount: </span><span class=\"eStore_price_value\">$ "+c.getDiscount()+"</span>"+
"	  </div>"+
"	  <div class=\"eStore_price\">"+
"	  <span class=\"eStore_price_label\">"+
"	  Price: </span><span class=\"eStore_price_value\">$ "+c.getPrice()+"</span>"+
"	  </div>"+
"      <form method=\"post\" action=\"/ebuy/CartPage\" style=\"display:inline\">"+
         "  <input type=\"hidden\" name=\"name\" value= \""+c.getName()+"\">"+
										"  <input type=\"hidden\" name=\"price\" value=\""+c.getPrice()+"\">"+
									"  <input type=\"hidden\" name=\"rdiscount\" value= \""+c.getRdiscount()+"\">"+
									"  <input type=\"hidden\" name=\"rwarranty\" value=\""+c.getRwarranty()+"\">"+
										"  <input type=\"hidden\" name=\"discount\" value=\""+c.getDiscount()+"\">"+
									"  <button type = \"submit\" value= \"Add To Cart\" class=\"addtocart\">Add To Cart</button>"+
									"</form>"+
   "</div>"+
"</div>";

							 	HashMap<String,Accessory> accessories = c.getAccessories();
							 	if(!accessories.isEmpty()) {
									System.out.println("Size-------"+accessories.size());
									contentStr = contentStr + Carousal.buildCarousal(accessories,c.getCategory());
							 	}
								else{
									System.out.println("No Accessory found");
								}
							// for(Entry<String, Accessory> ma :accessories.entrySet()){
							// 	Accessory a = ma.getValue();
							// 	contentStr = contentStr +"<div class=\"prod_box\">"+
							// 			"        <div class=\"top_prod_box\"></div>"+
							// 			"        <div class=\"center_prod_box\">"+
							// 			"          <div class=\"product_title\"><a href=\"details.html\">"+a.getName()+"</a></div>"+
							// 			"          <div class=\"product_img\"><a href=\"details.html\"><img width=\"130\" height=\"130\" src=\"images/"+c.getCategory()+"-acc.jpg\" alt=\"\" border=\"0\"></a></div>"+
							// 			"             <div class=\"specifications\"> Discount: <span class=\"blue\">$"+a.getDiscount()+" </span><br></div>"+
							//
							// 			"          <div class=\"prod_price\">price : <span class=\"price\">$"+a.getPrice()+"</span></div>"+
							//
							// 			"        </div>"+
							// 			"        <div class=\"bottom_prod_box\"></div>"+
							// 			"        <div class=\"prod_details_tab\">"+
													// "<form action=\"/csj/cart?act=addacc\" method = \"post\">"+
													// "  <input type=\"hidden\" name=\"accname\" value= \""+a.getName()+"\">"+
													// "  <input type=\"hidden\" name=\"price\" value=\""+a.getPrice()+"\">"+
													// "  <input type=\"hidden\" name=\"discount\" value=\""+a.getDiscount()+"\">"+
													// "  <button type = \"submit\" value= \"+Cart\" class=\"addtocart\">+Cart</button>"+
													// "</form>"+
							// 						 "<form action=\"/csj/preview?act=addacc\" method = \"post\">"+
							// 							"  <input type=\"hidden\" name=\"accname\" value= \""+a.getName()+"\">"+
							// 							"  <input type=\"hidden\" name=\"price\" value=\""+a.getPrice()+"\">"+
							// 							"  <input type=\"hidden\" name=\"discount\" value=\""+a.getDiscount()+"\">"+
							// 							"  <button type = \"submit\" value= \"Save&Order\" class=\"addtocart\">Save&Order</button>"+
							// 							"</form>"+
							// 			"      </div>  </div>";
							//
							//
							// }
							//
									//contentStr = contentStr +	"        <div class=\"bottom_prod_box_big\"></div>"+
									//	"      </div>";
					//}
			//}
		}

		String headval= "<div class=\"other_menu\">"+productname+"</div>";
		contentManager.setContent(headval+contentStr);

		//System.out.println("\t\t\t\t headerval : "+headerval);
			//---------Product Display End---------


		  out.println(contentManager.getPageContent());

	  }


}
