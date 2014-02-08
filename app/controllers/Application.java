package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import play.*;
import play.mvc.*;
import views.html.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import main.*;

public class Application extends Controller {
   
    public static Result contact() {
        return ok(contact.render());
    }

    public static Result index() {
        Advert advert = AutoTraderAPI.getRandomAdvert();
        List<String> images = AutoTraderAPI.getAdvertImageLinks(advert.getId());
        Mongo db;
		try {
			db = new Mongo();
			String fake_id = db.insertAdvert(advert.getId(), advert.getPrice(), images);
			//int result = db.findAdvert("201401241261493");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
        
//        String image = "";
//        for(String imageLink: images) {
//            image = image + imageLink + "\n";
//        }
        return ok(index.render(images, advert.getPrice()));
    }

}
