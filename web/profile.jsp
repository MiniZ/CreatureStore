<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="main.java.db.managers.AccountManager" %>
<%@page import="main.java.db.managers.PostManager" %>
<%@ page import="main.java.models.Account" %>
<%@ page import="main.java.models.Post" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<jsp:include page="header.jsp"/>
<html>
<head>
    <title>user profile</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <link rel="stylesheet" type="text/css" href="css/about.css">
    <link rel="stylesheet" type="text/css" href="css/new_profile.css">
    <link rel="stylesheet" type="text/css" href="css/following-users.css">
    <script src="/javascript/profile.js"></script>
    <link type="text/css" rel="stylesheet" href="css/style.css"/>
    <link type="text/css" rel="stylesheet" href="css/dashboard.css"/>

    <link type="text/css" rel="stylesheet" href="css/styles.max-width-600.css"/>
    <link type="text/css" rel="stylesheet" href="css/styles.max-width-900.css"/>
    <link type="text/css" rel="stylesheet" href="css/styles.max-width-1450.css"/>
</head>
<body style="background-image: url(images/logo/rsz_only_logo.png);">

<%
    ServletContext sc = getServletConfig().getServletContext();
    AccountManager manager = (AccountManager) sc.getAttribute(AccountManager.ATTRIBUTE_NAME);
    PostManager managerpost = (PostManager) sc.getAttribute(PostManager.ATTRIBUTE_NAME);

    String loggedUserDisplayName = (String) session.getAttribute("display_name");

    String displayName = null;
    if (request.getParameter("username") != null) {
        displayName = request.getParameter("username");
        Account profileAcc = manager.getAccount(displayName);
        String firstName = profileAcc.getFirstName();
        String lastName = profileAcc.getLastName();
        String email = profileAcc.getMail();
        String facebook = profileAcc.getFbLink();
        String twitter = profileAcc.getTwitterLink();
        String googlePlus = profileAcc.getGoogleLink();
        String city = profileAcc.getCity();
        String country = profileAcc.getCountry();
        String aboutMe = profileAcc.getAboutMe();
        String profileImg = profileAcc.getImgSrc();


        if (profileImg == null) {
            profileImg = "/images/images/nobody_m.original.jpg";
        } else {
            profileImg = "GetImage?image=" + profileImg;
        }


        List<Account> userFollowers = manager.getUserFollowers(displayName);
        List<Account> userFollowing = manager.getUserFollowing(displayName);
        List<Account> loggedUserFollowing = manager.getUserFollowing(loggedUserDisplayName);
        List<String> loggedUserFollowingDisplayNames = new ArrayList<>();

        for (int i = 0; i < loggedUserFollowing.size(); i++) {
            loggedUserFollowingDisplayNames.add(loggedUserFollowing.get(i).getDisplayName());
        }

        List<Post> posts = managerpost.getPostsForUser(displayName);
        Map<Integer, String> user_names = new HashMap<>();
        for (Post post : posts) {
            user_names.put(post.getId(), manager.getAccountDisplayNameByID(post.getAccountId()));
        }
        Map<Integer, Integer> pluses = new HashMap<>();
        Map<Integer, Integer> minuses = new HashMap<>();

        pluses = managerpost.getPlusesMapForPosts(posts);
        minuses = managerpost.getMinusesMapForPosts(posts);


        Map<String, String> user_img_src = new HashMap<>();
        for (String s : user_names.values()) {
            user_img_src.put(s, manager.getImgSrcByDisplayName(s));
        }


%>

<div id="follow-posts-header">
    <div id="followers_menu" class="inline-block">
        <ul>
            <li id="followers" style="background-color: #90d2de;"
                class="inline-block ff-superSquare fs-15 fc-grey-dark">
                <a class=" followers text-deco-none" href="#">followers</a>
            </li>
            <li id="following" class="following inline-block ff-superSquare fs-15 fc-grey-dark">
                <a class=" following text-deco-none" href="#">following</a>
            </li>

            <li id="uploads" class="uploads inline-block ff-superSquare fs-15 fc-grey-dark">
                <a class="uploads text-deco-none" href="#">uploads</a>
            </li>
        </ul>
    </div>
</div>

<div class="profile m-auto">

    <div class="pic_with_menu">
        <div class="profile-picture m-auto bg-cover">
            <img src="<%=profileImg%>" class="profile-picture m-auto bg-cover">
        </div>
        <% if (loggedUserDisplayName.equals(displayName)) {%>
        <div class="upload-avatar m-auto">
            <form action="ImageUploadServlet" method="post"
                  enctype="multipart/form-data">
                <input class="input-btn pointer ff-superSquare fs-13 fc-grey-dark" type="file" name="file"
                       size="50"/>
                <input class="input-btn pointer ff-superSquare fs-13 fc-grey-dark" type="submit"
                       value="Upload File"/>
            </form>
        </div>
        <%}%>

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
            <label><%
                out.print(city);
                out.print(", ");
                out.print(country);
            %>
            </label>
        </div>
        <%--<div class="facebook-link">--%>
        <%--<label><%=facebook%>--%>
        <%--</label>--%>
        <%--</div>--%>
        <%--<div class="twitter-link">--%>
        <%--<label><%=twitter%>--%>
        <%--</label>--%>
        <%--</div>--%>
        <%--<div class="google-link">--%>
        <%--<label><%=googlePlus%>--%>
        <%--</label>--%>
        <%--</div>--%>

        <div class="profile-icons">
            <a href="<%=facebook%>" class="about-fb-icon inline-hor">
                <img src="images/images/fb.png">
            </a>
            <a href="<%=twitter%>" class="about-git-icon inline-hor">
                <img src="images/images/twitter.png">
            </a>
            <a href="<%=googlePlus%>" class="about-google-icon inline-hor">
                <img src="images/images/google.png">
            </a>
        </div>

    </div>

    <div class="profile-about m-auto ff-superSquare fs-15 fc-grey-darker">
        <label><%=aboutMe%>
        </label>
    </div>

</div>
<div id="user-followers-dashboard" class="user-followers-dashboard">
    <%for (int i = 0; i < userFollowers.size(); i++) {%>
    <div class="single-user-info">
        <div class="single-user-picture">
            <%
                String imgSrc = userFollowers.get(i).getImgSrc();
                if (imgSrc == null) {
                    imgSrc = "/images/images/nobody_m.original.jpg";
                } else {
                    imgSrc = "GetImage?image=" + imgSrc;
                }
            %>
            <img src="<%=imgSrc%>" class="follow-picture m-auto bg-cover">
        </div>
        <div class="single-user-name-email">
            <a href="http://localhost:8080/profile.jsp?username=<%=userFollowers.get(i).getDisplayName()%>"
               class="single-user-name">
                <%=userFollowers.get(i).getDisplayName()%>
            </a>
            <div class="single-user-email">
                <%=userFollowers.get(i).getMail()%>
            </div>
        </div>
        <div class="single-user-buttons">
            <% if (loggedUserFollowingDisplayNames.contains(userFollowers.get(i).getDisplayName()) && !loggedUserDisplayName.equals(userFollowers.get(i).getDisplayName())) {%>
            <form action="FollowUnfollowServlet" method="post"
                  role="form">
                <input type="hidden" value='unfollow' name="type">
                <input type="hidden" value='profile.jsp?username=<%=displayName%>' name="page">
                <input type="hidden" value='<%=userFollowers.get(i).getDisplayName()%>' name="user">
                <button class="unfollow btn btn-tag post-tag ff-superSquare fs-13 fc-grey-dark follow-unfollow-button">
                    unfollow
                </button>
            </form>

            <%} else if(!loggedUserDisplayName.equals(userFollowers.get(i).getDisplayName())){%>

            <form action="FollowUnfollowServlet" method="post"
                  role="form">
                <input type="hidden" value='follow' name="type">
                <input type="hidden" value='profile.jsp?username=<%=displayName%>' name="page">
                <input type="hidden" value='<%=userFollowers.get(i).getDisplayName()%>' name="user">
                <button class="unfollow btn btn-tag post-tag ff-superSquare fs-13 fc-grey-dark follow-unfollow-button">
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
<div id="user-following-dashboard" class="user-followers-dashboard">
    <%for (int i = 0; i < userFollowing.size(); i++) {%>
    <div class="single-user-info">
        <div class="single-user-picture">
            <%
                String imgSrc = userFollowing.get(i).getImgSrc();
                if (imgSrc == null) {
                    imgSrc = "/images/images/nobody_m.original.jpg";
                } else {
                    imgSrc = "GetImage?image=" + imgSrc;
                }
            %>
            <img src="<%=imgSrc%>" class="follow-picture m-auto bg-cover">
        </div>
        <div class="single-user-name-email">
            <a href="http://localhost:8080/profile.jsp?username=<%=userFollowing.get(i).getDisplayName()%>"
               class="single-user-name">
                <%=userFollowing.get(i).getDisplayName()%>
            </a>
            <div class="single-user-email">
                <%=userFollowing.get(i).getMail()%>
            </div>
        </div>
        <div class="single-user-buttons">
            <% if (loggedUserFollowingDisplayNames.contains(userFollowing.get(i).getDisplayName())  && !loggedUserDisplayName.equals(userFollowing.get(i).getDisplayName())) {%>
            <form action="FollowUnfollowServlet" method="post"
                  role="form">
                <input type="hidden" value='unfollow' name="type">
                <input type="hidden" value='profile.jsp?username=<%=displayName%>' name="page">
                <input type="hidden" value='<%=userFollowing.get(i).getDisplayName()%>' name="user">
                <button class="unfollow btn btn-tag post-tag ff-superSquare fs-13 fc-grey-dark follow-unfollow-button">
                    unfollow
                </button>
            </form>

            <%} else if (!loggedUserDisplayName.equals(userFollowing.get(i).getDisplayName())){%>
            <form action="FollowUnfollowServlet" method="post"
                  role="form">
                <input type="hidden" value='follow' name="type">
                <input type="hidden" value='profile.jsp?username=<%=displayName%>' name="page">
                <input type="hidden" value='<%=userFollowing.get(i).getDisplayName()%>' name="user">
                <button class="unfollow btn btn-tag post-tag ff-superSquare fs-13 fc-grey-dark follow-unfollow-button">
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

<div class="m-auto " id="my-uploads" style="display : none">

    <%
        for (Post post : posts) {

            out.print("<div class=\"post\">\n" +
                    "      <div class=\"post-upper\">\n" +
                    "        <div class=\"\">\n" +
                    "          <div class=\"post-photo bg-cover m-auto\">");
    %>
    <img src="GetImage?image=<%=post.getImgSrc()%>" class="post-image m-auto">
    <%
        out.print("<div class=\"hover-title\" >\n" +
                "              <div class=\"ff-superSquareCap fs-18 opacity-hover\">");
        out.print("<a class=\"a-label\" href=\"/post-page.jsp?postId=" + post.getId() + "\">");
        out.print(post.getTitle());
        out.print("</a>");
        out.print("</div></div>\n" +
                "          </div>\n" +
                "        </div>\n" +
                "        <a class=\"tooltips right\" href=\"#\">\n" +
                "          <div class=\"post-pluses bg-plus-icon bg-cover right pointer on-hover\"></div>\n" +
                "          <span class=\"ff-superSquare\">");
        out.print(pluses.get(post.getId()));
        out.print("</span></a>\n" +
                "        <a class=\"tooltips right\" href=\"#\">\n" +
                "          <div class=\"post-minuses bg-minus-icon bg-cover right pointer on-hover\"></div>\n" +
                "          <span class=\"ff-superSquare\">");
        out.print(minuses.get(post.getId()));
        out.print("</span></a>\n" +
                "      </div>\n" +
                "      <div class=\"post-author ff-superSquare fs-13 pointer fc-grey-darker on-hover\">\n" +
                "        <div class=\"author-avatar left bg-cover\">");
    %>
    <img src="GetImage?image=<%=user_img_src.get(user_names.get(post.getId()))%>" class="avatar">
    <%
            out.print("</div> <span >");
            out.print("<a style=\"text-decoration: none;\" class=\"a-label\" href=\"/profile.jsp?username=" + user_names.get(post.getId()) + "\">");
            out.print(user_names.get(post.getId()));
            out.print("</a>");
            out.print("</span>\n" +
                    "      </div>\n" +
                    "    </div>");

        }
    %>


</div>
<%

    }
%>
</body>
</html>
