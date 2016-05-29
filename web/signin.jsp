<%@ page import="java.net.URLEncoder" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="header.jsp" />
<html>
<head>
    <link href="styles/signin.css" rel="stylesheet">
    <title>Login</title>
    <%
        String loggedInUser = (String) session.getAttribute("display_name");
    %>
</head>
<body>
<%
    if (loggedInUser != null) {
        response.sendRedirect("index.jsp");
        return;
    }
%>
    <div class="container">
        <div class=heading>
            <%
                String incorrectID = (String) request
                        .getAttribute("incorrectUsername");
                String incorrectPass = (String) request
                        .getAttribute("incorrectPassword");
                if (incorrectID != null || incorrectPass != null)
                    out.print("<h1 class=\"form-signin-heading text-center\"><span class=\"label-warning\">Please try again</span></h1>");
                else
                    out.print("<h1 class=\"form-signin-heading text-center\"><span class=\"label-success\">Please Sign In</span></h1>");
            %>
        </div>
        <form action="login" method="post" class="form-signin" id="gh_sign"
              role="form">
            <input type="text" class="form-control" placeholder="username" required
                   autofocus id="username" name="display_name"> <input
                type="password" class="form-control" placeholder="password" required
                id="password" name="password">
            <button class="form-control form-signin-button" type="submit">Login</button>
        </form>
    </div>
</body>
</html>
