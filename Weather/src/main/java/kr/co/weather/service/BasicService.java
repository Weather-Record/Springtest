package kr.co.weather.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.*;

import kr.co.weather.domain.Member;

public interface BasicService {
	//회원가입과 로그인 등 처리하는 서비스 서술
	
	//가입시, id/nickname check
	public Map<String, Object> idCheck(HttpServletRequest request, HttpServletResponse response);
	public Map<String, Object> emailCheck(HttpServletRequest request, HttpServletResponse response);
	public Map<String, Object> nicknameCheck(HttpServletRequest request, HttpServletResponse response);
	//회원 가입
	public Map<String, Object> insertMember(HttpServletRequest request, HttpServletResponse response);
	//로그인
	//public List<Member> login(HttpServletRequest request, HttpServletResponse response);
	
	//회원탈퇴
	
}
