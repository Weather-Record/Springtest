package kr.co.weather.service;

import java.util.*;
import javax.servlet.http.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

import kr.co.weather.dao.Mapper;
import kr.co.weather.domain.Member;

@Service
public class BasicServiceImpl implements BasicService {
	@Autowired
	private Mapper mapper;

	@Override
	public Map<String, Object> idCheck(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<>();

		String id = request.getParameter("id");
		String result = mapper.idCheck(id);

		//없으면 result = true
		if(result == null) {
			map.put("result", true);
		}else {
			map.put("result", false);
		}
		return map;
	}

	@Override
	public Map<String, Object> emailCheck(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<>();

		String email = request.getParameter("email");
		String result = mapper.emailCheck(email);

		//없으면 result = true
		if(result == null) {
			map.put("result", true);
		}else {
			map.put("result", false);
		}
		return map;
	}

	@Override
	public Map<String, Object> nicknameCheck(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<>();

		String nickname = request.getParameter("nickname");
		String result = mapper.nicknameCheck("nickname");
		//없으면 result = true
		if(result==null) {
			map.put("result", true);
		}else {
			map.put("result", false);
		}
		return map;
	}

	@Override
	public Map<String, Object> insertMember(HttpServletRequest request, HttpServletResponse response) {
		// id/nick/email check 결과 저장 + false로 초기화
		Map<String, Object> map = new HashMap<>();
		map.put("result", false); //가입 결과
		map.put("idcheck",false); // id 확인 결과
		map.put("emailcheck", false); //email 확인 결과
		map.put("nicknamecheck", false); //nick 확인 결과

		// read parameter
		String id = request.getParameter("id");
		String email = request.getParameter("email");
		String pw = request.getParameter("pw");
		String nickname = request.getParameter("nickname");


		//id 체크
		String idresult = mapper.idCheck(id);
		if(idresult == null) {
			map.put("idcheck", true);
		}

		//email 체크
		String emailresult = mapper.emailCheck(email);
		if(emailresult == null) {
			map.put("emailcheck", true);
		}

		//nickname 체크
		String nicknameresult = mapper.nicknameCheck(nickname);
		if(nicknameresult == null) {
			map.put("nicknamecheck", true);
		}

		//중복 확인 패스하면 진행,
		if(idresult == null && emailresult == null && nicknameresult == null) {
			//가입 정보 저장
			Member member = new Member();
			member.setMember_id(id);
			member.setMember_email(email);
			member.setMember_pw(pw);
			member.setNickname(nickname);

			//회원 가입
			int result = mapper.insertMember(member);
			if(result>0) {
				map.put("result", true);
			}
		}
		return map; //가입 및 중복 결과 리턴
	}
}
