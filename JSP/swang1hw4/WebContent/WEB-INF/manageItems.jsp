<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
    <head>
        <title>Manage Items</title>
    </head>
    
    <body>
    <center>
		<c:if test="${not empty user }">
		<table width="30%">
			<tr>
			<td align="left"><a href="displayItems.do">Home</a></td>
			<td><a href="gotoChangePassword.do">Change Password</a>
			<td><a href="logout.do">Logout</a></td>
			</tr>
		</table>
		<c:forEach var="error" items="${errors}">
			<h3 style="color:red"> ${error} </h3>
		</c:forEach>

        <form action="add.do" method="POST" enctype="multipart/form-data">
            <table>
                <tr>
                	<td>Title</td>
                	<td><input type="text" name="title"/></td>
                </tr>
                <tr>
                	<td>Description</td>
                	<td><input type="text" name="description"/></td>
                </tr>
                <tr>
                	<td>Price($)</td>
                	<td><input type="text" name="price"/></td>
                </tr>
				<tr>
					<td>Image: </td>
					<td colspan="2"><input type="file" name="file" value="${filename}"/></td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<input type="submit" name="button" value="Add"/>
					</td>
				</tr>
            </table>
        </form>

		<p style="font-size: x-large">You have ${ fn:length(items) } items.</p>

		<table>
			<c:set var="count" value="0" />
			<c:forEach var="item" items="${items}">
				<c:set var="count" value="${ count+1 }" />
           		<tr>
       				<td>
			            <form action="delete.do" method="POST">
                			<input type="hidden" name="id" value="${ item.id }" />
                			<input type="submit" name="button" value="X" />
           				</form>
        			</td>
        			<td valign="baseline" style="font-size: x-large"> &nbsp; ${ count }. &nbsp; </td>
        			<td valign="baseline">
        				<a href="showItemDetail.do?id=${item.id}">${item.title}</a>
        			</td>
   				</tr>
   			</c:forEach>
		</table>
       	</c:if>
       	</center>
    </body>
</html>