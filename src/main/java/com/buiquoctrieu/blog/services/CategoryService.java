package com.buiquoctrieu.blog.services;

import com.buiquoctrieu.blog.payloads.request.CategoryRequest;
import com.buiquoctrieu.blog.payloads.request.UserRequest;
import com.buiquoctrieu.blog.payloads.response.CategoryResponse;
import com.buiquoctrieu.blog.payloads.response.UserResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest categoryRequest);
    CategoryResponse updateCategory(CategoryRequest categoryRequest, Long id);
    CategoryResponse getCategoryById(Long id);
    List<CategoryResponse> getAllCategory();
    void deleteCategory(Long id);
}
