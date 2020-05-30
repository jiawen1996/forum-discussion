<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>User list</title>
</head>
<body>
	<% 
		String role = session.getAttribute("role").toString();
		if ("admin".equalsIgnoreCase(role)) {
	%>
	<h1>User list</h1>
	<table>
		<tr>
			<th>ID</th>
			<th>Last name & first name</th>
			<th>Gender</th>
			<th>Login</th>
			<th>Admin</th>
		</tr>
		<c:forEach items="${listUser}" var="user">
			<tr>
				<td><c:out value="${user.id}" /></td>
				<td><c:out value="${user.lastName}"/> <c:out value="${user.firstName}"/></td>
				<td><c:out value="${user.gender}" /></td>
				<td><c:out value="${user.login}" /></td>
				<td><c:out value="${user.isAdmin}" /></td>
				<td>
					<form action="UserManager" method="POST">
						<input type="hidden" name="idModify" value="${user.id}">
						<input type="submit" value="Modify">
					</form>
				</td>
				<td>
					<form action="UserManager" method="POST">
						<input type="hidden" name="idDelete" value="${user.id}">
						<input type="submit" value="Delete">
					</form>
				</td>
			</tr>
		</c:forEach>
	</table>
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