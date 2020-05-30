<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true"%>
	<%@ page import="com.sr03.forumdiscussion.model.User" %>
<jsp:useBean id="user" class="com.sr03.forumdiscussion.model.User" scope="session" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Administrator menu</title>
</head>
<body>
	<% 
		session.setAttribute("login", user.getLogin());
		String role = user.getRole();
		session.setAttribute("role", role);
		if ("admin".equalsIgnoreCase(role)) {
	%>
	<h1>Administrator menu</h1>
	<nav>
		<dl>
  			<dt>User</dt>
  				<dd><a href='ajouter_nv_util.jsp'> - Create a new user</a></dd>
  				<dd><a href='UserManager'>- Display user list</a></dd>
  			<dt>Forum</dt>
  				<dd><a href='ajouter_nv_forum.jsp'> - Create a new forum</a></dd>
  				<dd><a href='ForumManager?allForum'>- Display list of forums</a></dd>
		</dl>
		
		<a href='home.jsp'>Home</a>
	</nav>

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