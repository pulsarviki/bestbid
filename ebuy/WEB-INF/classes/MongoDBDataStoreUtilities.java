import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bson.Document;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoDBDataStoreUtilities {

	static DBCollection myReviews;
	static MongoCollection<Document> myReviewsCollection ;
	static MongoClient mongo;
	static String ALL = "ALL";

	public static void getConnection()
	{

		mongo = new MongoClient("localhost", 27017);
		DB db = mongo.getDB("smartportables");
		myReviews= db.getCollection("myReviews");

	}

	public static void destroyMongoConnection()
	{
		mongo.close();
	}

	public static HashMap<String, Integer> topLikedProducts()
	{
		if(mongo==null||myReviews==null)
		{
			getConnection();
		}
		HashMap<String, Integer> reviewHashmap=new HashMap<String, Integer>();
		DBObject groupFields = new BasicDBObject("_id", 0);
		DBObject setFields = new BasicDBObject("_id", 0);
		DBObject limit=new BasicDBObject();
		DBObject orderby=new BasicDBObject();
		DBObject sort = new BasicDBObject();

		setFields.put("productName", "$_id");
		setFields.put("reviewRating", "$likes");
		DBObject project = new BasicDBObject("$project", setFields);
		groupFields.put("_id", "$productName");
		groupFields.put("likes", new BasicDBObject("$sum", 1));
		DBObject group = new BasicDBObject("$group", groupFields);

		sort.put("reviewRating",-1);
		DBObject match = new BasicDBObject("$match", new BasicDBObject("reviewRating", 5));

		//Adding sort object in DbObject
		orderby=new BasicDBObject("$sort",sort);
		limit=new BasicDBObject("$limit",5);

		AggregationOutput aggregate = myReviews.aggregate(match,  group, project, limit, orderby);

		String reviewLikes = null;
		int reviewLikesCount;
		for (DBObject result : aggregate.results()) {
			BasicDBObject bobj = (BasicDBObject) result;
			System.out.println(bobj.getString("productName"));
			reviewLikes = bobj.getString("reviewRating");
			if(reviewLikes == null || reviewLikes.isEmpty()) {
				reviewLikesCount=0;
			} else {
				reviewLikesCount=Integer.parseInt(bobj.getString("reviewRating"));
			}
			System.out.println(reviewLikesCount);
			reviewHashmap.put(bobj.getString("productName"), reviewLikesCount);

		}

		return reviewHashmap;
	}


	public static HashMap<String, Integer> topZipCodes()
	{
		if(mongo==null||myReviews==null)
		{
			getConnection();
		}
		HashMap<String, Integer> reviewHashmap=new HashMap<String, Integer>();

		DBObject groupFields = new BasicDBObject("_id", 0);
		groupFields.put("_id", "$retailerZip");
		groupFields.put("count", new BasicDBObject("$sum", 1));
		DBObject group = new BasicDBObject("$group", groupFields);

		DBObject setFields = new BasicDBObject("_id", 0);
		setFields.put("retailerZip", "$_id");
		setFields.put("count", "$count");
		DBObject project = new BasicDBObject("$project", setFields);

		DBObject limit=new BasicDBObject();
		DBObject orderby=new BasicDBObject();

		DBObject sort = new BasicDBObject();
		// Specify the field that you want to sort on, and the direction of the sort
		sort.put("count",-1);

		//Adding sort object in DbObject
		orderby=new BasicDBObject("$sort",sort);
		limit=new BasicDBObject("$limit",5);

		AggregationOutput aggregate = myReviews.aggregate(group, project, limit, orderby);

		String reviewCount = null;
		int reviewCountCount;
		for (DBObject result : aggregate.results()) {
			BasicDBObject bobj = (BasicDBObject) result;
			System.out.println(bobj.getString("retailerZip"));
			reviewCount = bobj.getString("count");
			if(reviewCount == null || reviewCount.isEmpty()) {
				reviewCountCount=0;
			} else {
				reviewCountCount=Integer.parseInt(reviewCount);
			}
			System.out.println(reviewCountCount);
			reviewHashmap.put(bobj.getString("retailerZip"), reviewCountCount);

		}

		return reviewHashmap;
	}

	public static HashMap<String, Integer> topProducts()
	{
		if(mongo==null||myReviews==null)
		{
			getConnection();
		}
		HashMap<String, Integer> reviewHashmap=new HashMap<String, Integer>();

		DBObject groupFields = new BasicDBObject("_id", 0);
		groupFields.put("_id", "$productName");
		groupFields.put("count", new BasicDBObject("$sum", 1));
		DBObject group = new BasicDBObject("$group", groupFields);

		DBObject setFields = new BasicDBObject("_id", 0);
		setFields.put("productName", "$_id");
		setFields.put("count", "$count");
		DBObject project = new BasicDBObject("$project", setFields);

		DBObject limit=new BasicDBObject();
		DBObject orderby=new BasicDBObject();

		DBObject sort = new BasicDBObject();
		// Specify the field that you want to sort on, and the direction of the sort
		sort.put("count",1);


		//Adding sort object in DbObject
		orderby=new BasicDBObject("$sort",sort);
		limit=new BasicDBObject("$limit",5);

		AggregationOutput aggregate = myReviews.aggregate(group, project, limit, orderby);

		String reviewCount = null;
		int reviewCountCount;
		for (DBObject result : aggregate.results()) {
			BasicDBObject bobj = (BasicDBObject) result;
			System.out.println(bobj.getString("productName"));
			reviewCount = bobj.getString("count");
			if(reviewCount == null || reviewCount.isEmpty()) {
				reviewCountCount=0;
			} else {
				reviewCountCount=Integer.parseInt(reviewCount);
			}
			System.out.println(reviewCountCount);
			reviewHashmap.put(bobj.getString("productName"), reviewCountCount);

		}

		return reviewHashmap;
	}

	public static HashMap<String, ArrayList<Review>> selectReviewsDyanamically(boolean isNameSelected, String productName)
	{
		if(mongo==null||myReviews==null)
		{
			getConnection();
		}

		HashMap<String, ArrayList<Review>> reviewHashmap=new HashMap<String, ArrayList<Review>>();

		BasicDBObject query = new BasicDBObject();

		if(isNameSelected) {
			if(productName.equals(ALL)) {

			} else {
				query.put("productName", productName);
			}
		}

		DBCursor cursor = myReviews.find(query);

		System.out.println(query);
		while (cursor.hasNext())
		{
			BasicDBObject obj = (BasicDBObject) cursor.next();
			if(! reviewHashmap.containsKey(obj.getString("productName")))
			{
				ArrayList<Review> arr = new ArrayList<Review>();
				reviewHashmap.put(obj.getString("productName"), arr);
			}
			ArrayList<Review> listReview = reviewHashmap.get(obj.getString("productName"));
			Review review = new Review(obj.getString("productName"), obj.getString("category"), obj.getDouble("price"),
					obj.getString("retailerName"), obj.getInt("retailerZip"), obj.getString("retailerCity"), obj.getString("retailerState"),
					obj.getString("productOnSale"), obj.getString("manufacturerName"), obj.getString("manufacturerRebate"),
					obj.getString("userName"), obj.getInt("userAge"), obj.getString("userGender"),
					obj.getString("userOccupation"), obj.getInt("reviewRating"), obj.getDate("reviewDate"), obj.getString("reviewText"));
			listReview.add(review);
			System.out.println(review);
			System.out.println(obj.getString("productName")+" :::: " +listReview);

		}
		return reviewHashmap;
	}

	public static void insertReview(Review r)
	{
			if(mongo==null||myReviews==null)
			{
				getConnection();
			}
			BasicDBObject doc = new BasicDBObject("title", "myReviews").
			append("productName", r.getProductName()).
			append("category", r.getCategory()).
			append("price", r.getPrice()).
			append("retailerName", r.getRetailerName()).
			append("retailerZip",r.getRetailerZip()).
			append("retailerCity", r.getRetailerCity()).
			append("retailerState", r.getRetailerState()).
			append("productOnSale", r.isProductOnSale()).
			append("manufacturerName", r.getManufacturerName()).
			append("manufacturerRebate", r.isManufacturerRebate()).
			append("userName", r.getUserName()).
			append("userAge", r.getUserAge()).
			append("userGender", r.getUserGender()).
			append("userOccupation", r.getUserOccupation()).
			append("reviewRating", r.getReviewRating()).
			append("reviewDate", r.getReviewDate()).
			append("reviewText", r.getReviewText());
			myReviews.insert(doc);
	}

}
