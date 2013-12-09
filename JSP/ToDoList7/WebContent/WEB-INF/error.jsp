<%@page import="java.util.List"%>

<html>
    <head>
        <title>ToDoList7 -- Error Page</title>
    </head>
    
	<body>
	
		<h2>ToDoList7 Error</h2>
<%
		List<String> errors = (List<String>) request.getAttribute("errors");
		if (errors != null) {
			for (String error : errors) {
%>		
				<h3 style="color:red"> <%= error %> </h3>
<%
			}
		}
		
		if (session.getAttribute("user") == null) {
%>
			Click <a href="login.do">here</a> to login.
<%
		} else {
%>
			Click <a href="todolist.do">here</a> to return to the To Do List.
<%
		}
%>	
	
	</body>
</html>