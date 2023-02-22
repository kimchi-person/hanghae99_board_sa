package com.sparta.hanhae99board_sa.dto;


import com.sparta.hanhae99board_sa.entity.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BoardResponseDto {

    private Long id;
    private String title;
    private String content;
    private String username;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;

    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.username = board.getUsername();
        this.content = board.getContent();
        this.createAt = board.getCreateAt();
        this.modifiedAt = board.getModifiedAt();
    }

}
