package com.sparta.hanhae99board_sa.service;

import com.sparta.hanhae99board_sa.dto.ResponseDto;
import com.sparta.hanhae99board_sa.dto.UserRequestDto;
import com.sparta.hanhae99board_sa.entity.User;
import com.sparta.hanhae99board_sa.entity.UserRoleEnum;
import com.sparta.hanhae99board_sa.jwt.JwtUtil;
import com.sparta.hanhae99board_sa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    // 회원가입
    @Transactional
    public ResponseDto signup(@RequestBody UserRequestDto userRequestDto) {
        String username = userRequestDto.getUsername();
        String password = userRequestDto.getPassword();

        boolean userCheck = Pattern.matches("^[a-z0-9]{4,10}$", username);
        boolean passCheck = Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,15}$", password);

        if (userCheck && passCheck) {   // 형식에 맞는 아이디, 패스워드 일때 실행
            Optional<User> optionalUser = userRepository.findByUsername(username);
            if (optionalUser.isPresent()) {     // 아이디 중복체크
                throw new IllegalArgumentException("이미 존재하는 아이디 입니다.");
            }

            User user = new User(username, password, UserRoleEnum.USER);
            userRepository.save(user);
            return ResponseDto.success("회워가입 성공");
        } else {
            throw new IllegalArgumentException("아이디 / 비밀번호 형식이 맞지 않습니다.");
        }

    }

    // 로그인
    public ResponseDto login(@RequestBody UserRequestDto userRequestDto, HttpServletResponse response) {
        String username = userRequestDto.getUsername();
        String password = userRequestDto.getPassword();

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("회원을 찾을 수 없습니다.")
        );
        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));
        return ResponseDto.success("로그인 성공");
    }

}
