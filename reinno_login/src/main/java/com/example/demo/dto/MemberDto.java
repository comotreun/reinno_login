package com.example.demo.dto;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {

	private int userSeq;
	private String userName;
	private String userPw;
	private String userEmail;
	private String userTel;
	private String userYn;
	private Timestamp joinDate;

}
