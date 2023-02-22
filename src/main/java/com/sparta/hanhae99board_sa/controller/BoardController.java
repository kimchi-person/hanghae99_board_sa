package com.sparta.hanhae99board_sa.controller;


import com.sparta.hanhae99board_sa.dto.BoardRequestDto;
import com.sparta.hanhae99board_sa.dto.BoardResponseDto;
import com.sparta.hanhae99board_sa.dto.ResponseDto;
import com.sparta.hanhae99board_sa.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardController {

    private final BoardService boardService;

    // 게시글 작성하기
    @PostMapping("/post")
    public BoardResponseDto createPost(@RequestBody BoardRequestDto boardRequestDto, HttpServletRequest request) {
        return boardService.createPost(boardRequestDto, request);
    }

    // 모든 게시글 보여주기
    @GetMapping("/posts")
    public List<BoardResponseDto> getPosts() {
        return boardService.getPosts();
    }

    // 게시글 상세 조회
    @GetMapping("/post/{id}")
    public BoardResponseDto getPost(@PathVariable Long id) {
        return boardService.getPost(id);
    }

    @PutMapping("/post/{id}")
    public BoardResponseDto updatePost(@PathVariable Long id, BoardRequestDto boardRequestDto, HttpServletRequest request) {
        return boardService.updatePost(id, boardRequestDto, request);
    }

    @DeleteMapping("/post/{id}")
    public BoardResponseDto deletePost(@PathVariable Long id, HttpServletRequest request) {
        return boardService.deletePost(id, request);
    }
}
