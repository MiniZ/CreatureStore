<%--
  Created by IntelliJ IDEA.
  User: xatia
  Date: 5/30/2016
  Time: 5:38 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.lang.String"%>
<%@ page import="main.java.db.managers.AccountManager" %>
<%@ page import="java.util.List" %>
<jsp:include page="header.jsp" />

<html>
<head>
    <!-- <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no"> -->
    <title>
        About
    </title>
    <link type="text/css" rel="stylesheet" href="css/about.css"/>
    <!-- <link type="text/css" rel="stylesheet" href="./css/reset.css"/>
    <link type="text/css" rel="stylesheet" href="./css/global.css" />
    <script src="./js/jquery-1.12.1.min.js"></script>
    <script src="./js/main.js"></script> -->
</head>
<body style = "background-image: url(images/logo/rsz_only_logo.png);">

<div class = "about-logo-text">
    <div class="about-logo m-auto rel">
        <img src="images/logo/logo_transparent.png">
    </div>

    <div class="about-text f-28 m-auto">
        <p>A website giving you opportunity to share your creative art</p>
        <p>Dribbble is the single most important social network for anyone that cares about design. It's done more to help us build our team and brand than Facebook, Twitter and Instagram combined.</p>
    </div>
    <div class="about-team">
        <div class="about-team-member">
            <div class="about-member rel floatybox">
                <img src="images/images/rsz_anano.jpg">
                <div class="about-icons">
                    <div class = "about-fb-icon inline-hor">
                        <img src="images/images/fb.png">
                    </div>
                    <div class = "about-git-icon inline-hor">
                        <img src="images/images/git.png">
                    </div>
                    <div class = "about-google-icon inline-hor">
                        <img src="images/images/google.png">
                    </div>
                </div>
            </div>
            <div class="about-member rel floatybox">
                <img src="images/images/rsz_nika.jpg">
                <div class="about-icons ">
                    <div class = "about-fb-icon inline-hor">
                        <img src="images/images/fb.png">
                    </div>
                    <div class = "about-git-icon inline-hor">
                        <img src="images/images/git.png">
                    </div>
                    <div class = "about-google-icon inline-hor">
                        <img src="images/images/google.png">
                    </div>
                </div>
            </div>
            <div class="about-member rel floatybox">
                <img src="images/images/rsz_xatia.jpg">
                <div class="about-icons">
                    <div class = "about-fb-icon inline-hor">
                        <img src="images/images/fb.png">
                    </div>
                    <div class = "about-git-icon inline-hor">
                        <img src="images/images/git.png">
                    </div>
                    <div class = "about-google-icon inline-hor">
                        <img src="images/images/google.png">
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>
<div class="footer">
    <div class="ltd">
        Creature Store © 2016
    </div>
</div>
</body>