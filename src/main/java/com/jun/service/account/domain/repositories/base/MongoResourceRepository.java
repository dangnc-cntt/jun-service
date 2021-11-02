package com.jun.service.account.domain.repositories.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface MongoResourceRepository<T, I extends Serializable> extends MongoRepository<T, I> {

  Page<T> findAll(Query query, Pageable pageable);

  //  List<T> findAll(Query query);
}
