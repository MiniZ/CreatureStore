
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.lang.String"%>
<%@ page import="main.java.db.managers.AccountManager" %>
<%@ page import="main.java.db.managers.PostManager" %>
<%@ page import="main.java.models.Post" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.Date" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.util.ArrayList" %>
<jsp:include page="header.jsp" />

<html>
<head>
    <!-- <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no"> -->
    <link type="text/css" rel="stylesheet" href="../css/style.css"/>
    <link type="text/css" rel="stylesheet" href="../css/post-page.css"/>

    <link type="text/css" rel="stylesheet" href="../css/styles.max-width-600.css"/>
    <link type="text/css" rel="stylesheet" href="../css/styles.max-width-900.css"/>
    <link type="text/css" rel="stylesheet" href="../css/styles.max-width-1450.css"/>
    <!-- <link type="text/css" rel="stylesheet" href="./css/reset.css"/>
    <link type="text/css" rel="stylesheet" href="./css/global.css" />
    <script src="./js/jquery-1.12.1.min.js"></script>
    <script src="./js/main.js"></script> -->
    <title>Post</title>
    <%
        String loggedInUser = (String) session.getAttribute("display_name");
        String postID = request.getParameter("postId");
        ServletContext sc = getServletConfig().getServletContext();
        AccountManager manager = (AccountManager) sc.getAttribute(AccountManager.ATTRIBUTE_NAME);
        PostManager postManager = (PostManager) sc.getAttribute(PostManager.ATTRIBUTE_NAME);

        Post post = manager.getPostById(postID);
        String img_src = post.getImgSrc();
        String title = post.getTitle();
        String description = post.getDescription();
        Integer account_id = post.getAccountId();
        Integer post_id = post.getId();
        Timestamp post_date = post.getPostTime();
        String date = post_date.toString().split("\\ ")[0];

        String display_name = manager.getAccountDisplayNameByID(account_id);
        String user_avatar = manager.getUserAvatarByID(account_id);

        ArrayList<String> tags = postManager.getTagsByPostID(post_id);


    %>
</head>
<body style = "background-image: url(../images/logo/rsz_only_logo.png);">
<div class="post-page">
    <div class="left-post">

        <div class="left-content inline-block">

            <div class="post-author ff-superSquare fs-16 fc-grey-darker">
                <div class="post-title ff-superSquare fs-18 fc-grey-darker">
                    <%
                        out.print(title);
                    %>
                </div>
                <div class="author-avatar left bg-cover pointer">
                    <img src="GetImage?image=<%=user_avatar%>" class="author-avatar left bg-cover pointer">
                </div>

                <div class="author-name">by <span class="fc-grey-dark pointer on-hover">
                    <%
                        out.print(display_name);
                    %>
                </span></div>
            </div>

            <div class="post-photo">
                <img src="GetImage?image=<%=img_src%>">
            </div>

            <div class="post-comments">

            </div>
        </div>


        <div class="right-content inline-block ">

            <div class="post-pluses">
                <div class="plus-minus-num bg-plus-icon bg-cover  pointer on-hover"></div>
                <span class="ff-superSquare fc-grey-dark">1024</span>
            </div>

            <div class="post-minuses">
                <div class="plus-minus-num bg-minus-icon bg-cover  pointer on-hover"></div>
                <span class="ff-superSquare fc-grey-dark">1024</span>
            </div>

            <div class="post-date">
                <span class="ff-superSquare fs-13 fc-grey-darker">Date: </span>
					<span class="ff-superSquare fs-13 fc-grey-dark">
						  <%
                              out.print(date);
                          %>
					</span>
            </div>

            <div class="post-description">
                <span class="ff-superSquare fs-13 fc-grey-darker">Description: </span>
					<span class="ff-superSquare fs-13 fc-grey-dark">
                        <%
                            out.print(description);
                        %>
					</span>
            </div>

            <div class="post-tags">
                <span class="ff-superSquare fs-13 fc-grey-darker">Tags: </span>
                <%
                    for (String tag : tags) {
                        out.print(" <div class=\"btn btn-tag post-tag ff-superSquare fs-13 fc-grey-dark\">");
                        out.print(tag);
                        out.print("</div>");
                    }
                %>
            </div>

        </div>
    </div>

</div>

</body>
</html>
