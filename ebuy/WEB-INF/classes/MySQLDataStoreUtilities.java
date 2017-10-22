import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

public class MySQLDataStoreUtilities {

	public static  Statement stmt;
	public static  Connection conn ;

	public static void connectToMySQL(){

        final String JDBC_DRIVER="com.mysql.jdbc.Driver";
        final String DB_URL="jdbc:mysql://localhost:3306/bestbid";
        final String USER = "root";
        final String PASS = "root";

	    try {
			Class.forName("com.mysql.jdbc.Driver");

			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			stmt = conn.createStatement();
	    } catch (Exception e) {
			System.out.println("*************ERROR in connecting mySQL DB *******************");

		}

    }

	public static void insertUser(Users u){
		try{

			Connection conn = getConnection();
			String insertIntoCustomerRegisterQuery = "INSERT INTO USERS(NAME,PASSWORD,CREDNO,UTYPE,ADDRESS,ZIP,CREDEXP,CREDCVV) "
			+ "VALUES (?,?,?,?,?,?,?,?);";
			PreparedStatement pst = conn.prepareStatement(insertIntoCustomerRegisterQuery);

			pst.setString(1,u.getName());
			pst.setString(2,u.getPassword());
			pst.setString(3,u.getCredNo());
			pst.setString(4,u.getUtype());
			pst.setString(5,u.getAddress());
			pst.setString(6,u.getZip());
			pst.setString(7,u.getCredExp());
			pst.setString(8,u.getCredCvv());
			pst.execute();
			System.out.println(u.getName()+"  "+ u.getPassword()+"    "+ u.getUtype().toString());
			pst.close();

		} catch(Exception e){
			System.out.println("*************ERROR in insert user *******************");
			e.printStackTrace();
		}
	}



	public static void insertOrder(Order o){

		System.out.println("*************insert *******************");
		try{

			Connection conn = getConnection();
			String insertIntoCustomerRegisterQuery = "INSERT INTO ORDERS (ID,NAME,DESCRIPTION,PRICE,ORDERDATE,STATUS,BUYER) "
			+ "VALUES (?,?,?,?,?,?,?);";
			PreparedStatement pst = conn.prepareStatement(insertIntoCustomerRegisterQuery);
			System.out.println(o.getId());
			System.out.println(o.getShipName());
			System.out.println(o.getDescription());
			System.out.println(o.getPrice());
			System.out.println(o.getOrderDate());
			System.out.println(o.getStatus());
			System.out.println(o.getBuyer());
			pst.setString(1,o.getId());
			pst.setString(2,o.getShipName());
			pst.setString(3,o.getDescription());
			pst.setDouble(4,o.getPrice());
			pst.setDate(5, new java.sql.Date(o.getOrderDate().getTime()));
			pst.setString(6,o.getStatus());
			pst.setString(7,o.getBuyer());
			pst.execute();
			pst.close();

		} catch(Exception e){
			System.out.println("*************ERROR in insert order *******************");
			e.printStackTrace();
		}
	}

	public static HashMap<String, Users> fetchAllUsers()
	{
		HashMap<String, Users> usersFromDB=new HashMap<String, Users>();

		try{
			conn = getConnection();
			String q ="SELECT * FROM USERS";
			PreparedStatement pst = conn.prepareStatement(q);
			ResultSet rs = pst.executeQuery();

			while(rs.next())
			{
				Users user= new Users(rs.getString("NAME"), rs.getString("ADDRESS"), rs.getString("CREDNO"),0, rs.getString("UTYPE"));
				user.setId(rs.getInt("ID"));
				user.setPassword(rs.getString("PASSWORD"));

				usersFromDB.put(rs.getString("NAME"), user);
			}

			pst.close();

		}catch(Exception e){
			System.out.println("*************ERROR in fetch all users *******************");
			e.printStackTrace();
		}
		return usersFromDB;
	}


	public static HashMap<String, Product> fetchAllProducts()
	{
		HashMap<String, Product> productsFromDB=new HashMap<String, Product>();

		try{
			conn = getConnection();
			String q ="SELECT * FROM PRODUCTS";
			PreparedStatement pst = conn.prepareStatement(q);
			ResultSet rs = pst.executeQuery();

			while(rs.next())
			{
				Product product= new Product(rs.getInt("ID"), rs.getString("PRODUCT_NAME"), rs.getString("HIGHEST_BID"), rs.getString("HIGHEST_BID_BUYER"), rs.getString("CATEGORY"), rs.getString("WARRANTY"), rs.getString("SELLER_NAME"), rs.getString("SELLER_ZIP"), rs.getString("SELLER_CITY"), rs.getString("MANUFACTURE_NAME"),
												rs.getString("HIGHEST_BID_BUYER_EMAILID"), rs.getString("HIGHEST_BID_BUYER_PHONE"));
				product.setId(rs.getInt("ID"));

				productsFromDB.put(rs.getString("ID"), product);
			}

			pst.close();

		}catch(Exception e){
			System.out.println("*************ERROR in fetch all users *******************");
			e.printStackTrace();
		}
		return productsFromDB;
	}


	public static HashMap<String, Order> fetchAllOrders()
	{
		HashMap<String, Order> ordersFromDB=new HashMap<String, Order>();

		try{
			conn = getConnection();
			String q ="SELECT * FROM ORDERS";
			PreparedStatement pst = conn.prepareStatement(q);
			ResultSet rs = pst.executeQuery();

			while(rs.next())
			{
				Order order= new Order(rs.getString("NAME"), rs.getString("DESCRIPTION"), rs.getString("BUYER"));
				order.setOrderDate(((java.util.Date) rs.getDate("ORDERDATE")));
				order.setId(rs.getString("ID"));
				order.setStatus(rs.getString("STATUS"));
				order.setPrice(rs.getDouble("PRICE"));
				ordersFromDB.put(rs.getString("ID"), order);
			}

			pst.close();

		}catch(Exception e){
			System.out.println("*************ERROR in fetch all orders *******************");
			e.printStackTrace();
		}
		return ordersFromDB;
	}

	public static void removeOrder(String id)
	{

		try{
			Connection conn = getConnection();
			String insertIntoCustomerRegisterQuery = "DELETE FROM ORDERS WHERE ID = ? ";
			PreparedStatement pst = conn.prepareStatement(insertIntoCustomerRegisterQuery);

			pst.setString(1,id);

			pst.executeUpdate();
			pst.close();

		}catch(Exception e){
			System.out.println("*************ERROR in remove order *******************");
		}
	}

	// public static void updateOrderStatus(String name, String status)
	// {
	//
	// 	try{
	// 		Connection conn = getConnection();
	// 		String insertIntoCustomerRegisterQuery = "UPDATE orders SET status = ? WHERE name = ?  ";
	// 		PreparedStatement pst = conn.prepareStatement(insertIntoCustomerRegisterQuery);
	// 		pst.setString(1,status);
	// 		pst.setString(2,name);
	//
	// 		pst.executeUpdate();
	// 		pst.close();
	//
	// 	}catch(Exception e){
	// 		System.out.println("*************ERROR in remove order *******************");
	// 	}
	// }

	public static void updateOrderPriceStatus(String id, double price,String status)
	{

		try{
			Connection conn = getConnection();
			String insertIntoCustomerRegisterQuery = "UPDATE ORDERS SET PRICE = ?,STATUS=?  WHERE ID = ?  ";
			PreparedStatement pst = conn.prepareStatement(insertIntoCustomerRegisterQuery);
			pst.setDouble(1,price);
			pst.setString(2,status);
			pst.setString(3,id);

			pst.executeUpdate();
			pst.close();

		}catch(Exception e){
			System.out.println("*************ERROR in update order *******************");
		}
	}

	public static void updateShipNameShipAddress(String id, String name,String address)
	{

		try{
			Connection conn = getConnection();
			String insertIntoCustomerRegisterQuery = "UPDATE ORDERS SET NAME = ?,ADDRESS = ?  WHERE ID = ?  ";
			PreparedStatement pst = conn.prepareStatement(insertIntoCustomerRegisterQuery);
			pst.setString(1,name);
			pst.setString(2,address);
			pst.setString(3,id);

			pst.executeUpdate();
			pst.close();

		}catch(Exception e){
			System.out.println("*************ERROR in update order *******************");
		}
	}

	public static void updateUserAddressAndCredNo(String name, String address, String credNo)
	{
		System.out.println("*************Update *******************");
		try{
			Connection conn = getConnection();
			String insertIntoCustomerRegisterQuery = "UPDATE USERS SET ADDRESS = ? , CREDNO = ?  WHERE NAME = ?  ";
			PreparedStatement pst = conn.prepareStatement(insertIntoCustomerRegisterQuery);
			pst.setString(1,address);
			pst.setString(2,credNo);
			pst.setString(3,name);

			pst.executeUpdate();
			pst.close();

		}catch(Exception e){
			System.out.println("*************ERROR in update user *******************");
		}
	}

	public static void updateBidValue(String bid_value, int product_id){
		System.out.println("*************Update *******************");
		try{
			Connection conn = getConnection();
			String insertIntoCustomerRegisterQuery = "UPDATE PRODUCTS SET HIGHEST_BID = ? WHERE ID = ?";
			PreparedStatement pst = conn.prepareStatement(insertIntoCustomerRegisterQuery);
			pst.setString(1,bid_value);
			pst.setInt(2, product_id);

			pst.executeUpdate();
			pst.close();

		}catch(Exception e){
			System.out.println("*************ERROR in update user *******************");
		}
	}

    public static Statement getStatement() {
    	return stmt;
    }

    public static Connection getConnection() {
			if(conn==null)
			{
				connectToMySQL();
			}
    	return conn;
    }



}
