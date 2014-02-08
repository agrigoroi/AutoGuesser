package main;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.ServerAddress;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

public class Mongo 
{
	private DBCollection coll;
	private SecureRandom random = new SecureRandom();
	
	public Mongo()throws Exception
	{
		MongoClient mongoClient = new MongoClient( "widmore.mongohq.com" , 10000 );
		DB db = mongoClient.getDB( "StudentHack" );
		
		boolean auth = db.authenticate("studenthack", "jkgfhjgfdojnglegjdso".toCharArray());
		
		if(auth)		
			coll = db.getCollection("advert");
		else
			throw new Exception();
	}
	
	public String insertAdvert(String id, int price, List<String> links)
	{
		String rand = new BigInteger(40, random).toString();
		BasicDBObject in = new BasicDBObject("id", id).
							append("fake_id", rand).
							append("price", price).
							append("links", links);
		coll.insert(in);
		return rand;
	}
	
	public int findAdvert(String search)
	{
		BasicDBObject query = new BasicDBObject("id", search);
		
		DBCursor cursor = coll.find(query);
				
		try{
			while(cursor.hasNext())
				query = (BasicDBObject)cursor.next();
		}
		finally{
			cursor.close();
		}
		
		return (int)query.get("price");
	}// End findMongo 
	
}