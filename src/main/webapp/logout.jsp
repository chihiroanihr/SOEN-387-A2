<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Logout</title>
</head>
<body>
<% session.invalidate();%>
<% response.sendRedirect("index.jsp"); %>
</body>
</html>
