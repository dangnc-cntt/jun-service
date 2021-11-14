package com.jun.service;

import com.jun.service.domain.repositories.base.impl.MongoResourceRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(repositoryBaseClass = MongoResourceRepositoryImpl.class)
public class JunServiceApplication {
  public static void main(String[] args) {
    SpringApplication.run(JunServiceApplication.class, args);
  }
}
