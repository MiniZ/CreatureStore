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
        <form action="login" method="post" class="form-signin" id="gh_sign"
              role="form">
            <%
                String incorrectID = (String) request
                        .getAttribute("incorrectUsername");
                String incorrectPass = (String) request
                        .getAttribute("incorrectPassword");
                if (incorrectID != null || incorrectPass != null)
                    out.print("<h2 class=\"form-signin-heading text-center\"><span class=\"label label-warning\">Please try again</span></h2>");
                else
                    out.print("<h2 class=\"form-signin-heading text-center\"><span class=\"label label-success\">Please Sign In</span></h2>");
            %>
            <input type="text" class="form-control" placeholder="ID" required
                   autofocus id="username" name="display_name"> <input
                type="password" class="form-control" placeholder="Password" required
                id="password" name="password">
            <button class="btn btn-lg btn-success btn-block" type="submit">Login</button>
        </form>
    </div>
</body>
</html>
