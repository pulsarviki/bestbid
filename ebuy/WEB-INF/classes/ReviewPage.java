import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Map.Entry;
import java.text.SimpleDateFormat;
import java.text.DateFormat;


public class ReviewPage extends HttpServlet {

PageContent contentManager = new PageContent();

  protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HashMap<String, Users> usersFromDb = null;
		HashMap<String, Order> ordersFromDb = null;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    Review r = new Review();

    PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
    String usertype=(String)session.getAttribute("usertype");
    String username=(String)session.getAttribute("username");
		String productname = request.getParameter("productname");
    String productcat = request.getParameter("productcat");
    String productprice = request.getParameter("productprice");
    String retailername = request.getParameter("retailer");
    String retailercity = request.getParameter("retailercity");
    String retailerzip = request.getParameter("retailerzip");
    String retailerstate = request.getParameter("retailerstate");
    String productonsale = request.getParameter("productonsale");
    String manufacturername = request.getParameter("manufacturername");
    String manufacturerrebate = request.getParameter("manufacturerrebate");
    String userage = request.getParameter("userage");
    String usergender = request.getParameter("usergender");
    String useroccupation = request.getParameter("useroccupation");
    String userrating = request.getParameter("userrating");
    Date reviewdate=null;
    try {
      reviewdate = sdf.parse(request.getParameter("orderdate"));
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    String productreview = request.getParameter("productreview");

    r.setProductName(productname);
    r.setUserName(username);
    r.setCategory(productcat);
    r.setPrice(Double.parseDouble(productprice));
    r.setRetailerName(retailername);
    r.setRetailerZip(Integer.parseInt(retailerzip));
    r.setRetailerCity(retailercity);
    r.setRetailerState(retailerstate);
    r.setProductOnSale(productonsale);
    r.setManufacturerName(manufacturername);
    r.setManufacturerRebate(manufacturerrebate);
    r.setUserAge(Integer.parseInt(userage));
    r.setUserGender(usergender);
    r.setUserOccupation(useroccupation);
    r.setReviewRating(Integer.parseInt(userrating));
    r.setReviewText(productreview);
    r.setReviewDate(reviewdate);

    MongoDBDataStoreUtilities.insertReview(r);
    contentManager.setHeader(usertype,username,contentManager.getProductsCount(session));
    contentManager.setContent("<br/><div>Your review is submitted! Thank you!</div>");
      out.println(contentManager.getPageContent());
			//response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/CheckoutPage"));

		}



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


    String contentStr = "";
    	//prinProducttMap(orderproducts);

      contentStr=contentStr+"<div class=\"other_menu\">Product Review</div>";

    	contentStr = contentStr +
    			"      <div id=\"container\">"+
    			"        <div id=\"wrapper\">"+
    			"        <div class=\"formcontainer\">"+
    			"          <div id=\"alert\"></div>"+
    						"<form action=\"/ebuy/ReviewPage\" method=\"post\" class=\"bossform\">"+
                "<br/>"+
    			"              <span>Product Name:</span>"+
          "<br/>"+
    			"              <input type=\"text\" name=\"productname\" value=\""+cur_product.getName()+"\" readonly/>"+
          "<br/>"+
          "              <span>Product Category:</span>"+
          "<br/>"+
    			"              <input type=\"text\" name=\"productcat\" value=\""+cur_product.getCategory()+"\" readonly/>"+
          "<br/>"+
          "              <span>Product Price:</span>"+
          "<br/>"+
    			"              <input type=\"text\" name=\"productprice\" value=\""+cur_product.getPrice()+"\" readonly/>"+
          "<br/>"+
          "              <span>Retailer Name:</span>"+
          "<br/>"+
    			"              <input type=\"text\" name=\"retailer\" value=\""+cur_product.getRetailer()+"\" readonly/>"+
          "<br/>"+
          "              <span>Retailer City:</span>"+
          "<br/>"+
    			"              <input type=\"text\" name=\"retailercity\" value=\""+cur_product.getRetailerCity()+"\" readonly/>"+
          "<br/>"+
          "              <span>Retailer Zip:</span>"+
          "<br/>"+
    			"              <input type=\"text\" name=\"retailerzip\" value=\""+cur_product.getRetailerZip()+"\" readonly/>"+
          "<br/>"+
          "              <span>Retailer State:</span>"+
          "<br/>"+
    			"              <input type=\"text\" name=\"retailerstate\" value=\""+cur_product.getRetailerState()+"\" readonly/>"+
          "<br/>"+
          "              <span>Product On Sale:</span>"+
          "<br/>"+
    			"              <input type=\"text\" name=\"productonsale\" value=\""+cur_product.getProductOnSale()+"\" />"+
          "<br/>"+
          "              <span>Manufacturer Name:</span>"+
          "<br/>"+
    			"              <input type=\"text\" name=\"manufacturername\" value=\""+cur_product.getManufacturerName()+"\" readonly/>"+
          "<br/>"+
          "              <span>Manufacturer Rebate:</span>"+
          "<br/>"+
    			"              <input type=\"text\" name=\"manufacturerrebate\" value=\"Yes or no\" />"+
          "<br/>"+
          "              <span>User ID:</span>"+
          "<br/>"+
    			"              <input type=\"text\" name=\"username\" value=\""+username+"\" readonly/>"+
          "<br/>"+
          "              <span>User age:</span>"+
          "<br/>"+
    			"              <input type=\"text\" name=\"userage\" value=\"\" />"+
          "<br/>"+
          "              <span>Gender:</span>"+
          "<br/>"+
    			"              <input type=\"text\" name=\"usergender\" value=\"\" />"+
          "<br/>"+
          "              <span>User Occupation:</span>"+
          "<br/>"+
    			"              <input type=\"text\" name=\"useroccupation\" value=\"\" />"+
          "<br/>"+
          "              <span>User Rating (1-5):</span>"+
          "<br/>"+
    			"              <input type=\"text\" name=\"userrating\" value=\"\" />"+
          "<br/>"+
          "              <span>Review Date:</span>"+
          "<br/>"+
    			"              <input type=\"date\" name=\"orderdate\" value=\""+dateFormat.format(new Date())+"\" readonly/>"+
          "<br/>"+
          "<br/>"+
    			"              <span>Product Review:</span>"+
          "<br/>"+
          "<textarea name=\"productreview\" rows=\"4\" cols=\"50\" placeholder=\"Write Review Here!\">"+
          "</textarea>"+
          "<br/>"+
    			"             <button type=\"submit\" value=\"Submit\" style=\"float: right; margin-right: 90px;\" class=\"contact\">Submit</button> "+
    						"</form>"+
    			"          </div>"+
    			"        </div>"+
    			"        </div>";



  contentManager.setHeader(usertype,username,contentManager.getProductsCount(session));
	contentManager.setContent(contentStr);

    out.println(contentManager.getPageContent());
  }
}
