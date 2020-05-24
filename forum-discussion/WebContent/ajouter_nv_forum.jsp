<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Créer un nouveau forum</title>
</head>

<body>
	<% 
		String role = session.getAttribute("role").toString();
		if ("admin".equalsIgnoreCase(role)) {
	%>
    <form action="ForumManager" method="post">
        <label> Title </label>
        <input type="text" id="title" name="Forum title" required/>
        <br>
        <label> Description </label>
        <input type="text" id="description" name="Forum description" />
        <br>
        <!-- <input type="hidden" name="idDelete" value="${user.id}"> -->
        <input type="submit" value="Submit">
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