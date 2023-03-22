package com.buiquoctrieu.blog.services.impl;

import com.buiquoctrieu.blog.entities.Category;
import com.buiquoctrieu.blog.entities.User;
import com.buiquoctrieu.blog.exceptions.BadRequestException;
import com.buiquoctrieu.blog.exceptions.NotFoundException;
import com.buiquoctrieu.blog.payloads.request.CategoryRequest;
import com.buiquoctrieu.blog.payloads.response.CategoryResponse;
import com.buiquoctrieu.blog.payloads.response.UserResponse;
import com.buiquoctrieu.blog.repositories.CategoryRepository;
import com.buiquoctrieu.blog.services.CategoryService;
import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        Category  category = this.modelMapper.map(categoryRequest,Category.class);

        Optional<Category> findCategory = this.categoryRepository.findByTitle(category.getTitle());

        if (findCategory.isPresent()){
            throw new BadRequestException("Category", "title", category.getTitle());
        }

        Category saveCategory = this.categoryRepository.save(category);

        return this.modelMapper.map(saveCategory,CategoryResponse.class);
    }

    @Override
    public CategoryResponse updateCategory(CategoryRequest categoryRequest, Long id) {
        Category findCategory = this.categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Category", "id", String.valueOf(id)));

        findCategory.setTitle(categoryRequest.getTitle());
        findCategory.setDescription(categoryRequest.getDescription());

        Category updateCategory = this.categoryRepository.save(findCategory);

        return this.modelMapper.map(updateCategory,CategoryResponse.class);
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        Category findCategory = this.categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Category", "id", String.valueOf(id)));
        return this.modelMapper.map(findCategory,CategoryResponse.class);
    }

    @Override
    public List<CategoryResponse> getAllCategory() {
        List<Category> categories = this.categoryRepository.findByStatus(true);
        List<CategoryResponse> categoryResponses = categories.stream().map(user -> this.modelMapper.map(user,CategoryResponse.class)).collect(Collectors.toList());
        return categoryResponses;
    }

    @Override
    public void deleteCategory(Long id) {
        Category findCategory = this.categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Category", "id", String.valueOf(id)));
        findCategory.setStatus(false);
        this.categoryRepository.save(findCategory);
    }
}
