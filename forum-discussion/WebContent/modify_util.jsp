<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Modifier l'information d'un utilisateur</title>
</head>
<body>
	<% 
		String role = session.getAttribute("role").toString();
		if ("admin".equalsIgnoreCase(role)) {
	%>
	<h1>Modifier l'information d'un utilisateur</h1>
	<form action="UserManager" method="POST">
		<table>
			<tr>
				<td>ID</td>
				<td><c:out value="${modifUser.id}" /></td>
			</tr>
			<tr>
				<td>Nom : </td> 
				<td><input type="text" name="lastNameModif" placeholder="${modifUser.lastName}"></td>
			</tr>
			<tr>
				<td>Prenom : </td>
				<td><input type="text" name="firstNameModif" placeholder="${modifUser.firstName}"></td>
			</tr>
			<tr>
				<td>Genre : </td>
				<td><input type="text" name="genderModif" placeholder="${modifUser.gender}"></td>
			</tr>
			<tr>
				<td>Login : </td>
				<td><input type="text" name="loginModif" placeholder="${modifUser.login}"></td>
			</tr>
			<tr>
				<td>Mot de passe : </td>
				<td><input type="text" name="pwdModif" placeholder="${modifUser.pwd}"></td>
			</tr>
			<tr>
				<td>Admin : </td>
				<td><input type="number" name="isAdminModif" placeholder="${modifUser.isAdmin}"></td>
			</tr>
		</table>
		<input type="submit" name="validModify" value="Modifier">
	</form>
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