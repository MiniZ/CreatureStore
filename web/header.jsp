<!DOCTYPE html>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel="stylesheet" type="text/css" href="css/header.css">
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <%
        String loggedInUser = (String) session.getAttribute("display_name");
        Boolean is_admin = (Boolean) session.getAttribute("is_admin");
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
                <li class="ff-superSquareCap fc-grey fs-13"><a href="/index.jsp">Newest</a></li>
                <%
                    if (loggedInUser != null) {
                        out.print("<li class=\"ff-superSquareCap fc-grey fs-13\"><a href=\"/following-posts.jsp\">Following</a></li>");
                        out.print("<li class=\"ff-superSquareCap fc-grey fs-13\"><a href=\"#\">+ Plus</a></li>");
                        out.print("<li class=\"ff-superSquareCap fc-grey fs-13\"><a href=\"/users.jsp?search=\">Users</a></li>");
                    }
                %>

                <li class="ff-superSquareCap fc-grey fs-13"><a href="About.jsp">About</a></li>

            </ul>
            <ul id="top-right-menu">
                <%
                    if (loggedInUser != null) {
                        if (is_admin != null && is_admin) {
                            out.print("<li class=\"ff-superSquareCap fc-grey fs-13\"><a href=\"/admin.jsp?user=&post=\" class=\"admin-li\">" + "Admin Panel" + "</a></li>");
                            out.print("<li>");
                        }
                        out.print("<li class=\"ff-superSquareCap fc-grey fs-13\"><a href=\"/postupload.jsp\" class=\"post-upload-li\">" + "Upload Post"+ "</a></li>");
                        out.print("<li>");
                        out.print("<li class=\"ff-superSquareCap fc-grey fs-13\"><a href=\"/profile.jsp?username=" + loggedInUser + "\">" + loggedInUser + "</a></li>");
                        out.print("<li>");
                        out.print("<form action=\"Logout\" role=\"form\" id=\"signout\">");
                        out.print("<a href=\"#\" onclick=\'gotosignout()\'>Sign out</a>");
                        out.print("</li>");
                        out.print("</form>");
                    } else {
                        out.print("<li class=\"ff-superSquareCap fc-grey fs-13\"><a href=\"/signin.jsp\">Sign in</a></li>");
                        out.print("<li class=\"ff-superSquareCap fc-grey fs-13\"><a href=\"/signup.jsp\">Sign up</a></li>");
                    }
                %>
            </ul>
        </div>
    </div>
</header>

</body>
</html>