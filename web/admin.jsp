<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.lang.String"%>
<%@ page import="main.java.db.managers.AccountManager" %>
<%@ page import="main.java.db.managers.PostManager" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="main.java.models.Post" %>
<jsp:include page="header.jsp" />
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="css/admin.css">
        <script src="javascript/admin.js"></script>
        <title>Admin Panel</title>
            <%
                String type  = request.getParameter("type");
                String user = request.getParameter("user");
                String postt = request.getParameter("post");
                String loggedInUser = (String) session.getAttribute("display_name");
                Boolean is_admin = (Boolean) session.getAttribute("is_admin");
                if (loggedInUser == null) {
                    response.sendRedirect("index.jsp");
                }
                if (is_admin == null || !is_admin) {
                    response.sendRedirect("index.jsp");
                }
                ServletContext sc = getServletConfig().getServletContext();
                AccountManager manager = (AccountManager) sc.getAttribute(AccountManager.ATTRIBUTE_NAME);
                PostManager postManager = (PostManager) sc.getAttribute(PostManager.ATTRIBUTE_NAME);
                Map<String, Boolean> users = manager.getAllAccountsBannedMap(user);

                List<Post> posts = postManager.getAllPosts(postt);
                boolean isPost = false;
                if (type == null) {
                    isPost = false;
                } else if (type.equals("post")) {
                    isPost = true;
                }

            %>
    </head>
    <body style="background-image: url(images/logo/rsz_only_logo.png);">
        <div class="nav-container">
            <div class=nav-header>
                <%
                    if (isPost) {
                        out.print("<button class=\"form-control form-signin-button ff-superSquareCap fs-18\" id=\"users\">Users</button>\n" +
                                "                <button style=\"background-color: #FF2F19;\" class=\"form-control form-signin-button ff-superSquareCap fs-18\" id=\"posts\">Posts</button>");
                    } else {
                        out.print("<button style=\"background-color: #FF2F19;\" class=\"form-control form-signin-button ff-superSquareCap fs-18\" id=\"users\">Users</button>\n" +
                                "                <button class=\"form-control form-signin-button ff-superSquareCap fs-18\" id=\"posts\">Posts</button>");
                    }

                %>
            </div>
        </div>
        <%
            if (isPost) {
                out.print("<div class=\"container\" id=\"users_board\" style=\"display: none\">");
            } else {
                out.print("<div class=\"container\" id=\"users_board\" >");
            }

        %>
            <div class=heading>
                <%
                    out.print("<h2 class=\"form-signin-heading text-center\"><span class=\"label-success ff-superSquareCap fc-grey fs-30\">Manage Users</span></h2>");
                    out.print("<input type=\"text\" class=\"form-control user-search\">\n" +
                            "                </input>");
                    out.print("<button class=\"form-control form-signin-button ff-superSquareCap fs-18 user-search-button\" >Search</button>\n");
                %>

            </div>
            <%
                for (String s : users.keySet()) {
                    if (users.get(s)) {
                        out.print("<form action=\"UnBanUserServlet\" method=\"post\" class=\"form-horizontal form-signin\" id=\"gh_sign\" role=\"form\"\n" +
                                "                  accept-charset=\"UTF-8\">");
                        out.print("<div class=\"form-group\">");
                        out.print("<input type=\"hidden\" value=\""
                                + s + "\" name=\"user\">");
                        out.print("<label for=\"inputName5\" class=\"field-label ff-superSquareCap fc-grey fs-18\">");
                        out.print("<a class=\"a-label\" href=\"/profile.jsp?username=" + s + "\">");
                        out.print(s);
                        out.print("</a>");
                        out.print("</label>");
                        out.print("<div class=\"label-field\">");
                        out.print("<button class=\"form-control form-signin-button ff-superSquareCap fs-18 green-button\" type=\"submit\">");
                        out.print("UNBAN");
                        out.print("</button>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</form>");
                        out.print("<div class=\"clear\"");
                        out.print("</div>");
                        out.print("</div>");
                    } else {
                        out.print("<form action=\"BanUserServlet\" method=\"post\" class=\"form-horizontal form-signin\" id=\"gh_sign\" role=\"form\"\n" +
                                "                  accept-charset=\"UTF-8\">");
                        out.print("<div class=\"form-group\">");
                        out.print("<input type=\"hidden\" value=\""
                                + s + "\" name=\"user\">");
                        out.print("<label for=\"inputName5\" class=\"field-label ff-superSquareCap fc-grey fs-18\">");
                        out.print("<a class=\"a-label\" href=\"/profile.jsp?username=" + s + "\">");
                        out.print(s);
                        out.print("</a>");
                        out.print("</label>");
                        out.print("<div class=\"label-field\">");
                        out.print("<button class=\"form-control form-signin-button ff-superSquareCap fs-18 red-button\" type=\"submit\">");
                        out.print("BAN");
                        out.print("</button>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</form>");
                        out.print("<div class=\"clear\"");
                        out.print("</div>");
                        out.print("</div>");
                    }
                }
            %>
        </div>
        <%
            if (isPost) {
                out.print("<div class=\"container-posts\" id=\"posts_board\" style=\"display: block\">");
            } else {
                out.print("<div class=\"container-posts\" id=\"posts_board\" style=\"display: none\">");
            }
        %>
            <div class=heading>
                <%
                    out.print("<h2 class=\"form-signin-heading text-center\"><span class=\"label-success ff-superSquareCap fc-grey fs-30\">Manage Posts</span></h2>");
                    out.print("<input type=\"text\" class=\"form-control post-search\">\n" +
                            "                </input>");
                    out.print("<button class=\"form-control form-signin-button ff-superSquareCap fs-18 post-search-button\" >Search</button>\n");
                %>
            </div>
            <%
                for (Post post : posts) {
                    out.print("<form action=\"DeletePostServlet\" method=\"post\" class=\"form-horizontal form-signin\" id=\"gh_sign\" role=\"form\"\n" +
                            "                  accept-charset=\"UTF-8\">");
                    out.print("<div class=\"form-group\">");
                    out.print("<input type=\"hidden\" value=\""
                            + post.getId() + "\" name=\"post_id\">");
                    out.print("<label for=\"inputName5\" class=\"field-label ff-superSquareCap fc-grey fs-18\">");
                    out.print("<a class=\"a-label\" href=\"/post-page.jsp?postId=" + post.getId() + "\">"); //TODO
                    out.print(post.getTitle());
                    out.print("</a>");
                    out.print("</label>");
                    out.print("<div class=\"label-field\">");
                    out.print("<button class=\"form-control form-signin-button ff-superSquareCap fs-18 red-button\" type=\"submit\">");
                    out.print("DELETE");
                    out.print("</button>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</form>");
                    out.print("<div class=\"clear\"");
                    out.print("</div>");
                }
            %>
        </div>
    </body>
</html>