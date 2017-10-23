
import java.util.HashMap;

public class Product implements java.io.Serializable{

	private int PRODUCT_ID;
	private String PRODUCT_NAME;
	private String HIGHEST_BID;
	private String HIGHEST_BID_BUYER;
	private String CATEGORY = "OTHERS";
	private String WARRANTY;
	private String SELLER_NAME;
	private String SELLER_ZIP;
	private String SELLER_CITY;
	private String MANUFACTURE_NAME;
	private	String HIGHEST_BID_BUYER_EMAILID;
	private String HIGHEST_BID_BUYER_PHONE;


	private String name;
	private double price;
	private String image;
	private String retailer;
	private String condition;
	private double discount;

	private double rdiscount;
	private double rwarranty;
	private String category = "OTHERS";

	private int retailerZip;
	private String retailerCity;
	private String retailerState;
	private String productOnSale;
	private String manufacturerName;

	 private HashMap<String,Accessory> accessories = new HashMap<String,Accessory>();

	 private static HashMap<String, Product> productset;

	 public static HashMap<String, Product> loadProducts()
   {

 		productset = MySQLDataStoreUtilities.fetchAllProducts();
     //userset = (HashMap<String, Users>) PageContent.readFromFile("users");
     return productset;
   }

	public static void updateBidDetails(String bid_value, int product_id, String email, String phone, String username){
 		MySQLDataStoreUtilities.updateBidDetails(bid_value, product_id, email, phone, username);
 	}

	public Product(String name, double price, String image, String retailer,String condition,double discount , HashMap<String,Accessory> accessories){
		this.name=name;
		this.price=price;
		this.image=image;
		this.retailer = retailer;
		this.condition=condition;
		this.discount = discount;
		this.setAccessories(accessories);
	}

	public Product(int PRODUCT_ID, String PRODUCT_NAME, String HIGHEST_BID, String HIGHEST_BID_BUYER, String CATEGORY, String WARRANTY, String SELLER_NAME, String SELLER_ZIP, String SELLER_CITY, String MANUFACTURE_NAME,
									String HIGHEST_BID_BUYER_EMAILID, String HIGHEST_BID_BUYER_PHONE){
		this.PRODUCT_ID = PRODUCT_ID;
		this.PRODUCT_NAME = PRODUCT_NAME;
		this.HIGHEST_BID = HIGHEST_BID;
		this.HIGHEST_BID_BUYER = HIGHEST_BID_BUYER;
		this.CATEGORY = CATEGORY;
		this.WARRANTY = WARRANTY;
		this.SELLER_NAME = SELLER_NAME;
		this.SELLER_ZIP = SELLER_ZIP;
		this.SELLER_CITY = SELLER_CITY;
		this.MANUFACTURE_NAME = MANUFACTURE_NAME;
		this.HIGHEST_BID_BUYER_EMAILID = HIGHEST_BID_BUYER_EMAILID;
		this.HIGHEST_BID_BUYER_PHONE = HIGHEST_BID_BUYER_PHONE;
	}

   public Product(String name, double price,double discount){
		this.name=name;
		this.price=price;
		this.discount = discount;
	}

   public Product(String name, double price,double discount, String category){
		this.name=name;
		this.price=price;
		this.discount = discount;
		this.category = category;
	}

	public Product(String name, double price,double discount, String category, double rdiscount, double rwarranty ){
	 this.name=name;
	 this.price=price;
	 this.discount = discount;
	 this.category = category;
	 this.rwarranty = rwarranty;
	 this.rdiscount = rdiscount;
 }

   public Product(String name, double price,double discount, String category,String retailer, int retailerZip, String retailerCity, String retailerState,  String productOnSale, String manufacturerName){
		this.name=name;
		this.price=price;
		this.discount = discount;
		this.category = category;

		this.retailer=retailer;
		this.retailerZip=retailerZip;
		this.retailerCity=retailerCity;
		this.retailerState =retailerState;
		this.productOnSale = productOnSale;
		this.manufacturerName = manufacturerName;
	}

	public Product(){

	}

	public int getId() {
		return PRODUCT_ID;
	}
	public void setId(int id) {
		this.PRODUCT_ID = id;
	}

	public int getHighestBid() {
		return (Integer.parseInt(HIGHEST_BID));
	}
	public void setHighestBid(String HIGHEST_BID) {
		this.HIGHEST_BID = HIGHEST_BID;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getRetailer() {
		return retailer;
	}
	public void setRetailer(String retailer) {
		this.retailer = retailer;
	}

	public void setAccessories(HashMap<String,Accessory> accessories) {
		this.accessories = accessories;
	}

	public HashMap<String,Accessory> getAccessories() {
		return accessories;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public String getCategory() {
		return CATEGORY;
	}

	public void setCategory(String Category) {
		this.CATEGORY = Category;
	}

	public double getRdiscount() {
		return rdiscount;
	}

	public void setRdiscount(double rdiscount) {
		this.rdiscount = rdiscount;
	}


	public double getRwarranty() {
		return rwarranty;
	}

	public void setRwarranty(double rwarranty) {
		this.rwarranty = rwarranty;
	}

	public int getRetailerZip() {
		return retailerZip;
	}

	public void setRetailerZip(int retailerZip) {
		this.retailerZip = retailerZip;
	}

	public String getRetailerCity() {
		return retailerCity;
	}

	public void setRetailerCity(String retailerCity) {
		this.retailerCity = retailerCity;
	}

	public String getRetailerState() {
		return retailerState;
	}

	public void setRetailerState(String retailerState) {
		this.retailerState = retailerState;
	}


	public String getManufacturerName() {
		return manufacturerName;
	}

	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}

	public String getProductOnSale() {
		return productOnSale;
	}

	public void setProductOnSale(String productOnSale) {
		this.productOnSale = productOnSale;
	}

}
