package com.jun.service.account.domain.repositories;

import com.jun.service.account.domain.entities.Color;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorRepository extends MongoRepository<Color, Integer> {
  Color findColorById(Integer id);

  Color findByName(String name);
}
