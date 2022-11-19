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

<!DOCTYPE html>
<html>
<head>
    <!-- import common header -->
    <%@ include file="../header.jsp" %>
    <!-- CSS: custom styling for dashboard -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/dashboard.css">

    <title>Dashboard</title>
</head>

<body>
<div class="flex">
    <!-- navbar -->
    <%@ include file="dashboard-navbar.jsp" %>

    <div class="wrapper d-flex flex-row" id="dashboard-wrapper">
        <!-- sidebar -->
        <jsp:include page="dashboard-sidebar.jsp" flush="true"/>

        <!-- Page Content -->
        <% if (userType.equals("student")) { %>
        <jsp:include page="student-dashboard-content.jsp" flush="true"/>
        <%} else if (userType.equals("admin")) { %>
        <jsp:include page="admin-dashboard-content.jsp" flush="true"/>
        <% }%>
    </div>
</div>
</body>

<script>
    // show or hide sidebar based on viewport size
    $(window).on('resize', function () {
        if (window.innerWidth < 576) {
            $("#sidebar").hide();
        } else {
            $("#sidebar").show();
        }
    });
</script>
</html>