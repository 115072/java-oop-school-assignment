package sk.stuba.fei.uim.oop.assignment3.cart.logic;

import sk.stuba.fei.uim.oop.assignment3.cart.data.Cart;
import sk.stuba.fei.uim.oop.assignment3.cart.web.bodies.CartRequest;
import sk.stuba.fei.uim.oop.assignment3.exception.IllegalOperationException;
import sk.stuba.fei.uim.oop.assignment3.exception.NotFoundException;

import java.util.List;

public interface ICartService {
    Cart create();

    List<Cart> getAll();

    Cart getById(Long id) throws NotFoundException;

    void delete(Long id) throws NotFoundException;

    Cart addToCart(Long id, CartRequest body) throws NotFoundException, IllegalOperationException;

    String payForCart(Long id) throws NotFoundException, IllegalOperationException;


}
