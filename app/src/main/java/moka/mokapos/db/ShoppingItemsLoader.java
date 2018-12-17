package moka.mokapos.db;

import android.content.Context;

import java.util.List;

import moka.mokapos.model.ShoppingItem;

public class ShoppingItemsLoader extends BaseDatabaseLoader<ShoppingItem> {

    ShoppingDatabase shoppingDataBase;
    public ShoppingItemsLoader(Context context, OnDataLoadedListener listener) {
        super(context, listener);
        shoppingDataBase = new ShoppingDatabase(context);
    }

    @Override
    protected List<ShoppingItem> doInBackground(Void... voids) {
        return shoppingDataBase.getAllData();
    }
}
