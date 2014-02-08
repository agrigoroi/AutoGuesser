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
import java.util.List;
import java.util.Random;
import main.*;

public class Application extends Controller {

    public static Result index() {
//        List<String> images = AutoTraderAPI.getAdvertImageLinks(AutoTraderAPI.getRandomAdvertId());
////        System.out.println(AutoTraderAPI.getRandomAdvertId());
//        String image = "";
//        for(String imageLink: images) {
//            image = image += imageLink += "\n";
//        }
//        return ok(image);
        return ok(index.render());
    }
   
    public static Result contact() {
        return ok(contact.render());
    }

}
