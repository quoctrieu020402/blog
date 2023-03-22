package com.buiquoctrieu.blog.services.impl;

import com.buiquoctrieu.blog.entities.User;
import com.buiquoctrieu.blog.exceptions.BadRequestException;
import com.buiquoctrieu.blog.exceptions.NotFoundException;
import com.buiquoctrieu.blog.payloads.request.UserRequest;
import com.buiquoctrieu.blog.payloads.response.UserResponse;
import com.buiquoctrieu.blog.repositories.UserRepository;
import com.buiquoctrieu.blog.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        User user = this.userRequestToUser(userRequest);

        Optional<User> checkEmail = this.userRepository.findByEmail(user.getEmail());

        if (checkEmail.isPresent()){
            throw new BadRequestException("User", "email", user.getEmail());
        }

        User saveUser = this.userRepository.save(user);
        return this.userToUserResponse(saveUser);
    }

    @Override
    public UserResponse updateUser(UserRequest userRequest, Long id) {
        User findUser = this.userRepository.findById(id).orElseThrow(() -> new NotFoundException("User", "id", String.valueOf(id)));

        findUser.setName(userRequest.getName());
        findUser.setPassword(userRequest.getPassword());
        findUser.setAbout(userRequest.getAbout());

        User updateUser = this.userRepository.save(findUser);

        return this.userToUserResponse(updateUser);
    }

    @Override
    public UserResponse getUserById(Long id) {
        User findUser = this.userRepository.findById(id).orElseThrow(() -> new NotFoundException("User", "id", String.valueOf(id)));
        return this.userToUserResponse(findUser);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = this.userRepository.findByStatus(true);
        List<UserResponse> userResponses = users.stream().map(user -> this.userToUserResponse(user)).collect(Collectors.toList());
        return userResponses;
    }

    @Override
    public void deleteUser(Long id) {
        User findUser = this.userRepository.findById(id).orElseThrow(() -> new NotFoundException("User", "id", String.valueOf(id)));
        findUser.setStatus(false);
        this.userRepository.save(findUser);
    }

    private User userRequestToUser(UserRequest userRequest) {
        User user = this.modelMapper.map(userRequest, User.class);
        return user;
    }

    private UserResponse userToUserResponse(User user) {
        UserResponse userResponse = this.modelMapper.map(user,UserResponse.class);
        return userResponse;
    }
}
