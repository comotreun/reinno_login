package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import com.example.demo.dto.MemberDto;

@Repository
@Mapper
public interface LoginMapper {
	public void insertMember(MemberDto memberDto);								//회원가입
	public MemberDto selectMember(String userEmail);							//로그인
}
