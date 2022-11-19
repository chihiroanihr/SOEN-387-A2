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
    <script src="course-form.js"></script> <!-- edit course form necessary functions -->
    <script src="validate-course-form.js"></script> <!-- edit course form validation functions -->
    <script src="course-table.js"></script> <!-- edit course table necessary functions -->
    <script src="${pageContext.request.contextPath}/helper/table-utils.js"></script>
    <!-- course table utility functions -->
    <!-- CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/form.css">
    <!-- custom course form Styling -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/table.css">
    <!-- custom course table Styling -->

    <title>Edit Course</title>
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
            Administrator Hub - Edit Course
        </p>
    </div>

    <div class="container px-3" id='courseTableContainer'>
        <div class="wrap-table table-responsive" id="courseTableWrapper">
            <table class="table" id='courseTable'>
                <caption class='mt-2'>Click on the "Edit" button to apply changes</caption>
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
                    <th scope="col"></th>
                </tr>
                </thead>
                <tbody></tbody>
            </table>
        </div>
    </div>

    <!-- Course Edit Form -->
    <%@ include file="course-form.html" %>
</div>

<script>
    $(document).ready(function () {
        // show table and hide edit form in the beginning
        document.getElementById("formContainer").style.display = "none";

        // ---------------- GET ---------------- //
        $.get({
            url: '<%= request.getContextPath() %>/editCourse?userId=${userId}',
        }).done(function (response) {
            if (response.status === "success") {
                // parse object data
                courses = JSON.parse(response.data);
                $.each(courses, function (i, row) {
                    addCourseToEditTable(i, row);
                });
            } else if (response.status === "empty") {
                $("#courseTableWrapper").html(
                    '<div class="alert alert-warning">' + response.empty + '</div>'
                );
            } else if (response.status === "error-user") {
                $("#courseTableWrapper").html(
                    `<div class="alert alert-danger">This ID is not registered as Administrator. Please register yourself as Administrator first.</div>`
                );
            }
            else {
                $("#courseTableWrapper").html(
                    '<div class="alert alert-danger">Cannot display courses due to issues with server. Please try again later.'
                );
            }
        }).fail(function (xhr, textStatus, errorThrown) {
            // if server side error
            $("#courseTableWrapper").html(
                '<div class="alert alert-danger">Could not reach server, please try again later.' +
                '<br><br>' + xhr.responseText +
                '<br>' + textStatus +
                '<br>' + errorThrown + '</div>'
            );
        });

        // when edit button clicked - put default values in the form
        $(document).on("click", "#editButton", function () {
            let currentRow = $(this).closest("tr");
            let courseCode = currentRow.find("td:eq(0)").html();
            let courseTitle = currentRow.find("td:eq(1)").html();
            let semester = currentRow.find("td:eq(2)").html();
            let daysOfWeek = currentRow.find("td:eq(3)").html();
            let startTime = currentRow.find("td:eq(4)").html() ? convertTimeTo24Hclock(currentRow.find("td:eq(4)").html()) : '';
            let endTime = currentRow.find("td:eq(5)").html() ? convertTimeTo24Hclock(currentRow.find("td:eq(5)").html()) : '';
            let room = currentRow.find("td:eq(6)").html();
            let startDate = currentRow.find("td:eq(7)").html();
            let endDate = currentRow.find("td:eq(8)").html();

            // show edit form and hide table
            document.getElementById("courseTableContainer").style.display = "none";
            document.getElementById("formContainer").style.display = "block";

            // update semester select options dynamically
            addSemestersToSelectOptions();

            // change 'go back' href attribute
            $("a[href]").attr("href", "EditCourse.jsp");

            // place the default attributes to form
            $("input#courseCode").val(courseCode);
            $("input#courseCode").attr('disabled', true);
            $("input#courseTitle").val(courseTitle);
            $("select#semester").val(semester);
            $("select#semester").attr('disabled', true);
            daysOfWeek = daysOfWeek.split('<br>')
                .filter((item) => item)
                .map(element => {
                    return element.toLowerCase();
                });
            // remove empty element and lowercase them to match checkbox id value
            $.each(daysOfWeek, function (i, value) {
                $("input#" + value).attr('checked', true);
            })
            $("input#startTime").val(startTime);
            $("input#endTime").val(endTime);
            $("input#room").val(room);
            $("input#startDate").val(startDate);
            $("input#endDate").val(endDate);

            // set submit button display value
            $("#submitButton").attr("value", "Update");
            // set request path
            $('#courseForm').attr('action', '<%= request.getContextPath() %>/editCourse');
            $('#courseForm').attr('method', 'POST');
        });

        // ---------------- POST ---------------- //
        $("form").submit(function (event) {
            event.preventDefault();

            const courseCode = $("input#courseCode").val().replace(/\s+/g, ''); // remove space
            const courseTitle = $("input#courseTitle").val();
            const semester = $("select#semester").val();
            const daysOfWeek = JSON.stringify(getDaysOfWeekSelected());
            const startTime = $("input#startTime").val();
            const endTime = $("input#endTime").val();
            const room = $("input#room").val();
            const startDate = $("input#startDate").val();
            const endDate = $("input#endDate").val();

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
                url: "<%= request.getContextPath() %>/editCourse",
                data: formData
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