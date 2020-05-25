<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<!-- <link rel="stylesheet" type="text/css" href="WEB-INF/lib/bootstrap/css/bootstrap.min.css"> -->

	<title>List forums</title>
</head>

<body>
	<h1>List forums</h1>
	<table class="table table-striped table-hover">
		<tr class="thead-dark">
			<th>User</th>
			<th>Title</th>
			<th>Description</th>

		</tr>
		<c:forEach items="${listForums}" var="forum">
			<tr>
				<td>
					<c:out value="${forum.id}" />
				</td>
				<td>
					<c:out value="${forum.title}" />
				</td>
				<td>
					<c:out value="${forum.description}" />
				</td>
				<td>
					<form action="ForumManager" method="POST">
						<input type="hidden" name="idModify" value="${forum.id}">
						<input type="submit" value="Modifier">
					</form>
				</td>
				<td>
					<form action="ForumManager" method="POST">
						<input type="hidden" name="idDelete" value="${forum.id}">
						<input type="submit" value="Supprimer">
					</form>
				</td>
				<td>
					<!-- <form action="MessageManager" method="GET">-->
					<form action="ForumManager" method="GET">
						<input type="hidden" name="idForum" value="${forum.id}">
						<input type="submit" value="Entrer">
					</form>
				</td>
			</tr>

		</c:forEach>
	</table>

	<br />
	<a href="home.jsp">Accueil</a>

</body>

</html>