package com.city.user.security.service;


import com.city.user.entity.UserEntity;
import com.city.user.entity.UserRole;
import com.city.user.repository.UserRepository;
import com.city.user.security.model.PalabraAuthority;
import com.city.user.security.model.PalabraUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;


@Component
public class PalabraDetailService implements UserDetailsService {

    @Lazy
    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String mobile) {
        UserEntity user = repository.findByMobile(mobile);
        return  PalabraUserDetail.builder()
                .userId(user.getUuid().toString())
                .mobile(user.getMobile())
                .activationCode(user.getCode())
                .authority(toAuthority())
                .build();
    }

    private Collection<PalabraAuthority> toAuthority() {
        return Collections.singletonList(PalabraAuthority.user());
    }
}
