package com.buiquoctrieu.blog.controllers;

import com.buiquoctrieu.blog.constants.PagingConstant;
import com.buiquoctrieu.blog.payloads.request.PostRequest;
import com.buiquoctrieu.blog.payloads.response.MessageResponse;
import com.buiquoctrieu.blog.payloads.response.PagingResponse;
import com.buiquoctrieu.blog.payloads.response.PostResponse;
import com.buiquoctrieu.blog.services.FileService;
import com.buiquoctrieu.blog.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${project.image}")
    private String path;

    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostResponse> createPost(@Valid @RequestBody PostRequest postRequest,@PathVariable("categoryId") Long categoryId,@PathVariable("userId") Long userId) {

        PostResponse createPostResponse = this.postService.createPost(postRequest,userId,categoryId);

        return ResponseEntity.status(HttpStatus.CREATED).body(createPostResponse);
    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostResponse>> getAllPostOfUser(@PathVariable("userId") Long userId){

        List<PostResponse> lists = this.postService.getAllPostOfUser(userId);

        return ResponseEntity.status(HttpStatus.OK).body(lists);
    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostResponse>> getAllPostOfCategory(@PathVariable("categoryId") Long categoryId){

        List<PostResponse> lists = this.postService.getAllPostOfCategory(categoryId);

        return ResponseEntity.status(HttpStatus.OK).body(lists);
    }

    @GetMapping("/posts")
    public ResponseEntity<PagingResponse> getAllPost(
            @RequestParam(value = "pageNumber", defaultValue = PagingConstant.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = PagingConstant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = PagingConstant.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = PagingConstant.SORT_DIR, required = false) String sortDir){

        PagingResponse lists = this.postService.getAllPost(pageSize, pageNumber, sortBy, sortDir);

        return ResponseEntity.status(HttpStatus.OK).body(lists);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostResponse> getPost(@PathVariable("postId") Long postId){

        PostResponse post = this.postService.getPostById(postId);

        return ResponseEntity.status(HttpStatus.OK).body(post);
    }

    @GetMapping("post/search/{search}")
    public ResponseEntity<List<PostResponse>> searchPost(@PathVariable("search") String search){

        List<PostResponse> posts = this.postService.searchPost(search);

        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<MessageResponse> deletePost(@PathVariable("postId") Long postId){

        this.postService.deletePost(postId);

        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Post deleted successfully"));
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostResponse> updatePost(@Valid @RequestBody PostRequest postRequest, @PathVariable("postId") Long postId){

        PostResponse updatePost = this.postService.updatePost(postRequest, postId);

        return ResponseEntity.status(HttpStatus.OK).body(updatePost);
    }

    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostResponse> uploadPostImage(
            @RequestParam("image") MultipartFile file,
            @PathVariable("postId") Long postId) throws IOException {

        String fileName = this.fileService.uploadImage(path,file);

        System.out.println(fileName);

        PostResponse postResponse = this.postService.getPostById(postId);

        postResponse.setImage(fileName);

        PostRequest postRequest = this.modelMapper.map(postResponse,PostRequest.class);

        PostResponse updatePost = this.postService.updatePost(postRequest, postId);

        return ResponseEntity.status(HttpStatus.OK).body(updatePost);
    }

    @GetMapping(value = "post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @PathVariable("imageName") String imageName, HttpServletResponse response) throws IOException {

        InputStream resource = this.fileService.getResource(path,imageName);

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);

        StreamUtils.copy(resource,response.getOutputStream());
    }

}
