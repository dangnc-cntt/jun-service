package com.jun.service.domain.storages;

import com.jun.service.domain.entities.ProductOption;
import com.jun.service.domain.utils.CacheKey;
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

  public void saveAll(List<ProductOption> productOptions) {
    if (productOptions != null && productOptions.size() > 0) {
      for (ProductOption option : productOptions) {
        caching.del(CacheKey.genListOptionProductIdKey(option.getProductId()));
      }

      productOptionRepository.saveAll(productOptions);
    }
  }

  //  public static void main(String[] args) {}
}
