package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;

import model.Advert;
import model.Player;
import play.libs.Json;
import play.mvc.*;
import views.html.*;

import java.util.ArrayList;
import java.util.List;



import main.*;

public class Application extends Controller {
   
    public static Result contact() {
        return ok(contact.render());
    }
    
    public static Result rules() {
    	ArrayList<Player> toDisplay = Global.mongo.leaderboard();
    	// Convert into JSON array
    	
    	//JSONArray array = new JSONArray(toDisplay);
    	return ok(rules.render(toDisplay));
        
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
        if(player.getNumberOfGames() >= 10) {
            player = new Player();
            Global.mongo.insertPlayer(player);
        }
        response().setCookie("user", player.getId());
        return player;
    }

    private static int min(int a, int b) {
        return a<b?a:b;
    }

    private static int calculateScore(int guess, int price) {
        if(guess > price)
            guess = 2*price - guess; //TODO make sure that its right...
        if(guess <= 0) guess = 1;
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
        if(advert == null) {
            return badRequest();
        }
        Global.mongo.deleteAdvert(id);
        int ScoreRound = calculateScore(price, advert.getPrice());
        player.setTotalScore(player.getTotalScore() + ScoreRound);
        Global.mongo.insertPlayer(player);
        ObjectNode result = Json.newObject();
        result.put("realPrice", advert.getPrice());
        result.put("url", AutoTraderAPI.BASE_ADVERT_URL+advert.getId());
        result.put("score", player.getTotalScore());
        result.put("round", player.getNumberOfGames());
        result.put("roundScore",ScoreRound);
        return ok(result);
    }
    
    public static Result submitName(String name)
    {
    	Player player = getPlayer();
    	player.setName(name);
    	Global.mongo.insertPlayer(player);
    	return ok();
    }
    
    public static Result reset()
    {
    	Player player = new Player();
        Global.mongo.insertPlayer(player);
        response().setCookie("user", player.getId());
    	return redirect(routes.Application.index());
    }
 
}
