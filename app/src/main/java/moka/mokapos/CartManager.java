package moka.mokapos;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import moka.mokapos.db.CartDataBase;
import moka.mokapos.model.CartItem;
import moka.mokapos.model.Discount;
import moka.mokapos.model.Price;

public class CartManager {

    Context context;
    CartDataBase db;

    public interface CartUpdateListener {

        void onItemAdded(CartItem item);

        void onItemRemoved(CartItem item);

        void onCartCleared();
    }

    List<CartUpdateListener> updateListeners;

    HashMap<String, CartItem> cartList;

    private static CartManager instance;

    public static CartManager getInstance(Context context) {
        if (instance == null) {
            instance = new CartManager(context);
        }

        return instance;
    }

    public void addCartUpdateListener(CartUpdateListener listener) {
        updateListeners.add(listener);
    }

    private CartManager(Context context) {
        this.context = context;
        db = new CartDataBase(context);
        List<CartItem> list = db.getAllData();
        cartList = new HashMap<>();
        updateListeners = new ArrayList<>();
        for (CartItem item : list) {
            cartList.put(getUniqueKey(String.valueOf(item.getItemId()), item.getDiscount()), item);
        }

    }

    public static String getUniqueKey(String itemId, Discount discount) {
        return itemId + "_" + discount.getName();
    }

    public void addItem(int itemId, Discount discount, int quantity) {

        String key = getUniqueKey(String.valueOf(itemId), discount);

        CartItem cartItem;
        if (cartList.containsKey(key)) {
            cartItem = cartList.get(key);
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            cartItem = new CartItem();
            cartItem.setItemId(itemId);
            cartItem.setQuantity(quantity);
            cartItem.setDiscount(discount);
        }
        cartList.put(key, cartItem);

        for (CartUpdateListener listener : updateListeners) {
            listener.onItemAdded(cartItem);
        }

        //Make DB Entry
        db.inserOrUpdate(cartItem, key);
    }

    public void updateItem(CartItem cartItem) {

    }

    public void removeItem(CartItem item) {
        cartList.remove(item);
        //update DB
        db.deleteFromDB(getUniqueKey(String.valueOf(item.getItemId()), item.getDiscount()));

        for (CartUpdateListener listener : updateListeners) {
            listener.onItemRemoved(item);
        }
    }

    public void saveCart() {
        db.addAll(getCartList());
    }

    public List<CartItem> getCartList() {
        return new ArrayList<>(cartList.values());
    }

    public float getTotalPrice() {
        float price = 0;
        for (CartItem item : cartList.values()) {
            Price p = Price.getPriceForId(item.getItemId());
            price += p.getValue() * item.getQuantity();
        }
        return price;
    }

    public float getTotalDiscount() {
        float discount = 0;
        for (CartItem item : cartList.values()) {
            Price p = Price.getPriceForId(item.getItemId());
            discount += p.getValue() * item.getDiscount().getValue() * item.getQuantity() / 100;
        }
        return discount;
    }

    public void clearAllCart() {
        int size = cartList.size();
        cartList.clear();
        //perform clear action
        if (size > 0) {
            db.clearAllData();
            for (CartUpdateListener listener : updateListeners) {
                listener.onCartCleared();
            }
        }
    }
}
