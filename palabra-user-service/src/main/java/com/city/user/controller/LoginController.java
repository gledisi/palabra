package com.city.user.controller;

import com.city.user.dto.LoginRequest;
import com.city.user.dto.LoginResponse;
import com.city.user.dto.VerifyCodeResponse;
import com.city.user.security.configuration.PalabraJwtAuthentication;
import com.city.user.security.model.PalabraUserDetail;
import com.city.user.security.service.JwtService;
import com.city.user.service.UserService;
import com.city.user.util.Endpoints;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class LoginController {

    @Autowired
    private JwtService jwtProvider;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;


    @PostMapping(Endpoints.SEND_CODE)
    public ResponseEntity<VerifyCodeResponse> sendActivationCode(@RequestParam("mobile") String mobile) {
        log.info("Try authenticate user with mobile number: {}", mobile);
        return ResponseEntity.ok(userService.addOrUpdateActivationCode(mobile));
    }

    @PostMapping(Endpoints.LOGIN)
    public ResponseEntity<LoginResponse> authenticate(@RequestBody @Valid LoginRequest request) {
        Authentication authenticationRequest = new PalabraJwtAuthentication(request.getToken(), request.getCode());
        Authentication authenticationResult = authenticationManager.authenticate(authenticationRequest);
        LoginResponse response = createResponse(authenticationResult);
        log.info("Logged user: {}", response);
        return ResponseEntity.ok(response);
    }

    private LoginResponse createResponse(Authentication authentication) {
        PalabraUserDetail userDetail = (PalabraUserDetail) authentication.getDetails();
        LoginResponse response = new LoginResponse();
        response.setMobile(userDetail.getUsername());
        response.setRole(userDetail.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        String generatedToken = jwtProvider.issueToken(userDetail);
        response.setToken(generatedToken);
        return response;
    }

}
