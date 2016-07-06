<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.lang.String"%>
<%@ page import="main.java.db.managers.AccountManager" %>
<%@ page import="main.java.db.managers.PostManager" %>
<%@ page import="java.util.List" %>
<%@ page import="main.java.models.Post" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<jsp:include page="header.jsp" />
<html>
<head>
    <link type="text/css" rel="stylesheet" href="../css/style.css"/>
    <link type="text/css" rel="stylesheet" href="../css/dashboard.css"/>

    <link type="text/css" rel="stylesheet" href="../css/styles.max-width-600.css"/>
    <link type="text/css" rel="stylesheet" href="../css/styles.max-width-900.css"/>
    <link type="text/css" rel="stylesheet" href="../css/styles.max-width-1450.css"/>
    <%
        ServletContext sc = getServletConfig().getServletContext();
        AccountManager manager = (AccountManager) sc.getAttribute(AccountManager.ATTRIBUTE_NAME);
        PostManager postManager = (PostManager) sc.getAttribute(PostManager.ATTRIBUTE_NAME);

        String loggedInUser = (String) session.getAttribute("display_name");

        List<Post> posts = postManager.getUserFollowingPosts(loggedInUser);
        Map<Integer, String> user_names = new HashMap<>();
        for (Post post : posts) {
            user_names.put(post.getId(), manager.getAccountDisplayNameByID(post.getAccountId()));
        }
        Map<Integer, Integer> pluses = new HashMap<>();
        Map<Integer, Integer> minuses = new HashMap<>();

        pluses = postManager.getPlusesMapForPosts(posts);
        minuses = postManager.getMinusesMapForPosts(posts);


        Map<String, String> user_img_src = new HashMap<>();
        for (String s : user_names.values()) {
            user_img_src.put(s, manager.getImgSrcByDisplayName(s));
        }

    %>
    <title>Creature Store</title>
</head>
<body  style = "background-image: url(../images/logo/rsz_only_logo.png);">


<div class="m-auto">

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

</body>
</html>
