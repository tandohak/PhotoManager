package com.dgit.service;

import com.dgit.domain.MemberVO;

public interface MemeberService {
	public void insertMember(MemberVO vo)  throws Exception;
	public MemberVO readMember(String userid) throws Exception;
	public MemberVO readWithPw(MemberVO vo) throws Exception;
}
