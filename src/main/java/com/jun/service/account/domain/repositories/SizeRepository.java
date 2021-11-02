package com.jun.service.account.domain.repositories;

import com.jun.service.account.domain.entities.Size;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SizeRepository extends MongoRepository<Size, Integer> {
  Size findSizeById(Integer id);

  Size findByName(String name);
}
