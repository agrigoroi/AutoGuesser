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
import model.Advert;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;

public class Mongo 
{
	private DBCollection coll;
	private DBCollection coll1;
	private SecureRandom random = new SecureRandom();
	
	public Mongo()throws Exception
	{
		MongoClient mongoClient = new MongoClient( "widmore.mongohq.com" , 10000 );
		DB db = mongoClient.getDB( "StudentHack" );
		
		boolean auth = db.authenticate("studenthack", "jkgfhjgfdojnglegjdso".toCharArray());
		
		if(auth)		
			{  coll = db.getCollection("advert");
				coll1 = db.getCollection("players");
			}
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
	
	public Advert findAdvert(String search)
	{
		BasicDBObject query = new BasicDBObject("fake_id", search);
		
		DBCursor cursor = coll.find(query);
				
		try{
			while(cursor.hasNext())
				query = (BasicDBObject)cursor.next();
		}
		finally{
			cursor.close();
		}
		
		return new Advert((String)query.get("id"),(Integer)query.get("price"));
	}// End findMongo 
	
	public String insertPlayer(Player object)
	{
		Player rey = findPlayer(object.getId());
		if( rey != null)
			deletePlayer(object.getId());
		
		 BasicDBObject in = new BasicDBObject ("cookie", object.getId()).
				 							append("name", object.getName()).
				 							append("score", object.getTotalScore()).
				 							append("#games", object.getNumberOfGames());
		 coll1.insert(in);
		 return object.getId();
	}// End insertPLayer
	
	public Player findPlayer(String cookie)
	{
		BasicDBObject query = new BasicDBObject("cookie", cookie);
		DBCursor cursor = coll.find(query);
		
		try{
			if(cursor.hasNext())
				query = (BasicDBObject)cursor.next();
			else
			{
				cursor.close();
				return null;
			}
		}
		finally{
			cursor.close();
		}
		
		return new Player((String)query.get("name"),(String)query.get("cookie"), (Integer)query.get("score"), (Integer)query.get("#games"));
	}
	
	public void deletePlayer(String cookie)
	{
		BasicDBObject query = new BasicDBObject("cookie", cookie);
		coll.findAndRemove(query);
	}
}
