<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<!-- <link rel="stylesheet" type="text/css" href="WEB-INF/lib/bootstrap/css/bootstrap.min.css"> -->

	<title>List of forums</title>
</head>

<body>
	<h1>List of forums</h1>
	<div>
	<h3>Subscribed Forums</h3>
	<table class="table table-striped table-hover">
		<tr class="thead-dark">
			<th>ID</th>
			<th>Title</th>
			<th>Description</th>

		</tr>
		<c:forEach items="${forumSubs}" var="forum">
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
				<% 
					String role = session.getAttribute("role").toString();
					if ("admin".equalsIgnoreCase(role)) {
				%>
				<td>
					<form action="ForumManager" method="POST">
						<input type="hidden" name="idModify" value="${forum.id}">
						<input type="submit" value="Modify">
					</form>
				</td>
				<td>
					<form action="ForumManager" method="POST">
						<input type="hidden" name="idDelete" value="${forum.id}">
						<input type="submit" value="Delete">
					</form>
				</td>
				<% } %>
				
				<td>
				<form action="ForumManager" method="GET">
					<input type="hidden" name="idEnterForum" value="${forum.id}">
					<input type="submit" value="Enter">
				</form>
				</td>
				<td>
				<form action="ForumManager" method="POST">
					<input type="hidden" name="idQuitForum" value="${forum.id}">
					<input type="submit" value="Unsubscribe">
				</form>
				</td>
			</tr>
		</c:forEach>
	</table>
	</div>
	<div>
		<h3>Others Forums</h3>
		<table class="table table-striped table-hover">
			<tr class="thead-dark">
				<th>ID</th>
				<th>Title</th>
				<th>Description</th>
	
			</tr>
			<c:forEach items="${forumNoSubs}" var="forum">
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
					<% 
						String role = session.getAttribute("role").toString();
						if ("admin".equalsIgnoreCase(role)) {
					%>
					<td>
						<form action="ForumManager" method="POST">
							<input type="hidden" name="idModify" value="${forum.id}">
							<input type="submit" value="Modify">
						</form>
					</td>
					<td>
						<form action="ForumManager" method="POST">
							<input type="hidden" name="idDelete" value="${forum.id}">
							<input type="submit" value="Delete">
						</form>
					</td>
					<% } %>
					
					<td>
					<form action="ForumManager" method="GET">
						<input type="hidden" name="idEnterForum" value="${forum.id}">
						<input type="submit" value="Subscribe">
					</form>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<br />
	<a href="home.jsp">Home</a>

</body>

</html>