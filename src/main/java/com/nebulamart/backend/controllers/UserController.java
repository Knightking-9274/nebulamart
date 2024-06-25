package com.nebulamart.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nebulamart.backend.entities.User;
import com.nebulamart.backend.exceptions.UserException;
import com.nebulamart.backend.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<User>getUserProfileHandler(@RequestHeader ("Authorization")String jwt)throws UserException{
        User user = userService.UserProfileByJwt(jwt);
        return new ResponseEntity<User>(user,HttpStatus.ACCEPTED);
    }
}
