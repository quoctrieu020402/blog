package com.buiquoctrieu.blog.payloads.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostResponse {

    private Long id;

    private String title;

    private String content;

    private Date addDate;

    private String image;

    private CategoryResponse category;

    private UserResponse user;
}
