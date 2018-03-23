package com.dgit.domain;

public class LoginDTO {
	private String userid;
	private String userpw;
	private String username;
	
	public String getUserid() {
		return userid;
	}
	public String getUserpw() {
		return userpw;
	}
	public String getUsername() {
		return username;
	}
	
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public void setUserpw(String userpw) {
		this.userpw = userpw;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@Override
	public String toString() {
		return "LoginDTO [userid=" + userid + ", userpw=" + userpw + ", username=" + username + "]";
	}	
}
