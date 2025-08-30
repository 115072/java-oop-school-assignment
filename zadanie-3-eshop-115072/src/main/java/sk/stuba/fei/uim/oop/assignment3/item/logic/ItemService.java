package sk.stuba.fei.uim.oop.assignment3.item.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.stuba.fei.uim.oop.assignment3.cart.web.bodies.CartRequest;
import sk.stuba.fei.uim.oop.assignment3.item.data.IItemRepository;
import sk.stuba.fei.uim.oop.assignment3.item.data.Item;

@Service
public class ItemService implements IItemService {

    @Autowired
    private IItemRepository repository;


    @Override
    public Item create(CartRequest body) {
        Item shoppingList = new Item();
        shoppingList.setAmount(body.getAmount());
        shoppingList.setProductId(body.getProductId());
        return this.repository.save(shoppingList);
    }

}
