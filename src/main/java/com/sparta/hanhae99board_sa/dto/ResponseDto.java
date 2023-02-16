package com.sparta.hanhae99board_sa.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDto<T> {
    private int statusCode;
    private T msg;

    public ResponseDto(int statusCode, T result) {
        this.statusCode = statusCode;
        this.msg = result;
    }

    public static <T> ResponseDto<T> success(T result) {
        return new ResponseDto<>(200, result);
    }

    public static <T> ResponseDto<T> fail(int statusCode ,T result) {
        return new ResponseDto<>(statusCode, result);
    }
}
