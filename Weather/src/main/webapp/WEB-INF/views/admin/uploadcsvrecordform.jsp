<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>upload_record</title>
</head>
<body>
	<h3>Record 파일 업로드</h3>
	<form action="uploadcsvrecord" method="post" enctype="multipart/form-data">
		DB에 업로드 할 파일 : <input type="file" name="csv" /> <br /> 
		<input type="submit" value="업로드"/>
	</form>
</body>
</html>