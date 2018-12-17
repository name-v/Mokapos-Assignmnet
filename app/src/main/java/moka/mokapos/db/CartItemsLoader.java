package moka.mokapos.db;

import android.content.Context;

import java.util.List;

import moka.mokapos.model.CartItem;

public class CartItemsLoader extends BaseDatabaseLoader<CartItem> {

    CartDataBase cartDataBase;

    public CartItemsLoader(Context context, OnDataLoadedListener listener) {
        super(context, listener);
        cartDataBase = new CartDataBase(context);
    }

    @Override
    protected List<CartItem> doInBackground(Void... voids) {
        return cartDataBase.getAllData();
    }

}
