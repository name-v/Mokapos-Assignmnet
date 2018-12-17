package moka.mokapos.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import moka.mokapos.model.ShoppingItem;


public class ShoppingDatabase extends AbsDatabase<ShoppingItem> {

    static final String TABLE_NAME = "table_items";

    static final String COLUMN_NAME_ID = "item_id";
    static final String COLUMN_NAME_ALBUM_ID = "albunmId";
    static final String COLUMN_NAME_TITLE = "title";
    static final String COLUMN_NAME_URL = "url";
    static final String COLUMN_NAME_THUMBNAIL_URL = "thumbnailUrl";

    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME_ID  + INTEGER_TYPE + COMMA_SEP +
                    COLUMN_NAME_ALBUM_ID + INTEGER_TYPE + COMMA_SEP +
                    COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    COLUMN_NAME_URL + TEXT_TYPE + COMMA_SEP +
                    COLUMN_NAME_THUMBNAIL_URL + TEXT_TYPE +
                    ");";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public ShoppingDatabase(Context context) throws IllegalArgumentException {
        super(DatabaseHelper.getInstance(context));
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected void writeContentValue(ShoppingItem shoppingItem, ContentValues values) {
        values.put(COLUMN_NAME_ID, shoppingItem.getId());
        values.put(COLUMN_NAME_ALBUM_ID, shoppingItem.getAlbumId());
        values.put(COLUMN_NAME_TITLE, shoppingItem.getTitle());
        values.put(COLUMN_NAME_URL, shoppingItem.getUrl());
        values.put(COLUMN_NAME_THUMBNAIL_URL, shoppingItem.getThumbNailUrl());
    }

    @Override
    protected String getUniqueKeyColumnName() {
        return COLUMN_NAME_ID;
    }

    @Override
    protected List<ShoppingItem> getDataFromCursor(Cursor cursor) {
        List<ShoppingItem> list = new ArrayList<>();
        if (cursor == null) return list;
        if (!cursor.moveToFirst()) {
            Log.w(TAG, "table is empty");
            return list;
        }
        try {
            do {
                ShoppingItem item = new ShoppingItem();
                item.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NAME_ID)));
                item.setAlbumId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NAME_ALBUM_ID)));
                item.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_TITLE)));
                item.setUrl(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_URL)));
                item.setThumbNailUrl(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_THUMBNAIL_URL)));
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
