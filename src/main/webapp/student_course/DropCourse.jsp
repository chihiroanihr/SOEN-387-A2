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

    <title>Drop Course</title>
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
                Student Hub - Drop Course
            </p>
        </div>

        <div class="container px-3 mb-5" id='enrolledCourseTableContainer'>
            <div class="wrap-table table-responsive" id="enrolledCourseTableWrapper">
                <table class="table" id='enrolledCourseTable'>
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
                            <th scope="col"><strong>Professor</strong></th>
                            <th scope="col"></th>
                        </tr>
                    </thead>
                    <tbody></tbody>
                </table>
            </div>
        </div>

        <!-- Course Drop confirmation popup -->
        <%@ include file="../helper/popup.html" %>
    </div>

    <script>
        $(document).ready(function() {
            // ---------------- GET ---------------- //
            $.get({
                url: '<%= request.getContextPath() %>/dropCourse?userId=${userId}'
            }).done(function(response) {
                if (response.status === "success") {
                    const courses = JSON.parse(response.courses);
                    $.each(courses, function (i, row) {
                        addCourseToDropTable(i, row);
                    });
                } else if (response.status === "empty") {
                    $("#enrolledCourseTableWrapper").html(
                        '<div class="alert alert-warning">You have no courses enrolled yet.</div>'
                    );
                } else {
                    $("#enrolledCourseTableWrapper").html(
                        '<div class="alert alert-danger">Cannot display courses due to issues with server. Please try again later.</div>'
                    );
                }
            }).fail(function(xhr, textStatus, errorThrown) {
                // if server side error
                $("#enrolledCourseTableWrapper").html(
                    '<div class="alert alert-danger">Could not reach server, please try again later.' +
                    '<br><br>' + xhr.responseText +
                    '<br>' + textStatus +
                    '<br>' + errorThrown + '</div>'
                );
            });

            // when enroll button in the table row clicked
            $(document).on("click", "#dropButton", function() {
                const currentRow = $(this).closest("tr");
                const courseCode = currentRow.find("td:eq(0)").html();
                const semester = currentRow.find("td:eq(2)").html();
                const adminID = currentRow.find("td:eq(9)").attr('id');
                const professor = currentRow.find("td:eq(9)").html().split("<br>")[0];

                // data to be sent to post request for deletion
                const data = JSON.stringify({
                    'courseCode': courseCode,
                    'semester': semester,
                    'adminID': adminID
                });

                // change popup inner html
                const modalTitle = "You're about to drop a course " + courseCode + " (" + semester + ") by " + professor;
                const modalBody = "<div class='alert alert-danger d-flex align-items-center' role='alert'>" +
                    "<svg class='bi flex-shrink-0 me-3' width='24' height='24' role='img' aria-label='Danger:'><use xlink:href='${pageContext.request.contextPath}/assets/svg/alert-icon.svg#exclamation-triangle-fill'/></svg>" +
                    "<div>All your course information will be permanently removed and you won't be able to see them again. Are you sure you want to delete this course?</div>" +
                    "</div>";

                $(".modal-title").html(modalTitle);
                $(".modal-body").html(modalBody);

                $("#modalSubmitButton").attr("value", "Drop this course");
                $("#modalSubmitButton").attr("data-id", data);
            });

            // when enroll button in the popup/modal clicked
            $(document).on("click", "#modalSubmitButton", function() {
                const data = JSON.parse($(this).attr('data-id'));

                $.post({
                    url: '<%= request.getContextPath() %>/dropCourse?userId=${userId}',
                    data: data
                }).done(function(status) {
                    if (status === "success") {
                        // reload table
                        location.reload();
                    } else {
                        $("#enrolledCourseTable").html(
                            '<div class="alert alert-danger">Failed to drop the course due to server side issue. Please try again later.</div>'
                        );
                    }
                }).fail(function(xhr, textStatus, errorThrown) {
                    // if server side error
                    $("#enrolledCourseTableWrapper").html(
                        '<div class="alert alert-danger">Could not reach server, please try again later.' +
                        '<br><br>' + xhr.responseText +
                        '<br>' + textStatus +
                        '<br>' + errorThrown + '</div>'
                    );
                });;

            });
        });
    </script>

</body>

</html>