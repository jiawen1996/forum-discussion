<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Liste message</title>
</head>

<body>
    <table class="table table-striped table-hover">
        <tr class="thead-dark">
            <th>User</th>
            <th>Title</th>
            <th>Description</th>

        </tr>
        <c:forEach items="${listMessages}" var="message">
            <tr>
                <td>
                    <c:out value="${message.editor}" />
                </td>
                <td>
                    <c:out value="${message.content}" />
                </td>
            </tr>

        </c:forEach>
    </table>

</body>

</html>