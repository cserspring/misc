<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>

<html>
    <head>
        <title>ToDoList9 -- Login Page</title>
        <link rel="stylesheet" type="text/css" href="todolist.css" />
   </head>
    
    <body onLoad="document.getElementById('userName').focus()">
		<h2>ToDoList9 Login</h2>
		
		<c:forEach var="error" items="${errors}">
			<h3 style="color:red"> ${error} </h3>
		</c:forEach>
	
		<form action="login.do" method="POST">
		    <table class="center">
		        <tr>
		            <td> <span class="form-label"> User Name: </span> </td>
		            <td>
		                <input id="userName" type="text" name="userName" value="${form.userName}" />
		            </td>
		        </tr>
		        <tr>
                    <td> <span class="form-label"> Password: </span> </td>
		            <td> <input type="password" name="password" /> </td>
		        </tr>
		        <tr>
		            <td colspan="2" align="center">
		                <input type="submit" name="action" value="Login" />
		                <input type="submit" name="action" value="Register" />
		            </td>
		        </tr>
			</table>
		</form>
	</body>
</html>