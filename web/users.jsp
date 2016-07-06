<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.lang.String" %>
<%@ page import="main.java.db.managers.AccountManager" %>
<%@ page import="java.util.List" %>
<%@ page import="main.java.models.Account" %>
<%@ page import="java.util.ArrayList" %>
<jsp:include page="header.jsp"/>
<html>
<head>
    <title>user profile</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <link rel="stylesheet" type="text/css" href="css/about.css">
    <link rel="stylesheet" type="text/css" href="css/new_profile.css">
    <link rel="stylesheet" type="text/css" href="css/following-users.css">
    <script src="/javascript/jquery-2.1.1.min.js"></script>
    <script src="/javascript/profile.js"></script>
    <%
        String displayName = (String) session.getAttribute("display_name");
        Boolean is_admin = (Boolean) session.getAttribute("is_admin");
    %>
</head>
<body style="background-image: url(images/logo/rsz_only_logo.png);">
<div class="search-wrapper">
    <input class="users-search" type="text"> </input>
    <button class="search-button">Search</button>
</div>
<%
    ServletContext sc = getServletConfig().getServletContext();
    AccountManager manager = (AccountManager) sc.getAttribute(AccountManager.ATTRIBUTE_NAME);

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

    List<Account> allUsers = manager.getAllAccounts(request.getParameter("search"));
    List<Account> userFollowing = manager.getUserFollowing(displayName);
    List<String> userFollowingDisplayNames = new ArrayList<>();

    for (int i = 0; i < userFollowing.size(); i++) {
        userFollowingDisplayNames.add(userFollowing.get(i).getDisplayName());
    }

%>

<div id="user-followers-dashboard" class="user-followers-dashboard">
    <%
        for (int i = 0; i < allUsers.size(); i++) {
            if (allUsers.get(i).getDisplayName().equals(displayName)) continue;
    %>
    <div class="single-user-info">
        <div class="single-user-picture">
            <%
                String imgSrc = allUsers.get(i).getImgSrc();
                if (imgSrc == null) {
                    imgSrc = "/images/images/nobody_m.original.jpg";
                } else {
                    imgSrc = "GetImage?image=" + imgSrc;
                }
            %>
            <img src="<%=imgSrc%>" class="follow-picture m-auto bg-cover">
        </div>
        <div class="single-user-name-email">
            <a href="http://localhost:8080/profile.jsp?username=<%=allUsers.get(i).getDisplayName()%>"
               class="single-user-name">
                <%=allUsers.get(i).getDisplayName()%>
            </a>
            <div class="single-user-email">
                <%=allUsers.get(i).getMail()%>
            </div>
        </div>
        <div class="single-user-buttons">
            <% if (userFollowingDisplayNames.contains(allUsers.get(i).getDisplayName())) {%>
            <form action="FollowUnfollowServlet" method="post"
                  role="form">
                <input type="hidden" value='unfollow' name="type">
                <input type="hidden" value='users.jsp' name="page">
                <input type="hidden" value='<%=allUsers.get(i).getDisplayName()%>' name="user">
                <button class="unfollow btn btn-tag post-tag ff-superSquare fs-13 fc-grey-dark follow-unfollow-button">
                    unfollow
                </button>
            </form>
            <%} else {%>
            <form action="FollowUnfollowServlet" method="post"
                  role="form">
                <input type="hidden" value='follow' name="type">
                <input type="hidden" value='users.jsp' name="page">
                <input type="hidden" value='<%=allUsers.get(i).getDisplayName()%>' name="user">
                <button class="follow btn btn-tag post-tag ff-superSquare fs-13 fc-grey-dark follow-unfollow-button">
                    follow
                </button>
            </form>
            <%}%>
        </div>
    </div>
    <%
        }
    %>
</div>


</body>
</html>
