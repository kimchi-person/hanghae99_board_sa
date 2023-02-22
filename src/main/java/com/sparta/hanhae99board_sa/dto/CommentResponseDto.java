package com.sparta.hanhae99board_sa.dto;

import com.sparta.hanhae99board_sa.entity.Comment;
import com.sparta.hanhae99board_sa.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
public class CommentResponseDto {

    private Long id;
    private String content;
    private String username;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;

    public CommentResponseDto(Comment comment, User user) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.username = user.getUsername();
        this.createAt = comment.getCreateAt();
        this.modifiedAt = comment.getModifiedAt();
    }

    public static CommentResponseDto from(Comment comment) {
        return CommentResponseDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .username(comment.getUser().getUsername())
                .createAt(comment.getCreateAt())
                .modifiedAt(comment.getModifiedAt())
                .build();
    }
}
