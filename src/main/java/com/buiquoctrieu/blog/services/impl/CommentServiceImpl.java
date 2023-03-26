package com.buiquoctrieu.blog.services.impl;

import com.buiquoctrieu.blog.entities.Comment;
import com.buiquoctrieu.blog.entities.Post;
import com.buiquoctrieu.blog.entities.User;
import com.buiquoctrieu.blog.exceptions.NotFoundException;
import com.buiquoctrieu.blog.payloads.request.CommentRequest;
import com.buiquoctrieu.blog.payloads.response.CommentResponse;
import com.buiquoctrieu.blog.repositories.CommentRepository;
import com.buiquoctrieu.blog.repositories.PostRepository;
import com.buiquoctrieu.blog.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentResponse createComment(CommentRequest commentRequest, Long postId) {

        Post post = this.postRepository.findById(postId).orElseThrow(() -> new NotFoundException("Post", "id", String.valueOf(postId)));

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Comment comment = this.modelMapper.map(commentRequest,Comment.class);

        comment.setUser(user);

        comment.setPost(post);

        Comment saveComment = this.commentRepository.save(comment);

        return this.modelMapper.map(saveComment,CommentResponse.class);
    }

    @Override
    public void deleteComment(Long id) {

        Comment comment = this.commentRepository.findById(id).orElseThrow( () -> new NotFoundException("Comment", "id", String.valueOf(id)));

        this.commentRepository.delete(comment);
    }
}
