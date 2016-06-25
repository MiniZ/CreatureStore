<%@ page import="java.net.URLEncoder" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="header.jsp" />
<html>
<head>
    <link href="styles/signup.css" rel="stylesheet">
    <title>Login</title>
    <%
        String loggedInUser = (String) session.getAttribute("display_name");
    %>
</head>
<body>
<script src="/javascript/countries.js"></script>
<script src="/javascript/jquery-2.1.1.js"></script>
<%
    if (loggedInUser != null) {
        response.sendRedirect("index.jsp");
        return;
    }
%>
<div class="container">
    <form action="SignUp" method="post" class="form-horizontal form-signin" id="gh_sign" role="form"
          accept-charset="UTF-8">
        <div class=heading>
            <%
                String usernameTaken = (String) request
                        .getAttribute("usernameTaken");
                String error = (String) request.getAttribute("error");
                if (usernameTaken != null)
                    out.print("<h2 class=\"form-signin-heading text-center\"><span class=\"label-warning ff-superSquareCap fc-grey fs-30\">Account with such username already exists</span></h2>");
                else if (error != null)
                    out.print("<h2 class=\"form-signin-heading text-center\"><span class=\"label-warning ff-superSquareCap fc-grey fs-30\">Please, try again</span></h2>");
                else
                    out.print("<h2 class=\"form-signin-heading text-center\"><span class=\"label-success ff-superSquareCap fc-grey fs-30\">Sign Up Here</span></h2>");
            %>
        </div>
        <div class="form-group">
            <label for="inputName3" class="field-label ff-superSquareCap fc-grey fs-18">Display Name:</label>
            <div class="label-field">
                <input type="text" class="form-control" placeholder="diplay name" required
                       autofocus id="display_name" name="display_name">
            </div>
        </div>
        <div class="form-group">
            <label for="inputPassword3" class="field-label ff-superSquareCap fc-grey fs-18">Password:</label>
            <div class="label-field">
                <input type="password" class="form-control" placeholder="Password"
                       required id="password" name="password">
            </div>
        </div>
        <div class="form-group">
            <label for="firstName3" class="field-label ff-superSquareCap fc-grey fs-18">First Name:</label>
            <div class="label-field">
                <input type="text" class="form-control" placeholder="first name"
                       required id="first_name" name="first_name">
            </div>
        </div>
        <div class="form-group">
            <label for="lastName3" class="field-label ff-superSquareCap fc-grey fs-18">Last Name:</label>
            <div class="label-field">
                <input type="text" class="form-control" placeholder="last name"
                       required id="last_name" name="last_name">
            </div>
        </div>
        <div class="form-group">
            <label for="email3" class="field-label ff-superSquareCap fc-grey fs-18">E-mail:</label>
            <div class="label-field">
                <input type="text" class="form-control" placeholder="email"
                       required id="email" name="email">
            </div>
        </div>
        <div class="form-group">
            <label for="fbLink3" class="field-label ff-superSquareCap fc-grey fs-18">Facebook Link:</label>
            <div class="label-field">
                <input type="text" class="form-control" placeholder="facebook link"
                       required id="facebook_link" name="facebook_link">
            </div>
        </div>
        <div class="form-group">
            <label for="twitterLink3" class="field-label ff-superSquareCap fc-grey fs-18">Twitter Link:</label>
            <div class="label-field">
                <input type="text" class="form-control" placeholder="twitter link"
                       required id="twitter_link" name="twitter_link">
            </div>
        </div>
        <div class="form-group">
            <label for="googlePlusLink3" class="field-label ff-superSquareCap fc-grey fs-18">Google + link:</label>
            <div class="label-field">
                <input type="text" class="form-control" placeholder="google + link"
                       required id="google_plus_link" name="google_plus_link">
            </div>
        </div>
        <div class="form-group">
            <label for="country3" class="field-label ff-superSquareCap fc-grey fs-18">Country :</label>
            <div class="label-field">
                <%@include file="html/countries.html"%>
            </div>
        </div>
        <div class="form-group">
            <label for="city3" class="field-label ff-superSquareCap fc-grey fs-18">City :</label>
            <div class="label-field">
                <input type="text" class="form-control" placeholder="city"
                       required id="city" name="city">
            </div>
        </div>
        <div class="form-group">
            <label for="aboutMe3" class="field-label ff-superSquareCap fc-grey fs-18">About Me :</label>
            <div class="label-field">
                <input type="text" class="form-control" placeholder="about me"
                       required id="about_me" name="about_me">
            </div>
        </div>
        <div class="form-group">
            <button class="form-control form-signin-button" type="submit">Sign
                Up</button>
        </div>
    </form>
</div>
</body>
</html>
