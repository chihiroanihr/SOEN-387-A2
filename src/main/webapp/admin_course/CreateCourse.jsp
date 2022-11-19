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
    <script src="course-form.js"></script> <!-- create course form necessary functions -->
    <script src="validate-course-form.js"></script> <!-- create course form validation functions -->
    <!-- CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/form.css"> <!-- custom course form Styling -->

    <title>Create Course</title>
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
            Administrator Hub - Create Course
        </p>
    </div>

    <!-- Course Create Form -->
    <%@ include file="course-form.html" %>
</div>

<script>
    $(document).ready(function () {
        // update semester select options dynamically
        addSemestersToSelectOptions();
        // set submit button display value
        $("#submitButton").attr("value", "Add");
        // set request method and its path
        $('#courseForm').attr('action', '<%= request.getContextPath() %>/createCourse');
        $('#courseForm').attr('method', 'POST');

        // ---------------- POST ---------------- //
        $("form").submit(function (event) {
            event.preventDefault();

            const courseCode = $("#courseCode").val().replace(/\s+/g, ''); // remove space
            const courseTitle = $("#courseTitle").val();
            const semester = $("#semester").val();
            const daysOfWeek = JSON.stringify(getDaysOfWeekSelected());
            const startTime = $("#startTime").val();
            const endTime = $("#endTime").val();
            const room = $("#room").val();
            const startDate = $("#startDate").val();
            const endDate = $("#endDate").val();

            if (!validateForm(
                courseCode,
                courseTitle,
                semester,
                daysOfWeek,
                startTime,
                endTime,
                room,
                startDate,
                endDate,
            )) {
                return false;
            }

            const formData = {
                'adminId': ${userId},
                'courseCode': courseCode,
                'courseTitle': courseTitle,
                'semester': semester,
                'daysOfWeek': daysOfWeek,
                'startTime': startTime,
                'endTime': endTime,
                'room': room,
                'startDate': startDate,
                'endDate': endDate,
            };

            $.post({
                url: "<%= request.getContextPath() %>/createCourse",
                data: formData,
            })
                .done(function (status) {
                    if (status === "success") {
                        let output = "<div class='alert alert-success'>You have successfully added a new course.</div>";
                        output += "<hr><div class='px-1'>";
                        output += "<b>Course Code: </b>" + courseCode + "<br>";
                        output += "<b>Course Title: </b>" + courseTitle + "<br>";
                        output += "<b>Semester: </b>" + semester + "<br>";
                        output += "<b>Days: </b>" + daysOfWeek + "<br>";
                        output += "<b>Start Time: </b>" + startTime + "<br>";
                        output += "<b>End Time: </b>" + endTime + "<br>";
                        output += "<b>Room: </b>" + room + "<br>";
                        output += "<b>Start Date: </b>" + startDate + "<br>";
                        output += "<b>End Date: </b>" + endDate + "<br>";
                        output += "</div>";
                        $("#courseForm").replaceWith(output);
                    } else if (status === "error-overlap") {
                        alert("This course's class date/time overlaps with one of the courses you have created. Try entering different class date/time.");
                    } else if (status === "error-registered") {
                        alert("This course is already created by other admin. Please enter different course to create.");
                    } else if (status === "error-user") {
                        $("#formContainer").html(
                            `<div class="alert alert-danger">This ID is not registered as Administrator. Please register yourself as Administrator first.</div>`
                        );
                    } else {
                        $("#formContainer").html(
                            `<div class="alert alert-danger">Failed to add new course due to server side issue. Please try again later.</div>`
                        );
                    }
                })
                .fail(function (xhr, textStatus, errorThrown) {
                    // if server side error
                    $("#formContainer").html(
                        '<div class="alert alert-danger">Could not reach server, please try again later.' +
                        '<br><br>' + xhr.responseText +
                        '<br>' + textStatus +
                        '<br>' + errorThrown + '</div>'
                    );
                });

        });
    });
</script>

</body>

</html>