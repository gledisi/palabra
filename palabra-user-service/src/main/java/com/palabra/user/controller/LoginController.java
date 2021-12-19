package com.palabra.user.controller;

import com.palabra.user.dto.LoginRequest;
import com.palabra.user.dto.LoginResponse;
import com.palabra.user.dto.VerifyCodeResponse;
import com.palabra.user.security.configuration.PalabraJwtAuthentication;
import com.palabra.user.security.model.PalabraUserDetail;
import com.palabra.user.security.service.JwtService;
import com.palabra.user.service.UserService;
import com.palabra.user.util.Endpoints;
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
