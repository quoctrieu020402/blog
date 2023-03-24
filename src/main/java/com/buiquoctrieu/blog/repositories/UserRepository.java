package com.buiquoctrieu.blog.repositories;

import com.buiquoctrieu.blog.entities.Category;
import com.buiquoctrieu.blog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    List<User> findByStatus(boolean b);
}
