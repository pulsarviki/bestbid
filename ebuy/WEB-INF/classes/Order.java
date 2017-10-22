import java.util.*;
import java.text.*;

public class Order implements java.io.Serializable{
	private String id;
	private String description;
	private String buyer;
  private String shipName;
  private String shipAddress;
	private String retailer;
	private double price;
  private HashMap<String,Product> products;
  private HashMap<String,Accessory> accessories;

	private String status = "PLACED";
	private Date orderDate = new Date();

	public Order(String id, String description, String buyer, String status){
		this.setId(id);
		this.setDescription(description);
		this.setBuyer(buyer);
		this.setStatus(status);
	}

	public Order(String id, String description, String buyer){
		this.setId(id);
		this.setDescription(description);
		this.setBuyer(buyer);
	}

	public Order(){
    this.products = new HashMap<String,Product>();
    this.accessories = new HashMap<String,Accessory>();
  }

	public static HashMap<String,Order> getAllOrders()
	{
		HashMap<String, Order> ordersFromDB = MySQLDataStoreUtilities.fetchAllOrders();
		return ordersFromDB;
	}

	public static void insertOrder(Order o)
	{
		MySQLDataStoreUtilities.insertOrder(o);
	}

	public static void removeOrder(String id)
	{
		MySQLDataStoreUtilities.removeOrder(id);
	}

	public static void updateOrderPriceStatus(String id,double price,String status)
	{
		MySQLDataStoreUtilities.updateOrderPriceStatus(id,price,status);
	}

	public static void updateShipNameShipAddress(String id,String name,String address)
	{
		MySQLDataStoreUtilities.updateShipNameShipAddress(id,name,address);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

  public String getShipName() {
		return shipName;
	}

	public void setShipName(String shipName) {
		this.shipName = shipName;
	}

  public String getShipAddress() {
		return shipAddress;
	}

	public void setShipAddress(String shipAddress) {
		this.shipAddress = shipAddress;
	}

	public String getBuyer() {
		return buyer;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}

  public HashMap<String, Accessory> getAccessories() {
		return this.accessories;
	}

	public void setAccessories(HashMap<String, Accessory> accessories) {
		this.accessories = accessories;
	}

  public HashMap<String, Product> getProducts() {
		return this.products;
	}

	public void setProducts(HashMap<String, Product> products) {
		this.products = products;
	}

	public String getStatus() {
    System.out.println("Fetching status!");
    Date deliveryDate = new Date();
    deliveryDate = addDays(this.getOrderDate(), 14);

    Date processedDate = new Date();
    processedDate = addDays(this.getOrderDate(), 9);

    Date todaysDate=new Date();
    if(!this.status.equalsIgnoreCase("CANCELLED"))
    {
      if(todaysDate.compareTo(processedDate)>=0)
      {
        if(todaysDate.compareTo(deliveryDate)>=0)
        {
          return "DELIVERED";
        }
        else{
          return "PROCESSED";
        }
      }
      else{
        return status;
      }
    }
    return status;
	}

  private Date addDays(Date d, int days)
    {
        Date ddate=new Date(d.getTime());
        ddate.setTime(d .getTime() + days * 1000 * 60 * 60 * 24);
        return ddate;
    }

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getRetailer() {
		return retailer;
	}

	public void setRetailer(String retailer) {
		this.retailer = retailer;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

  public static String orderIdGenerator()
  {
    Date dNow = new Date();
    SimpleDateFormat ft = new SimpleDateFormat("yyMMddhhmmssMs");
    String datetime = ft.format(dNow);
    return datetime;
  }


}
