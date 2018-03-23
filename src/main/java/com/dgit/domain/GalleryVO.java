package com.dgit.domain;

import java.util.Date;

public class GalleryVO {
	private int gno;
	private String userId;
	private String gPath;
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
	public String getgPath() {
		return gPath;
	}
	public void setgPath(String gPath) {
		this.gPath = gPath;
	}
	public Date getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}
}
