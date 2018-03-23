package com.dgit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dgit.domain.MemberVO;
import com.dgit.persistence.MemberDao;

@Repository
public class MemberServiceImpl implements MemeberService{
	
	@Autowired
	MemberDao dao;
	
	@Override
	public void insertMember(MemberVO vo) throws Exception {
		dao.insertMember(vo);		
	}

	@Override
	public MemberVO readMember(String userid) throws Exception {
		return dao.readMember(userid);
	}

	@Override
	public MemberVO readWithPw(MemberVO vo) throws Exception {
		// TODO Auto-generated method stub
		return dao.readWithPw(vo);
	}

	


}
