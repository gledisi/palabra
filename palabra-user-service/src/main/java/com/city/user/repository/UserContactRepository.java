package com.city.user.repository;

import com.city.user.entity.UserContactEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserContactRepository extends CrudRepository<UserContactEntity,Long> {
}
