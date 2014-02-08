package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.*;
import play.libs.Json;
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
//        String image = "";
//        for(String imageLink: images) {
//            image = image + imageLink + "\n";
//        }
        if (images.isEmpty()) return index();
        return ok(index.render(images, advert.getId()));
    }

    public static Result checkPrice(String id, int price) {
        System.out.println(id + ":" + price);
        ObjectNode result = Json.newObject();
        result.put("realPrice", 1000);
        return ok(result);
    }
}
