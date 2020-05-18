<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>List forums</title>
</head>
<body>

	<table>
		<c:forEach items="${listForums}" var="forum">
			<tr>
				<td><c:out value="${forum.id}" /></td>
				<td><c:out value="${forum.title}" /></td>
			</tr>
		</c:forEach>
	</table>





</body>
</html>