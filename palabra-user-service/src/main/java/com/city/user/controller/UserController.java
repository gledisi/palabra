package com.city.user.controller;

import com.city.user.dto.ChangeContactNameRequest;
import com.city.user.dto.ContactResponse;
import com.city.user.dto.NewContactRequest;
import com.city.user.dto.UserResponse;
import com.city.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping(value = "/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long userId){
        return ResponseEntity.ok(service.getUser(userId));
    }

    @GetMapping(value = "/contacts")
    public ResponseEntity<List<ContactResponse>> getContacts(){
        return ResponseEntity.ok(service.getContacts());
    }

    @PostMapping(value = "/contacts")
    public ResponseEntity<Boolean> addContact(@RequestBody @Valid NewContactRequest contact){
        return ResponseEntity.ok(service.addContact(contact));
    }

    @PutMapping(value = "/contacts")
    public ResponseEntity<ContactResponse> changeContactName(@RequestBody @Valid ChangeContactNameRequest contact){
        return ResponseEntity.ok(service.changeContactName(contact));
    }

    @DeleteMapping(value = "/contacts")
    public ResponseEntity<Void> deleteContact(@PathParam("contactId") Long contactId){
        service.deleteContact(contactId);
        return ResponseEntity.ok().build();
    }
}
