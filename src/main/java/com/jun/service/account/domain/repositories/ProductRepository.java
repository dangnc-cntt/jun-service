package com.jun.service.account.domain.repositories;

import com.jun.service.account.domain.entities.Product;
import com.jun.service.account.domain.entities.types.ProductState;
import com.jun.service.account.domain.repositories.base.MongoResourceRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoResourceRepository<Product, Integer> {
  Product findByCode(String code);

  Page<Product> findByStateOrderByCreatedAtDesc(ProductState state, Pageable pageable);

  Product findProductByIdAndState(int id, ProductState state);

  List<Product> findByCategoryIdAndStateOrderByCreatedAtDesc(int categoryId, ProductState state);

  List<Product> findByIsHotAndStateOrderByCreatedAtDesc(Boolean isHot, ProductState state);
}
