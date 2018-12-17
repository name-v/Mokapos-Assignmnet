package moka.mokapos.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import moka.mokapos.CartManager;
import moka.mokapos.R;
import moka.mokapos.model.CartItem;


public class CartFragment extends BaseFragment implements View.OnClickListener, CartManager.CartUpdateListener{

    TextView totalPriceDisplay;
    RecyclerView listView;

    @Override
    boolean shouldShowBack() {
        return false;
    }

    @Override
    int getHeaderTitle() {
        return R.string.header_title_shopping_cart;
    }

    @Override
    View getView(LayoutInflater inflater, ViewGroup container, Bundle savedInstances) {
        View view = inflater.inflate(R.layout.cart_layout, null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        totalPriceDisplay = getView().findViewById(R.id.total);
        listView = getView().findViewById(R.id.list_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(layoutManager);
        totalPriceDisplay.setText("Charge $" + CartManager.getInstance(getContext()).getTotalPrice());
        listView.setAdapter(new CartAdapter(getContext()));
        getView().findViewById(R.id.action_clear_cart).setOnClickListener(this);
        CartManager.getInstance(getContext()).addCartUpdateListener(this);
        updateView();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.action_clear_cart){
            CartManager.getInstance(getContext()).clearAllCart();
        }
    }

    @Override
    public void onItemAdded(CartItem item) {
        updateView();
    }

    @Override
    public void onItemRemoved(CartItem item) {
        updateView();
    }

    @Override
    public void onCartCleared() {
        if(getContext() != null) {
            Toast.makeText(getContext(), R.string.msg_cart_clear, Toast.LENGTH_SHORT).show();
            updateView();
        }
    }

    private void updateView(){
        if(totalPriceDisplay != null && getContext() != null){
            totalPriceDisplay.setText("Charge $" + String.format("%.2f",CartManager.getInstance(getContext()).getTotalPrice() -
                    CartManager.getInstance(getContext()).getTotalDiscount()));
        }
    }
}
