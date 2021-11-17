package com.jun.service.domain.storages;

import com.jun.service.app.responses.Metadata;
import com.jun.service.app.responses.PageResponse;
import com.jun.service.app.responses.ProductResponse;
import com.jun.service.domain.entities.Product;
import com.jun.service.domain.entities.ProductOption;
import com.jun.service.domain.entities.types.ProductState;
import com.jun.service.domain.exceptions.ResourceNotFoundException;
import com.jun.service.domain.utils.CacheKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductStorage extends BaseStorage {

  public PageResponse<ProductResponse> findAll(Pageable pageable) {
    Page<Product> productPage =
        productRepository.findByStateOrderByCreatedAtDesc(ProductState.ACTIVE, pageable);

    if (productPage == null || productPage.getContent().size() < 1) {
      throw new ResourceNotFoundException("No product found!");
    }
    List<ProductResponse> responseList = toProductResponses(productPage.getContent());
    //    for (Product product : productPage.getContent()) {
    //      ProductResponse productResponse = modelMapper.toProductResponse(product);
    //      List<ProductOption> optionList = productOptionStorage.findByProduct(product.getId());
    //      productResponse.setOptionList(optionList == null ? new ArrayList<>() : optionList);
    //      responseList.add(productResponse);
    //    }
    return new PageResponse<>(responseList, Metadata.createFrom(productPage));
  }

  public Product findById(int productId) {
    Product product = caching.get(CacheKey.genProductKey(productId), Product.class);
    if (product == null) {
      product = productRepository.findProductByIdAndState(productId, ProductState.ACTIVE);
      if (product != null) {
        caching.put(CacheKey.genProductKey(productId), product);
      }
    }

    return product;
  }

  public PageResponse<ProductResponse> findByCategoryId(int categoryId, Pageable pageable) {
    List<Product> productList =
        caching.getList(CacheKey.genListProductByCategoryKey(categoryId), Product.class);
    if (productList == null) {
      productList = new ArrayList<>();
      productList =
          productRepository.findByCategoryIdAndStateOrderByCreatedAtDesc(
              categoryId, ProductState.ACTIVE);

      if (productList != null) {
        caching.put(CacheKey.genListProductByCategoryKey(categoryId), productList);
      }
    }
    if (productList == null) {
      throw new ResourceNotFoundException("No product found!");
    }

    Page<ProductResponse> productPage =
        mapperUtil.listToPage(toProductResponses(productList), pageable);
    return PageResponse.createFrom(productPage);
  }

  public PageResponse<ProductResponse> findHotProduct(Pageable pageable) {
    List<Product> productList = caching.getList(CacheKey.genListProductIsHotKey(), Product.class);
    if (productList == null) {
      productList = new ArrayList<>();
      productList =
          productRepository.findByIsHotAndStateOrderByCreatedAtDesc(true, ProductState.ACTIVE);

      if (productList != null) {
        caching.put(CacheKey.genListProductIsHotKey(), productList);
      }
    }
    if (productList == null) {
      throw new ResourceNotFoundException("No product found!");
    }

    Page<ProductResponse> productPage =
        mapperUtil.listToPage(toProductResponses(productList), pageable);
    return PageResponse.createFrom(productPage);
  }

  //  public Product save(Product product) {
  //    product = productRepository.save(product);
  //
  //    if (product.getState() != ProductState.ACTIVE) {
  //      processCache(product);
  //    }
  //    return product;
  //  }
  //
  //  public void processCache(Product product) {
  //    caching.put(CacheKey.genListProductKey(product.getId()), product);
  //    if (product.getIsHot()) {
  //      List<Product> hotProductList = productRepository.findByIsHot(true);
  //      if (hotProductList != null && hotProductList.size() > 0) {
  //        caching.put(CacheKey.genListProductIsHotKey(), toProductResponses(hotProductList));
  //      }
  //    }
  //
  //    List<Product> productListCategory =
  //        productRepository.findByCategoryIdAndState(product.getCategoryId(),
  // ProductState.ACTIVE);
  //    if (productListCategory != null && productListCategory.size() > 0) {
  //      caching.put(
  //          CacheKey.genListProductByCategoryKey(product.getCategoryId()),
  //          toProductResponses(productListCategory));
  //    }
  //  }

  public List<ProductResponse> toProductResponses(List<Product> productList) {
    List<ProductResponse> responseList = new ArrayList<>();

    for (Product item : productList) {
      ProductResponse productResponse = modelMapper.toProductResponse(item);
      List<ProductOption> optionList = productOptionRepository.findByProductId(item.getId());
      productResponse.setOptionList(optionList == null ? new ArrayList<>() : optionList);
      responseList.add(productResponse);
    }
    return responseList;
  }

  //  public void delete(Product product) {
  //    productRepository.delete(product);
  //
  //    caching.del(CacheKey.genListProductKey(product.getId()));
  //    caching.del(CacheKey.genListProductByCategoryKey(product.getCategoryId()));
  //    caching.del(CacheKey.genListProductIsHotKey());
  //  }
}
