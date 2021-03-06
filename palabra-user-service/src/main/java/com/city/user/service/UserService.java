package com.city.user.service;

import com.city.user.dto.UserResponse;
import com.city.user.dto.VerifyCodeResponse;
import com.city.user.entity.UserEntity;
import com.city.user.entity.UserRole;
import com.city.user.mapper.UserMapper;
import com.city.user.repository.UserRepository;
import com.city.user.security.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
public class UserService {


    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final ActivationCodeService activationCodeService;

    @Autowired
    public UserService(UserRepository userRepository, JwtService jwtService, ActivationCodeService service) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.activationCodeService = service;
    }

    public UserResponse getUser(Long userId){
        return UserMapper.toDto(userRepository.findById(userId).orElseThrow(EntityNotFoundException::new));
    }

//    public List<UserResponse> getUsers(){
//        return UserMapper.toDto(userRepository.findAll());
//    }

    public UserResponse getUserByMobile(String mobile){
        return null;
    }

    @Transactional
    public VerifyCodeResponse addOrUpdateActivationCode(String mobile) {
        String code = activationCodeService.send(mobile);
        UserEntity entity = userRepository.findByMobile(mobile);
        if (entity==null){
           entity= userRepository.save(UserMapper.fromCodeAndMobile(code,mobile));
        }else {
            userRepository.updateCode(code,mobile);
        }
        return createResponse(entity.getMobile());
    }

    private VerifyCodeResponse createResponse(String mobile) {
        VerifyCodeResponse response = new VerifyCodeResponse();
        response.setMobile(mobile);
        response.setRole(UserRole.VERIFY_CODE);
        String generatedToken = jwtService.issueSmsCodeToken(mobile);
        response.setToken(generatedToken);
        return response;
    }
}
