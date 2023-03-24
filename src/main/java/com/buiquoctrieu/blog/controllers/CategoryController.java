package com.buiquoctrieu.blog.controllers;

import com.buiquoctrieu.blog.payloads.request.CategoryRequest;
import com.buiquoctrieu.blog.payloads.request.UserRequest;
import com.buiquoctrieu.blog.payloads.response.CategoryResponse;
import com.buiquoctrieu.blog.payloads.response.MessageResponse;
import com.buiquoctrieu.blog.payloads.response.UserResponse;
import com.buiquoctrieu.blog.services.CategoryService;
import com.buiquoctrieu.blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/")
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryRequest categoryRequest) {

        CategoryResponse createCategoryResponse = this.categoryService.createCategory(categoryRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(createCategoryResponse);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> updateCategory(@Valid @RequestBody CategoryRequest categoryRequest, @PathVariable(name = "categoryId") Long id) {

        CategoryResponse updateCategoryResponse = this.categoryService.updateCategory(categoryRequest, id);

        return ResponseEntity.status(HttpStatus.OK).body(updateCategoryResponse);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<MessageResponse> deleteCategory(@PathVariable(name = "categoryId") Long id) {

        this.categoryService.deleteCategory(id);

        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Category deleted successfully"));
    }

    @GetMapping("/")
    public ResponseEntity<List<CategoryResponse>> getAllCategories(){

        List<CategoryResponse> categoryResponses = this.categoryService.getAllCategory();

        return ResponseEntity.status(HttpStatus.OK).body(categoryResponses);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> getCategory(@PathVariable(name = "categoryId") Long id){

        CategoryResponse categoryResponse = this.categoryService.getCategoryById(id);

        return ResponseEntity.status(HttpStatus.OK).body(categoryResponse);
    }
}
