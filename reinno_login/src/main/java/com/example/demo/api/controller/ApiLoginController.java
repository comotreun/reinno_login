package com.example.demo.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.LoginService.LoginService;
import com.example.demo.dto.MemberDto;

@RestController
@RequestMapping("/member")  // URL 경로 예시, 상황에 맞게 조정하세요
public class ApiLoginController {

    private final LoginService loginService;

    public ApiLoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody MemberDto memberDto) {
        try {
            MemberDto savedMember = loginService.insertMember(memberDto);
            return ResponseEntity.ok("회원가입에 성공하였습니다.");
        } catch (Exception e) {
            // 예외 메시지 로깅 권장
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("회원가입에 실패하였습니다.");
        }
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberDto memberDto) {
        MemberDto savedMember = loginService.selectMember(memberDto);
        if (savedMember != null) {
            return ResponseEntity.ok("로그인에 성공하였습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                 .body("아이디 또는 비밀번호가 올바르지 않습니다.");
        }
    }
}

