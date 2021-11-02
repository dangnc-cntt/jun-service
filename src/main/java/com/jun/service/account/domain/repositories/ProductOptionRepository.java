package com.jun.service.account.domain.repositories;

import com.jun.service.account.domain.entities.ProductOption;
import com.jun.service.account.domain.repositories.base.MongoResourceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductOptionRepository extends MongoResourceRepository<ProductOption, Integer> {
  List<ProductOption> findByProductId(int productId);
}
