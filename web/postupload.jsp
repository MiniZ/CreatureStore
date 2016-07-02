<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="header.jsp" />
<html>
<head>
    <link href="css/postupload.css" rel="stylesheet">
    <title>Post Upload</title>
    <%
        String loggedInUser = (String) session.getAttribute("display_name");
    %>
</head>
<body style = "background-image: url(/images/logo/rsz_only_logo.png);">
    <div class="container">
        <div class=heading>
            <%
                String incorrectID = (String) request
                        .getAttribute("incorrectUsername");
                String incorrectPass = (String) request
                        .getAttribute("incorrectPassword");
                if (incorrectID != null || incorrectPass != null)
                    out.print("<h1 class=\"form-signin-heading text-center\"><span class=\"label-warning ff-superSquareCap fc-grey fs-30\">Please try again</span></h1>");
                else
                    out.print("<h1 class=\"form-signin-heading text-center\"><span class=\"label-success ff-superSquareCap fc-grey fs-30\">Add your work here!</span></h1>");
            %>
        </div>
        <form action="PostUploadServlet" method="post" class="form-signin" id="gh_sign"
              role="form" enctype="multipart/form-data">
            <input type="text" class="form-control" placeholder="title" required
                   autofocus id="post_title" name="post_title">

            <input type="text" class="form-control" placeholder="description" required
                   autofocus id="description_title" name="description_title">

            <input type="text" class="form-control" placeholder="youtube_link" required
                   autofocus id="youtube_link" name="youtube_link">

            <input type="file" name="file" size="50" class="file-upload-button form-control form-signin-button"/>


            <button class="form-control form-signin-button" type="submit">Upload</button>
        </form>
    </div>
</body>
</html>
