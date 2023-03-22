package com.buiquoctrieu.blog.payloads.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserResponse {
    private Long id;
    private String email;
    private String name;
}
