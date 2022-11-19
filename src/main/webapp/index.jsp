<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <!-- import common header -->
    <%@ include file="header.jsp" %>
    <!-- CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/index.css"> <!-- Index Page Styling -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/login-modal.css"> <!-- Custom Login Modal Styling -->
    <!-- JS -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/login/validate-login-form.js"></script> <!-- Validate Login Form Functions -->

    <title>Home Page</title>
</head>

<body>
<div class="container-fluid">
    <img src="assets/images/school_logo.png" alt="School Logo" class="mx-auto d-block img-fluid" width="350">
    <div class="container">
        <div class="d-flex flex-column p-3 gap-3">
            <button type="button" id="studentButton" data-id="student" data-toggle="modal" data-target="#loginModal">I'm
                a Student
            </button>
            <button type="button" id="adminButton" data-id="admin" data-toggle="modal" data-target="#loginModal">I'm an
                Administrator
            </button>
        </div>
    </div>
</div>
<%@ include file="login/login-modal.jsp" %>
</body>

<!-- Redirect to Student Login Page after button click -->
<script>
    let userType = ''; // student or admin

    $(document).on("click", "#studentButton", function () {
        userType = $(this).data('id');
        $("#userID").attr("placeholder", "Student ID");
        $('#userRegister').attr('href', 'registration/StudentRegistrationForm.jsp')
    });

    $(document).on("click", "#adminButton", function () {
        userType = $(this).data('id');
        $("#userID").attr("placeholder", "Employee ID");
        $('#userRegister').attr('href', 'registration/AdminRegistrationForm.jsp')
    });

    $(document).ready(function () {
        $("form").submit(function (event) {
            event.preventDefault();

            const userID = $("#userID").val();
            const userPassword = $("#userPassword").val();

            if (!validateLogin(
                userID,
                userPassword
            )) {
                return false;
            }

            const formData = {
                'userID': userID,
                'userPassword': userPassword,
                'userType': userType
            };

            $.post({
                data: formData,
                url: "<%= request.getContextPath() %>/login"
            })
                .done(function (status) {
                    // if login info invalid
                    if (status === "error") {
                        const message = userType == "student"
                            ? "Student ID or Password is incorrect. Type the correct Student ID and password, and try again."
                            : "Admin ID or Password is incorrect. Type the correct Admin ID and password, and try again.";
                        document.getElementById("errorMessage").style.display = "block";
                        document.getElementById("errorMessage").innerHTML = message;
                    }
                    // if successful login - redirect to dashboard
                    else if (status === "success") {
                        window.location.href = "<%= request.getContextPath() %>/dashboard/dashboard.jsp?user=" + userID;
                    }
                })
                .fail(function (xhr, textStatus, errorThrown) {
                    // if server side error
                    $("#loginForm").html(
                        '<div class="alert alert-danger">Could not reach server, please try again later.'
                        // + '<br><br>' + xhr.responseText
                        // + '<br>' + textStatus
                        // + '<br>' + errorThrown
                        + '</div>'
                    );
                });

        });
    });
</script>

</html>