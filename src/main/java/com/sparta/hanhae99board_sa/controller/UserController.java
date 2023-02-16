package com.sparta.hanhae99board_sa.controller;

import com.sparta.hanhae99board_sa.dto.ResponseDto;
import com.sparta.hanhae99board_sa.dto.UserRequestDto;
import com.sparta.hanhae99board_sa.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseDto signup(@RequestBody UserRequestDto userRequestDto) {
        return userService.signup(userRequestDto);
    }

    @PostMapping("login")
    public ResponseDto login(@RequestBody UserRequestDto userRequestDto, HttpServletResponse response) {

        return userService.login(userRequestDto, response);
    }


}
