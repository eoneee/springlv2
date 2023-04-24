package com.example.springlv2.service;

import com.example.springlv2.dto.LoginRequestDto;
import com.example.springlv2.dto.MsgResponseDto;
import com.example.springlv2.dto.SignupRequestDto;
import com.example.springlv2.entity.User;
import com.example.springlv2.entity.UserRoleEnum;
import com.example.springlv2.jwt.JwtUtil;
import com.example.springlv2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.*;
import java.lang.*;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    // ADMIN_TOKEN
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    @Transactional
//    public void ResponseEntity <MsgResponseDto> signup(SignupRequestDto signupRequestDto) {
        public void signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String password = signupRequestDto.getPassword();
        // 회원 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("아이디가 이미 존재합니다.");
        }
        //아이디 정규식 확인
        if (!Pattern.matches("^[a-z0-9]{4,10}$", username)) {
//            return ResponseEntity.ok(new MsgResponseDto("아이디는 4자 이상, 10자 이하 알파벳 소문자, 숫자로만 이루어져야 합니다.",505));
            throw new IllegalArgumentException("아이디는 4자 이상, 10자 이하 알파벳 소문자, 숫자로만 이루어져야 합니다.");
        }
        //비밀번호 정규식 확인
        Optional<User> pw = userRepository.findByUsername(password);
        if (!Pattern.matches("^\\w{8,15}$", password)) {
//            return ResponseEntity.ok(new MsgResponseDto("비밀번호는 8자 이상, 15자 이하 알파벳 대/소문자, 숫자로만 이루어져야 합니다.",505));

            throw new IllegalArgumentException("비밀번호는 8자 이상, 15자 이하 알파벳 대/소문자, 숫자로만 이루어져야 합니다.");
        }
        User user = new User(username, password);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public void login(LoginRequestDto loginRequestDto,HttpServletResponse response) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        // 사용자 확인
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 아이디가 없습니다.")
        );

        // 비밀번호 확인
        if(!user.getPassword().equals(password)){
            throw  new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername()));
    }
}