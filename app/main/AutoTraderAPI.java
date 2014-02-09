package main;

import model.Advert;
import org.jsoup.Jsoup;
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

    private static final String AUTO_TRADER_ACCESS_TOKEN = "yAELZNI2to-elvoxnf2PTWc6neStvwn-NqHfSP2WSqOfo7EFwu1Rk692UP8NomaCuk4LjwcbjcfttKdjZN8nly6bxE0zkpaWDOfEP_Y2yofx3ItuUvKFZ8EVCt23nSZzKhCp4GS6pUV1TH28yLK9N_1NxvVUPTzDoRIUyiVm0clXHhKYIvBdVmnQzx6H8aSuW09R8oJ5xqx9oJLkTC8ZTkCwgghomxQchDHZyagZowfAeCX1PKwkjVwAShCt7kgi";
    private static final Random random = new Random();
//    public static String DEFAULT_IMAGES_URL = "http://pictures2.autotrader.co.uk/imgser-uk/servlet/media";
    private static final String ADVERT_BASE_URL = "http://www.autotrader.co.uk/classified/advert/";
    private static final String FIREFOX_USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0";
    private static final String GALLERY_DIV_ID = "fpa-showroom-thumbnails";
    private static final String BASE_IMAGE_URL = "http://pictures2.autotrader.co.uk/imgser-uk/servlet/media?id=";
    private static final String SEARCH_URL = "http://www.autotrader.co.uk/search/used/cars/postcode/m12bj/radius/1501/price-from/500/sort/locasc/page/";
    private static final String SORT_PARAMETER = "";
    private static final int IMAGE_HEIGHT = 800;
    private static final int SEARCH_NUMBER_OF_PAGES = 1000;
    public static final String BASE_ADVERT_URL = "http://www.autotrader.co.uk/classified/advert/";

    public static List<String> getAdvertImageLinks(String id) {
        try {
            Document page = Jsoup.connect(ADVERT_BASE_URL + id).userAgent(FIREFOX_USER_AGENT).get();
            Elements imgs = page.getElementById(GALLERY_DIV_ID).children().get(0).children();
            List<String> toReturn = new ArrayList<String>();
            for(Element img: imgs) {
                if(img.childNodeSize()>0)
                    toReturn.add(BASE_IMAGE_URL + img.child(0).attr("data-imageid"));// + "&height=" + IMAGE_HEIGHT); //+ "&width=" + IMAGE_WIDTH
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

    public static Advert getRandomAdvert() {
//        return "201401191150754";
        int pageNumber = random.nextInt(SEARCH_NUMBER_OF_PAGES) + 1;
        try {
            Document page = Jsoup.connect(SEARCH_URL + pageNumber + SORT_PARAMETER).userAgent(FIREFOX_USER_AGENT).get();
            Elements divs = page.getElementsByClass("resultsWrapper").get(0).children();
            List<Advert> adverts = new ArrayList<Advert>();
            for(Element div: divs) {
                if(div.id().equals("")) {
                    for(Element car:div.children()) {
                        if(car.id().contains("advert")) {
                            String id = car.id().replace("advert", "");
                            Element priceElement = car.getElementsByClass("deal-price").get(0);
                            String price = priceElement.html().replaceAll("\\D+", "");
                            adverts.add(new Advert(id, Integer.parseInt(price)));
                        }
                    }
                }
             }
            return adverts.get(random.nextInt(adverts.size()));
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
