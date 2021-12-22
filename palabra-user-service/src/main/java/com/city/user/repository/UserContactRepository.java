package com.city.user.repository;

import com.city.user.entity.UserContactEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserContactRepository extends CrudRepository<UserContactEntity,Long> {

    @Query("select e from UserContactEntity e where e.user.id=?1")
    List<UserContactEntity> findByUserId(Long userId);

    @Query("select e from UserContactEntity e where e.contact.mobile=?1 and e.user.id=?2")
    UserContactEntity existsByContactPhone(String mobile, Long userId);

    @Query("select e from UserContactEntity e where e.name=?1 and e.user.id=?2")
    UserContactEntity existsByContactName(String name, Long userId);
}
