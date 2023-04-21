package com.example.springlv2.controller;

import com.example.springlv2.dto.LoginRequestDto;
import com.example.springlv2.dto.MsgResponseDto;
import com.example.springlv2.dto.SignupRequestDto;
import com.example.springlv2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.aspectj.bridge.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController //
//@Controller // Client로 부터 view반환, MVC패턴의 Controller클래스 명시
//@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {
    //임의로 했음
    private final UserService userService;
//    public UserController(UserService userService) {
//        this.userService = userService;
//    }
//
//    @GetMapping("/signup")
//    public ModelAndView signupPage() {
//        return new ModelAndView("signup");
//    }
//
//    @GetMapping("/login")
//    public ModelAndView loginPage() {
//        return new ModelAndView("login");
//    }

    @PostMapping("/signup")
//    @Valid
//    public void signup(@RequestBody SignupRequestDto signupRequestDto ) {
    public ResponseEntity<MsgResponseDto> signup(@RequestBody SignupRequestDto signupRequestDto ) {
        userService.signup(signupRequestDto);
        return ResponseEntity.ok(new MsgResponseDto("회원가입 완료", HttpStatus.OK.value()));
    }

//    @PostMapping("/login")
//    public String login(LoginRequestDto loginRequestDto) {
//        userService.login(loginRequestDto);
//        return "redirect:/api/shop";
//    }

    @ResponseBody
    @PostMapping("/login")
    public ResponseEntity<MsgResponseDto> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        userService.login(loginRequestDto, response);
        return ResponseEntity.ok(new MsgResponseDto("로그인 완료",HttpStatus.OK.value()));
    }

}