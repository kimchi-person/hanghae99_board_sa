package com.sparta.hanhae99board_sa.service;


import com.sparta.hanhae99board_sa.dto.BoardRequestDto;
import com.sparta.hanhae99board_sa.dto.BoardResponseDto;
import com.sparta.hanhae99board_sa.entity.Board;
import com.sparta.hanhae99board_sa.entity.User;
import com.sparta.hanhae99board_sa.jwt.JwtUtil;
import com.sparta.hanhae99board_sa.repository.BoardRepository;
import com.sparta.hanhae99board_sa.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    // 게시글 작성하기
    @Transactional
    public BoardResponseDto createPost(BoardRequestDto boardRequestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
                User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                        () -> new IllegalArgumentException("회원정보를 찾을 수 없습니다.")
                );
                Board board = new Board(boardRequestDto, user);
                boardRepository.save(board);

                return new BoardResponseDto(board);
            }
            throw new IllegalArgumentException("토큰 오류");
        }
        return null;
    }

    // 게시글 목록 조회
    @Transactional
    public List<BoardResponseDto> getPosts() {
        List<Board> list = boardRepository.findAll();

        List<BoardResponseDto> boardResponseDtoList = new ArrayList<>();
        for (Board board : list) {
            boardResponseDtoList.add(new BoardResponseDto(board));
        }
        return boardResponseDtoList;
    }

    @Transactional
    public BoardResponseDto getPost(Long id) {
        return new BoardResponseDto(boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 게시글입니다.")
        ));
    }

    @Transactional
    public BoardResponseDto updatePost(Long id, BoardRequestDto boardRequestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다.")
            );
            Board board = boardRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("해당 게시글을 찾을 수 없습니다.")
            );
            if (user.getUsername().equals(board.getUsername())) {
                board.update(boardRequestDto);
                return new BoardResponseDto(board);
            }
        }
        return null;
    }

    @Transactional
    public BoardResponseDto deletePost(Long id, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );
            Board board = boardRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                    () -> new IllegalArgumentException("해당 상품은 존재하지 않습니다.")
            );
            if (user.getUsername().equals(board.getUsername())) {
                boardRepository.deleteById(id);
            } else {
                return null;
            }
        }
        throw new IllegalArgumentException("토큰 오류");
    }
}



