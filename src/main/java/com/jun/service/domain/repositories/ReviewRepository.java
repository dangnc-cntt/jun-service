package com.jun.service.domain.repositories;

import com.jun.service.domain.entities.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends MongoRepository<Review, String> {
  Review findReviewById(String id);

  Page<Review> findByProductId(Integer productId, Pageable pageable);
}
