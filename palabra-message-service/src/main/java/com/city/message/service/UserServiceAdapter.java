package com.city.message.service;

import com.city.message.service.dto.UserDetails;
import com.city.user.dto.UserResponse;
import com.city.user.service.IAuthenticationService;
import com.city.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceAdapter {

    private final UserService service;
    private final IAuthenticationService iAuthenticationService;

    @Autowired
    public UserServiceAdapter(UserService service, IAuthenticationService iAuthenticationService) {
        this.service = service;
        this.iAuthenticationService = iAuthenticationService;
    }

    public UserDetails getUserDetails(String userId) {
        UserResponse userResponse = service.getUser(userId);
        return toDto(userResponse);
    }

    public UserDetails getAuthenticatedUser() {
        UserResponse userResponse = iAuthenticationService.getUser();
        return toDto(userResponse);
    }

    private UserDetails toDto(UserResponse response) {
        UserDetails dto = new UserDetails();
        dto.setContactPhoto(response.getPhoto());
        dto.setUserUUID(response.getUuid());
        dto.setContactName(response.getName());
        return dto;
    }
}
