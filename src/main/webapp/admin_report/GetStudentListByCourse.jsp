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
    <!-- Import common header -->
    <%@ include file="../header.jsp" %>
    <!-- JS -->
    <script src="student-table.js"></script> <!-- course table necessary functions -->
    <!-- CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/table.css"><!-- custom course edit table Styling -->

    <title>Students List</title>
</head>

<body>
<div class="container-fluid">
    <a href="<%= request.getContextPath() %>/dashboard/dashboard.jsp?user=${userId}">
        Go Back
    </a>
</div>

<div class="mt-5 mb-5">
    <div class="container-fluid text-center" id="headingWrapper">
        <p id="heading">
            Administrator Hub - Show List of Students Enrolled in a Certain Course
        </p>
    </div>

    <div class="container px-3" id='studentTableContainer'>
        <!-- table goes here -->
    </div>
</div>

<script>
    $(document).ready(function () {
        $.get({
            url: '<%= request.getContextPath() %>/getStudentListByCourse?userId=${userId}'
        })
            .done(function (response) {
                if (response.status === "error-user") {
                    $("#studentTableContainer").html(
                        `<div class="alert alert-danger">This ID is not registered as Administrator. Please register yourself as Administrator first.</div>`
                    );
                } else if (response.courses === "empty") {
                    $("#studentTableContainer").html(
                        '<div class="alert alert-warning">You have no courses created yet.</div>'
                    );
                } else {
                    courses = JSON.parse(response.courses);
                    $.each(courses, function (i, row) { // index, courses
                        courseRow = JSON.parse(row);

                        courseInfo = JSON.parse(courseRow.course);
                        makeTable(i, courseInfo);

                        if (courseRow.students === "empty") {
                            $("#wrapStudentTable-" + i).html(
                                '<div class="alert alert-warning m-0">You have no students registered in this course.</div>'
                            );
                        } else {
                            students = JSON.parse(courseRow.students);
                            $.each(students, function (j, studentInfo) { // index, students
                                addStudentToTable(i, j, studentInfo);
                            });
                        }
                    });
                }
            })
            .fail(function (xhr, textStatus, errorThrown) {
                // if server side error
                $("#studentTableContainer").html(
                    '<div class="alert alert-danger">Could not reach server, please try again later.' +
                    '<br><br>' + xhr.responseText +
                    '<br>' + textStatus +
                    '<br>' + errorThrown + '</div>'
                );
            });
    });
</script>

</body>

</html>