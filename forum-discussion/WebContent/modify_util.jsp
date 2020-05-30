<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edit user information</title>
</head>
<body>
	<% 
		String role = session.getAttribute("role").toString();
		if ("admin".equalsIgnoreCase(role)) {
	%>
	<h1>Edit user information</h1>
	<form action="UserManager" method="POST">
		<table>
			<tr>
				<td>ID</td>
				<td><c:out value="${modifUser.id}" /></td>
			</tr>
			<tr>
				<td>Last name : </td> 
				<td><input type="text" name="lastNameModif" placeholder="${modifUser.lastName}"></td>
			</tr>
			<tr>
				<td>First name : </td>
				<td><input type="text" name="firstNameModif" placeholder="${modifUser.firstName}"></td>
			</tr>
			<tr>
				<td>Gender : </td>
				<td><input type="text" name="genderModif" placeholder="${modifUser.gender}"></td>
			</tr>
			<tr>
				<td>Login : </td>
				<td><input type="text" name="loginModif" placeholder="${modifUser.login}"></td>
			</tr>
			<tr>
				<td>Password : </td>
				<td><input type="text" name="pwdModif" placeholder="${modifUser.pwd}"></td>
			</tr>
			<tr>
				<td>Admin : </td>
				<td><input type="number" name="isAdminModif" placeholder="${modifUser.isAdmin}"></td>
			</tr>
		</table>
		<input type="submit" name="validModify" value="Modify">
	</form>
	<br/>
	<a href="menu.jsp">Administrator menu</a>
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