package com.sparta.hanhae99board_sa.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.regex.Pattern;

@Getter
@Setter
public class UserRequestDto {
    private String username;
    private String password;

    private String adminToken = "";

    public boolean chek(String username, String password) {

        boolean userCheck = Pattern.matches("^[a-z0-9]{4,10}$", username);
        boolean passCheck = Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,15}$", password);

        return userCheck && passCheck;
    }



}
