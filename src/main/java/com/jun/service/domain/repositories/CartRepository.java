package com.jun.service.domain.repositories;

import com.jun.service.domain.entities.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends MongoRepository<Cart, String> {
  Cart findByAccountId(Integer accountId);
}
