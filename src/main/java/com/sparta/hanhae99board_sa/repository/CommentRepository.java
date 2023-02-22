package com.sparta.hanhae99board_sa.repository;

import com.sparta.hanhae99board_sa.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
