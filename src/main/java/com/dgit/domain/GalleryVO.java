package com.dgit.domain;

import java.util.Date;

public class GalleryVO {
	private int gno;
	private String userId;
	private String gpath;
	private Date uploadDate;
	
	public int getGno() {
		return gno;
	}
	public void setGno(int gno) {
		this.gno = gno;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getGpath() {   
		return gpath;
	}
	public void setGpath(String gpath) {
		this.gpath = gpath;
	}
	public Date getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}
}
