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
	<% 
		String role = session.getAttribute("role").toString();
		if ("admin".equalsIgnoreCase(role)) {
	%>
	<h1>Liste des utilisateurs</h1>
	<table>
		<tr>
			<th>ID</th>
			<th>Nom & Prenom</th>
			<th>Genre</th>
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
	<a href="menu.jsp">Menu administrateur</a>
	<%
		} else {
	%>
	<h1>Erreur</h1>
	<p> Vous n'avez pas droit à accéder à cette page. Veullez revenir vers l'accueil !</p>
	<a href='home.jsp'>Accueil</a>
	<%
		}
	%>
</body>
</html>