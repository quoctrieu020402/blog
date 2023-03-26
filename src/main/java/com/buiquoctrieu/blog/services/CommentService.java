package com.buiquoctrieu.blog.services;

import com.buiquoctrieu.blog.payloads.request.CommentRequest;
import com.buiquoctrieu.blog.payloads.response.CommentResponse;

public interface CommentService {

    CommentResponse createComment(CommentRequest commentRequest, Long postId);

    void deleteComment(Long id);
}
