package com.jun.service.account.domain.storages;

import com.jun.service.account.domain.entities.ProductOption;
import com.jun.service.account.domain.utils.CacheKey;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductOptionStorage extends BaseStorage {
  public List<ProductOption> findByProduct(int productId) {
    List<ProductOption> optionList =
        caching.getList(CacheKey.genListOptionProductIdKey(productId), ProductOption.class);
    if (optionList == null) {
      optionList = new ArrayList<>();
      optionList = productOptionRepository.findByProductId(productId);
      if (optionList != null) {
        caching.put(CacheKey.genListOptionProductIdKey(productId), optionList);
      }
    }
    return optionList;
  }
}
