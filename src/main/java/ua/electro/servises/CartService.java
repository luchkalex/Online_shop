package ua.electro.servises;


import org.springframework.stereotype.Service;
import ua.electro.models.CartItem;
import ua.electro.models.User;
import ua.electro.repos.CartRepo;

import java.util.Set;

@Service
public class CartService {

    private final CartRepo cartRepo;

    public CartService(CartRepo cartRepo) {
        this.cartRepo = cartRepo;
    }

    public void saveSet(Set<CartItem> cartItems) {
        cartItems.forEach(cartRepo::save);
    }

    public void deleteOne(CartItem u_cartItem) {
        cartRepo.delete(u_cartItem);
    }

    public void deleteSet(Set<CartItem> cartItems) {
        cartItems.forEach(cartRepo::delete);
    }

    public Set<CartItem> findByUser(User user) {
        return cartRepo.findByUser(user);
    }
}
