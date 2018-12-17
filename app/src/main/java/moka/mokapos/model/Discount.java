package moka.mokapos.model;

import java.util.ArrayList;
import java.util.List;

public class Discount {

    private final DISCOUNT discount;

    enum DISCOUNT{
        A(0),
        B(10),
        C(35.5f),
        D(50),
        E(100);

        private float value;
        DISCOUNT(float i) {
            this.value = i;
        }

        public float getValue() {
            return value;
        }
    }

    public static float getValueByName(String s){
        return DISCOUNT.valueOf(s).getValue();
    }

    public static Discount getByName(String name){
        return new Discount(DISCOUNT.valueOf(name));
    }

    public Discount(DISCOUNT discount){
        this.discount = discount;
    }

    public String getName(){
        return discount.name();
    }

    public float getValue(){
        return discount.getValue();
    }

    public String getDisplayName(){
        return "Discount " + discount.name();
    }

    public String getDisplayValue(){
        return discount.value + "%";
    }

    public static Discount getRandomDiscount() {
        double random = Math.random()*100;
        DISCOUNT [] discounts = DISCOUNT.values();
        return new Discount(discounts[(int)random % discounts.length]);
    }

    public static List<Discount> getAllDiscountList(){
        List<Discount> list = new ArrayList<>();
        for(DISCOUNT d : DISCOUNT.values()){
            list.add(new Discount(d));
        }
        return list;
    }
}
