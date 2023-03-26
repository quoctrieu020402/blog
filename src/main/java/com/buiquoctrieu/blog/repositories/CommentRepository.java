package com.buiquoctrieu.blog.repositories;

import com.buiquoctrieu.blog.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
