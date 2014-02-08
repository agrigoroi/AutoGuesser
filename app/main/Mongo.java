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

import java.util.Arrays;
import java.util.List;

public class Mongo 
{
	private DBCollection coll;
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
	
	public void insertAdvert(String id, int price, List<String> links)
	{
		BasicDBObject in = new BasicDBObject("id", id).
							append("price", price).
							append("links", links);
		coll.insert(in);
	}
	
	/*
	public int findMongo(String search)
	{
		BasicDBObject query = new BasicDBObject("id", search);
		
		DBCursor cursor = coll.find(query);
		DBO result;
		
		try{
			while(cursor.hasNext())
				result = cursor.next().get("price");
		}
		finally{
			cursor.close();
		}
		return result;		
	}// End findMongo */
	
	
}
