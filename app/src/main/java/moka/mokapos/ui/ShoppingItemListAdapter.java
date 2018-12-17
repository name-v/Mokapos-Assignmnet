package moka.mokapos.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import moka.mokapos.R;
import moka.mokapos.model.Price;
import moka.mokapos.model.ShoppingItem;

public class ShoppingItemListAdapter extends RecyclerView.Adapter<ShoppingItemListAdapter.ListItem>{

    List<ShoppingItem> dataList;
    Context context;

    private OnItemSelectedListener listener;

    public interface OnItemSelectedListener{
        void onItemSelected(ShoppingItem item);
    }

    public ShoppingItemListAdapter(Context context, List<ShoppingItem> list, OnItemSelectedListener listener){
        this.context = context;
        this.dataList = list;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ListItem onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.shopping_list_item_view, null);

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(params);
        return new ListItem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItem listItem, int i) {
        listItem.bind(dataList.get(i));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ListItem extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView titleTextView;
        ImageView imageThumbnailView;
        TextView priceTextView;
        ShoppingItem item;

        public ListItem(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            titleTextView = itemView.findViewById(R.id.display_title);
            imageThumbnailView = itemView.findViewById(R.id.item_thumbnail);
            priceTextView = itemView.findViewById(R.id.price_view);
        }


        void bind(ShoppingItem item){
            this.item = item;
            Picasso.with(context).load(item.getThumbNailUrl()).into(imageThumbnailView);
            titleTextView.setText(item.getDisplayTitle());
            priceTextView.setText(Price.getPriceForId(item.getId()).getDisplayPrice());
        }

         @Override
         public void onClick(View view) {
             if(listener != null){
                 listener.onItemSelected(item);
             }
         }
     }

}
