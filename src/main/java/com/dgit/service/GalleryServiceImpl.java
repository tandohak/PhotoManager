package com.dgit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dgit.domain.GalleryVO;
import com.dgit.persistence.GalleryDao;

@Service
public class GalleryServiceImpl implements GalleryService {
	
	@Autowired
	GalleryDao dao;
	
	@Override
	public List<GalleryVO> listAll() {
		return dao.listAll();
	}

	@Override
	public void deleteImg(int gno) {
		dao.deleteImg(gno);
	}

	@Override
	public void insertImg(GalleryVO vo) {
		dao.insertImg(vo);
	}

	@Override
	public List<GalleryVO> listWithUserId(String userId) {
		return dao.listWithUserId(userId);
	}

	@Override
	public GalleryVO selectOneByGno(int gno) {
		return dao.selectOneByGno(gno);
	}

}
