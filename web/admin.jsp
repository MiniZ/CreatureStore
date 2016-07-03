<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.lang.String"%>
<%@ page import="main.java.db.managers.AccountManager" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<jsp:include page="header.jsp" />
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="css/admin.css">
        <title>Admin Panel</title>
            <%
                ServletContext sc = getServletConfig().getServletContext();
                AccountManager manager = (AccountManager) sc.getAttribute(AccountManager.ATTRIBUTE_NAME);
                List<String> result = manager.getAllAccountDisplayNames();
                String loggedInUser = (String) session.getAttribute("display_name");
                Boolean is_admin = (Boolean) session.getAttribute("is_admin");
                Map<String, Boolean> users = manager.getAllAccountsBannedMap();
                if (loggedInUser == null) {
                    response.sendRedirect("index.jsp");
                }
                if (is_admin == null || !is_admin) {
                    response.sendRedirect("index.jsp");
                }

            %>
    </head>
    <body style="background-image: url(images/logo/rsz_only_logo.png);">
        <div class=heading>
            <%

                out.print("<h2 class=\"form-signin-heading text-center\"><span class=\"label-success ff-superSquareCap fc-grey fs-30\">Manage Users</span></h2>");
            %>
        </div>
        <div class="container" style = "background-image: url(/images/logo/rsz_only_logo.png);">
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
                        out.print("BUN");
                        out.print("</button>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</form>");
                        out.print("<div class=\"clear\"");
                        out.print("</div>");
                    }
                }
            %>
        </div>
    </body>
</html>