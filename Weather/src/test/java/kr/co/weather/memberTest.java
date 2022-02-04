package kr.co.weather;

import java.text.*;
import java.util.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import kr.co.weather.dao.Mapper;
import kr.co.weather.domain.*;
import kr.co.weather.service.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class memberTest {
	@Autowired
	private Mapper mapper;
	
	//@Test
	public void idCheckTest() { //work
		//존재하는 경우는 자신의 이메일 리턴
		System.out.println(
				mapper.idCheck("aaa"));
		//존재하지 않는 경우는 null
		System.out.println(
				mapper.idCheck("bbb"));
	}
	
	//@Test
	public void emailCheckTest() { //work
		//존재하는 경우는 자신의 이메일 리턴
		System.out.println(
				mapper.emailCheck("eatr75@gmail.net"));
		//존재하지 않는 경우는 null
		System.out.println(
				mapper.emailCheck("bbb@gmail.com"));
	}
	
	//@Test
	public void nicknameCheckTest() { //work
		//존재하는 경우는 자신의 닉네임 리턴
		System.out.println(
				mapper.nicknameCheck("lol"));
		//존재하지 않는 경우는 null
		System.out.println(
				mapper.nicknameCheck("AK"));
			
	}
	
	//@Test
	public void joinTest() { //work
		Member user = new Member();
		user.setMember_id("bbb");
		user.setMember_pw("1234");
		user.setMember_email("bbb@gmail.com");
		user.setNickname("AK");
		
		//insert, delete, update 는 영향받은 행의 개수가 리턴됨
		System.out.println(mapper.insertMember(user));
	}
	

}
