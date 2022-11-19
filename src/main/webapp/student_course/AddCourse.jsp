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
    <script src="course-table.js"></script> <!-- enroll course table necessary functions -->
    <script src="${pageContext.request.contextPath}/helper/table-utils.js"></script> <!-- course table utility functions -->
    <!-- CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/table.css"> <!-- custom course table Styling -->

    <title>Add Course</title>
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
            Student Hub - Add Course
        </p>
    </div>

    <div class="container px-3 mb-5" id='enrolledCourseTableContainer'>
        <h3>Courses Enrolled</h3>
        <div class="wrap-table table-responsive" id="enrolledCourseTableWrapper">
            <table class="table" id='enrolledCourseTable'>
                <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col"><strong>Course Code</strong></th>
                    <th scope="col"><strong>Course Title</strong></th>
                    <th scope="col"><strong>Semester</strong></th>
                    <th scope="col"><strong>Course Days</strong></th>
                    <th scope="col"><strong>Start Time</strong></th>
                    <th scope="col"><strong>End Time</strong></th>
                    <th scope="col"><strong>Room</strong></th>
                    <th scope="col"><strong>Start Date</strong></th>
                    <th scope="col"><strong>End Date</strong></th>
                    <th scope="col"><strong>Professor</strong></th>
                </tr>
                </thead>
                <tbody></tbody>
            </table>
        </div>
    </div>

    <div class="container px-3 mb-5" id='availableCourseTableContainer'>
        <h3>Available Courses</h3>
        <span id="errorMessage" class="text-danger"></span>
        <div class="wrap-table table-responsive" id="availableCourseTableWrapper">
            <table class="table" id='availableCourseTable'>
                <caption class="mt-2">Click on the "Enroll" button to add courses to your course list</caption>
                <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col"><strong>Course Code</strong></th>
                    <th scope="col"><strong>Course Title</strong></th>
                    <th scope="col"><strong>Semester</strong></th>
                    <th scope="col"><strong>Course Days</strong></th>
                    <th scope="col"><strong>Start Time</strong></th>
                    <th scope="col"><strong>End Time</strong></th>
                    <th scope="col"><strong>Room</strong></th>
                    <th scope="col"><strong>Start Date</strong></th>
                    <th scope="col"><strong>End Date</strong></th>
                    <th scope="col"><strong>Professor</strong></th>
                    <th scope="col"></th>
                </tr>
                </thead>
                <tbody></tbody>
            </table>
        </div>
    </div>

    <!-- Course Enroll confirmation popup -->
    <%@ include file="../helper/popup.html" %>
</div>

<script>
    $(document).ready(function () {
        // ---------------- GET ---------------- //
        $.get({
            url: '<%= request.getContextPath() %>/addCourse?userId=${userId}'
        })
            .done(function (response) {
                // (1) Get/Fetch all courses enrolled by student into the table
                const coursesByStudent = JSON.parse(response.coursesByStudent);
                if (coursesByStudent.status === "success") {
                    const courses = JSON.parse(coursesByStudent.courses);
                    $.each(courses, function (i, row) {
                        addCourseToEnrollTable(i, row);
                    });
                } else if (coursesByStudent.status === "empty") {
                    $("#enrolledCourseTableWrapper").html(
                        '<div class="alert alert-warning">You have no courses enrolled yet.</div>'
                    );
                } else {
                    $("#enrolledCourseTableWrapper").html(
                        '<div class="alert alert-danger">Cannot display courses due to issues with server. Please try again later.</div>'
                    );
                }

                // (2) Get/Fetch all courses available for (not registered by) student into the table
                const coursesForStudent = JSON.parse(response.coursesForStudent);
                if (coursesForStudent.status === "success") {
                    const courses = JSON.parse(coursesForStudent.courses);
                    $.each(courses, function (i, row) {
                        addCourseToAvailableTable(i, row);
                    });
                } else if (coursesForStudent.status === "empty") {
                    $("#availableCourseTableWrapper").html(
                        '<div class="alert alert-warning">You have no available courses left.</div>'
                    );
                } else {
                    $("#availableCourseTableWrapper").html(
                        '<div class="alert alert-danger">Cannot display courses due to issues with server. Please try again later.</div>'
                    );
                }

                // Disable course enrollment if maximum of 5 is reached
                const maxRow = $('#enrolledCourseTable tbody tr').length;
                if (maxRow == 5) {
                    const tableRows = document.querySelectorAll("#availableCourseTable tbody > tr");
                    tableRows.forEach(tr => {
                        tr.classList.toggle("table-disabled", tr);
                    });
                    $('button#enrollButton').prop('disabled', true);
                    $("#errorMessage").text("You have reached maximum courses to enroll.");
                }

            })
            .fail(function (xhr, textStatus, errorThrown) {
                // if server side error
                $("#availableCourseTableWrapper").html(
                    '<div class="alert alert-danger">Could not reach server, please try again later.' +
                    '<br><br>' + xhr.responseText +
                    '<br>' + textStatus +
                    '<br>' + errorThrown + '</div>'
                );
            });

        // when enroll button in the table row clicked
        $(document).on("click", "#enrollButton", function () {
            const currentRow = $(this).closest("tr");
            const courseCode = currentRow.find("td:eq(0)").html();
            const courseTitle = currentRow.find("td:eq(1)").html();
            const semester = currentRow.find("td:eq(2)").html();
            const daysOfWeek = currentRow.find("td:eq(3)").html();
            const startTime = convertTimeTo24Hclock(currentRow.find("td:eq(4)").html());
            const endTime = convertTimeTo24Hclock(currentRow.find("td:eq(5)").html());
            const startDate = currentRow.find("td:eq(7)").html();
            const endDate = currentRow.find("td:eq(8)").html();
            const adminID = currentRow.find("td:eq(9)").attr('id');
            const professor = currentRow.find("td:eq(9)").html().split("<br>")[0];

            // data to be sent to post request for deletion
            const data = JSON.stringify({
                'courseCode': courseCode,
                'courseTitle': courseTitle,
                'semester': semester,
                'daysOfWeek': daysOfWeek,
                'startTime': startTime,
                'endTime': endTime,
                'startDate': startDate,
                'endDate': endDate,
                'adminID': adminID
            });

            // change popup inner html
            const modalTitle = "Enrollement Confirmation";
            const modalBody = "You are going to register " + courseCode + " (" + semester + ") by " + professor + "<br>to your course list.";

            $(".modal-title").html(modalTitle);
            $(".modal-body").html(modalBody);

            $("#modalSubmitButton").attr("value", "Yes, enroll me in this course");
            $("#modalSubmitButton").attr("data-id", data);

            $("#modalSubmitButton").attr("style", "width: unset;");
        });

        // when enroll button in the popup/modal clicked
        $(document).on("click", "#modalSubmitButton", function () {
            const data = JSON.parse($(this).attr('data-id'));

            $.post({
                url: '<%= request.getContextPath() %>/addCourse?userId=${userId}',
                data: data
            }).done(function (status) {
                if (status === "success") {
                    // reload table
                    location.reload();
                }
                else {
                    $("#availableCourseTable").html(
                        '<div class="alert alert-danger">Failed to enroll to the course due to server side issue. Please try again later.</div>'
                    );
                }
            }).fail(function (xhr, textStatus, errorThrown) {
                // if server side error
                $("#availableCourseTableWrapper").html(
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