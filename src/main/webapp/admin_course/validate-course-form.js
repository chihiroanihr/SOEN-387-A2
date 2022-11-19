// [From] validate course form
function validateForm(
  courseCode,
  courseTitle,
  semester,
  daysOfWeek,
  startTime,
  endTime,
  room,
  startDate,
  endDate
) {
  var isValid = true;

  // initialize error messages to none
  document.getElementById("invalidCourseCode").style.display = "none";
  document.getElementById("invalidCourseTitle").style.display = "none";
  document.getElementById("invalidSemester").style.display = "none";
  document.getElementById("invalidDaysOfWeek").style.display = "none";
  document.getElementById("invalidStartTime").style.display = "none";
  document.getElementById("invalidEndTime").style.display = "none";
  document.getElementById("invalidRoom").style.display = "none";
  document.getElementById("invalidStartDate").style.display = "none";
  document.getElementById("invalidEndDate").style.display = "none";

  //------------------ Course Code Verification ------------------//
  if (!courseCode) {
    document.getElementById("invalidCourseCode").style.display = "block";
    document.getElementById("invalidCourseCode").innerHTML =
      "You did not enter your Course Code.";
    isValid = false;
  }
  else{
    var courseIDPattern = /^\d+$/;

    if (courseIDPattern.test(courseCode)) {
      document.getElementById("invalidCourseCode").style.display = "block";
      document.getElementById("invalidCourseCode").innerHTML =
        "Error: Invalid Course Code. It must contain letters, followed by digits.";
      isValid = false;
    }
  }

  //------------------ Course Title Verification ------------------//
  if (!courseTitle) {
    document.getElementById("invalidCourseTitle").style.display = "block";
    document.getElementById("invalidCourseTitle").innerHTML =
      "You did not enter your Course Title.";
    isValid = false;
  }

  //------------------ Semester Select Verification ------------------//
  if (semester == "select") {
    document.getElementById("invalidSemester").style.display = "block";
    document.getElementById("invalidSemester").innerHTML =
      "You did not select the Semester.";
    isValid = false;
  }

  // //------------------ Days (week) Verification ------------------//
  // if (!daysOfWeek == "select") {
  //     document.getElementById("invalidDaysOfWeek").style.display = "block";
  //     document.getElementById("invalidDaysOfWeek").innerHTML = "You did not enter your course Days.";
  //     isValid = false;
  // }

  // //------------------ Start Time Verification ------------------//
  // if (!startTime) {
  //     document.getElementById("invalidStartTime").style.display = "block";
  //     document.getElementById("invalidStartTime").innerHTML = "You did not enter your course Start Time.";
  //     isValid = false;
  // }

  // //------------------ End Time Verification ------------------//
  // if (!endTime) {
  //     document.getElementById("invalidEndTime").style.display = "block";
  //     document.getElementById("invalidEndTime").innerHTML = "You did not enter your course End Time.";
  //     isValid = false;
  // }

  // //------------------ Room Verification ------------------//
  // if (!room) {
  //     document.getElementById("invalidRoom").style.display = "block";
  //     document.getElementById("invalidRoom").innerHTML = "You did not enter your course Room."
  //     isValid = false;
  // }

  // //------------------ Start Date Verification ------------------//
  // if (!startDate) {
  //     document.getElementById("invalidStartDate").style.display = "block";
  //     document.getElementById("invalidStartDate").innerHTML = "You did not enter your course Start Date.";
  //     isValid = false;
  // }

  // //------------------ End Date Verification ------------------//
  // if (!endDate) {
  //     document.getElementById("invalidEndDate").style.display = "block";
  //     document.getElementById("invalidEndDate").innerHTML = "You did not enter your course End Date.";
  //     isValid = false;
  // }

  return isValid;
}
