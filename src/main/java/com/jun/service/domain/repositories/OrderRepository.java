package com.jun.service.domain.repositories;

import com.jun.service.domain.entities.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends MongoRepository<Order, Long> {
  Page<Order> findByOrderedByOrderByCreatedAt(Integer accountId);

  Order findByIdAndOrderedBy(Long id, Integer accountId);
}
