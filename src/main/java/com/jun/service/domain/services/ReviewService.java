package com.jun.service.domain.services;

import com.jun.service.app.dtos.ReviewDTO;
import com.jun.service.app.responses.Metadata;
import com.jun.service.app.responses.PageResponse;
import com.jun.service.app.responses.ReviewResponse;
import com.jun.service.domain.entities.Review;
import com.jun.service.domain.entities.account.Account;
import jun.message.ReviewMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReviewService extends BaseService {
  public void producerReview(ReviewDTO dto, int accountId) {
    ReviewMessage message = new ReviewMessage();
    message.setContent(dto.getContent() == null ? "1" : dto.getContent());
    message.setProductId(dto.getProductId());
    message.setCreatedBy(accountId);
    message.setStar(dto.getStar());
    producer.sendReviewMessage(message);
  }

  public PageResponse<ReviewResponse> findByProduct(int productId, Pageable pageable) {
    Page<Review> reviews = reviewRepository.findByProductId(productId, pageable);
    List<ReviewResponse> reviewResponses = new ArrayList<>();

    if (reviews != null && reviews.getContent().size() > 0) {

      Map<Integer, Account> accountMap = new HashMap<>();
      for (Review review : reviews.getContent()) {

        Account account = accountMap.get(review.getCreatedBy());
        if (account == null) {
          account = accountStorage.findAccountById(review.getCreatedBy());
          accountMap.put(account.getId(), account);
        }

        ReviewResponse response = new ReviewResponse();
        response.assign(review, account);
        reviewResponses.add(response);
      }
    }

    return new PageResponse<ReviewResponse>(reviewResponses, Metadata.createFrom(reviews));
  }
}
