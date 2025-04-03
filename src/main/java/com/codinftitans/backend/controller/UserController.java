package com.codinftitans.backend.controller;

import com.codinftitans.backend.Enum.Role;
import com.codinftitans.backend.dto.request.UserRequestDTO;
import com.codinftitans.backend.dto.response.UserResponseDTO;
import com.codinftitans.backend.model.User;
import com.codinftitans.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class UserController {
    @Autowired
    UserService userService;
    @GetMapping("/user")
    public ResponseEntity<List<UserResponseDTO> >findAll(){
        List<UserResponseDTO> users=userService.findAll();
        long count=users.size();
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.add("X-Total-Count",String.valueOf(count));
        return new ResponseEntity<>(users,httpHeaders, HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PostMapping("user/new")
    public User newUser(@RequestBody UserRequestDTO user){
     return    userService.newUser(user, Role.ADMIN);
    }
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @DeleteMapping("user/{id}")
    public String deleteUser(@PathVariable UUID id){
        return userService.deleteUser(id);
    }


}
