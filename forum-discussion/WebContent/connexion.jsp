<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<jsp:useBean id="info" class="Controller.Connexion" scope="request" />
<html>
<head>
<meta charset="UTF-8">
<title>Welcome</title>
</head>
<body>
	<form action="Connexion" method="POST">
           <div>
               <label for="username">Username:</label>
               <input type="text" id="username" name="username">
           </div>

           <div>
               <label for="pass">Password (8 characters minimum):</label>
               <input type="password" id="pass" name="password"
                      minlength="8" required>
           </div>

           <input type="submit" value="Sign in">

       </form>

</body>
</html>