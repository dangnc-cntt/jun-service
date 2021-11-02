package com.jun.service.account.domain.repositories;

import com.jun.service.account.domain.entities.Category;
import com.jun.service.account.domain.repositories.base.MongoResourceRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends MongoResourceRepository<Category, Integer> {
  Category findCategoryById(Integer id);
}
