<?php
include("../../../path.php");

// define paths to import / redirect
$headerPath = APP_INCLUDES_PATH . '/header.php'; // include
$dashboardPath = APP_VIEW_URL . '/dashboard/dashboard.php';
$htmlSchedulePath = APP_VIEW_PATH . '/schedule/schedule.html'; // include
$controllerGetSchedulePath = APP_CONTROLLER_URL . '/getSchedule.php';

session_start();
error_reporting(0);
if (empty($_SESSION['userID'])) {
    header("location: index.php");
    exit();
}

$userID = $_SESSION['userID'];

?>

<!DOCTYPE html>

<head>
    <!-- Import common header -->
    <?php include($headerPath); ?>

    <!-- TOAST UI plugins -->
    <link rel="stylesheet" href="https://uicdn.toast.com/calendar/latest/toastui-calendar.min.css" />
    <script src="https://uicdn.toast.com/calendar/latest/toastui-calendar.min.js"></script>

    <link rel="stylesheet" href="../../../plugins/schedule-template-master/assets/css/style.css">

    <script>
        const Calendar = tui.Calendar;
    </script>


    <title>My Schedule</title>
</head>

<body>
    <div class="container-fluid">
        <a href="<?php echo ($dashboardPath); ?>?user=<?php echo $userID; ?>">
            Go Back
        </a>
    </div>

    <div class="mt-5 mb-5">
        <div class="container-fluid text-center" id="headingWrapper">
            <p id="heading">
                FACULTY SCHEDULE
            </p>
        </div>

        <!-- Faculty Schedule Table -->
        <?php include($htmlSchedulePath); ?>
    </div>

    <script>
        $(document).ready(function() {});
    </script>

</body>

</html>