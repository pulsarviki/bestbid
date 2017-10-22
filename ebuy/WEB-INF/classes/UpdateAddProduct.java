import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Map.Entry;
import javax.xml.parsers.ParserConfigurationException;
import java.net.URL;
import org.xml.sax.SAXException;

public class UpdateAddProduct extends HttpServlet {

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
        if(act.equalsIgnoreCase("add"))
        {

          String contentStr="";

          contentStr=contentStr+"<div class=\"other_menu\">Add Product Details</div>";

        	contentStr = contentStr +
        			"      <div id=\"container\">"+
        			"        <div id=\"wrapper\">"+
        			"        <div class=\"formcontainer\">"+
        			"          <div id=\"alert\"></div>"+
        						"<form action=\"/ebuy/ManageProducts?act=add\" method=\"post\" class=\"bossform\">"+
        			"              <span>Name:</span>"+
              "<br/>"+
        			"              <input type=\"text\" name=\"name\" value=\"\">"+
              "<br/>"+
        			"              <span>Price:</span>"+
              "<br/>"+
        			"              <input type=\"text\" name=\"price\" value=\"\">"+
              "<br/>"+
              "              <span>Retailer Discount:</span>"+
              "<br/>"+
        			"              <input type=\"text\" name=\"rdiscount\" value=\"\">"+
              "<br/>"+
              "              <span>Retailer Warranty:</span>"+
        			"              <input type=\"text\" name=\"rwarranty\" value=\"\">"+
              "<br/>"+
              "              <span>Discount:</span>"+
              "<br/>"+
        			"              <input type=\"text\" name=\"discount\" value=\"\">"+
              "<br/>"+

              "<span>Category</span>"+
              "<br/>"+
							"<select name=\"category\" value=\"\">"+
							"<option value=\"SMARTWATCHES\">SMARTWATCHES</option>"+
							"<option value=\"SPEAKERS\">SPEAKERS</option>"+
							"<option value=\"HEADPHONES\">HEADPHONES</option>"+
							"<option value=\"LAPTOPS\">LAPTOPS</option>"+
							"<option value=\"PHONES\">PHONES</option>"+
							"<option value=\"EXTERNALSTORAGE\">EXTERNALSTORAGE</option>"+
							"<option value=\"OTHERS\">OTHERS</option>"+
							"</select>"+
                "<br/>"+
        			"             <button type=\"submit\" value=\"Add\" style=\"float: right; margin-right: 90px;\" class=\"contact\">Add</button> "+
        						"</form>"+
        			"          </div>"+
        			"        </div>"+
        			"        </div>";

              contentManager.setContent(contentStr);
              out.println(contentManager.getPageContent());

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

          contentStr=contentStr+"<div class=\"other_menu\">Update Product Details</div>";

        	contentStr = contentStr +
        			"      <div id=\"container\">"+
        			"        <div id=\"wrapper\">"+
        			"        <div class=\"formcontainer\">"+
        			"          <div id=\"alert\"></div>"+
        						"<form action=\"/ebuy/ManageProducts?act=update\" method=\"post\" class=\"bossform\">"+
                    "<img src=\"/images/"+category+".jpg\" alt=\"Product Image\" style=\"width:200px;height:200px;margin:0 auto;display:block\" >"+
        			"              <span>Name:</span>"+
              "<br/>"+
        			"              <input type=\"text\" name=\"name\" value=\""+name+"\" readonly>"+
              "<br/>"+
        			"              <span>Price:</span>"+
              "<br/>"+
        			"              <input type=\"text\" name=\"price\" value=\""+price+"\">"+
              "<br/>"+
              "              <span>Retailer Discount:</span>"+
              "<br/>"+
        			"              <input type=\"text\" name=\"rdiscount\" value=\""+rdiscount+"\">"+
              "<br/>"+
              "              <span>Retailer Warranty:</span>"+
              "<br/>"+
        			"              <input type=\"text\" name=\"rwarranty\" value=\""+rwarranty+"\">"+
              "<br/>"+
              "              <span>Discount:</span>"+
              "<br/>"+
        			"              <input type=\"text\" name=\"discount\" value=\""+discount+"\">"+
              "<br/>"+

              "<span>Category</span>"+
							"<br/>"+
                "<select name=\"category\" value=\""+category+"\">"+
                "<option value=\"SMARTWATCHES\">SMARTWATCHES</option>"+
                "<option value=\"SPEAKERS\">SPEAKERS</option>"+
                "<option value=\"HEADPHONES\">HEADPHONES</option>"+
                "<option value=\"LAPTOPS\">LAPTOPS</option>"+
                "<option value=\"EXTERNALSTORAGE\">EXTERNALSTORAGE</option>"+
								"<option value=\"OTHERS\">OTHERS</option>"+
                "</select>"+
                "<br/>"+
        			"             <button type=\"submit\" value=\"Update\" style=\"float: right; margin-right: 90px;\" class=\"contact\">Update</button> "+
        						"</form>"+
        			"          </div>"+
        			"        </div>"+
        			"        </div>";

              contentManager.setContent(contentStr);
              out.println(contentManager.getPageContent());

        }
        else if(act.equalsIgnoreCase("updateacc"))
        {


            String productname = request.getParameter("productname");
            String price = request.getParameter("price");
            String category = request.getParameter("category");
            String accname = request.getParameter("accname");
            String discount = request.getParameter("discount");
            Product curr_prod = products.get(productname);
            HashMap<String,Accessory> curr_acces = curr_prod.getAccessories();

            String contentStr="";

            contentStr=contentStr+"<div class=\"other_menu\">Update Accessory Details</div>";

          	contentStr = contentStr +
          			"      <div id=\"container\">"+
          			"        <div id=\"wrapper\">"+
          			"        <div class=\"formcontainer\">"+
          			"          <div id=\"alert\"></div>"+
          						"<form action=\"/ebuy/ManageProducts?act=updateacc\" method=\"post\" class=\"bossform\">"+
                      "<img src=\"/images/"+category+"-acc.jpg\" alt=\"Product Image\" style=\"width:200px;height:200px;margin:0 auto;display:block\" >"+
          			"              <span style=\"padding-right: 12px;\">Name:</span>"+
                "<br/>"+
          			"              <input type=\"text\" name=\"accname\" value=\""+accname+"\" readonly>"+
                "<br/>"+
          			"              <span>Parent Product:</span>"+
                "<br/>"+
          			"              <input type=\"text\" name=\"productname\" value=\""+productname+"\" readonly>"+
                "<br/>"+
                "              <span>Category:</span>"+
                "<br/>"+
          			"              <input type=\"text\" name=\"category\" value=\""+category+"\" readonly>"+
                "<br/>"+
                "              <span>Price:</span>"+
                "<br/>"+
          			"              <input type=\"text\" name=\"price\" value=\""+price+"\">"+
                "<br/>"+
                "              <span>Discount:</span>"+
                "<br/>"+
          			"              <input type=\"text\" name=\"discount\" value=\""+discount+"\">"+
                "<br/>"+
          			"             <button type=\"submit\" value=\"Update\" style=\"float: right; margin-right: 90px;\" class=\"contact\">Update</button> "+
          						"</form>"+
          			"          </div>"+
          			"        </div>"+
          			"        </div>";

                contentManager.setContent(contentStr);
                out.println(contentManager.getPageContent());


        }
        else if(act.equalsIgnoreCase("addacc"))
        {

          String contentStr="";

          contentStr=contentStr+"<div class=\"other_menu\">Add Accessory</div>";

          contentStr = contentStr +
              "      <div id=\"container\">"+
              "        <div id=\"wrapper\">"+
              "        <div class=\"formcontainer\">"+
              "          <div id=\"alert\"></div>"+
                    "<form action=\"/ebuy/ManageProducts?act=addacc\" method=\"post\" class=\"bossform\">"+
              "              <span style=\"padding-right: 12px;\">Name:</span>"+
              "<br/>"+
              "              <input type=\"text\" name=\"accname\" value=\"\">"+
              "<br/>"+
              "<span>Parent Product</span>"+
              "<br/>"+
                "<select name=\"productname\" value=\"\">";
              for(Entry<String, Product> m :products.entrySet()){
                contentStr=contentStr+"<option value=\""+m.getKey()+"\">"+m.getKey()+"</option>";
              }
              contentStr=contentStr+ "</select>"+
              "<br/>"+
              "              <span>Price:</span>"+
              "<br/>"+
              "              <input type=\"text\" name=\"price\" value=\"\">"+
              "<br/>"+
              "              <span>Discount:</span>"+
              "<br/>"+
              "              <input type=\"text\" name=\"discount\" value=\"\">"+
              "<br/>"+
              "             <button type=\"submit\" value=\"Add\" style=\"float: right; margin-right: 90px;\" class=\"contact\">Add</button> "+
                    "</form>"+
              "          </div>"+
              "        </div>"+
              "        </div>";

              contentManager.setContent(contentStr);
              out.println(contentManager.getPageContent());


        }
        else
        {
        System.out.print("Specified Action not found!");
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

			String contentStr = "";
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
          "      <form method=\"post\" action=\"/ebuy/UpdateProduct?act=update\" style=\"display:inline\">"+
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
                "<form action=\"/ebuy/ManageProducts?act=updateacc\" method = \"post\" style=\"display:inline\">"+
                "  <input type=\"hidden\" name=\"accname\" value= \""+a.getName()+"\">"+
                "  <input type=\"hidden\" name=\"price\" value=\""+a.getPrice()+"\">"+
                "  <input type=\"hidden\" name=\"discount\" value=\""+a.getDiscount()+"\">"+
                "  <input type=\"hidden\" name=\"productname\" value=\""+m.getKey()+"\">"+
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
