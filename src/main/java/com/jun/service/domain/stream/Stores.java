// package com.jun.service.domain.stream;
//
// import org.apache.kafka.streams.KafkaStreams;
// import org.apache.kafka.streams.state.QueryableStoreTypes;
// import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.kafka.config.StreamsBuilderFactoryBean;
// import org.springframework.stereotype.Component;
//
// @Component
// public class Stores {
//  public static final String CAMPAIGN_FIRST_VIEW_STORE = "jun.review.store";
//  public static final String CAMPAIGN_FIRST_VIEW_GLOBAL_STORE = "junView";
//
//  @Autowired private StreamsBuilderFactoryBean streamsBuilderFactoryBean;
//
//  public ReadOnlyKeyValueStore<String, Long> campaignFirstViewGlobalStore() {
//
//    KafkaStreams streams = streamsBuilderFactoryBean.getKafkaStreams();
//    return streams.store(CAMPAIGN_FIRST_VIEW_GLOBAL_STORE, QueryableStoreTypes.keyValueStore());
//  }
// }
