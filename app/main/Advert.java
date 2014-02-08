package main;

/**
 * Created by Alexandru Grigoroi on 08/02/14.
 */
public class Advert {

    private String id;
    private int price;

    public Advert(String id, int price) {
        this.id = id;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }
}
