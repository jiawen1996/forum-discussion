<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true"%>
	<%@ page import="com.sr03.forumdiscussion.model.User" %>
<jsp:useBean id="user" class="com.sr03.forumdiscussion.model.User" scope="session" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Navigation Administrateur</title>
</head>
<body>
	<% 
		session.setAttribute("login", user.getLogin());
		String role = user.getRole();
		session.setAttribute("role", role);
		if ("admin".equalsIgnoreCase(role)) {
	%>
	<h1>Hello <%=session.getAttribute("login") %></h1>
	<nav>
		<ul>
			<li>Connected</li>
			<li><a href='ajouter_nv_util.jsp'>Créer un nouveau
				utilisateur</a></li>
			<li><a href='UserManager'>Afficher la liste des utilisateurs</a></li>
			<li><a href='Deconnexion'>Déconnecter</a></li>
		</ul>
	</nav>

	<%
		} else {
	%>
	<h1>Succes : utilisateur non admin.</h1>
	<%
		}
	%>
</body>
</html>