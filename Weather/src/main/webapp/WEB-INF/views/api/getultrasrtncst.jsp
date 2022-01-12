<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>초단기실황조회</title>
</head>
<body>
	<div align="left">
		<h2>초단기실황 조회 항목</h2>
		<table border="1">
			<!-- <tr>현재 일시 : </tr><br/> -->
			<tr>조회 날짜 : ${list.baseDate}</tr><br/>
			<tr>조회 시간 : ${list.baseTime}</tr><br/>
			<tr>온도 : ${list.t1h}</tr><br/>
			<tr>습도 : ${list.reh}</tr><br/>
		</table>
	</div>
	<br/>
	<a href = "/">메인으로</a><br/>
</body>
</html>