<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>List users</title>
</head>
<body>
	<h1>Liste des utilisateurs</h1>
	<table>
		<tr>
			<th>ID</th>
			<th>Nom & Prénom</th>
			<th>Genre</th>
			<th>Login</th>
			<th>Admin</th>
		</tr>
		<c:forEach items="${listUser}" var="user">
			<tr>
				<td><c:out value="${user.id}" /></td>
				<td><c:out value="${user.lastName}"/><c:out value="${user.firstName}"/></td>
				<td><c:out value="${user.gender}" /></td>
				<td><c:out value="${user.login}" /></td>
				<td><c:out value="${user.isAdmin}" /></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>