package com.buiquoctrieu.blog.repositories;

import com.buiquoctrieu.blog.entities.Category;
import com.buiquoctrieu.blog.entities.Post;
import com.buiquoctrieu.blog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);

    List<Post> findByTitleStartingWith(String title);


}
