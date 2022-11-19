<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    // Retrieve session attributes
    String status = (String) session.getAttribute("login");
    long userId = (long) session.getAttribute("userId");
    String userName = (String) session.getAttribute("userName");
    String userEmail = (String) session.getAttribute("userEmail");
    long userPhoneNum = (long) session.getAttribute("userPhoneNum");
    String userType = (String) session.getAttribute("userType");

    // Redirect to welcome page if not logged in
    if (status == null) {
        response.sendRedirect("index.jsp");
    }
%>

<%--<nav class="d-flex flex-column" id="sidebar">--%>
<nav id="sidebar">
    <a href="<%= request.getContextPath() %>/dashboard/dashboard.jsp?user=${userId}">
        <div id="sidebar-header">
            <% if (userType.equals("student")) { %>
            <h3>Student Dashboard</h3>
            <%} else if (userType.equals("admin")) { %>
            <h3>Admin Dashboard</h3>
            <% }%>
        </div>
    </a>

    <% if (userType.equals("student")) { %>
    <jsp:include page="student-option.jsp" flush="true"/>
    <%} else if (userType.equals("admin")) { %>
    <jsp:include page="admin-option.jsp" flush="true"/>
    <% }%>
</nav>