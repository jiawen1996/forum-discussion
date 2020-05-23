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

	<table class = "table table-striped table-hover">
		<tr class="thead-dark">
			<th>User</th>
			<th>Title</th>
			<th>Description</th>
			
		</tr>
		<c:forEach items="${listForums}" var="forum">
			<tr>
				<td><c:out value="${forum.id}" /></td>
				<td><c:out value="${forum.title}" /></td>
				<td><c:out value="${forum.description}" /></td>
			</tr>
		</c:forEach>
	</table>





</body>
</html>