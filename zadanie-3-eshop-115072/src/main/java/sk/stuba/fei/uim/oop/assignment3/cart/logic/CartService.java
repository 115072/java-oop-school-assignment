package sk.stuba.fei.uim.oop.assignment3.cart.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.stuba.fei.uim.oop.assignment3.cart.data.Cart;
import sk.stuba.fei.uim.oop.assignment3.cart.data.ICartRepository;
import sk.stuba.fei.uim.oop.assignment3.cart.web.bodies.CartRequest;
import sk.stuba.fei.uim.oop.assignment3.exception.IllegalOperationException;
import sk.stuba.fei.uim.oop.assignment3.exception.NotFoundException;
import sk.stuba.fei.uim.oop.assignment3.item.data.Item;
import sk.stuba.fei.uim.oop.assignment3.item.logic.IItemService;
import sk.stuba.fei.uim.oop.assignment3.product.data.Product;
import sk.stuba.fei.uim.oop.assignment3.product.logic.IProductService;

import java.util.List;
import java.util.Objects;

@Service
public class CartService implements ICartService {
    @Autowired
    private ICartRepository repository;

    @Autowired
    private IItemService itemService;

    @Autowired
    private IProductService productService;


    @Override
    public Cart create() {
        return this.repository.save(new Cart());
    }

    @Override
    public List<Cart> getAll() {
        return this.repository.findAll();
    }

    @Override
    public Cart getById(Long id) throws NotFoundException {
        Cart c = this.repository.findCartById(id);
        if (c == null) {
            throw new NotFoundException();
        }
        return c;
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        Cart c = getById(id);
        this.repository.delete(c);
    }

    @Override
    public Cart addToCart(Long id, CartRequest body) throws NotFoundException, IllegalOperationException {
        Cart cart = getById(id);
        if (cart.isPayed()) {
            throw new IllegalOperationException();
        }
        Product product = productService.getById(body.getProductId());
        boolean newEntity = true;
        if (product.getAmount() - body.getAmount() < 0) {
            throw new IllegalOperationException();
        }
        for (int i = 0; i < cart.getItem().size(); i++) {
            if (Objects.equals(cart.getItem().get(i).getProductId(), body.getProductId())) {
                cart.getItem().get(i).setAmount(cart.getItem().get(i).getAmount() + body.getAmount());
                newEntity = false;
                break;
            }
        }
        product.setAmount(product.getAmount() - body.getAmount());
        if (newEntity) {
            cart.getItem().add(itemService.create(body));
        }
        return this.repository.save(cart);
    }

    @Override
    public String payForCart(Long id) throws NotFoundException, IllegalOperationException {
        Cart cart = getById(id);
        if (cart.isPayed()) {
            throw new IllegalOperationException();
        } else {
            cart.setPayed(true);
        }
        this.repository.save(cart);
        double payment = 0;
        for (int i = 0; i < cart.getItem().size(); i++) {
            Item temporaryItem = cart.getItem().get(i);
            Product temporaryProduct = productService.getById(cart.getItem().get(i).getProductId());
            payment = payment + (temporaryItem.getAmount() * temporaryProduct.getPrice());
        }
        return Double.toString(payment);
    }


}
