package com.buiquoctrieu.blog.controllers;

import com.buiquoctrieu.blog.payloads.request.CommentRequest;
import com.buiquoctrieu.blog.payloads.response.CommentResponse;
import com.buiquoctrieu.blog.payloads.response.MessageResponse;
import com.buiquoctrieu.blog.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentResponse> createComment(@Valid @RequestBody CommentRequest commentRequest, @PathVariable("postId") Long postId){

        CommentResponse commentResponse = this.commentService.createComment(commentRequest,postId);

        return ResponseEntity.status(HttpStatus.CREATED).body(commentResponse);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<MessageResponse> createComment(@PathVariable("commentId") Long commentId){

        this.commentService.deleteComment(commentId);

        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Comment deleted successfully"));
    }
}
