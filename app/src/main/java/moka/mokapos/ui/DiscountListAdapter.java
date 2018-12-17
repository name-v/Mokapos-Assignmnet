package moka.mokapos.ui;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import moka.mokapos.R;
import moka.mokapos.model.Discount;


public class DiscountListAdapter extends RecyclerView.Adapter <DiscountListAdapter.DiscountView>{

    List<Discount> data;
    public DiscountListAdapter(List<Discount> data){
        this.data = data;
    }

    @NonNull
    @Override
    public DiscountView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.discount_list_item_view, null);
        return new DiscountView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiscountView discountView, int i) {
        discountView.bind(data.get(i));
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    class DiscountView extends RecyclerView.ViewHolder {

        TextView label;
        TextView value;
        public DiscountView(@NonNull View itemView) {
            super(itemView);
            label = itemView.findViewById(R.id.discount_title);
            value = itemView.findViewById(R.id.discount_value);
        }

        void bind(Discount discount){
            label.setText(discount.getDisplayName());
            value.setText(discount.getDisplayValue());
        }

    }
}
