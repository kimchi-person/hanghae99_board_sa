package com.sparta.hanhae99board_sa.repository;

import com.sparta.hanhae99board_sa.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Optional<Board> findByIdAndUserId(Long id, Long userId);
    List<Board> findAllByOrderByCreateAtDesc();
}
