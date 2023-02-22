package com.sparta.hanhae99board_sa.controller;

import com.sparta.hanhae99board_sa.dto.BoardRequestDto;
import com.sparta.hanhae99board_sa.dto.BoardResponseDto;
import com.sparta.hanhae99board_sa.dto.ResponseDto;
import com.sparta.hanhae99board_sa.security.UserDetailsImpl;
import com.sparta.hanhae99board_sa.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardController {

    private final BoardService boardService;

    // 게시글 작성하기 (로그인 필요)
    @PostMapping("/post")
    public BoardResponseDto createPost(@RequestBody BoardRequestDto boardRequestDto,
                                       @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.createPost(boardRequestDto, userDetails.getUser().getUsername());
    }

    // 모든 게시글 보여주기 (로그인 필요없음)
    @GetMapping("/posts")
    public List<BoardResponseDto> getPosts() {
        return boardService.getPosts();
    }

    // 특정 게시글 조회 (로그인 필요없음)
    @GetMapping("/post/{id}")
    public BoardResponseDto getPost(@PathVariable Long id) {
        return boardService.getPost(id);
    }

    // 특정 게시글 업데이트
    @PutMapping("/post/{id}")
    public BoardResponseDto updatePost(@PathVariable Long id,
                                       @RequestBody BoardRequestDto boardRequestDto,
                                       @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.updatePost(id, boardRequestDto, userDetails.getUser().getUsername());
    }

    // 게시글 삭제
    @DeleteMapping("/post/{id}")
    public ResponseDto<String>  deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.deletePost(id, userDetails.getUser().getUsername());
    }
}
