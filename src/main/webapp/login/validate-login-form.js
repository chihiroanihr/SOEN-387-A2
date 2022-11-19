function validateLogin(userID, userPassword) {
  let isValid = true;

  // default: do not display validation error message
  document.getElementById("errorMessage").style.display = "none";
  document.getElementById("invalidID").style.display = "none";
  document.getElementById("invalidPassword").style.display = "none";

  // validate ID
  if (userID === "") {
    document.getElementById("invalidID").style.display = "block";
    document.getElementById("invalidID").innerHTML =
      userType === "student"
        ? "Enter your student ID."
        : "Enter you Employee ID.";
    isValid = false;
  } else if (isNaN(userID)) {
    document.getElementById("invalidID").style.display = "block";
    document.getElementById("invalidID").innerHTML =
      userType === "student"
        ? "Student ID must only contain digits."
        : "Employee ID must only contain digits.";
    isValid = false;
  }

  // validate password
  if (userPassword === "") {
    document.getElementById("invalidPassword").style.display = "block";
    document.getElementById("invalidPassword").innerHTML =
      "Enter your password.";
    isValid = false;
  }

  return isValid;
}
