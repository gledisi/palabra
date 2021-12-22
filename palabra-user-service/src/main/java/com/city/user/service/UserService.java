package com.city.user.service;

import com.city.user.dto.*;
import com.city.user.entity.UserContactEntity;
import com.city.user.entity.UserEntity;
import com.city.user.entity.UserRole;
import com.city.user.exceptions.EntityExistsException;
import com.city.user.exceptions.EntityNotFoundException;
import com.city.user.mapper.UserMapper;
import com.city.user.repository.UserContactRepository;
import com.city.user.repository.UserRepository;
import com.city.user.security.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {


    private final UserContactRepository contactRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final IAuthenticationService authenticationService;
    private final ActivationCodeService activationCodeService;

    @Autowired
    public UserService(UserContactRepository contactRepository, UserRepository userRepository, JwtService jwtService, IAuthenticationService authenticationService, ActivationCodeService service) {
        this.contactRepository = contactRepository;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.activationCodeService = service;
    }

    public UserResponse getUser(Long userId) {
        return UserMapper.toDto(userRepository.findById(userId).orElseThrow(EntityNotFoundException::new));
    }

    public UserResponse getUser(String uuid) {
        return UserMapper.toDto(userRepository.findByUUID(uuid));
    }

//    public List<UserResponse> getUsers(){
//        return UserMapper.toDto(userRepository.findAll());
//    }

    public UserResponse getUserByMobile(String mobile) {
        return null;
    }

    @Transactional
    public VerifyCodeResponse addOrUpdateActivationCode(String mobile) {
        String code = activationCodeService.send(mobile);
        UserEntity entity = userRepository.findByMobile(mobile);
        if (entity == null) {
            entity = userRepository.save(UserMapper.fromCodeAndMobile(code, mobile));
        } else {
            userRepository.updateCode(code, mobile);
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

    public List<ContactResponse> getContacts() {
        return UserMapper.contactResponse(contactRepository.findByUserId(authenticationService.getUser().getId()));
    }

    public Boolean addContact(NewContactRequest contact) {
        UserEntity currentUser = userRepository.findByMobile(authenticationService.getUserMobile());
        checkIfExistsContactPhone(contact.getPhone(), currentUser.getId());
        checkIfExistsContactName(contact.getName(),currentUser.getId());

        UserContactEntity entity = UserMapper.toContactEntity(contact);
        entity.setUser(currentUser);
        entity.setContact(checkIfUserExistsByPhone(contact.getPhone()));
        contactRepository.save(entity);
        return true;
    }

    private UserEntity checkIfUserExistsByPhone(String mobile){
        UserEntity entity =userRepository.findByMobile(mobile);
        if(entity==null){
            throw new EntityNotFoundException(String.format("User with phone number %s not found!", mobile));
        }
        return entity;
    }

    private void checkIfExistsContactName(String contactName,Long userId) {
        if (contactRepository.existsByContactName(contactName,userId) != null) {
            throw new EntityExistsException(String.format("Contact with name %s exists!", contactName));
        }
    }

    private void checkIfExistsContactPhone(String contactPhone,Long userId) {
        if (contactRepository.existsByContactPhone(contactPhone,userId) != null) {
            throw new EntityExistsException(String.format("Contact with phone number %s exists!", contactPhone));
        }
    }

    public void deleteContact(Long contactId) {
        contactRepository.deleteById(contactId);
    }

    public ContactResponse changeContactName(ChangeContactNameRequest contact) {
        UserContactEntity entity = contactRepository.findById(contact.getContactId()).orElseThrow(EntityNotFoundException::new);
        entity.setName(contact.getNewName());
        return UserMapper.toContactResponse(contactRepository.save(entity));
    }
}
