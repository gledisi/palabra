package com.city.user.repository;

import com.city.user.entity.UserEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<UserEntity,Long> {

     UserEntity findByMobile(String mobile);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE UserEntity e SET e.code=:code where e.mobile=:mobile")
    int updateCode(@Param("code")String code,@Param("mobile")String mobile);

    @Query("select e FROM UserEntity e where e.id=:uuid")
    UserEntity findByUUID(String uuid);
}
