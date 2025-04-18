package com.example.demo.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(value = "/member")
public class ApiLoginController {
	
	
	@Autowired
	private LoginService loginService;
	
    @PostMapping("/signup")
    public ResponseEntity<MemberDto> signup(@RequestBody MemberDto memberDto) {
        MemberDto savedMember = loginService.insertMember(memberDto);
        return ResponseEntity.ok(savedMember);
    }
    
    
    @PostMapping("/login")
    public ResponseEntity<MemberDto> login(@RequestBody MemberDto memberDto) {
        MemberDto savedMember = loginService.selectMember(memberDto);
        return ResponseEntity.ok(savedMember);
    }
}
