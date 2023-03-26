package com.buiquoctrieu.blog.services;

import com.buiquoctrieu.blog.payloads.request.UserRequest;
import com.buiquoctrieu.blog.payloads.response.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse updateUser(UserRequest userRequest, Long id);
    UserResponse getUserById(Long id);
    List<UserResponse> getAllUsers();
    void deleteUser(Long id);

    UserResponse registerUser(UserRequest userRequest);
}
