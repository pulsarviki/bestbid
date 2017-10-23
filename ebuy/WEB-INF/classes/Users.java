import java.io.*;
import java.util.*;
import java.util.Map.Entry;

import javax.servlet.*;
import javax.servlet.http.*;


public class Users implements java.io.Serializable{
	private int id;
	private String name;
	private String address;
	private String email;
	private String phone;
	private String credNo;
	private String credCvv;
	private String credExp;
	private String zip;
	private String password;
  private static HashMap<String, Users> userset;
	private String utype = "BUYER";

  public static HashMap<String, Users> loadUsers()
  {

		userset = MySQLDataStoreUtilities.fetchAllUsers();
    //userset = (HashMap<String, Users>) PageContent.readFromFile("users");
    return userset;
  }

	public static void insertUser(Users u)
	{
		MySQLDataStoreUtilities.insertUser(u);
	}

	public static void updateUserAddressAndCredNo(String name, String address, String credNo)
	{
		MySQLDataStoreUtilities.updateUserAddressAndCredNo(name,address,credNo);
	}


	public Users(String name, String address, String credNo, int cartCount,String utype){
		this.name = name;
		this.address = address;
		this.credNo = credNo;
		this.utype = utype;
	}

	public Users(String name, String address, String credNo, String credExp, String credCvv, String utype, String email, String phone){
		this.name = name;
		this.address = address;
		this.credNo = credNo;
		this.credExp = credExp;
		this.credCvv = credCvv;
		this.utype = utype;
		this.email = email;
		this.phone = phone;
	}

	public Users(String name, String password){
		this.name = name;
		this.password = password;
	}

	public Users() {

	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
		System.out.println("set email is "+this.email);
	}

	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public String getCredCvv() {
		return credCvv;
	}
	public void setcredCvv(String credCvv) {
		this.credCvv = credCvv;
	}


	public String getCredExp() {
		return address;

	}
	public void setCredExp(String address) {
		this.address = address;
	}

	public String getUtype() {
		return utype;
	}
	public void setUtype(String utype) {
		this.utype = utype;
	}
	public String getCredNo() {
		return credNo;
	}
	public void setCredNo(String credNo) {
		this.credNo = credNo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
