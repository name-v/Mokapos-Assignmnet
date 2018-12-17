package moka.mokapos.model;

public class Price {

    private static final int MIN_VALUE = 10;
    private static final int MAX_VALUE = 100;
    private static final String currencySymbolPrefix = "$ ";

    private final float value;

    private Price(int value){
        this.value = value;
    }

    public float getValue(){
        return value;
    }

    public String getDisplayPrice(){
        return currencySymbolPrefix + value;
    }

    //price based on item id
    public static Price getPriceForId(int id){
        return new Price(MIN_VALUE + 10 * id % MAX_VALUE);
    }
}
