package com.sparta.hanhae99board_sa.service;


import com.sparta.hanhae99board_sa.dto.CommentRequestDto;
import com.sparta.hanhae99board_sa.dto.CommentResponseDto;
import com.sparta.hanhae99board_sa.dto.ResponseDto;
import com.sparta.hanhae99board_sa.entity.Board;
import com.sparta.hanhae99board_sa.entity.Comment;
import com.sparta.hanhae99board_sa.entity.User;
import com.sparta.hanhae99board_sa.entity.UserRoleEnum;
import com.sparta.hanhae99board_sa.repository.BoardRepository;
import com.sparta.hanhae99board_sa.repository.CommentRepository;
import com.sparta.hanhae99board_sa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;


    // 코멘트 남기기
    public CommentResponseDto createComment(Long id, CommentRequestDto commentRequestDto, String username) {

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );

        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        Comment comment = commentRepository.saveAndFlush(new Comment(commentRequestDto, board, user));

        return new CommentResponseDto(comment, user);
    }

    // 코멘트 수정하기
    public CommentResponseDto updateComment(Long id, CommentRequestDto commentRequestDto, String username) {

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );
        if (user.getRole() == UserRoleEnum.ADMIN || user.getId().equals(comment.getUser().getId())) {
            comment.update(commentRequestDto);
            return new CommentResponseDto(comment, user);
        }
        throw new IllegalArgumentException("댓글이 이미 삭제 되었거나, 댓글 작성자만 삭제 가능합니다.");
    }
    
    // 댓글 삭제하기
    public ResponseDto<String> deleteComment(Long id, String username) {

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );
        if (user.getRole() == UserRoleEnum.ADMIN || user.getId().equals(comment.getUser().getId())) {
            commentRepository.delete(comment);
            return ResponseDto.success("댓글 삭제 완료");
        }
        throw new IllegalArgumentException("존재하지 않는 댓글입니다.");
    }
}

