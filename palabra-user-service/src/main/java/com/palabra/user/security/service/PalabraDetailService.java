package com.palabra.user.security.service;


import com.palabra.user.entity.UserEntity;
import com.palabra.user.repository.UserRepository;
import com.palabra.user.security.model.PalabraAuthority;
import com.palabra.user.security.model.PalabraUserDetail;
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
                .mobile(user.getMobile())
                .activationCode(user.getCode())
                .authority(toAuthority())
                .build();
    }

    private Collection<PalabraAuthority> toAuthority() {
        return Collections.singletonList(PalabraAuthority.user());
    }
}
