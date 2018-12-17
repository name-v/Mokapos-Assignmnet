package moka.mokapos;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import moka.mokapos.db.ShoppingDatabase;
import moka.mokapos.model.ShoppingItem;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
 * Helper class to download data and save to database
 * */
public class DownloadHelper {

    private OnCompleteListener listener;
    private Context context;

    public interface OnCompleteListener {
        void onSuccess();
    }


    public DownloadHelper(final Context context, OnCompleteListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void downloadAndSaveToDB(){
        ServiceManager.getInstance().getService().getItems().enqueue(new Callback<List<ShoppingItem>>() {
            @Override
            public void onResponse(Call<List<ShoppingItem>> call, Response<List<ShoppingItem>> response) {
                new WriteToDatabaseTask(context).execute(response.body());
            }

            @Override
            public void onFailure(Call<List<ShoppingItem>> call, Throwable t) {
                Log.e("MainActivity", "onFailure " + t.getMessage());
            }
        });
    }

    class WriteToDatabaseTask extends AsyncTask<List<ShoppingItem>, Void, Void> {

        Context context;
        ShoppingDatabase db;

        public WriteToDatabaseTask(Context context) {
            this.context = context;
            this.db = new ShoppingDatabase(context);
        }

        @Override
        protected Void doInBackground(List<ShoppingItem>... list) {
            db.addAll(list[0]);
            SharedPreferenceHelper.getInstance(context).setItemsFetched();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (listener != null) {
                listener.onSuccess();
            }
        }
    }

}
