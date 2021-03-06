<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<meta http-equiv="refresh" content="60">
<title><c:out value="${forum.title}" /></title>
</head>

<body>
	<header>
		<h1>Forum : <c:out value="${forum.title}" /></h1>
		Description : <c:out value="${forum.description}" /> <br />
		Owner : <c:out value="${owner.firstName}" /> <c:out value="${owner.lastName}" /><br />
	</header>
	
	<div>
	<h3>List of messages</h3>
			<table class="table table-striped table-hover">
				<tr class="thead-dark">
					<th>Sender</th>
					<th>Message</th>
					<th>Date and time publication</th>
		
				</tr>
				<c:forEach items="${listMessages}" var="message">
					<tr>
						<c:if test="${empty message.editor}">
							<td>anonyme</td>
						</c:if>
						<c:if test="${not empty message.editor}">
							<td><c:out value="${message.editor}" /></td>
						</c:if>
						<td><c:out value="${message.content}" /></td>
						<td><c:out value="${message.datePublication}" /></td>
					</tr>
				</c:forEach>
			</table>
	<br />
	<form action="MessageManager" method="post">
		<label> Message </label> 
		<input type="text" id="content" name="Message content" style="width:200px; height:20px;" /> <br> 
		<input type="hidden" name="envoyerMessage" value="message"> 
		<input type="submit" value="Send">
	</form>
	
	</div>
		
	<br />
	<a href="affi_list_forum.jsp">List forums</a><br />
	<a href="home.jsp">Home</a>
</body>

</html>