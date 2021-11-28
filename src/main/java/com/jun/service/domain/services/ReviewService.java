package com.jun.service.domain.services;

import com.jun.service.app.dtos.ReviewDTO;
import jun.message.ReviewMessage;
import org.springframework.stereotype.Service;

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
}
