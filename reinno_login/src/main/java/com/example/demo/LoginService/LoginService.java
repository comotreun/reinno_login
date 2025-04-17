package com.example.demo.LoginService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.MemberDto;
import com.example.demo.mapper.LoginMapper;

@Service
public class LoginService {

	@Autowired
	LoginMapper loginMapper;

	// 회원가입
	public MemberDto insertMember(MemberDto memberDto) {
		loginMapper.insertMember(memberDto);
		return memberDto;
	}

	// 로그인
	public MemberDto selectMember(String userEmail) {
		return loginMapper.selectMember(userEmail);
	}

}
