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
    <link rel="stylesheet" type="text/css" href="css/new_profile.css">
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
        String aboutMe = profileAcc.getAboutMe();
        String profileImg = profileAcc.getImgSrc();
        if (profileAcc.getImgSrc() == null) {
            profileImg = "https://x1.xingassets.com/assets/frontend_minified/img/users/nobody_m.original.jpg";
        }
%>

<div class="follow-posts-header">
    <div class="followers_menu inline-block">
        <ul>
            <li class="followers inline-block ff-superSquare fs-15 fc-grey-dark">
                <a class="text-deco-none" href="#">followers</a>
            </li>

            <li class="following inline-block ff-superSquare fs-15 fc-grey-dark">
                <a class="text-deco-none" href="#">following</a>
            </li>

            <li class="following inline-block ff-superSquare fs-15 fc-grey-dark">
                <a class="text-deco-none" href="#">uploads</a>
            </li>

        </ul>
    </div>
</div>

<div class="profile m-auto">
    <div class="pic_with_menu">
        <div class="profile-picture m-auto bg-cover"
             style="background-image: url(<%=profileImg%>);">
        </div>
        <div class="upload-avatar m-auto">

            <form action="ImageUploadServlet" method="post"
                  enctype="multipart/form-data">
                <input class="input-btn pointer ff-superSquare fs-13 fc-grey-dark" type="file" name="file"
                       size="50"/>
                <input class="input-btn pointer ff-superSquare fs-13 fc-grey-dark" type="submit"
                       value="Upload File"/>
            </form>
        </div>

    </div>
    <div class="profile-info m-auto ff-superSquare fs-13 fc-grey-dark">
        <div class="margin-top display-name">
            <label><%=displayName%>
            </label>
        </div>
        <div class="margin-top first-name">
            <label><%
                out.print(firstName);
                out.print(" ");
                out.print(lastName);
            %>
            </label>
        </div>
        <div class="margin-top email">
            <label><%=email%>
            </label>
        </div>
        <div class="margin-top city">
            <label><%out.print(city);
                out.print(", ");
                out.print(country);%>
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
            <label><<%=googlePlus%>
            </label>
        </div>
    </div>
    <div class="profile-about m-auto ff-superSquare fs-15 fc-grey-darker">
        <label><%=aboutMe%>
        </label>
    </div>
</div>

<%
    }
%>
</body>
</html>
