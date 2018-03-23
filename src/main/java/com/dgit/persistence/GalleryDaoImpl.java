package com.dgit.persistence;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import com.dgit.domain.GalleryVO;

public class GalleryDaoImpl implements GalleryDao {
	private static final String namespace = "com.dgit.mapper.GalleryMapper.";

	@Autowired
	private SqlSession sqlSession;

	@Override
	public List<GalleryVO> listAll() {
		return sqlSession.selectList(namespace+"listAll");
	}

	@Override
	public void deleteImg(int gno) {
		sqlSession.delete(namespace+"delete", gno);
	}

	@Override
	public void insertImg(GalleryVO vo) {
		sqlSession.insert(namespace+"insert",vo);
	}
	
}
