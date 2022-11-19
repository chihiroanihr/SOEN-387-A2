<nav class="navbar navbar-light sticky-top">
    <div class="container-fluid">
        <a class="navbar-brand" href="<%= request.getContextPath() %>/dashboard/dashboard.jsp?user=${userId}">
            <img src="${pageContext.request.contextPath}/assets/images/school_logo.png" alt="Dashboard School Logo"
                 class="d-inline-block align-text-center">
            RIVERSIDE ACADEMY
        </a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarToggler"
                aria-controls="navbarToggler" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="navbar-collapse justify-content-end collapse" id="navbarToggler">
            <% if (userType.equals("student")) { %>
            <jsp:include page="student-option.jsp" flush="true"/>
            <%} else if (userType.equals("admin")) { %>
            <jsp:include page="admin-option.jsp" flush="true"/>
            <% }%>
        </div>
    </div>
</nav>