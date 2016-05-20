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
    %>
    <title>Creature Store</title>
  </head>
  <body>
  <h1>Users</h1>
  <%
    for (String s : result) {
      out.print(s + "\n");
    }
  %>
  </body>
</html>
