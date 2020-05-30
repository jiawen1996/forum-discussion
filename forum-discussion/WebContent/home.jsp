<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true"%>
<%@ page import="com.sr03.forumdiscussion.model.User"%>
<jsp:useBean id="user" class="com.sr03.forumdiscussion.model.User"
	scope="session" />
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
	<header>
		<h1>Home</h1>
	</header>
	<h3>
		Hello
		<%=session.getAttribute("login")%></h3>
	<nav>
		| <a href='ForumManager?showlistforums'>List of forums</a> |
		<!-- | <a href='ForumManager?publicForum'>Afficher la liste de forums publiés</a> |
		<a href='ForumManager?followedForum'>Afficher la liste de forums abonnés</a> | -->
		<%
		if ("admin".equalsIgnoreCase(role)) {
		%>
		<a href='menu.jsp'>Administrator menu</a> |

		<%
			}
		%>
		<a href='Deconnexion'>Sign out</a> |


	</nav>
	<footer>
		<p>Posted by: Jiawen Lyu - Linh Nguyen</p>
		<p>
			Contact information: <a href="mailto:nguyetra@etu.utc.fr">
				nguyetra@etu.utc.fr</a>.
		</p>
	</footer>
</body>
</html>