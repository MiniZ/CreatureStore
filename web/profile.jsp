<%--
  Created by IntelliJ IDEA.
  User: nika
  Date: 6/29/2016
  Time: 1:56 AM
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
    <title>user profile</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <link rel="stylesheet" type="text/css" href="css/profile.css">
</head>
<body style="background-image: url(images/logo/rsz_only_logo.png);">

<%
    ServletContext sc = getServletConfig().getServletContext();
    AccountManager manager = (AccountManager) sc.getAttribute(AccountManager.ATTRIBUTE_NAME);

    String displayName = null;
    if (request.getParameter("username") != null) {
        displayName = request.getParameter("username");
        Account profileAcc = manager.getAccount(displayName);
        long id = profileAcc.getId();
        String firstName = profileAcc.getFirstName();
        String lastName = profileAcc.getLastName();
        String email = profileAcc.getMail();
        String password = profileAcc.getHashedPassword();
        String facebook = profileAcc.getFbLink();
        String twitter = profileAcc.getTwitterLink();
        String googlePlus = profileAcc.getGoogleLink();
        String city = profileAcc.getCity();
        String country = profileAcc.getCountry();
        String imgSource = "test";
        String aboutMe = profileAcc.getAboutMe();
        String profileImg = profileAcc.getImgSrc();
%>


<div class="profile">
    <div class="pic_with_menu">
    <div class="profile-picture">
        <img src="/images/logo/only_logo_transparent.png" alt="no pic">
    </div>
    <div class="followers_menu">
        <ul>
            <li class="followers"><a href="#">followers</a></li>
            <li class="following"><a href="#">following</a></li>

        </ul>
    </div>
    </div>
    <div class="profile-info">
        <div class="display-name">
            <label><%=displayName%>
            </label>
        </div>
        <div class="first-name">
            <label><%=firstName%>
            </label>
        </div>
        <div class="last-name">
            <label><%=lastName%>
            </label>
        </div>
        <div class="email">
            <label><%=email%>
            </label>
        </div>
        <div class="city">
            <label><%=city%>
            </label>
        </div>
        <div class="country">
            <label><%=country%>
            </label>
        </div>
        <div class="facebook-link">
            <label><%=facebook%>
            </label>
        </div>
        <div class="twitter-link">
            <label><%=twitter%>
            </label>
        </div>
        <div class="google-link">
            <label><%=googlePlus%>
            </label>
        </div>
    </div>
    <div class="profile-about">
        <label><%=aboutMe%>
        </label>
    </div>
</div>
<%
    }
%>
</body>
</html>
