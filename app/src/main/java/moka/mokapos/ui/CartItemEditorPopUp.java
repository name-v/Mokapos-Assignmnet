package moka.mokapos.ui;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import moka.mokapos.CartManager;
import moka.mokapos.R;
import moka.mokapos.model.Discount;
import moka.mokapos.model.Price;

public class CartItemEditorPopUp extends PopupWindow implements View.OnClickListener {

    int currentItemId;
    int quantity;
    Discount discountApplied;

    TextView quantityTextView;

    Context context;

    public CartItemEditorPopUp(Context context, int itemId, int quantity, Discount discount) {

        this.context = context;

        currentItemId = itemId;
        this.quantity = quantity;
        this.discountApplied = discount;

        View view = LayoutInflater.from(context).inflate(R.layout.cart_item_editor_popup, null);
        setContentView(view);

        setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setOutsideTouchable(true);
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setHeight(WindowManager.LayoutParams.MATCH_PARENT);

        view.findViewById(R.id.action_cancel).setOnClickListener(this);
        view.findViewById(R.id.action_save).setOnClickListener(this);
        view.findViewById(R.id.action_increment).setOnClickListener(this);
        view.findViewById(R.id.action_decrement).setOnClickListener(this);

        quantityTextView = view.findViewById(R.id.value_quantity);
        quantityTextView.setText(String.valueOf(this.quantity));

        TextView title = view.findViewById(R.id.item_title);
        title.setText(String.format("%s "+ "%03d", "Item ", currentItemId) + " - " + Price.getPriceForId(currentItemId).getDisplayPrice());

        RecyclerView gridView = view.findViewById(R.id.grid_view);

        GridLayoutManager layoutManager = new GridLayoutManager(context, 2);
        gridView.setLayoutManager(layoutManager);

        gridView.setAdapter(new DiscountSelectorGridAdapter(new DiscountSelectorGridAdapter.OnDiscountAppliedListener() {
            @Override
            public void onDiscountApplie(Discount discount) {
                discountApplied = discount;
            }
        }, discountApplied));

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.action_cancel:
                dismiss();
                break;
            case R.id.action_save:
                CartManager.getInstance(context).addItem(currentItemId, discountApplied, quantity);
                dismiss();
                break;
            case R.id.action_increment:
                quantity++;
                quantityTextView.setText(String.valueOf(quantity));
                break;
            case R.id.action_decrement:
                quantity--;
                quantityTextView.setText(String.valueOf(quantity));
                if (quantity < 1)
                    dismiss();
                break;
        }
    }
}
