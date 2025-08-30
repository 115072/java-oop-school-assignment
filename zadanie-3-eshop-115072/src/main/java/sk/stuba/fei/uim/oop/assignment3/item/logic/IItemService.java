package sk.stuba.fei.uim.oop.assignment3.item.logic;

import sk.stuba.fei.uim.oop.assignment3.cart.web.bodies.CartRequest;
import sk.stuba.fei.uim.oop.assignment3.item.data.Item;

public interface IItemService {

    Item create(CartRequest body);
}
