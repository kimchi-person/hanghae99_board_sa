package com.sparta.hanhae99board_sa.service;

import com.sparta.hanhae99board_sa.dto.ResponseDto;
import com.sparta.hanhae99board_sa.dto.UserRequestDto;
import com.sparta.hanhae99board_sa.entity.User;
import com.sparta.hanhae99board_sa.entity.UserRoleEnum;
import com.sparta.hanhae99board_sa.jwt.JwtUtil;
import com.sparta.hanhae99board_sa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;

    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";


    // 회원가입
    @Transactional
    public ResponseDto<String> signup(UserRequestDto userRequestDto) {
        String username = userRequestDto.getUsername();
        String password = userRequestDto.getPassword();

        if (userRequestDto.chek(username, password)) {   // 형식에 맞는 아이디, 패스워드 일때 실행
            // 들어온 비밀번호가 정규식 조건에서 통과하면 encode 진행
            String pass_encode = passwordEncoder.encode(password);
            Optional<User> optionalUser = userRepository.findByUsername(username);
            if (optionalUser.isPresent()) {     // 아이디 중복체크
                throw new IllegalArgumentException("이미 존재하는 아이디 입니다.");
            }

            UserRoleEnum role = UserRoleEnum.USER;
            if (!userRequestDto.getAdminToken().isEmpty()) {
                if (!userRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                    throw new IllegalArgumentException("관리자 암호가 맞지 않습니다.");
                }
                role = UserRoleEnum.ADMIN;
            }
            User user = new User(username, pass_encode, role);
            userRepository.save(user);
            return ResponseDto.success("회원가입 성공");
        }
        throw new IllegalArgumentException("아이디 / 비밀번호 형식이 틀립니다.");
    }

    // 로그인
    public ResponseDto<String> login(UserRequestDto userRequestDto, HttpServletResponse response) {
        String username = userRequestDto.getUsername();
        String password = userRequestDto.getPassword();

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("회원을 찾을 수 없습니다.")
        );
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));
        return ResponseDto.success("로그인 성공");
    }

}
