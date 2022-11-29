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
    <script src="course-table.js"></script> <!-- course table necessary functions -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/helper/validate-search-query.js"></script> <!-- Validate Search Query Functions -->
    <!-- CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/table.css"> <!-- custom course table Styling -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/search-box.css"> <!-- custom search box Styling -->

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
                Administrator Hub - Show List of Courses Taken by a Certain Student
            </p>
        </div>

        <!-- search box -->
        <%@ include file="../helper/search-box.html" %>

        <!-- list of courses by student table -->
        <div class="container px-3" id="studentCourseTableContainer">
            <div class='wrap-table table-responsive mb-5' id='studentCourseTableWrapper'>
                <h3 id="studentName"></h3>
                <p id="studentEmail"></p>
                <table class='table table-striped' id='studentCourseTable'>
                    <thead>
                        <tr>
                            <th scope='col'>#</th>
                            <th scope='col'><strong>Course Code</strong></th>
                            <th scope='col'><strong>Semester</strong></th>
                            <th scope='col'><strong>Admin ID</strong></th>
                            <th scope='col'></th>
                        </tr>
                    </thead>
                    <tbody></tbody>
                </table>
            </div>
            <div class="alert alert-warning m-0" id='alertWarning'></div>
            <div class="alert alert-danger m-0" id='alertDanger'></div>
        </div>
</body>

</html>
<script>
    $(document).ready(function() {
        $("input#searchBox").attr("placeholder", "Search for Student ID");
        document.getElementById("studentCourseTableWrapper").style.display = "none";
        document.getElementById("alertWarning").style.display = "none";
        document.getElementById("alertDanger").style.display = "none";

        // when edit button clicked - put default values in the form
        $(document).on("click", "#searchButton", function() {
            const studentID = $("input#searchBox").val();

            if (!validateSearchQuery(
                studentID
            )) {
                return false;
            }

            $.get({
                    url: "<%= request.getContextPath() %>/getCourseListByStudent?userId=${userId}",
                    data: {
                        'studentId': studentID
                    }
                })
                .done(function(response) {
                    if (response.status === "success") {
                        // Output student info
                        const studentInfo = JSON.parse(response.student);
                        fullName = studentInfo.firstName + " " + studentInfo.lastName;
                        email = studentInfo.email;
                        $("#studentName").text(fullName + " (" + studentID + ")");
                        $("#studentEmail").text(email);

                        if (response.courses === "empty") {
                            document.getElementById("studentCourseTableWrapper").style.display = "none";
                            document.getElementById("alertWarning").style.display = "block";
                            document.getElementById("alertDanger").style.display = "none";
                            $("#alertWarning").html(
                                "This student is not registered in any courses."
                            );
                        } else {
                            courses = JSON.parse(response.courses);
                            console.log(courses);
                            // refresh table
                            $("#studentCourseTable tbody").empty();
                            document.getElementById("studentCourseTableWrapper").style.display = "block";
                            document.getElementById("alertWarning").style.display = "none";
                            document.getElementById("alertDanger").style.display = "none";
                            $.each(courses, function(i, row) { // index, students
                                row = JSON.parse(row);
                                courseInfo = JSON.parse(row.course);
                                profInfo = JSON.parse(row.prof);

                                addCourseToStudentTable(i, courseInfo);
                            });
                        }
                    }
                    // No student info and its corresponding courses found
                    else if (response.status === "empty") {
                            document.getElementById("studentCourseTableWrapper").style.display = "none";
                            document.getElementById("alertWarning").style.display = "block";
                            document.getElementById("alertDanger").style.display = "none";
                            $("#alertWarning").html(
                                "This student ID does not exist."
                            );
                    }
                    // Other errors
                    else if (response.status === "error-user") {
                            document.getElementById("studentCourseTableWrapper").style.display = "none";
                            document.getElementById("alertWarning").style.display = "none";
                            document.getElementById("alertDanger").style.display = "block";
                            $("#alertDanger").html(
                                "This ID is not registered as Administrator. Please register yourself as Administrator first."
                            );
                    } else {
                            document.getElementById("studentCourseTableWrapper").style.display = "none";
                            document.getElementById("alertWarning").style.display = "none";
                            document.getElementById("alertDanger").style.display = "block";
                            $("#alertDanger").html(
                                "annot display list of courses by this student due to server side issue. Please try again later."
                            );
                    }
                    // if (studentInfo = response.studentInfo) {
                    //     fullName = studentInfo.firstName + " " + studentInfo.lastName;
                    //     email = studentInfo.email;
                    //     $("#studentName").text(fullName + " (" + studentID + ")");
                    //     $("#studentEmail").text(email);
                    // }
                    // if (courseList = response.data) {
                    //     $("#studentCourseTable tbody").empty();
                    //     document.getElementById("studentCourseTableWrapper").style.display = "block";
                    //     document.getElementById("alertWarning").style.display = "none";
                    //     document.getElementById("alertDanger").style.display = "none";
                    //     $.each(courseList, function(i, courseRow) { // index, students
                    //         addCourseToStudentTable(i, courseRow);
                    //     });
                    // } else if (response.empty) {
                    //     document.getElementById("studentCourseTableWrapper").style.display = "none";
                    //     document.getElementById("alertWarning").style.display = "block";
                    //     document.getElementById("alertDanger").style.display = "none";
                    //     $("#alertWarning").html(response.empty);
                    // } else {
                    //     document.getElementById("studentCourseTableWrapper").style.display = "none";
                    //     document.getElementById("alertWarning").style.display = "none";
                    //     document.getElementById("alertDanger").style.display = "block";
                    //     $("#alertDanger").html(response.error);
                    // }
                })
                .fail(function(xhr, textStatus, errorThrown) {
                    // if server side error
                    $("#studentCourseTableContainer").html(
                        '<div class="alert alert-danger">Could not reach server, please try again later.' +
                        '<br><br>' + xhr.responseText +
                        '<br>' + textStatus +
                        '<br>' + errorThrown + '</div>'
                    );
                });
        });
    });
</script>