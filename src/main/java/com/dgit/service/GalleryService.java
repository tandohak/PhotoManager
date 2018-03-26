package com.dgit.service;

import java.util.List;

import com.dgit.domain.GalleryVO;

public interface GalleryService {
	public List<GalleryVO> listAll();
	public List<GalleryVO> listWithUserId(String userId);
	public void deleteImg(int gno);
	public void insertImg(GalleryVO vo);
	public GalleryVO selectOneByGno(int gno);
}
