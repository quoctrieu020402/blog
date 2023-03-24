package com.buiquoctrieu.blog.payloads.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostRequest {
    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

    private String image;
}
