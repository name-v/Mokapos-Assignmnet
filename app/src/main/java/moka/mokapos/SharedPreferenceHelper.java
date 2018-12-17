package moka.mokapos;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceHelper {
    public static final String KEY_ITEMS_FETCHED = "com.mokapos.ItemsFetched";

    private static SharedPreferenceHelper helper;

    SharedPreferences sharedPreferences;

    public static SharedPreferenceHelper getInstance(Context context){
        if (helper == null){
            helper = new SharedPreferenceHelper(context);
        }

        return helper;
    }
    private SharedPreferenceHelper(Context context){
        sharedPreferences = context.getSharedPreferences("mokapos_pref", Context.MODE_PRIVATE);
    }

    public void setItemsFetched(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_ITEMS_FETCHED, true);
        editor.apply();
    }

    public boolean itemsFetched(){
        return sharedPreferences.getBoolean(KEY_ITEMS_FETCHED, false);
    }
}
