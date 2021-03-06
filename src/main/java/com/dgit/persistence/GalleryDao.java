package com.dgit.persistence;

import java.util.List;

import com.dgit.domain.GalleryVO;

public interface GalleryDao {
	public List<GalleryVO> listAll();
	public List<GalleryVO> listWithUserId(String userId);
	public void deleteImg(int gno);
	public GalleryVO selectOneByGno(int gno);
	public void insertImg(GalleryVO vo);
}
