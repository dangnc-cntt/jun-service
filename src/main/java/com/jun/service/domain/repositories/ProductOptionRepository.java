package com.jun.service.domain.repositories;

import com.jun.service.domain.entities.ProductOption;
import com.jun.service.domain.repositories.base.MongoResourceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductOptionRepository extends MongoResourceRepository<ProductOption, Integer> {
  List<ProductOption> findByProductId(int productId);
}
