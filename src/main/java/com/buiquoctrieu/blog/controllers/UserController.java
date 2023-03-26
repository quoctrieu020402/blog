package com.buiquoctrieu.blog.controllers;

import com.buiquoctrieu.blog.payloads.request.UserRequest;
import com.buiquoctrieu.blog.payloads.response.MessageResponse;
import com.buiquoctrieu.blog.payloads.response.UserResponse;
import com.buiquoctrieu.blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;


    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUser(@Valid @RequestBody UserRequest userRequest, @PathVariable(name = "userId") Long id) {

        UserResponse updateUserResponse = this.userService.updateUser(userRequest, id);

        return ResponseEntity.status(HttpStatus.OK).body(updateUserResponse);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<MessageResponse> deleteUser(@PathVariable(name = "userId") Long id) {

        this.userService.deleteUser(id);

        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("User deleted successfully"));
    }

    @GetMapping("/")
    public ResponseEntity<List<UserResponse>> getAllUsers(){

        List<UserResponse> userResponseList = this.userService.getAllUsers();

        return ResponseEntity.status(HttpStatus.OK).body(userResponseList);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable(name = "userId") Long id){

        UserResponse userResponse = this.userService.getUserById(id);

        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }
}
