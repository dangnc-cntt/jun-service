package com.jun.service.domain.storages;

import com.jun.service.domain.entities.Cart;
import org.springframework.stereotype.Component;

@Component
public class CartStorage extends BaseStorage {
  public Cart save(Cart cart) {
    // todo: cache
    return cartRepository.save(cart);
  }
}
