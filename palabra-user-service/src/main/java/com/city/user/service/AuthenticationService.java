package com.city.user.service;

import com.city.user.dto.UserResponse;
import com.city.user.mapper.UserMapper;
import com.city.user.repository.UserRepository;
import com.city.user.security.model.PalabraTokenDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements IAuthenticationService{

    private final UserRepository userRepository;

    @Autowired
    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public PalabraTokenDetails getPrincipal() {
        return (PalabraTokenDetails)getAuthentication().getPrincipal();
    }

    @Override
    public String getUserMobile() {
        return getPrincipal().getMobile();
    }

    @Override
    public UserResponse getUser(){
        return UserMapper.toDto(userRepository.findByMobile(getUserMobile()));
    }
}
