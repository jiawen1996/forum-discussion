<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Create a new forum</title>
</head>

<body>
	<% 
		String role = session.getAttribute("role").toString();
		if ("admin".equalsIgnoreCase(role)) {
	%>
	<h1>Create a new forum</h1>
    <form action="ForumManager" method="post">
        <label> Title </label>
        <input type="text" id="title" name="Forum title" required/>
        <br>
        <label> Description </label>
        <input type="text" id="description" name="Forum description" />
        <br>
        <input type="hidden" name="idCreate" value="${user.id}">
        <input type="submit" value="Submit">
    </form>
    <br/>
	<a href="menu.jsp">Administrator menu</a>
	<%
		} else {
	%>
	<h1>Error</h1>
	<p>You do not have the right to access this page. Please come back to the reception!</p>
	<a href='home.jsp'>Home</a>
	<%
		}
	%>
</body>

</html>