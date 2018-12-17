package moka.mokapos.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import moka.mokapos.CartManager;
import moka.mokapos.R;
import moka.mokapos.model.CartItem;
import moka.mokapos.model.Price;

public class CartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<CartItem> dataList;

    static int VIEW_TYPE_ITEM = 0;
    static int VIEW_TYPE_SUMMARY = 1;

    CartManager cartManager;

    public CartAdapter(Context context) {
        cartManager = CartManager.getInstance(context);
        dataList = cartManager.getCartList();

        cartManager.addCartUpdateListener(new CartManager.CartUpdateListener() {
            @Override
            public void onItemAdded(CartItem item) {
                dataList = cartManager.getCartList();
                notifyDataSetChanged();
            }

            @Override
            public void onItemRemoved(CartItem item) {
                dataList = cartManager.getCartList();
                notifyDataSetChanged();
            }

            @Override
            public void onCartCleared() {
                dataList = cartManager.getCartList();
                notifyDataSetChanged();
            }
        });
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        if (i == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_item_view, null);
            view.setLayoutParams(new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            return new CartItemView(view);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_item_summary_view, null);
            view.setLayoutParams(new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            return new CartItemSummaryView(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (position < dataList.size()) ? VIEW_TYPE_ITEM : VIEW_TYPE_SUMMARY;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        int viewType = getItemViewType(i);
        if (viewType == VIEW_TYPE_ITEM) {
            CartItemView cartItemView = (CartItemView) viewHolder;
            cartItemView.bind(dataList.get(i));
        } else {
            CartItemSummaryView cartItemSummaryView = (CartItemSummaryView) viewHolder;
            cartItemSummaryView.bind();
        }
    }

    @Override
    public int getItemCount() {
        int size = dataList.size();
        return size > 0 ? size + 1 : 0;
    }

    class CartItemView extends RecyclerView.ViewHolder{

        TextView item;
        TextView quantity;
        TextView discount;
        TextView price;

        public CartItemView(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.item_name);
            quantity = itemView.findViewById(R.id.item_quantity);
            discount = itemView.findViewById(R.id.item_discount);
            price = itemView.findViewById(R.id.item_price);
        }

        void bind(CartItem cartItem) {
            item.setText(cartItem.getDisplayName());
            quantity.setText("x" + cartItem.getQuantity());
            discount.setText(cartItem.getDiscount().getDisplayValue());
            price.setText(Price.getPriceForId(cartItem.getItemId()).getDisplayPrice());
        }

    }

    class CartItemSummaryView extends RecyclerView.ViewHolder {
        TextView discount;
        TextView total;

        public CartItemSummaryView(@NonNull View itemView) {
            super(itemView);
            discount = itemView.findViewById(R.id.discount_value);
            total = itemView.findViewById(R.id.total_bill);
        }

        void bind() {
            discount.setText("$ " + String.format("%.2f", cartManager.getTotalDiscount()));
            total.setText("$ " + cartManager.getTotalPrice());

        }
    }
}
