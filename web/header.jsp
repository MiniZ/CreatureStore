<!DOCTYPE html>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>title</title>
    <link rel="stylesheet" type="text/css" href="css/header.css">
    <%
        String loggedInUser = (String) session.getAttribute("display_name");
    %>
</head>
<body>
<script src="/javascript/app.js"></script>
<script src="/javascript/jquery-2.1.1.min.js"></script>
<header id="header">
    <div id="header-inner">
        <div id="logo">
            <a href="index.jsp"><img src="/images/logo/only_logo_transparent.png"; ></a>
        </div>
        <div id="top-nav">
            <ul id="top-left-menu">
                <li><a href="#">Newest</a></li>
                <%
                    if (loggedInUser != null) {
                        out.print("<li class=\"ff-superSquareCap fc-grey fs-15\"><a href=\"#\">Following</a></li>");
                        out.print("<li class=\"ff-superSquareCap fc-grey fs-15\"><a href=\"#\">+ Plus</a></li>");
                        out.print("<li class=\"ff-superSquareCap fc-grey fs-15\"><a href=\"#\">- Minus</a></li>");
                    }
                %>
                <li><a href="about.jsp">About</a></li>
            </ul>
            <ul id="top-right-menu">
                <%
                    if (loggedInUser != null) {
                        out.print("<li class=\"ff-superSquareCap fc-grey fs-15\"><a href=\"#\">" + loggedInUser + "</a></li>");
                        out.print("<li>");
                        out.print("<form action=\"Logout\" role=\"form\" id=\"signout\">");
                        out.print("<a href=\"#\" onclick=\'gotosignout()\'>Sign out</a>");
                        out.print("</li>");
                        out.print("</form>");
                    } else {
                        out.print("<li class=\"ff-superSquareCap fc-grey fs-15\"><a href=\"/signin.jsp\">Sign in</a></li>");
                        out.print("<li class=\"ff-superSquareCap fc-grey fs-15\"><a href=\"/signup.jsp\">Sign up</a></li>");
                    }
                %>
            </ul>
        </div>
    </div>
</header>

</body>
</html>