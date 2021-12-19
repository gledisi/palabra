package com.palabra.user.repository;

import com.palabra.user.entity.UserContactEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserContactRepository extends CrudRepository<UserContactEntity,Long> {
}
