package moka.mokapos;

import java.util.List;

import moka.mokapos.model.ShoppingItem;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class ServiceManager {

    private static ServiceManager instance;
    private static String BASE_URL = "https://jsonplaceholder.typicode.com";
    private MokaService service;

    public static ServiceManager getInstance() {
        if (instance == null) {
            instance = new ServiceManager();
        }
        return instance;
    }

    private ServiceManager() {
        Retrofit retrofit = new Retrofit.Builder().
                baseUrl(BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).build();
        service = retrofit.create(MokaService.class);
    }

    public MokaService getService() {
        return service;
    }

    public interface MokaService {
        @GET("/photos")
        Call<List<ShoppingItem>> getItems();
    }
}
