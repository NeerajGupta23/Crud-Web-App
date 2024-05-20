<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<style>
body {
	margin: 0%;
	background-color: rgb(252, 255, 96);
	color: rgb(88, 0, 0);
	text-align: center;
	padding-top: 5%;
	font-size: 35px;
	font-size: 35px;
	border: 5px solid rgb(255, 88, 116);
	border-radius: 10px;
	height: 443px;
}

#back {
	font-size: 20px;
	padding: 10px;
	padding-right: 12px;
	border-radius: 10px;
	background-color: #a7d4ff;
	text-decoration: none;
}

#back:hover {
	background-color: #67b6ff;
}

#back:focus {
	color: rgb(88, 0, 0);
	border: 3px solid #166cff;
	border-top: 4px solid #166cff;
	border-left: 4px solid #166cff;
}
</style>
<body>
	 <h3>No Data is Associated with id :: ${sessionScope.student.id}</h3>
	 <a id='back' href='${sessionScope.backURL}'>Back</a>	
</body>
</html>