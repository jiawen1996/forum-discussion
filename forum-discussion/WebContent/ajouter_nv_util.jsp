<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%-- <jsp:useBean id="info" class="Controller.Connexion" scope="request" /> --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Ajouter un nouveau utilisateur</title>
</head>
<body>
	<% 
		String role = session.getAttribute("role").toString();
		if ("admin".equalsIgnoreCase(role)) {
	%>
	<h1>Ajouter un nouveau utilisateur</h1>
	<form action="Validation" method="post">
           <label> First name </label>
           <input type="text" id="frname" name="User first name" required/>
           <br>
           <label> Familly name </label>
           <input type="text" id="faname" name="User familly name"required/>
           <br>
           <label> Login </label>
           <input type="text" id="login" name="User login"required/>
           <br>
           
           <label> Password </label>
           <input type="password" id="psw" name="User password"required/>
           <br>
           <label> male </label>
           <input type="radio" id="male" name="gender" value="male" checked/>
           <br>
           <label> female </label>
           <input type="radio" id="female" name="gender" value="female"/>
           <br>
            
           <label> Admin </label>
           <input type="checkbox" name="admin" value="admin" />
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