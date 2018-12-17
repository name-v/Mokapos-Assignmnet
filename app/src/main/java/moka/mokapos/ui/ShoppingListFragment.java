package moka.mokapos.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import moka.mokapos.R;
import moka.mokapos.db.BaseDatabaseLoader;
import moka.mokapos.db.ShoppingItemsLoader;

public class ShoppingListFragment extends BaseFragment {

    RecyclerView listView;

    private ShoppingItemListAdapter.OnItemSelectedListener listener;

    public ShoppingListFragment(){
        super();
    }

    public void setOnItemSelectedListener(ShoppingItemListAdapter.OnItemSelectedListener listener){
        this.listener = listener;
    }

    @Override
    boolean shouldShowBack() {
        return true;
    }

    @Override
    int getHeaderTitle() {
        return R.string.header_title_items;
    }

    @Override
    View getView(LayoutInflater inflater, ViewGroup container, Bundle savedInstances) {
        return inflater.inflate(R.layout.simple_list_layout, null);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView = getView().findViewById(R.id.list_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(layoutManager);
        loadData();
    }

    private void loadData(){
        new ShoppingItemsLoader(getContext(), new BaseDatabaseLoader.OnDataLoadedListener() {
            @Override
            public void onDataLoaded(List dataList) {
                listView.setAdapter(new ShoppingItemListAdapter(getContext(), dataList, listener));
            }
        }).load();
    }
}
