package com.spring.common;

import java.sql.Date;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class MemberVO {
	@Size(min=4,max=10, message="아이디는 4자이상 10자 이내로 입력해주세요.")
	@NotEmpty(message="아이디를 입력하세요.")
	private String memid;
	@Size(min=4,max=10, message="비밀번호는 4자이상 20자 이내로 입력해주세요.")
	@NotEmpty(message="비밀번호를 입력하세요.")
	private String mempass;
	@NotEmpty(message="이름을 입력하세요.")
	@Size(max=10, message="이름은 10자 이내로 입력해주세요.")
	private String memname;
	private Date memdate;
	
	public String getMemid() {
		return memid;
	}
	public void setMemid(String memid) {
		this.memid = memid;
	}
	public String getMempass() {
		return mempass;
	}
	public void setMempass(String mempass) {
		this.mempass = mempass;
	}
	public String getMemname() {
		return memname;
	}
	public void setMemname(String memname) {
		this.memname = memname;
	}
	public Date getMemdate() {
		return memdate;
	}
	public void setMemdate(Date memdate) {
		this.memdate = memdate;
	}
	
	@Override
	public String toString() {
		return "MemberVO [memid=" + memid + ", mempass=" + mempass + ", memname="
				+ memname + ", memdate=" + memdate + "]";
	}
}
