package com.city.user.service;

import com.city.user.dto.UserResponse;
import com.city.user.security.model.PalabraTokenDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public interface IAuthenticationService {

    Authentication getAuthentication();
    PalabraTokenDetails getUserDetails();
    String getUserMobile();
    UserResponse getUser();
}
