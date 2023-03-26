package com.buiquoctrieu.blog.payloads.request;

import com.buiquoctrieu.blog.entities.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class UserRequest {
    @NotEmpty
    @Size(min = 3 , message = "Username must be min of 4 characters")
    private String name;

    @NotEmpty
    @Email(message = "Email address is not valid")
    private String email;

    @NotEmpty
    @Size(min =  6, max = 12, message = "Password must be min of 3 characters and max of 10 character")
    private String password;

    private String about;

    private Set<String> roles;
}
