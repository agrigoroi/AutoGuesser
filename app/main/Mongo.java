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
import model.Player;
import main.MongoSecret;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class Mongo 
{
	private DBCollection advertCollection;
	private DBCollection playerCollection;
	private SecureRandom random = new SecureRandom();
	
	public Mongo()throws Exception
	{
		MongoClient mongoClient = new MongoClient(MongoSecret.HOST);
		DB db = mongoClient.getDB("StudentHack");
		
		boolean auth = db.authenticate(MongoSecret.USERNAME, MongoSecret.PASSWORD.toCharArray());
		
		if(auth){
            advertCollection = db.getCollection("advert");
			playerCollection = db.getCollection("players");
        } else
			throw new Exception();
	}
	
	public String insertAdvert(String id, int price, List<String> links)
	{
		String rand = new BigInteger(40, random).toString();
		BasicDBObject in = new BasicDBObject("id", id).
							append("fake_id", rand).
							append("price", price).
							append("links", links);
		advertCollection.insert(in);
		return rand;
	}
	
	public Advert findAdvert(String search)
	{
		BasicDBObject query = new BasicDBObject("fake_id", search);
		
		DBCursor cursor = advertCollection.find(query);
				
		if(cursor.hasNext()) {
            query = (BasicDBObject)cursor.next();
            cursor.close();
        } else {
            cursor.close();
            return null;
        }
		
		return new Advert((String)query.get("id"),(Integer)query.get("price"));
	}// End findMongo 
	
	public void deleteAdvert(String search)
	{
		BasicDBObject query = new BasicDBObject("fake_id", search);
		advertCollection.findAndRemove(query);
	}
	
	public String insertPlayer(Player object)
	{
		Player rey = findPlayer(object.getId());
		if( rey != null)
			deletePlayer(object.getId());
		
		 BasicDBObject in = new BasicDBObject ("cookie", object.getId()).
				 							append("name", object.getName()).
				 							append("score", object.getTotalScore()).
				 							append("#games", object.getNumberOfGames());
		 playerCollection.insert(in);
		 return object.getId();
	}// End insertPLayer
	
	public Player findPlayer(String cookie)
	{
		BasicDBObject query = new BasicDBObject("cookie", cookie);
		DBCursor cursor = playerCollection.find(query);

        if(cursor.hasNext()) {
            query = (BasicDBObject)cursor.next();
            cursor.close();
        } else {
            cursor.close();
            return null;
        }
		return new Player((String)query.get("name"),(String)query.get("cookie"), (Integer)query.get("score"), (Integer)query.get("#games"));
	}
	
	public void deletePlayer(String cookie)
	{
		BasicDBObject query = new BasicDBObject("cookie", cookie);
		playerCollection.findAndRemove(query);
	}
	
	public ArrayList<Player> leaderboard()
	{
		ArrayList<Player> top10 = new ArrayList<Player>();
		BasicDBObject query = new BasicDBObject();
		DBCursor cursor = playerCollection.find(query);
		cursor.sort(new BasicDBObject("score", -1));
			
		int i =0;
		while(cursor.hasNext() && i<10)
		{
			i++;
			query = (BasicDBObject)cursor.next();
			top10.add(new Player((String)query.get("name"),(String)query.get("cookie"), (Integer)query.get("score"), (Integer)query.get("#games")));
		}
		return top10;
	}
	
	
}
