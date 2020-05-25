<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Forum ${forum.title}</title>
</head>
<body>
	<h1>Forum ${forum.title}</h1>
	<h3><b>Liste des utilisateurs</b></h3>
	<table>
		<tr>
			<th>ID</th>
			<th>Nom & Prenom</th>
			<th>Genre</th>
			<th>Login</th>
			<th>Admin</th>
		</tr>
		<c:forEach items="${usersForum}" var="user">
			<tr>
				<td><c:out value="${user.id}" /></td>
				<td><c:out value="${user.lastName}"/> <c:out value="${user.firstName}"/></td>
				<td><c:out value="${user.gender}" /></td>
				<td><c:out value="${user.login}" /></td>
				<td><c:out value="${user.isAdmin}" /></td>
				<td>
					<form action="UserManager" method="POST">
						<input type="hidden" name="idModify" value="${user.id}">
						<input type="submit" value="Modifier">
					</form>
				</td>
				<td>
					<form action="UserManager" method="POST">
						<input type="hidden" name="idDelete" value="${user.id}">
						<input type="submit" value="Supprimer">
					</form>
				</td>
			</tr>
		</c:forEach>
	</table>
	<br/>
	<a href="home.jsp">Accueil</a>
</body>
</html>