import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Map.Entry;
import javax.xml.parsers.ParserConfigurationException;
import java.net.URL;
import org.xml.sax.SAXException;

public class ManageProducts extends HttpServlet {

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
        String act=request.getParameter("act");

        if(act.equalsIgnoreCase("delete"))
        {
          String name = request.getParameter("name");
          products.remove(name);
          PageContent.writeToFile(products, "products");
          response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/ManageProducts"));
        }
        else if(act.equalsIgnoreCase("update"))
        {
          String name = request.getParameter("name");
          String price = request.getParameter("price");
          String category = request.getParameter("category");
          String rdiscount = request.getParameter("rdiscount");
          String rwarranty = request.getParameter("rwarranty");
          String discount = request.getParameter("discount");
          Product curr_prod = products.get(name);
          String contentStr="";

          curr_prod.setPrice(Double.parseDouble(price));
          curr_prod.setImage(category);
          curr_prod.setRdiscount(Double.parseDouble(rdiscount));
          curr_prod.setRwarranty(Double.parseDouble(rwarranty));
          curr_prod.setDiscount(Double.parseDouble(discount));
          curr_prod.setCategory(category);

          products.put(name,curr_prod);
          PageContent.writeToFile(products, "products");
          response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/ManageProducts"));

        }
        else if(act.equalsIgnoreCase("add"))
        {
          String name = request.getParameter("name");
          String price = request.getParameter("price");
          String category = request.getParameter("category");
          String rdiscount = request.getParameter("rdiscount");
          String rwarranty = request.getParameter("rwarranty");
          String discount = request.getParameter("discount");
          Product curr_prod = new Product();
          String contentStr="";

          curr_prod.setName(name);
          curr_prod.setPrice(Double.parseDouble(price));
          curr_prod.setImage(category);
          curr_prod.setRdiscount(Double.parseDouble(rdiscount));
          curr_prod.setRwarranty(Double.parseDouble(rwarranty));
          curr_prod.setDiscount(Double.parseDouble(discount));
          curr_prod.setCategory(category);

          products.put(name,curr_prod);
          PageContent.writeToFile(products, "products");
          response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/ManageProducts"));

        }
        else if(act.equalsIgnoreCase("updateacc"))
        {

          String accname = request.getParameter("accname");
          String price = request.getParameter("price");
          String productname = request.getParameter("productname");
          String discount = request.getParameter("discount");
          Product curr_prod = products.get(productname);
          HashMap<String,Accessory> curr_acces=curr_prod.getAccessories();
          Accessory the_acces=curr_acces.get(accname);
          String contentStr="";

          the_acces.setPrice(Double.parseDouble(price));
          the_acces.setDiscount(Double.parseDouble(discount));
          curr_acces.put(accname,the_acces);
          curr_prod.setAccessories(curr_acces);
          products.put(productname,curr_prod);
          PageContent.writeToFile(products, "products");
          response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/ManageProducts"));

        }
        else if(act.equalsIgnoreCase("deleteacc"))
        {
          String accname = request.getParameter("accname");
          String productname = request.getParameter("productname");
          Product curr_prod=products.get(productname);
          HashMap<String,Accessory> curr_acces=curr_prod.getAccessories();
          curr_acces.remove(accname);
          curr_prod.setAccessories(curr_acces);
          products.put(productname,curr_prod);
          PageContent.writeToFile(products, "products");
          response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/ManageProducts"));
        }
        else if(act.equalsIgnoreCase("addacc"))
        {

          String accname = request.getParameter("accname");
          String price = request.getParameter("price");
          String productname = request.getParameter("productname");
          String discount = request.getParameter("discount");
          Product curr_prod = products.get(productname);
          HashMap<String,Accessory> curr_acces=curr_prod.getAccessories();
          Accessory the_acces=new Accessory();
          String contentStr="";
          the_acces.setName(accname);
          the_acces.setPrice(Double.parseDouble(price));
          the_acces.setDiscount(Double.parseDouble(discount));
          curr_acces.put(accname,the_acces);
          curr_prod.setAccessories(curr_acces);
          products.put(productname,curr_prod);
          PageContent.writeToFile(products, "products");
          response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/ManageProducts"));

        }
        else
        {
        System.out.print("Specified Action not found in ManageProducts!");
        }

      }


	public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {

		  contentManager = new PageContent();

		  HttpSession session = request.getSession();

		  String usertype=(String)session.getAttribute("usertype");
			String username=(String)session.getAttribute("username");
			//contentManager.setContent("This is Home Page you are logged in! "+username+usertype);
		  contentManager.setHeader(usertype,username,contentManager.getProductsCount(session));

			//---------Product Display-------------

			PrintWriter out = response.getWriter();
			HashMap<String, Product> products = (HashMap<String, Product>) PageContent.readFromFile("products");
			prinProducttMap(products);

			String cat = request.getParameter("cat");
			String category = "ALL PRODUCTS";
			boolean isCategory = false;
			if(cat == null || cat == "") {
				isCategory = false;
				category = "ALL PRODUCTS";
			} else if(cat.toUpperCase().equals("SMARTWATCHES")){
				isCategory = true;
				category = "SMARTWATCHES";
			} else if(cat.toUpperCase().equals("SPEAKERS")){
				isCategory = true;
				category = "SPEAKERS";
			}  else if(cat.toUpperCase().equals("HEADPHONES")){
				isCategory = true;
				category = "HEADPHONES";
			} else if(cat.toUpperCase().equals("PHONES")){
				isCategory = true;
				category = "PHONES";
			} else if(cat.toUpperCase().equals("LAPTOPS")){
				isCategory = true;
				category = "LAPTOPS";
			} else if(cat.toUpperCase().equals("EXTERNALSTORAGE")){
				isCategory = true;
				category = "EXTERNALSTORAGE";
			} else if(cat.toUpperCase().equals("OTHERS")){
				isCategory = true;
				category = "OTHERS";
			}

			String contentStr ="";

			if(products == null || products.isEmpty()) {
				System.out.println("NO products found!");
			} else {

		 for(Entry<String, Product> m :products.entrySet()){
					System.out.println("Each product--------"+m.getKey());
					Product c = m.getValue();
          HashMap<String, Accessory> curr_accs= c.getAccessories();
						//		  		contentStr = contentStr +buildString(m, contentStr);
    if(isCategory == false || (isCategory == true && category.equalsIgnoreCase(c.getCategory()))) {
            contentStr = contentStr + "<div class=\"other_menu\">Product Information for "+m.getKey()+"</div>";
						 contentStr = contentStr +
						 "<div class=\"eStore-product\">"+
"   <div class=\"eStore-thumbnail\">"+
"   <a href=\"/ProductPage?productname="+m.getKey()+"\" rel=\"lightbox["+c.getCategory()+"]\" title=\""+c.getCategory()+"\">"+
"	<img class=\"thumb-image\" src=\"/images/"+c.getCategory()+".jpg\" alt=\""+c.getCategory()+"\">"+
"	</a>"+
"	</div>"+
"   <div class=\"eStore-product-description\">"+
"      <div class=\"eStore-product-name\"><strong>Product</strong>:<a href=\"/ebuy/ProductPage?productname="+m.getKey()+"\"> "+m.getKey()+"</a></div>"+
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
"      <form method=\"post\" action=\"/ebuy/ManageProducts?act=delete\" style=\"display:inline\">"+
         "  <input type=\"hidden\" name=\"name\" value= \""+c.getName()+"\">"+
										"  <input type=\"hidden\" name=\"price\" value=\""+c.getPrice()+"\">"+
										"  <input type=\"hidden\" name=\"category\" value=\""+c.getCategory()+"\">"+
									"  <input type=\"hidden\" name=\"rdiscount\" value= \""+c.getRdiscount()+"\">"+
									"  <input type=\"hidden\" name=\"rwarranty\" value=\""+c.getRwarranty()+"\">"+
										"  <input type=\"hidden\" name=\"discount\" value=\""+c.getDiscount()+"\">"+
									"  <button type = \"submit\" value= \"Delete Product\" class=\"addtocart\">Delete</button>"+
									"</form>"+
          "      <form method=\"post\" action=\"/ebuy/UpdateAddProduct?act=update\" style=\"display:inline\">"+
                   "  <input type=\"hidden\" name=\"name\" value= \""+c.getName()+"\">"+
          										"  <input type=\"hidden\" name=\"price\" value=\""+c.getPrice()+"\">"+
          										"  <input type=\"hidden\" name=\"category\" value=\""+c.getCategory()+"\">"+
          									"  <input type=\"hidden\" name=\"rdiscount\" value= \""+c.getRdiscount()+"\">"+
          									"  <input type=\"hidden\" name=\"rwarranty\" value=\""+c.getRwarranty()+"\">"+
          										"  <input type=\"hidden\" name=\"discount\" value=\""+c.getDiscount()+"\">"+
          									"  <button type = \"submit\" value= \"Update Product\" class=\"addtocart\">Update</button>"+
          									"</form>"+
                         "</div>"+
                      "</div>";


              if(curr_accs == null || curr_accs.isEmpty()) {
              	System.out.println("No products found");
              } else {
                String acc_header = "<div class=\"other_menu\">Accessories for "+m.getKey()+"</div>";
                contentStr = contentStr + acc_header;
          		for(Entry<String, Accessory> ma :curr_accs.entrySet()){
          			Accessory a = ma.getValue();
                contentStr = contentStr +
                "<div class=\"eStore-product\">"+
                "   <div class=\"eStore-thumbnail\">"+
                "   <a href=\"/ProductPage?productname="+a.getName()+"\" rel=\"lightbox["+c.getCategory()+"]\" title=\""+c.getCategory()+"\">"+
                "	<img class=\"thumb-image\" src=\"/images/"+c.getCategory()+"-acc.jpg\" alt=\""+c.getCategory()+"\">"+
                "	</a>"+
                "	</div>"+
                "   <div class=\"eStore-product-description\">"+
                "      <div class=\"eStore-product-name\"><strong>Product</strong>:<a href=\"/ebuy/ProductPage?productname="+a.getName()+"\"> "+a.getName()+"</a></div>"+
                "      <div class=\"eStore_price\">"+
                "	  <span class=\"eStore_price_label\">"+
                "	  Price: </span><span class=\"eStore_price_value\">"+a.getPrice()+"</span>"+
                "	  </div>"+
                "	  <div class=\"eStore_price\">"+
                "	  <span class=\"eStore_price_label\">"+
                "	  Discount: </span><span class=\"eStore_price_value\">$ "+a.getDiscount()+"</span>"+
                "	  </div>"+
                "<form action=\"/ebuy/ManageProducts?act=deleteacc\" method = \"post\" style=\"display:inline\">"+
                "  <input type=\"hidden\" name=\"accname\" value= \""+a.getName()+"\">"+
                "  <input type=\"hidden\" name=\"price\" value=\""+a.getPrice()+"\">"+
                "  <input type=\"hidden\" name=\"discount\" value=\""+a.getDiscount()+"\">"+
                "  <input type=\"hidden\" name=\"productname\" value=\""+m.getKey()+"\">"+
                "  <button type = \"submit\" value= \"Remove\" >Delete</button>"+
                "</form>"+
                "<form action=\"/ebuy/UpdateAddProduct?act=updateacc\" method = \"post\" style=\"display:inline\">"+
                "  <input type=\"hidden\" name=\"accname\" value= \""+a.getName()+"\">"+
                "  <input type=\"hidden\" name=\"price\" value=\""+a.getPrice()+"\">"+
                "  <input type=\"hidden\" name=\"discount\" value=\""+a.getDiscount()+"\">"+
                "  <input type=\"hidden\" name=\"productname\" value=\""+m.getKey()+"\">"+
                "  <input type=\"hidden\" name=\"category\" value=\""+c.getCategory()+"\">"+
                "  <button type = \"submit\" value= \"Remove\" >Update</button>"+
                "</form>"+
                "</div>"+
                "</div>";


          		}

              }

            }
			}

		}

		//String headval= "<div class=\"other_menu\">"+category+"</div>";
		contentManager.setContent(contentStr);

		//System.out.println("\t\t\t\t headerval : "+headerval);
			//---------Product Display End---------


		  out.println(contentManager.getPageContent());

	  }


}
