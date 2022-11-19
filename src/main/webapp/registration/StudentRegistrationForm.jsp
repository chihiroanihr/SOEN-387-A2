<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <!-- import common header -->
    <%@ include file="../header.jsp" %>
    <!-- Custom Registration Form Styling -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/form.css">
    <!-- JS Validate Registration Form -->
    <script src="${pageContext.request.contextPath}/registration/validate-registration-form.js"></script>

    <title>Student Registration</title>
</head>

<body>
<div class="container-fluid">
    <a href="${pageContext.request.contextPath}/index.jsp">Go Back</a>
</div>

<div class="mt-5 mb-5">
    <div class="container-fluid text-center" id="headingWrapper">
        <p id="heading">
            Student Registration Form
        </p>
    </div>
    <!-- Registration Form -->
    <%@ include file="registration-form.jsp" %>
</div>

<script>
    const userType = 'student';

    $(document).ready(function () {
        // set path for post request
        $('#registrationForm').attr('action', '<%= request.getContextPath() %>/register');
        // change some attributes inside the registration form
        $("label.form-label#userID").html('Student ID');
        $("input#userID.form-control").attr('placeholder', 'Enter Student ID');

        $("form").submit(function (event) {
            event.preventDefault();

            const studentID = $("input#userID").val();
            const studentFirstName = $("input#userFirstName").val();
            const studentLastName = $("input#userLastName").val();
            const studentDOB = $("input#userDOB").val();
            const studentAddress = $("input#userAddress").val();
            const studentCity = $("input#userCity").val();
            const studentCountry = $("select#userCountry").val();
            const studentPostal = $("input#userPostal").val();
            const studentPhone = $("input#userPhone").val();
            const studentEmail = $("input#userEmail").val();

            if (!validateForm(
                userType,
                studentID,
                studentFirstName,
                studentLastName,
                studentDOB,
                studentAddress,
                studentCity,
                studentCountry,
                studentPostal,
                studentPhone,
                studentEmail
            )) {
                return false;
            }

            const formData = {
                'userID': studentID,
                'userFirstName': studentFirstName,
                'userLastName': studentLastName,
                'userDOB': studentDOB,
                'userAddress': studentAddress,
                'userCity': studentCity,
                'userCountry': studentCountry,
                'userPostal': studentPostal,
                'userPhone': studentPhone,
                'userEmail': studentEmail,
                'userType': userType
            };

            $.post({
                url: "<%= request.getContextPath() %>/register",
                data: formData,
            })
                .done(function (data) {
                    if (data.status === 'success') {
                        $("#formContainer").html(
                            "<div class='alert alert-success'>Registration Successful!</div>" +
                            "<div>Your temporary password is: <b>" + data.password + "</b>" +
                            "<br> You may proceed to change your password once you log in." +
                            "</div>"
                        );
                    } else if (data.status === 'error-registered') {
                        alert("This user is already registered.");
                    } else {
                        $("#formContainer").html(
                            '<div class="alert alert-danger">'
                            + 'Registration failed due to server side issue. Please try again later.'
                            + '</div>'
                        );
                    }
                })
                .fail(function (xhr, textStatus, errorThrown) {
                    // if server side error
                    $("#formContainer").html(
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

</body>

</html>