package moka.mokapos.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

import moka.mokapos.model.CartItem;
import moka.mokapos.model.Discount;

public class CartDataBase extends AbsDatabase<CartItem> {

    static final String COLUMN_NAME_ID = "uid";
    static final String COLUMN_NAME_ITEM_ID = "item_id";
    static final String COLUMN_NAME_ITEM_QUANTITY = "quantity";
    static final String COLUMN_NAME_DISCOUNT_APPLIED = "discount";

    static final String TABLE_NAME = "table_cart";

    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CartDataBase.COLUMN_NAME_ID  + TEXT_TYPE + COMMA_SEP +
                    CartDataBase.COLUMN_NAME_ITEM_ID  + INTEGER_TYPE + COMMA_SEP +
                    CartDataBase.COLUMN_NAME_DISCOUNT_APPLIED  + TEXT_TYPE + COMMA_SEP +
                    CartDataBase.COLUMN_NAME_ITEM_QUANTITY + INTEGER_TYPE +
                    ");";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;


    public CartDataBase(Context context) throws IllegalArgumentException {
        super(DatabaseHelper.getInstance(context));
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected void writeContentValue(CartItem cartItem, ContentValues values) {
        values.put(COLUMN_NAME_ID, String.valueOf(cartItem.getItemId()) + "_" + cartItem.getDiscount().getName());
        values.put(COLUMN_NAME_ITEM_ID, cartItem.getItemId());
        values.put(COLUMN_NAME_ITEM_QUANTITY, cartItem.getQuantity());
        values.put(COLUMN_NAME_DISCOUNT_APPLIED, cartItem.getDiscount().getName());
    }

    @Override
    protected String getUniqueKeyColumnName() {
        return COLUMN_NAME_ID;
    }

    @Override
    protected List<CartItem> getDataFromCursor(Cursor cursor) {
        List<CartItem> list = new ArrayList<>();
        if (cursor == null) return list;
        if (!cursor.moveToFirst()) {
            Log.w(TAG, "table is empty");
            return list;
        }
        try {
            do {
                CartItem item = new CartItem();
                item.setItemId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NAME_ID)));
                item.setQuantity(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NAME_ITEM_QUANTITY)));
                item.setDiscount(Discount.getByName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_DISCOUNT_APPLIED))));
                list.add(item);

            } while (cursor.moveToNext());
            return list;
        } catch (Exception e) {
            Log.w(TAG, e);
            return new ArrayList<>();
        } finally {
            try {
                cursor.close();
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }
}
