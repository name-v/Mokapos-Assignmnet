package moka.mokapos.model;

public class CartItem {
    private int itemId;
    private int quantity;
    private Discount discount;

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Discount getDiscount() {
        return discount;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getItemId() {
        return itemId;
    }

    public String getDisplayName(){
        return String.format("%s "+ "%03d", "Item ", itemId);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(" itemId - " + itemId);
        builder.append(" quantity - " + quantity);
        builder.append(" discount - " + discount.getName() + "/" + discount.getValue());
        return builder.toString();
    }
}
