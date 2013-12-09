<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Register</title>
</head>
<body>
<center>
	<c:if test="${empty user }">
	<table width="30%">
			<tr>
			<td align="left"><a href="displayItems.do">Home</a></td>
			<td align="right"><a href="gotoLogin.do">Login</a></td>
			</tr>
		</table>
	<c:forEach var="error" items="${errors}">
			<h3 style="color:red"> ${error} </h3>
	</c:forEach>
	
	<form action="register.do" method="post">
		<input type="hidden" name="redirect" value="${redirect}"/>
		<table>
			<tr>
				<td>Username: </td>
				<td><input type="text" name="username" value="${form.username}"/></td>
			</tr>
			<tr>
				<td>Email: </td>
				<td><input type="text" name="email" value="${form.email}"/></td>
			</tr>
			<tr>
				<td>First Name: </td>
				<td><input type="text" name="firstname" value="${form.firstname}"/></td>
			</tr>
			<tr>
				<td>Last Name: </td>
				<td><input type="text" name="lastname" value="${form.lastname}"/></td>
			</tr>
			<tr>
				<td>Password: </td>
				<td><input type="password" name="password" value=""/></td>
			</tr>
			<tr>
				<td>Confirm Password: </td>
				<td><input type="password" name="confirm" value=""/></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="submit" name="button" value="Register"/>
				</td>
			</tr>
		</table>
	</form>
	</c:if>
	</center>
</body>
</html>