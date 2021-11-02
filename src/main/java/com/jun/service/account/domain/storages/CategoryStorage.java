package com.jun.service.account.domain.storages;

import com.jun.service.account.domain.entities.Category;
import com.jun.service.account.domain.utils.CacheKey;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryStorage extends BaseStorage {

  public Category findById(int categoryId) {
    Category category = caching.get(CacheKey.genCategoryIdKey(categoryId), Category.class);
    if (category == null) {
      category = categoryRepository.findCategoryById(categoryId);

      if (category != null) {
        caching.put(CacheKey.genCategoryIdKey(categoryId), category);
      }
    }
    return category;
  }

  public List<Category> findAll() {
    List<Category> categoryList = caching.getList(CacheKey.genListCategoryKey(), Category.class);

    if (categoryList == null) {
      categoryList = new ArrayList<>();
      categoryList = categoryRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
      if (categoryList != null) {
        caching.put(CacheKey.genListCategoryKey(), categoryList);
      }
    }
    return categoryList;
  }
  //  public Category save(Category category) {
  //    category = categoryRepository.save(category);
  //
  //    List<Category> categories = categoryRepository.findAll();
  //    if (categories.size() > 0) {
  //      caching.put(CacheKey.genListCategoryKey(), categories);
  //    }
  //    return category;
  //  }
  //
  //  public void delete(Integer categoryId) {
  //    categoryRepository.deleteById(categoryId);
  //    List<Category> categories = caching.getList(CacheKey.genListCategoryKey(), Category.class);
  //    if (categories != null && categories.size() > 0) {
  //      categories.removeIf(p -> p.getId().equals(categoryId));
  //    }
  //    caching.put(CacheKey.genListCategoryKey(), categories);
  //  }
}
