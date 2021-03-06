<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Modify a forum</title>
</head>
<body>
	<% 
		String role = session.getAttribute("role").toString();
		if ("admin".equalsIgnoreCase(role)) {
	%>
	<h1>Modify a forum</h1>
	<form action="ForumManager" method="POST">
		<table>
			<tr>
				<td>ID</td>
				<td><c:out value="${editForum.id}" /></td>
			</tr>
			<tr>
				<td>Title : </td> 
				<td><input type="text" name="Forum title" placeholder="${editForum.title}"></td>
			</tr>
			<tr>
				<td>Description : </td>
				<td><input type="text" name="Forum description" placeholder="${editForum.description}"></td>
			</tr>
			<tr>
				<td>Owner (first name & last name) : </td>
				<td><input type="text" name="Forum owner first name" placeholder="${owner.firstName}"></td>
				<td><input type="text" name="Forum owner last name" placeholder="${owner.lastName}"></td>
			</tr>
		</table>
		<input type="hidden" name="idOwner" value="${owner.id}">
		<input type="submit" name="validModify" value="Modifier">
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