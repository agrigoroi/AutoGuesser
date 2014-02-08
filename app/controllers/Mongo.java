package controllers;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.ServerAddress;

import java.util.Arrays;

public class Mongo 
{
	private DBCollection coll;
	public Mongo()
	{
		MongoClient mongoClient = new MongoClient( "widmore.mongohq.com" , 10000 );
		DB db = mongoClient.getDB( "mydb" );
		
		boolean auth = db.authenticate("studenthack", "reallyStrongPassword");
		
		if(auth)		
			coll = db.getCollection("advert");
		else
			throw new Exception();
	}
	
	public void insertMongo(String id, int price, List<String> links)
	{
		BasicDBObject in = new BasicDBObject("id", id).
							append("price", price).
							append("links", links);
		coll.insert(doc);
	}
	
	public int findMongo(String search)
	{
		BasicDBObject query = new BasicDBObject("id", search);
		
		DBCursor cursor = coll.find(query).fetch();
		int result;
		
		try{
			while(cursor.hasNext())
				cursor.next();
			result = cursor.get().get("price");
		}
		finally{
			cursor.close();
		}
		return result;		
	}// End findMongo
	
	
}
