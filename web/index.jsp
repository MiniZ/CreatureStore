<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.lang.String"%>
<%@ page import="main.java.db.managers.AccountManager" %>
<%@ page import="main.java.db.managers.PostManager" %>
<%@ page import="java.util.List" %>
<%@ page import="main.java.models.Post" %>
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

    List<Post> posts = postManager.getPosts();


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
      out.print(post.getTitle());
      out.print("</div></div>\n" +
              "          </div>\n" +
              "        </div>\n" +
              "        <a class=\"tooltips right\" href=\"#\">\n" +
              "          <div class=\"post-pluses bg-plus-icon bg-cover right pointer on-hover\"></div>\n" +
              "          <span class=\"ff-superSquare\">");
      out.print("1024");
      out.print("</span></a>\n" +
              "        <a class=\"tooltips right\" href=\"#\">\n" +
              "          <div class=\"post-minuses bg-minus-icon bg-cover right pointer on-hover\"></div>\n" +
              "          <span class=\"ff-superSquare\">");
      out.print("4");
      out.print("</span></a>\n" +
              "      </div>\n" +
              "      <div class=\"post-author ff-superSquare fs-13 pointer fc-grey-darker on-hover\">\n" +
              "        <div class=\"author-avatar left bg-cover\">\n" +
              "          <img src=\"../images/images/rsz_anano.jpg\" class=\"avatar\">\n" +
              "        </div>\n" +
              "        <span>");
      out.print("Eva Green");
      out.print("</span>\n" +
              "      </div>\n" +
              "    </div>");

    }
  %>


  <%-- <div class="post">
     <div class="post-upper">
       <div class="">
         <div class="post-photo bg-cover m-auto">
           <img src="GetImage?image=<%=posts.get(0).getImgSrc()%>" class="post-image">
           <div class="hover-title" >
             <div class="ff-superSquare">bitch better have ma money</div></div>
         </div>
       </div>
       <a class="tooltips right" href="#">
         <div class="post-pluses bg-plus-icon bg-cover right pointer on-hover"></div>
         <span class="ff-superSquare">1024</span></a>
       <a class="tooltips right" href="#">
         <div class="post-minuses bg-minus-icon bg-cover right pointer on-hover"></div>
         <span class="ff-superSquare">4</span></a>
     </div>
     <div class="post-author ff-superSquare fs-13 pointer fc-grey-darker on-hover">
       <div class="author-avatar left bg-cover">
         <img src="../images/images/rsz_anano.jpg" class="avatar">
       </div>
       <span>Eva Green</span>
     </div>
   </div>--%>


</div>

</body>
</html>
