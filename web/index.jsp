<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.lang.String"%>
<%@ page import="main.java.db.managers.AccountManager" %>
<%@ page import="java.util.List" %>
<html>
  <head>
    <%
      ServletContext sc = getServletConfig().getServletContext();
      AccountManager manager = (AccountManager) sc.getAttribute(AccountManager.ATTRIBUTE_NAME);
      List<String> result = manager.getAllAccountDisplayNames();
      String loggedInUser = (String) session.getAttribute("display_name");
    %>
    <title>Creature Store</title>
  </head>
  <body>
  <h1>Users</h1>
  <%
    if (loggedInUser != null) {
        out.println("<h1>" + loggedInUser + "</h1>");
    } else {
        out.println("no user");
    }
  %>
  </body>
</html>
