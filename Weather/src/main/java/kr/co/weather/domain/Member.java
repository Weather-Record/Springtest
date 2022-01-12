package kr.co.weather.domain;

import java.util.Date;

public class Member {
	private int member_num;
	private String member_id;
	private String member_pw;
	private String member_email;
	private Date signup_date;
	private Date info_modify_date;
	private String nickname;
	private String group_id;
	
	public int getMember_num() {
		return member_num;
	}
	public void setMember_num(int member_num) {
		this.member_num = member_num;
	}
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public String getMember_pw() {
		return member_pw;
	}
	public void setMember_pw(String member_pw) {
		this.member_pw = member_pw;
	}
	public String getMember_email() {
		return member_email;
	}
	public void setMember_email(String member_email) {
		this.member_email = member_email;
	}
	public Date getSignup_date() {
		return signup_date;
	}
	public void setSignup_date(Date signup_date) {
		this.signup_date = signup_date;
	}
	public Date getInfo_modify_date() {
		return info_modify_date;
	}
	public void setInfo_modify_date(Date info_modify_date) {
		this.info_modify_date = info_modify_date;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getGroup_id() {
		return group_id;
	}
	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}
	@Override
	public String toString() {
		return "Member [member_num=" + member_num + ", member_id=" + member_id + ", member_pw=" + member_pw
				+ ", member_email=" + member_email + ", signup_date=" + signup_date + ", info_modify_date="
				+ info_modify_date + ", nickname=" + nickname + ", group_id=" + group_id + "]";
	}
}
