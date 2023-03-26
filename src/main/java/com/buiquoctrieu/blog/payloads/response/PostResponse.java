package com.buiquoctrieu.blog.payloads.response;

import com.buiquoctrieu.blog.entities.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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

    private Set<CommentResponse> comments = new HashSet<>();
}
