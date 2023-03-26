package com.buiquoctrieu.blog.controllers;

import com.buiquoctrieu.blog.entities.User;
import com.buiquoctrieu.blog.payloads.request.JwtAuthRequest;
import com.buiquoctrieu.blog.payloads.request.UserRequest;
import com.buiquoctrieu.blog.payloads.response.JwtAuthResponse;
import com.buiquoctrieu.blog.payloads.response.MessageResponse;
import com.buiquoctrieu.blog.payloads.response.UserResponse;
import com.buiquoctrieu.blog.repositories.UserRepository;
import com.buiquoctrieu.blog.security.jwt.JwtHelper;
import com.buiquoctrieu.blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserDetailsService userDetailsService;


    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@Valid @RequestBody JwtAuthRequest jwtAuthRequest){

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(jwtAuthRequest.getUsername(), jwtAuthRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtHelper.generateJwtToken(authentication);

        JwtAuthResponse response = new JwtAuthResponse();

        response.setToken(jwt);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody UserRequest userRequest) {

        UserResponse userResponse = this.userService.registerUser(userRequest);

        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }
}
