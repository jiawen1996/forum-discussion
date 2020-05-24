<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true"%>
	<%@ page import="com.sr03.forumdiscussion.model.User" %>
<jsp:useBean id="user" class="com.sr03.forumdiscussion.model.User" scope="session" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Home</title>
</head>
<body>
	<% 
		session.setAttribute("login", user.getLogin());
		String role = user.getRole();
		session.setAttribute("role", role);
	%>
	<h1>Home</h1>
	<h3> Hello <%=session.getAttribute("login") %></h3>
	<nav>
		<ul>
			<li>Connected</li>
			<li><a href='ForumManager'>Afficher la liste des forums publiés</a></li>
			<% if ("admin".equalsIgnoreCase(role)) { %>
				<li><a href='menu.jsp'>Menu administrateur</a></li>
			<%
				}
			%>
			<li><a href='Deconnexion'>Déconnecter</a></li>
		</ul>
	</nav>
	
</body>
</html>