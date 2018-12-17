package moka.mokapos.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import moka.mokapos.R;
import moka.mokapos.model.Discount;

public class DiscountFragment extends BaseFragment{

    @Override
    boolean shouldShowBack() {
        return true;
    }

    @Override
    int getHeaderTitle() {
        return R.string.header_title_discounts;
    }

    @Override
    View getView(LayoutInflater inflater, ViewGroup container, Bundle savedInstances) {
        View view = inflater.inflate(R.layout.simple_list_layout, null);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(params);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        RecyclerView listView = getView().findViewById(R.id.list_view);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(manager);
        listView.setAdapter(new DiscountListAdapter(Discount.getAllDiscountList()));
    }
}
