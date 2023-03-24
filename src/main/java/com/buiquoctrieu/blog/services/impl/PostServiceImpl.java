package com.buiquoctrieu.blog.services.impl;

import com.buiquoctrieu.blog.entities.Category;
import com.buiquoctrieu.blog.entities.Post;
import com.buiquoctrieu.blog.entities.User;
import com.buiquoctrieu.blog.exceptions.NotFoundException;
import com.buiquoctrieu.blog.payloads.request.PostRequest;
import com.buiquoctrieu.blog.payloads.response.PagingResponse;
import com.buiquoctrieu.blog.payloads.response.PostResponse;
import com.buiquoctrieu.blog.repositories.CategoryRepository;
import com.buiquoctrieu.blog.repositories.PostRepository;
import com.buiquoctrieu.blog.repositories.UserRepository;
import com.buiquoctrieu.blog.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PostResponse createPost(PostRequest postRequest,Long userId, Long categoryId) {

        User userPost = this.userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User", "id", String.valueOf(userId)));

        Category categoryPost = this.categoryRepository.findById(categoryId).orElseThrow( () -> new NotFoundException("Category", "id", String.valueOf(categoryId)));

        Post post = this.modelMapper.map(postRequest,Post.class);

        post.setImage("");

        post.setAddDate(new Date());

        post.setUser(userPost);

        post.setCategory(categoryPost);

        Post savePost = this.postRepository.save(post);

        return this.modelMapper.map(savePost,PostResponse.class);
    }

    @Override
    public PostResponse updatePost(PostRequest postRequest, Long postId) {

        Post findPost = this.postRepository.findById(postId).orElseThrow(() -> new NotFoundException("Post", "id", String.valueOf(postId)));

        findPost.setTitle(postRequest.getTitle());

        findPost.setContent(postRequest.getContent());

        findPost.setImage((postRequest.getImage()));

        Post updatePost = this.postRepository.save(findPost);

        return this.modelMapper.map(updatePost,PostResponse.class);
    }

    @Override
    public PostResponse getPostById(Long postId) {

        Post findPost = this.postRepository.findById(postId).orElseThrow(() -> new NotFoundException("Post", "id", String.valueOf(postId)));

        return this.modelMapper.map(findPost,PostResponse.class);
    }

    @Override
    public PagingResponse getAllPost(Integer pageSize, Integer pageNumber,String sortBy,String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber,pageSize, sort);

        Page<Post> postPage = this.postRepository.findAll(pageable);

        List<Post> postList = postPage.getContent();

        List<PostResponse> postResponses = postList.stream().map( post -> this.modelMapper.map(post,PostResponse.class)).collect(Collectors.toList());

        PagingResponse pagingResponse = new PagingResponse();

        pagingResponse.setContent(postResponses);

        pagingResponse.setPageNumber(postPage.getNumber());

        pagingResponse.setPageSize(postPage.getSize());

        pagingResponse.setTotalPages(postPage.getTotalPages());

        pagingResponse.setTotalElements(postPage.getTotalElements());

        pagingResponse.setLastPage(postPage.isLast());

        return pagingResponse;
    }

    @Override
    public void deletePost(Long postId) {

        Post post = this.postRepository.findById(postId).orElseThrow( () -> new NotFoundException("Post", "id", String.valueOf(postId)));

        post.setStatus(false);

        this.postRepository.save(post);

    }

    @Override
    public List<PostResponse> getAllPostOfUser(Long userId) {

        User user = this.userRepository.findById(userId).orElseThrow( () -> new NotFoundException("User", "id", String.valueOf(userId)));

        List<Post> postList = this.postRepository.findByUser(user);

        List<PostResponse> postResponses = postList.stream().map( post -> this.modelMapper.map(post,PostResponse.class)).collect(Collectors.toList());

        return postResponses;
    }

    @Override
    public List<PostResponse> getAllPostOfCategory(Long categoryId) {

        Category category = this.categoryRepository.findById(categoryId).orElseThrow( () -> new NotFoundException("Category", "id", String.valueOf(categoryId)));

        List<Post> postList = this.postRepository.findByCategory(category);

        List<PostResponse> postResponses = postList.stream().map( post -> this.modelMapper.map(post,PostResponse.class)).collect(Collectors.toList());

        return postResponses;
    }

    @Override
    public List<PostResponse> searchPost(String keyword) {

        List<Post> postList = this.postRepository.findByTitleStartingWith(keyword);

        List<PostResponse> postResponses = postList.stream().map( post -> this.modelMapper.map(post,PostResponse.class)).collect(Collectors.toList());

        return postResponses;
    }
}
