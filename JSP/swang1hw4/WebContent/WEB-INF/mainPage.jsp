<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Main Page</title>
</head>
<body>
<center>
	<table width="40%">
	<tr>
		<td align="left"><a href="displayItems.do">Home</a></td>
		<c:if test="${empty user}">
			<td align="right"><a href="gotoLogin.do">Login</a>&nbsp;<a href="gotoRegister.do">Register</a></td>
		</c:if>
		<c:if test="${not empty user}">
			<td align="right"><a href="logout.do">Logout</a>&nbsp;
			<a href="manageItems.do">Manage My Items</a>&nbsp;
			<a href="gotoChangePassword.do">Change Password</a>
			</td>
		</c:if>
	</tr>
	<tr><td colspan="2" align="center">
	<form action="search.do" method="POST">
		<input type="text" name="keywords"/><input type="submit" value="Search"/>
	</form>
	</td></tr>
	<c:forEach var="item" items="${items}">
		<tr><td>
		<a href="showItemDetail.do?id=${item.id}">${item.title}</a><br/>
		&nbsp;&nbsp;&nbsp;&nbsp;${item.description}<br/><br/>
		</td></tr>
	</c:forEach>
	</table>
</center>
</body>
</html>