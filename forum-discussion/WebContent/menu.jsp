<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true"%>
	<%@ page import="com.sr03.forumdiscussion.model.User" %>
<jsp:useBean id="user" class="com.sr03.forumdiscussion.model.User" scope="session" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Menu administrateur</title>
</head>
<body>
	<% 
		session.setAttribute("login", user.getLogin());
		String role = user.getRole();
		session.setAttribute("role", role);
		if ("admin".equalsIgnoreCase(role)) {
	%>
	<h1>Menu administrateur</h1>
	<h3> Hello <%=session.getAttribute("login") %></h3>
	<nav>
		<ul>
			<li><a href='ajouter_nv_util.jsp'>Créer un nouveau
				utilisateur</a></li>
			<li><a href='UserManager'>Afficher la liste des utilisateurs</a></li>
			<li><a href='ajouter_nv_forum.jsp'>Créer un nouveau
				forum</a></li>
			<li><a href='ForumManager'>Afficher la liste des forum</a></li>
			<li><a href='home.jsp'>Accueil</a></li>
		</ul>
	</nav>

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