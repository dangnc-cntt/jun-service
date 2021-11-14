package com.jun.service.domain.repositories;

import com.jun.service.domain.entities.Category;
import com.jun.service.domain.repositories.base.MongoResourceRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends MongoResourceRepository<Category, Integer> {
  Category findCategoryById(Integer id);
}
