package com.buiquoctrieu.blog.repositories;

import com.buiquoctrieu.blog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
