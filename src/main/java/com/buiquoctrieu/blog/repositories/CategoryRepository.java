package com.buiquoctrieu.blog.repositories;

import com.buiquoctrieu.blog.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    Optional<Category> findByTitle(String title);
    List<Category> findByStatus(boolean b);
}
