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
	color: rgb(88, 0, 0);
	background-color: rgb(252, 255, 96);
	border: 5px solid rgb(255, 88, 116);
	border-radius: 10px;
	text-align: center;
	padding-top: 5%;
	font-size: 35px;
	margin: 0%;
}

th, td {
	text-align: center;
	padding: 10px;
	font-size: 30px;
}

#back {
	text-decoration: none;
	border-radius: 10px;
	background-color: #a7d4ff;
	font-size: 25px;
	padding: 10px;
	position: relative;
	top: -60px;
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
	<h3>Data Associated with id :: ${sessionScope.student.id}</h3>
	<table align='center'>
		<tr>
			<th>Name</th>
			<th>Age</th>
			<th>Address</th>
		</tr>
		<tr>
			<td>${sessionScope.student.name}</td>
			<td>${sessionScope.student.age}</td>
			<td>${sessionScope.student.address}</td>
		</tr>
	</table>
	<br>
	<br>
	<br>
	<br>
	<a id='back' href='${sessionScope.backURL}'>Back</a>
</body>
</html>