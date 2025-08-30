package sk.stuba.fei.uim.oop.assignment3.item.web;

import lombok.Data;
import sk.stuba.fei.uim.oop.assignment3.item.data.Item;

@Data
public class ItemResponse {
    long productId;

    int amount;

    public ItemResponse(Item sl) {
        this.productId = sl.getProductId();
        this.amount = sl.getAmount();
    }
}
