package com.jun.service.domain.services;

import com.jun.service.app.responses.PageResponse;
import com.jun.service.domain.entities.Category;
import com.jun.service.domain.entities.types.CategoryState;
import com.jun.service.domain.exceptions.ResourceNotFoundException;
import com.jun.service.domain.utils.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService extends BaseService {
  @Autowired private MapperUtil mapperUtil;

  public PageResponse<Category> filter(Integer id, String name, Pageable pageable) {
    List<Criteria> andConditions = new ArrayList<>();
    andConditions.add(Criteria.where("state").is(CategoryState.ACTIVE));
    if (id != null) {
      andConditions.add(Criteria.where("id").is(id));
    }
    if (name != null) {
      andConditions.add(Criteria.where("name").regex(name, "i"));
    }

    Query query = new Query();
    Criteria criteria = new Criteria();
    query.addCriteria(criteria.andOperator((andConditions.toArray(new Criteria[0]))));
    query.with(Sort.by(Sort.Direction.DESC, "createdAt"));

    Page<Category> categories = categoryRepository.findAll(query, pageable);
    return PageResponse.createFrom(categories);
  }

  public List<Category> findAll() {
    List<Category> categoryList = categoryStorage.findAll();
    if (categoryList == null || categoryList.size() <= 0) {
      throw new ResourceNotFoundException("No category found!");
    }
    return categoryList;
  }

  public Category findById(Integer id) {
    Category category = categoryStorage.findById(id);
    if (category == null) {
      throw new ResourceNotFoundException("Category " + id + " not found");
    }
    return category;
  }

  //
  //  public Category update(CategoryDTO dto, int categoryId) {
  //    Category category = findById(categoryId);
  //    category = modelMapper.toCategory(dto);
  //    category.setId(categoryId);
  //    return categoryStorage.save(category);
  //  }

  //  public boolean delete(Integer categoryId) {
  //    categoryStorage.delete(categoryId);
  //    return true;
  //  }
}
