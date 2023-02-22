package com.sparta.hanhae99board_sa.service;


import com.sparta.hanhae99board_sa.dto.BoardRequestDto;
import com.sparta.hanhae99board_sa.dto.BoardResponseDto;
import com.sparta.hanhae99board_sa.dto.ResponseDto;
import com.sparta.hanhae99board_sa.entity.Board;
import com.sparta.hanhae99board_sa.entity.User;
import com.sparta.hanhae99board_sa.entity.UserRoleEnum;
import com.sparta.hanhae99board_sa.repository.BoardRepository;
import com.sparta.hanhae99board_sa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;


    // 게시글 작성하기
    @Transactional
    public BoardResponseDto createPost(BoardRequestDto boardRequestDto, String username) {
            User user = userRepository.findByUsername(username).orElseThrow(
                    () -> new IllegalArgumentException("사용자 정보가 없습니다.")
            );
            Board board = new Board(boardRequestDto, user);
            boardRepository.save(board);

            return new BoardResponseDto(board);
        }

    // 게시글 전부조회
    @Transactional(readOnly = true)
    public List<BoardResponseDto> getPosts() {
        List<Board> list = boardRepository.findAllByOrderByCreateAtDesc();

        List<BoardResponseDto> boardResponseDtoList = new ArrayList<>();
        for (Board board : list) {
            boardResponseDtoList.add(new BoardResponseDto(board));
        }
        if (!boardResponseDtoList.isEmpty()) {
            return boardResponseDtoList;
        }else {
            throw new IllegalArgumentException("게시글이 아무것도 존재하지 않습니다.");
        }
    }

    // 특정 게시글 조회
    @Transactional
    public BoardResponseDto getPost(Long id) {
        return new BoardResponseDto(boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 게시글입니다.")
        ));
    }

    // 특정 게시글 업데이트
    @Transactional
    public BoardResponseDto updatePost(Long id, BoardRequestDto boardRequestDto, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("사용자 정보가 없습니다.")
        );
        Board board = boardRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다.")
        );
        if (user.getRole() == UserRoleEnum.ADMIN || user.getUsername().equals(board.getUser().getUsername())) {
            board.update(boardRequestDto);
            return new BoardResponseDto(board);
        }
        return null;
    }

    // 특정 게시글 삭제
    public ResponseDto<String> deletePost(Long id, String username) {
            User user = userRepository.findByUsername(username).orElseThrow(
                    () -> new IllegalArgumentException("사용자 정보가 없습니다.")
            );
            Board board = boardRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                    () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다.")
            );
            if (user.getRole() == UserRoleEnum.ADMIN || user.getUsername().equals(board.getUser().getUsername())){
                boardRepository.delete(board);
                return ResponseDto.success("게시글 삭제완료");
            }else {
                throw new IllegalArgumentException("게시글이 이미 삭제 되었거나, 게시글 작성자만 삭제 가능합니다.");
            }
    }
}



