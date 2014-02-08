package main;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Alexandru Grigoroi on 08/02/14.
 */
public class AutoTraderAPI {

    public static String AUTO_TRADER_ACCESS_TOKEN = "yAELZNI2to-elvoxnf2PTWc6neStvwn-NqHfSP2WSqOfo7EFwu1Rk692UP8NomaCuk4LjwcbjcfttKdjZN8nly6bxE0zkpaWDOfEP_Y2yofx3ItuUvKFZ8EVCt23nSZzKhCp4GS6pUV1TH28yLK9N_1NxvVUPTzDoRIUyiVm0clXHhKYIvBdVmnQzx6H8aSuW09R8oJ5xqx9oJLkTC8ZTkCwgghomxQchDHZyagZowfAeCX1PKwkjVwAShCt7kgi";
    private static Random random = new Random();
//    public static String DEFAULT_IMAGES_URL = "http://pictures2.autotrader.co.uk/imgser-uk/servlet/media";
    public static String ADVERT_BASE_URL = "http://www.autotrader.co.uk/classified/advert/";
    public static String FIREFOX_USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0";
    public static String GALLERY_DIV_ID = "fpa-showroom-thumbnails";
    public static String BASE_IMAGE_URL = "http://pictures2.autotrader.co.uk/imgser-uk/servlet/media?id=";
    public static String SEARCH_URL = "http://www.autotrader.co.uk/search/used/cars/price-from/500/onesearchad/used%2Cnearlynew%2Cnew/page/";

    public static List<String> getAdvertImageLinks(String id) {
        try {
            Document page = Jsoup.connect(ADVERT_BASE_URL + id).userAgent(FIREFOX_USER_AGENT).get();
            Elements imgs = page.getElementById(GALLERY_DIV_ID).children().get(0).children();
            List<String> toReturn = new ArrayList<String>();
            for(Element img: imgs) {
                if(img.childNodeSize()>0)
                    toReturn.add(BASE_IMAGE_URL + img.child(0).attr("data-imageid"));
            }
            return toReturn;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
//        try {
//            System.out.println(id);
//            URL url = new URL("https://staging-cws.autotrader.co.uk/CoordinatedWebService/application/crs/sss/classified-advert/"+id);
//            HttpURLConnection con = (HttpURLConnection) url.openConnection();
//            con.setRequestMethod("GET");
//            con.setRequestProperty("Access-Token", AUTO_TRADER_ACCESS_TOKEN);
//            con.setRequestProperty("Accept", "application/json");
//            con.connect();
//            int status = con.getResponseCode();
//            System.out.println(status);
//            JsonNode node = null;
//            switch(status) {
//                case 200:
//                    node = (new ObjectMapper()).readTree(con.getInputStream());
//                    break;
//                default:
//                    return null;
//            }
//            return node;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
    }

    public static String getRandomAdvertId() {
//        return "201401191150754";
        int pageNumber = random.nextInt(100) + 1;
        try {
            Document page = Jsoup.connect(SEARCH_URL + pageNumber).userAgent(FIREFOX_USER_AGENT).get();
            Elements divs = page.getElementsByClass("resultsWrapper").get(0).children();
            List<String> advertIds = new ArrayList<String>();
            for(Element div: divs) {
                if(div.id().equals("")) {
                    for(Element car:div.children()) {
                        if(car.id().contains("advert"))
                            advertIds.add(car.id().replace("advert", ""));
                    }
                };
             }

            return advertIds.get(random.nextInt(advertIds.size()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }


//        try {
//            URL url = new URL("https://staging-cws.autotrader.co.uk/CoordinatedWebService/application/crs/sss/classified-adverts?Sort_Key=PlacementDate&Sort_Order=Desc");
//            HttpURLConnection con = (HttpURLConnection) url.openConnection();
//            con.setRequestMethod("GET");
//            con.setRequestProperty("Access-Token", AUTO_TRADER_ACCESS_TOKEN);
//            con.setRequestProperty("Accept", "application/json");
//            con.connect();
//            int status = con.getResponseCode();
////            System.out.println(status);
//            JsonNode node = null;
//            switch(status) {
//                case 200:
//                    node = (new ObjectMapper()).readTree(con.getInputStream());
//                    break;
//                default:
//                    return null;
//            }
//            node = node.get("searchResults").get("classifiedAdverts");
//            System.out.println(node.size());
//            return node.get(random.nextInt(node.size()));
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
    }

}
