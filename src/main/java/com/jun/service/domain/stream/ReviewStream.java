// package com.jun.service.domain.stream;
//
// import com.jun.service.domain.config.TopicConfig;
// import jun.message.ProductStatistic;
// import jun.message.ReviewMessage;
// import lombok.extern.slf4j.Slf4j;
// import org.apache.kafka.common.serialization.Serdes;
// import org.apache.kafka.common.utils.Bytes;
// import org.apache.kafka.common.utils.Utils;
// import org.apache.kafka.streams.StreamsBuilder;
// import org.apache.kafka.streams.kstream.*;
// import org.apache.kafka.streams.state.KeyValueStore;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
//
// import java.io.ByteArrayOutputStream;
// import java.io.IOException;
// import java.io.ObjectOutputStream;
//
// @Configuration
// @Slf4j
// public class ReviewStream {
//
//  @Bean
//  public KStream<Integer, ReviewMessage> reviewKStream(StreamsBuilder streamsBuilder) {
//    KStream<Integer, ReviewMessage> eventLogStream =
//        streamsBuilder.stream(TopicConfig.SEND_REIVEW, Consumed.with(Serdes.Integer(), null));
//    log.info("========");
//    return eventLogStream.peek(
//        (key, event) -> log.info("========= NEW DOMAIN EVENT: {} -----> {}", key, event));
//  }
//
//  @Bean
//  public KStream<Integer, ReviewMessage> validReviewStream(
//      KStream<Integer, ReviewMessage> reviewKStream) {
//    KStream<Integer, ReviewMessage> validReviewStream =
//        reviewKStream.filter((s, review) -> isValidReview(review));
//    validReviewStream.to(TopicConfig.VALID_TOPIC);
//    return validReviewStream;
//  }
//
//  @Bean
//  public KStream<Long, ReviewMessage> validReview(KStream<Long, ReviewMessage> validReviewStream)
// {
//    return validReviewStream.selectKey((key, review) -> review.getCourse());
//  }
//
//  @Bean
//  public KTable<Long, ProductStatistic> courseStatistic(KStream<Long, ReviewMessage> validReview)
// {
//    KTable<Long, ProductStatistic> KvalidReview =
//        validReview
//            .groupByKey(Grouped.with(Serdes.Long(), null))
//            .aggregate(
//                ReviewStream::emptyStats,
//                (courseId, newReview, currentStats) -> {
//                  String reviewRating = newReview.getRating();
//                  // increase or decrease?
//                  Integer incOrDec = (reviewRating.contains("-")) ? -1 : 1;
//
//                  switch (reviewRating.replace("-", "")) {
//                    case "0.5":
//                      currentStats.setCountZeroStar(currentStats.getCountZeroStar() + incOrDec);
//                      break;
//                    case "1":
//                    case "1.5":
//                      log.info(incOrDec + "_" + currentStats.getCountOneStar());
//                      currentStats.setCountOneStar(currentStats.getCountOneStar() + incOrDec);
//                      break;
//                    case "2":
//                    case "2.5":
//                      currentStats.setCountTwoStars(currentStats.getCountTwoStars() + incOrDec);
//                      break;
//                    case "3":
//                    case "3.5":
//                      currentStats.setCountThreeStars(currentStats.getCountThreeStars() +
// incOrDec);
//                      break;
//                    case "4":
//                    case "4.5":
//                      currentStats.setCountFourStars(currentStats.getCountFourStars() + incOrDec);
//                      break;
//                    case "5":
//                      currentStats.setCountFiveStars(currentStats.getCountFiveStars() + incOrDec);
//                      break;
//                    default:
//                  }
//
//                  long newCount = currentStats.getCountReviews() + incOrDec;
//                  double newSumRating =
//                      currentStats.getSumRating() + new Double(newReview.getRating());
//                  double newAverageRating = newSumRating / newCount;
//
//                  currentStats.setCountReviews(newCount);
//                  currentStats.setSumRating(newSumRating);
//                  currentStats.setAverageRating(newAverageRating);
//                  log.info(currentStats + "");
//
//                  return currentStats;
//                },
//                Materialized.<Long, ProductStatistic, KeyValueStore<Bytes, byte[]>>as("test")
//                    .withKeySerde(Serdes.Long()));
//
//    log.error("-----------------------------fhmjdgasjdghas" + KvalidReview.toString());
//    KStream<Long, ProductStatistic> courseStatisticStream = KvalidReview.toStream();
//
//    courseStatisticStream.to(TopicConfig.RESULT_TOPIC);
//    return KvalidReview;
//  }
//
//  private static ProductStatistic initCourseStatistics() {
//    return ProductStatistic.newBuilder().build();
//  }
//
//  private static ProductStatistic emptyStats() {
//    return new ProductStatistic();
//    //            ProductStatistic.newBuilder().setLastReviewTime(new
//    // DateTime(0L).millisOfDay()).build();
//  }
//
//  //        public KStream<Bytes, Test> eventLogStream(StreamsBuilder streamsBuilder) {
//  //          return   KStream<Bytes, Test> eventLogStream =
//  // streamsBuilder.stream(TopicConfig.VALID_TOPIC);
//  //        }
//
//  private boolean isValidReview(ReviewMessage review) {
//    int hash = Utils.toPositive(Utils.murmur2(serialize(review)));
//    return (hash % 100) >= 5; // 95 % of the reviews will be valid reviews
//  }
//
//  public static byte[] serialize(ReviewMessage review) {
//    if (review == null) {
//      return null;
//    }
//    ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
//    try {
//      ObjectOutputStream oos = new ObjectOutputStream(baos);
//      oos.writeObject(review);
//      oos.flush();
//    } catch (IOException ex) {
//      throw new IllegalArgumentException(
//          "Failed to serialize object of type: " + review.getClass(), ex);
//    }
//    return baos.toByteArray();
//  }
// }
