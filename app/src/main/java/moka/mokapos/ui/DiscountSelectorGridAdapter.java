package moka.mokapos.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import java.util.List;

import moka.mokapos.R;
import moka.mokapos.model.Discount;

public class DiscountSelectorGridAdapter extends RecyclerView.Adapter<DiscountSelectorGridAdapter.RadioView> {

    public interface OnDiscountAppliedListener{
        void onDiscountApplie(Discount discount);
    }

    private OnDiscountAppliedListener listener;

    List<Discount> dataList;
    int selectedPosition = 0;

    public DiscountSelectorGridAdapter(OnDiscountAppliedListener listener, Discount defaultDiscount){

        this.listener = listener;
        dataList = Discount.getAllDiscountList();
        for(int i = 0; i <  dataList.size(); i++){
            if(defaultDiscount.getName().equals(dataList.get(i).getName())){
                selectedPosition = i;
            }
        }
    }

    @NonNull
    @Override
    public RadioView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new RadioView(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.discount_select_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull RadioView radioView, int i) {
        radioView.bind(dataList.get(i), i);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    class RadioView extends RecyclerView.ViewHolder implements View.OnClickListener {

        Switch button;
        int position;
        public RadioView(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.switch_btn);
            itemView.findViewById(R.id.container).setOnClickListener(this);
        }

        void bind(Discount d, int position){
            button.setChecked(position == selectedPosition);
            button.setText(d.getDisplayName() + " " + d.getDisplayValue());
            this.position = position;
        }

        @Override
        public void onClick(View view) {
            if(position != selectedPosition){
                selectedPosition = position;
                listener.onDiscountApplie(dataList.get(position));
                notifyDataSetChanged();
            }
        }
    }

}
