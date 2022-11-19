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

    <title>Admin Registration</title>
</head>

<body>
<div class="container-fluid">
    <a href="${pageContext.request.contextPath}/index.jsp">Go Back</a>
</div>

<div class="mt-5 mb-5">
    <div class="container-fluid text-center" id="headingWrapper">
        <p id="heading">
            Admin Registration Form
        </p>
    </div>
    <!-- Registration Form -->
    <%@ include file="registration-form.jsp" %>
</div>

<script>
    const userType = 'admin';

    $(document).ready(function () {
        // set path for post request
        $('#registrationForm').attr('action', '<%= request.getContextPath() %>/register');
        // change some attributes inside the registration form
        $("label.form-label#userID").html('Admin ID');
        $("input#userID.form-control").attr('placeholder', 'Enter Admin ID');

        $("form").submit(function (event) {
            event.preventDefault();

            const adminID = $("input#userID").val();
            const adminFirstName = $("input#userFirstName").val();
            const adminLastName = $("input#userLastName").val();
            const adminDOB = $("input#userDOB").val();
            const adminAddress = $("input#userAddress").val();
            const adminCity = $("input#userCity").val();
            const adminCountry = $("select#userCountry").val();
            const adminPostal = $("input#userPostal").val();
            const adminPhone = $("input#userPhone").val();
            const adminEmail = $("input#userEmail").val();

            if (!validateForm(
                userType,
                adminID,
                adminFirstName,
                adminLastName,
                adminDOB,
                adminAddress,
                adminCity,
                adminCountry,
                adminPostal,
                adminPhone,
                adminEmail
            )) {
                return false;
            }

            const formData = {
                'userID': adminID,
                'userFirstName': adminFirstName,
                'userLastName': adminLastName,
                'userDOB': adminDOB,
                'userAddress': adminAddress,
                'userCity': adminCity,
                'userCountry': adminCountry,
                'userPostal': adminPostal,
                'userPhone': adminPhone,
                'userEmail': adminEmail,
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