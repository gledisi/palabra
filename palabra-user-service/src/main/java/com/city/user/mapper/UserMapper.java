package com.city.user.mapper;

import com.city.user.dto.ContactResponse;
import com.city.user.dto.NewContactRequest;
import com.city.user.dto.UserResponse;
import com.city.user.entity.UserContactEntity;
import com.city.user.entity.UserEntity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    private UserMapper() {
        throw new UnsupportedOperationException("Constructor not allowed!");
    }

    public static UserResponse toDto(UserEntity entity){
        UserResponse response = new UserResponse();
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setMobile(entity.getMobile());
        response.setPhoto(entity.getPhoto());
        return response;
    }

    public static List<UserResponse> toDto(Collection<UserEntity> entities){
        return entities.stream().map(UserMapper::toDto).collect(Collectors.toList());
    }

    public static UserEntity fromCodeAndMobile(String code,String mobile){
        UserEntity entity = new UserEntity();
        entity.setCreatedOn(LocalDateTime.now());
        entity.setMobile(mobile);
        entity.setName("");
        entity.setCode(code);
        return entity;
    }

    public static UserContactEntity toContactEntity(NewContactRequest dto){
        UserContactEntity entity = new UserContactEntity();
        entity.setCreatedOn(LocalDateTime.now());
        entity.setName(dto.getName());
        return entity;
    }

    public static ContactResponse toContactResponse(UserContactEntity entity){
        ContactResponse response = new ContactResponse();
        response.setContactId(entity.getId());
        response.setCreatedOn(entity.getCreatedOn());
        response.setName(entity.getName());
        response.setUser(toDto(entity.getContact()));
        return response;
    }

    public static List<ContactResponse> contactResponse(List<UserContactEntity> entities) {
        return entities.stream().map(UserMapper::toContactResponse).collect(Collectors.toList());
    }
}
