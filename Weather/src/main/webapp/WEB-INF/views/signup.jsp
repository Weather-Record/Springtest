<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<!-- pageContext.request.contextPath 를 이용하면 절대 경로를 기준으로 설정 
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/user/join.css">-->
<script src="${pageContext.request.contextPath}/js-css/signup.js"></script>

<title>회원가입</title>
</head>
<body>
	<form id="signup">
		<h2>회원가입</h2>
		<div align="center" id="msg"></div>
		<ul>
			<li><label for="id">ID</label> <input type="text" id="id"
				name="id" class="textinput" placeholder="ID를 입력하세요" /></li>
			<div id="idmsg"></div>

			<li><label for="email">Email</label> <input type="text"
				id="email" name="email" class="textinput"
				placeholder="이메일 주소를 입력하세요" /></li>
			<div id="emailmsg"></div>

			<li><label for="pw">Pass</label> <input type="password" id="pw"
				name="pw" class="textinput" placeholder="비밀번호를 입력하세요" /></li>
			<div id="pwmsg"></div>

			<li><label for="pw1">Check Pass</label> <input type="password"
				id="pw1" class="textinput" placeholder="똑같은 비밀번호를 입력하세요" /></li>

			<li><label for="nickname">Nickname</label> <input type="text"
				id="nickname" name="nickname" class="textinput" /></li>
			<div id="nicknamemsg"></div>

			<div align="left">
				<li class="buttons"><input type="button" value="회원가입"
					id="signupbtn" /> <input type="button" value="메인" id="mainbtn" /> <input
					type="button" value="로그인" id="loginbtn" /></li>
			</div>
		</ul>
	</form>
</body>
</html>