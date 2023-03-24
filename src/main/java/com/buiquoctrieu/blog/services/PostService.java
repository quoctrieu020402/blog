package com.buiquoctrieu.blog.services;

import com.buiquoctrieu.blog.payloads.request.PostRequest;
import com.buiquoctrieu.blog.payloads.response.PagingResponse;
import com.buiquoctrieu.blog.payloads.response.PostResponse;

import java.util.List;

public interface PostService {
    PostResponse createPost(PostRequest postRequest,Long userId, Long categoryId);
    PostResponse updatePost(PostRequest postRequest, Long postId);
    PostResponse getPostById(Long postId);
    PagingResponse getAllPost(Integer pageSize, Integer pageNumber,String sortBy,String sortDir);
    void deletePost(Long postId);
    List<PostResponse> getAllPostOfUser(Long userId);
    List<PostResponse> getAllPostOfCategory(Long categoryId);

    List<PostResponse> searchPost(String keyword);
}
