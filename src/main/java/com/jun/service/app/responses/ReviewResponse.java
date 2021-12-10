package com.jun.service.app.responses;

import com.jun.service.domain.entities.Review;
import com.jun.service.domain.entities.account.Account;
import com.jun.service.domain.entities.types.ReviewState;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ReviewResponse {
  private String id;

  private AccountView account;

  private String content;

  private Integer star;

  private ReviewState state;

  private Integer productId;

  public LocalDateTime createdAt;

  @Data
  @NoArgsConstructor
  public static class AccountView {
    private Integer id;

    private String avatarUrl;

    private String username;

    private String fullName;

    public AccountView(Account account) {
      id = account.getId();
      username = account.getUsername();
      fullName = account.getFullName();
      avatarUrl = account.getAvatarUrl();
    }
  }

  public void assign(Review review, Account account) {
    id = review.getId();
    this.account = new AccountView(account);
    content = review.getContent() == null ? "" : review.getContent();
    star = review.getStar();
    state = review.getState();
    productId = review.getProductId();
    createdAt = review.getCreatedAt();
  }
}
