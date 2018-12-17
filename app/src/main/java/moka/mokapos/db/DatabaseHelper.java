package moka.mokapos.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "mokapos.db";

    private static DatabaseHelper instance;

    static String [] CREATE = new String[]{ShoppingDatabase.CREATE_TABLE, CartDataBase.CREATE_TABLE};
    static String [] DROP = new String[]{ShoppingDatabase.DROP_TABLE, CartDataBase.DROP_TABLE};

    public static DatabaseHelper getInstance(Context context){
        if(instance == null){
            instance = new DatabaseHelper(context);
        }
        return instance;
    }

    private DatabaseHelper (Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        for(String s : CREATE){
            sqLiteDatabase.execSQL(s);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        for (String s : DROP) {
            sqLiteDatabase.execSQL(s);
        }
        onCreate(sqLiteDatabase);
    }
}
