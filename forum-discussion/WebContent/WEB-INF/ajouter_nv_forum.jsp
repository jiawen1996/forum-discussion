<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Ajouter un nouveau forum</title>
</head>
<body>
	<form action="Validation" method="post">
           <label> First name </label>
           <input type="text" id="frname" name="User first name"/>
           <br>
           <label> Familly name </label>
           <input type="text" id="faname" name="User familly name"/>
           <br>
           <label> Email </label>
           <input type="email" id="email" name="User email"/>
           <br>
           
           <label> Password </label>
           <input type="password" id="psw" name="User password"/>
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
</body>
</html>