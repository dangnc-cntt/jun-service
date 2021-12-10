package com.jun.service.domain.services;

import com.jun.service.app.responses.CartProductResponse;
import com.jun.service.app.responses.Metadata;
import com.jun.service.app.responses.PageResponse;
import com.jun.service.app.responses.ProductResponse;
import com.jun.service.domain.data.SortType;
import com.jun.service.domain.entities.Product;
import com.jun.service.domain.entities.ProductOption;
import com.jun.service.domain.entities.types.ProductState;
import com.jun.service.domain.exceptions.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductService extends BaseService {
  public PageResponse<ProductResponse> filter(
      Integer categoryId,
      String code,
      String name,
      Boolean isHot,
      SortType sortType,
      Integer star,
      Float from,
      Float to,
      Pageable pageable) {
    List<Criteria> andConditions = new ArrayList<>();

    andConditions.add(Criteria.where("state").is(ProductState.ACTIVE));
    if (categoryId != null) {
      andConditions.add(Criteria.where("categoryId").is(categoryId));
    }
    if (StringUtils.isNotEmpty(code)) {
      andConditions.add(Criteria.where("code").regex(code, "i"));
    }
    if (StringUtils.isNotEmpty(name)) {
      andConditions.add(Criteria.where("name").regex(name, "i"));
    }
    if (isHot != null) {
      andConditions.add(Criteria.where("is_hot").is(isHot));
    }

    if (star != null) {
      andConditions.add(Criteria.where("star").is(star));
    }

    if (from != null && to != null) {
      andConditions.add(Criteria.where("price").gte(from));
      andConditions.add(Criteria.where("price").lte(to));
    }

    Query query = new Query();
    Criteria criteria = new Criteria();
    query.addCriteria(criteria.andOperator((andConditions.toArray(new Criteria[0]))));
    if (sortType != null) {
      if (sortType.equals(SortType.asc)) {
        query.with(Sort.by(Sort.Direction.ASC, "price"));
      } else {
        query.with(Sort.by(Sort.Direction.DESC, "price"));
      }
    }
    query.with(Sort.by(Sort.Direction.DESC, "createdAt"));

    log.info(query + "");

    Page<Product> productPage = productRepository.findAll(query, pageable);
    List<ProductResponse> productResponses = new ArrayList<>();
    if (productPage == null) {
      return null;
    }
    if (productPage.getContent().size() > 0) {
      for (Product product : productPage.getContent()) {
        ProductResponse productResponse = modelMapper.toProductResponse(product);
        List<ProductOption> optionList = productOptionStorage.findByProduct(product.getId());
        productResponse.setOptionList(optionList == null ? new ArrayList<>() : optionList);
        productResponses.add(productResponse);
      }
    }

    return new PageResponse<>(productResponses, Metadata.createFrom(productPage));
  }

  //  @Transactional(isolation = Isolation.SERIALIZABLE)
  //  public ProductResponse create(ProductDTO dto, int userId) {
  //    Product product = productRepository.findByCode(dto.getCode());
  //    if (product != null) {
  //      throw new ResourceExitsException("Product with code: " + dto.getCode() + " is exist!");
  //    }
  //    product = modelMapper.toProduct(dto);
  //    product.setId((int) generateSequence(Product.SEQUENCE_NAME));
  //    product.setCreatedBy(userId);
  //
  //    product = productStorage.save(product);
  //
  //    if (dto.getOptionList().size() > 0) {
  //      for (ProductOption option : dto.getOptionList()) {
  //        option.setProductId(product.getId());
  //      }
  //    }
  //    dto.setOptionList(productOptionRepository.saveAll(dto.getOptionList()));
  //
  //    ProductResponse response = modelMapper.toProductResponse(product);
  //    response.setOptionList(dto.getOptionList());
  //    return response;
  //  }

  //  @Transactional(isolation = Isolation.SERIALIZABLE)
  //  public ProductResponse update(ProductDTO dto, int productId) {
  //    Product product = productRepository.findProductById(productId);
  //    if (product == null) {
  //      throw new ResourceNotFoundException("Product with id: " + productId + " is exist!");
  //    }
  //    product.from(dto);
  //    product = productStorage.save(product);
  //
  //    if (dto.getOptionList().size() > 0) {
  //      for (ProductOption option : dto.getOptionList()) {
  //        option.setProductId(product.getId());
  //      }
  //    }
  //    dto.setOptionList(productOptionRepository.saveAll(dto.getOptionList()));
  //    ProductResponse response = modelMapper.toProductResponse(product);
  //    response.setOptionList(dto.getOptionList());
  //    return response;
  //  }

  public ProductResponse findById(int productId) {
    Product product = productStorage.findById(productId);

    if (product == null) {
      throw new ResourceNotFoundException("Product with id: " + productId + " is exist!");
    }
    ProductResponse productResponse = modelMapper.toProductResponse(product);
    List<ProductOption> optionList = productOptionStorage.findByProduct(product.getId());
    productResponse.setOptionList(optionList == null ? new ArrayList<>() : optionList);
    return productResponse;
  }

  public ProductResponse findByIdNoException(int productId) {
    Product product = productStorage.findById(productId);

    if (product == null) {
      return null;
    }
    return modelMapper.toProductResponse(product);
  }

  public CartProductResponse findByIdNoExceptionForCart(int productId) {
    Product product = productStorage.findById(productId);

    if (product == null) {
      return null;
    }
    return modelMapper.toCartProductResponse(product);
  }

  public PageResponse<ProductResponse> findAll(Pageable pageable) {
    return productStorage.findAll(pageable);
  }

  public PageResponse<ProductResponse> findHotProduct(Pageable pageable) {
    return productStorage.findHotProduct(pageable);
  }

  public PageResponse<ProductResponse> findByCategory(int categoryId, Pageable pageable) {
    return productStorage.findByCategoryId(categoryId, pageable);
  }

  //  public Color createColor(ColorDTO dto) {
  //    Color color = colorRepository.findByName(dto.getName());
  //    if (color != null) {
  //      throw new ResourceExitsException("This color is exist!");
  //    }
  //
  //    color = new Color();
  //    color.setId((int) generateSequence(Color.SEQUENCE_NAME));
  //    color.setName(dto.getName());
  //    color = colorRepository.save(color);
  //    return color;
  //  }
  //
  //  public Size createSize(SizeDTO dto) {
  //    Size size = sizeRepository.findByName(dto.getName());
  //    if (size != null) {
  //      throw new ResourceExitsException("This color is exist!");
  //    }
  //
  //    size = new Size();
  //    size.setId((int) generateSequence(Size.SEQUENCE_NAME));
  //    size.setName(dto.getName());
  //    size = sizeRepository.save(size);
  //    return size;
  //  }
  //
  //  public List<Size> getAllSize() {
  //    List<Size> sizes = sizeRepository.findAll();
  //    if (sizes == null || sizes.size() > 0) {
  //      throw new ResourceNotFoundException("No size found!");
  //    }
  //    return sizes;
  //  }
  //
  //  public List<Color> getAllColor() {
  //    List<Color> colors = colorRepository.findAll();
  //    if (colors == null || colors.size() > 0) {
  //      throw new ResourceNotFoundException("No size found!");
  //    }
  //    return colors;
  //  }
}
