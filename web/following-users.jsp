<%--
  Created by IntelliJ IDEA.
  User: nika
  Date: 7/3/2016
  Time: 1:57 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.lang.String" %>
<%@ page import="main.java.db.managers.AccountManager" %>
<%@ page import="java.util.List" %>
<%@ page import="main.java.models.Account" %>
<jsp:include page="header.jsp"/>
<html>
<head>
    <title>users</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <link rel="stylesheet" type="text/css" href="css/following-users.css">
</head>
<body>
    <div class="single-user-info">
        <div class="single-user-picture">

        </div>
        <div class="single-user-name-email">
            <div class="single-user-name">
                user display name
            </div>
            <div class="single-user-email">
                user email
            </div>
        </div>
        <div class="single-user-buttons">
            <div class="btn btn-tag post-tag ff-superSquare fs-13 fc-grey-dark follow-unfollow-button">
                follow
            </div>
        </div>
    </div>
</body>
</html>
