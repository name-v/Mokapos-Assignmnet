package moka.mokapos.db;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

public abstract class BaseDatabaseLoader<T> extends AsyncTask<Void, Void, List<T>> {


    public interface OnDataLoadedListener<T> {
        void onDataLoaded(List<T> dataList);
    }
    protected OnDataLoadedListener listener;
    protected Context context;


    public BaseDatabaseLoader(Context context, OnDataLoadedListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected void onPostExecute(List<T> t) {
        super.onPostExecute(t);
        if(listener != null){
            listener.onDataLoaded(t);
        }
    }

    public void load(){
        executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
    }
}
