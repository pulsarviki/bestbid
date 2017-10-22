import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Map.Entry;
import javax.xml.parsers.ParserConfigurationException;
import java.net.URL;
import org.xml.sax.SAXException;

public class Carousal{

	private static String pre_first_carousal=
	"<div class=\"container\" style=\"display:inline-block;width:25%;height:25%;margin:0px 30px 0px 120px;\">"+
	"  <div id=\"myCarousel\" class=\"carousel slide\" data-ride=\"carousel\">"+
	"    <!-- Indicators -->"+
	""+
	"    <!-- Wrapper for slides -->"+
	"    <div class=\"carousel-inner\">";

	private static String pre_second_carousal="<div class=\"container\" style=\"display:inline-block;width:25%;height:25%;margin:0px 30px 0px 30px;\">"+
	"  <div id=\"myCarousel2\" class=\"carousel slide\" data-ride=\"carousel\">"+
	""+
	"    <!-- Wrapper for slides -->"+
	"    <div class=\"carousel-inner\">";

	private static String post_first_carousal="</div>"+
	""+
	"    <!-- Left and right controls -->"+
	"    <a class=\"left carousel-control\" href=\"[id^=myCarousel]\" data-slide=\"prev\">"+
	"      <span class=\"glyphicon glyphicon-chevron-left\"></span>"+
	"      <span class=\"sr-only\">Previous</span>"+
	"    </a>"+
	""+
	"  </div>"+
	"</div>";

	private static String post_second_carousal="</div>"+
	""+
	"    <!-- Left and right controls -->"+
	"    <a class=\"right carousel-control\" href=\"[id^=myCarousel]\"  data-slide=\"next\">"+
	"      <span class=\"glyphicon glyphicon-chevron-right\"></span>"+
	"      <span class=\"sr-only\">Next</span>"+
	"    </a>"+
	"  </div>"+
	"</div>";

	Carousal(){

	}

	public static String buildCarousal(HashMap<String,Accessory> accessories, String category){

		String main_content="";
		String first_content="";
		String second_content="";
		boolean marked_active = false;
		Iterator it = accessories.entrySet().iterator();
		System.out.println("it value----------"+it.hasNext());
		while (it.hasNext()) {
        Entry<String, Accessory> pair = (Entry<String, Accessory>)it.next();
        Accessory a = pair.getValue();
		//--------First Carousal--------

		if(it.hasNext()!=true)
		{

		first_content = first_content + "<div style=\"text-align:center\" class=\"item active\">"+
		"        <img src=\"/images/"+category+"-acc.jpg\" alt=\"Los Angeles\" style=\"width:100%;\">"+
		"		<a href=\"details.html\">"+a.getName()+"</a>"+
		"		<div> Discount: $"+a.getDiscount()+"</div>"+
		"		<div>Price: $"+a.getPrice()+"</div>"+
		"<form action=\"/ebuy/CartPage?act=addacc\" method = \"post\">"+
		"  <input type=\"hidden\" name=\"accname\" value= \""+a.getName()+"\">"+
		"  <input type=\"hidden\" name=\"price\" value=\""+a.getPrice()+"\">"+
		"  <input type=\"hidden\" name=\"discount\" value=\""+a.getDiscount()+"\">"+
		"  <input type=\"hidden\" name=\"category\" value=\""+category+"\">"+
		"  <button type = \"submit\" value= \"+Cart\" class=\"addtocart\">+Cart</button>"+
		"</form>"+
		"</div>";

         // avoids a ConcurrentModificationException
		}
		else
		{

			first_content = first_content + "<div style=\"text-align:center\" class=\"item \">"+
		"        <img src=\"/images/"+category+"-acc.jpg\" alt=\"Los Angeles\" style=\"width:100%;\">"+
		"		<a href=\"details.html\">"+a.getName()+"</a>"+
		"		<div> Discount: $"+a.getDiscount()+"</div>"+
		"		<div>Price: $"+a.getPrice()+"</div>"+
		"<form action=\"/csj/cart?act=addacc\" method = \"post\">"+
		"  <input type=\"hidden\" name=\"accname\" value= \""+a.getName()+"\">"+
		"  <input type=\"hidden\" name=\"price\" value=\""+a.getPrice()+"\">"+
		"  <input type=\"hidden\" name=\"discount\" value=\""+a.getDiscount()+"\">"+
		"  <button type = \"submit\" value= \"+Cart\" class=\"addtocart\">+Cart</button>"+
		"</form>"+
		"</div>";


		}

		if(marked_active==false)
		{

		second_content = second_content + "<div style=\"text-align:center\" class=\"item active\">"+
		"        <img src=\"/images/"+category+"-acc.jpg\" alt=\"Los Angeles\" style=\"width:100%;\">"+
		"		<a href=\"details.html\">"+a.getName()+"</a>"+
		"		<div> Discount: $"+a.getDiscount()+"</div>"+
		"		<div>Price: $"+a.getPrice()+"</div>"+
		"<form action=\"/csj/cart?act=addacc\" method = \"post\">"+
		"  <input type=\"hidden\" name=\"accname\" value= \""+a.getName()+"\">"+
		"  <input type=\"hidden\" name=\"price\" value=\""+a.getPrice()+"\">"+
		"  <input type=\"hidden\" name=\"discount\" value=\""+a.getDiscount()+"\">"+
		"  <button type = \"submit\" value= \"+Cart\" class=\"addtocart\">+Cart</button>"+
		"</form>"+
		"</div>";

		marked_active = true;

         // avoids a ConcurrentModificationException
		}
		else
		{

			second_content = second_content + "<div style=\"text-align:center\" class=\"item \">"+
		"        <img src=\"/images/"+category+"-acc.jpg\" alt=\"Los Angeles\" style=\"width:100%;\">"+
		"		<a href=\"details.html\">"+a.getName()+"</a>"+
		"		<div> Discount: $"+a.getDiscount()+"</div>"+
		"		<div>Price: $"+a.getPrice()+"</div>"+
		"<form action=\"/csj/cart?act=addacc\" method = \"post\">"+
		"  <input type=\"hidden\" name=\"accname\" value= \""+a.getName()+"\">"+
		"  <input type=\"hidden\" name=\"price\" value=\""+a.getPrice()+"\">"+
		"  <input type=\"hidden\" name=\"discount\" value=\""+a.getDiscount()+"\">"+
		"  <button type = \"submit\" value= \"+Cart\" class=\"addtocart\">+Cart</button>"+
		"</form>"+
		"</div>";

		}

		it.remove();

		}

		String headval= "<div class=\"other_menu\">Accessories</div>";
		main_content = headval+pre_first_carousal+first_content+post_first_carousal+pre_second_carousal+second_content+post_second_carousal;

		//System.out.println(main_content);
		return main_content;
	}
}
