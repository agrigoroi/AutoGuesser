package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;

import model.Advert;
import model.Player;
import play.libs.Json;
import play.mvc.*;
import views.html.*;

import java.util.List;

import main.*;

public class Application extends Controller {
   
    public static Result contact() {
        return ok(contact.render());
    }
    
    public static Result rules() {
        return ok(rules.render());
    }

    private static Player getPlayer() {
        Http.Cookie cookieCokie =request().cookie("user");
        Player player = null;
        if(cookieCokie != null) {
            String cookie = cookieCokie.value();
            player = Global.mongo.findPlayer(cookie);
        }
        if(player == null) {
            player = new Player();
            Global.mongo.insertPlayer(player);
        }
        response().setCookie("user", player.getId());
        return player;
    }

    private static int calculateScore(int guess, int price) {
        if(guess > price)
            guess = 2*price - guess; //TODO make sure that its right...
        return (guess*10-1)/price+1;
    }


    public static Result index() {
        Player player = getPlayer();
        Advert advert = AutoTraderAPI.getRandomAdvert();
        List<String> images = AutoTraderAPI.getAdvertImageLinks(advert.getId());
	   String fake_id = Global.mongo.insertAdvert(advert.getId(), advert.getPrice(), images);
//			int result = db.findAdvert("201401241261493");
        if (images.isEmpty()) return index();
        return ok(index.render(images, fake_id, player));
    }

    public static Result checkPrice(String id, int price) {
        Player player = getPlayer();
        player.setNumberOfGames(player.getNumberOfGames() + 1);
        Advert advert = Global.mongo.findAdvert(id);
        player.setTotalScore(player.getTotalScore() + calculateScore(price, advert.getPrice()));
        Global.mongo.insertPlayer(player);
        ObjectNode result = Json.newObject();
//        result.put("advertUrl", AutoTraderAPI.)
        result.put("realPrice", advert.getPrice());
        result.put("url", AutoTraderAPI.BASE_ADVERT_URL+advert.getId());
        return ok(result);
    }
}
