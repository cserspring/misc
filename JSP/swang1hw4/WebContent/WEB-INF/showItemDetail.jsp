<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Show Item Detail</title>
</head>
<body>
<center>
<table width="40%">
	<tr>
		<td align="left"><a href="displayItems.do">Home</a></td>
			<c:if test="${empty user}">
				<td><a href="gotoLogin.do">Login</a>&nbsp;<a href="gotoRegister.do">Register</a></td>
			</c:if>
			<c:if test="${not empty user}">
			
				<a href="logout.do">Logout</a>&nbsp;
				<a href="manageItems.do">Manage My Items</a>&nbsp;
				<a href="gotoChangePassword.do">Change Password</a>
			
			</c:if>
		
	</tr>
	<tr><td align="left">
	<b>${item.title}</b> 
	</td></tr>
	<tr><td align="right">
	&nbsp;&nbsp;&nbsp;&nbsp;Poster: ${seller.username} &nbsp; Email: <a href="mailto:${seller.email}">${seller.email}</a> &nbsp; Date: ${item.listingDate}
	</td></tr>
	<tr><td align="left">
	&nbsp;&nbsp;&nbsp;&nbsp;Description: ${item.description}
	</td></tr>
	<tr><td align="left">
	&nbsp;&nbsp;&nbsp;&nbsp;Price: $${item.price}
	</td></tr>
	<tr><td>
		<img src="image.do?id=${item.id }" width="70%" height="60%"/>
	</td></tr>
</table>
</center>
</body>
</html>