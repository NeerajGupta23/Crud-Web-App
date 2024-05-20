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
	font-size: 34.5px;
	margin: 0%;
	height: -1%;
}

th, td {
	text-align: center;
	padding: 10px;
	font-size: 30px;
}

#update {
	font-size: 20px;
	padding: 10px;
	margin-top: 40px;
	padding-right: 12px;
	text-decoration: none;
	border-radius: 10px;
	background-color: #a7d4ff;
	position: relative;
}

#update:hover {
	background-color: #67b6ff;
}

#update:focus {
	color: rgb(88, 0, 0);
	border: 3px solid #166cff;
	border-top: 4px solid #166cff;
	border-left: 4px solid #166cff;
}
</style>
<body>
	<h3>Data Associated with id :: ${sessionScope.student.id}</h3>
	<form action="html/updateIt.perform" method="post">
		<table align="center">
			<tr>
				<th>Name</th>
				<td><input type='text' id='sname' name='sname'
					value='${sessionScope.student.name}'></td>
			</tr>
			<tr>
				<th>Age</th>
				<td><input type='number' id='sage' name='sage'
					value='${sessionScope.student.age}'></td>
			</tr>
			<tr>
				<th>Address</th>
				<td><input type='text' id='saddress' name='saddress'
					value='${sessionScope.student.address}'></td>
			</tr>
			<tr>
				<input type='hidden' id='sid' name='sid'
					value='${sessionScope.student.id}'>
			</tr>
			<tr>
				<input type='hidden' id='urlLoc' name='urlLoc'
					value='${sessionScope.backURL}'>
			</tr>
			<tr>
				<th></th>
				<td><input type='submit' id='update' value='Update'></td>
			</tr>
		</table>
	</form>
	<br>
</body>
</html>