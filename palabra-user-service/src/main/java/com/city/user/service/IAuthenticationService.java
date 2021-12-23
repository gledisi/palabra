package com.city.user.service;

import com.city.user.dto.UserResponse;
import com.city.user.security.model.PalabraTokenDetails;
import org.springframework.security.core.Authentication;

public interface IAuthenticationService {

    Authentication getAuthentication();
    PalabraTokenDetails getPrincipal();
    String getUserMobile();
    UserResponse getUser();
}
