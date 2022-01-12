<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>초단기실황조회 FORM</title>
</head>
<body>
	<form action="getultrasrtncst" method="post">
		<li><a>초단기실황조회는 1일 이내의 일시만 가능합니다.</a></li>
		조회 날짜 : <input type="text" name="date"/> ( 단, yyyymmdd 형태로 입력.  ex)20220105 )
		<br/>
		조회 시간 : <input type="text" name="time" /> ( 단, hhmm 형태로 입력.  ex)0600 )
		<br/>
		<input type="submit" value="조회"/>
	</form>	
</body>
</html>