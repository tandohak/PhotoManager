package com.dgit.persistence;

import java.util.List;

import com.dgit.domain.MemberVO;

public interface MemberDao {
	public void insertMember(MemberVO vo)  throws Exception;
	public MemberVO readMember(String userid) throws Exception;
	public MemberVO readWithPw(MemberVO vo) throws Exception;
}
 