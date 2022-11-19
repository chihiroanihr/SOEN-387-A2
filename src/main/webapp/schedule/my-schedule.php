<?php

session_start();
if (empty($_SESSION['userID'])) {
    header("location: index.php");
    exit();
}

$userType = $_SESSION['userType'];

?>

<!DOCTYPE html>

<head>
    <!-- import common header -->
    <?php include('header.php') ?>
    <!-- custom styling for schedule -->
    <link rel="stylesheet" href="../assets/css/dashboard.css">
    
    <title>My schedule</title>
</head>

<body>
    <div class="flex">
        <!-- navbar -->
        <?php include('dashboard-navbar.php') ?>

        <div class="wrapper d-flex flex-row" id="dashboard-wrapper">
            <!-- sidebar -->
            <?php
            $userType == 'student' ? include('student-dashboard-sidebar.php') : include('admin-dashboard-sidebar.php');
            ?>

            <!-- Page Content -->
            <?php include('time-table.html') ?>

        </div>
    </div>

</body>

</html>