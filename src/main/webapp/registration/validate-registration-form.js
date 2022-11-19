// test for empty fields
function validateForm(
  userType,
  userID,
  userFirstName,
  userLastName,
  userDOB,
  userAddress,
  userCity,
  userCountry,
  userPostal,
  userPhone,
  userEmail
) {
  var isValid = true;

  // initialize error messages to none
  document.getElementById("invalidID").style.display = "none";
  document.getElementById("invalidFirstName").style.display = "none";
  document.getElementById("invalidLastName").style.display = "none";
  document.getElementById("invalidDOB").style.display = "none";
  document.getElementById("invalidAddress").style.display = "none";
  document.getElementById("invalidCity").style.display = "none";
  document.getElementById("invalidCountry").style.display = "none";
  document.getElementById("invalidPostal").style.display = "none";
  document.getElementById("invalidPhone").style.display = "none";
  document.getElementById("invalidEmail").style.display = "none";

  //------------------ user ID Verification ------------------//
  if (!userID) {
    document.getElementById("invalidID").style.display = "block";
    document.getElementById("invalidID").innerHTML =
      userType == "student"
        ? "You did not enter your Student ID."
        : "You did not enter your Admin ID.";
    isValid = false;
  }
  //check if user ID contains only numbers, if not, returns false
  else if (isNaN(userID)) {
    document.getElementById("invalidID").style.display = "block";
    document.getElementById("invalidID").innerHTML =
      userType == "student"
        ? "Student ID must only contain 8 digits."
        : "Admin ID must only contain 8 digits.";
    isValid = false;
  } else {
    //length of user ID
    var idLength = userID.length;
    //check if user ID contains exactly 8 digits.
    if (idLength != 8) {
      document.getElementById("invalidID").style.display = "block";
      document.getElementById("invalidID").innerHTML =
        userType == "student"
          ? "Student ID must only contain 8 digits."
          : "Admin ID must only contain 8 digits.";
      isValid = false;
    }
  }

  //------------------ user First Name Verification ------------------//
  if (!userFirstName) {
    document.getElementById("invalidFirstName").style.display = "block";
    document.getElementById("invalidFirstName").innerHTML =
      "You did not enter your first name.";
    isValid = false;
  }
  //check if user's First Name contains only characters, if not, display error message
  else if (!isNaN(userFirstName)) {
    document.getElementById("invalidFirstName").style.display = "block";
    document.getElementById("invalidFirstName").innerHTML =
      "First Name must contain only characters.";
    isValid = false;
  }

  //------------------ user Last Name Verification ------------------//
  if (!userLastName) {
    document.getElementById("invalidLastName").style.display = "block";
    document.getElementById("invalidLastName").innerHTML =
      "You did not enter your last name.";
    isValid = false;
  }
  //check if user's Last Name contains only characters, if not, display error message
  else if (!isNaN(userLastName)) {
    document.getElementById("invalidLastName").style.display = "block";
    document.getElementById("invalidLastName").innerHTML =
      "Last Name must contain only characters.";
    isValid = false;
  }

  //------------------ user DOB Verification ------------------//
  if (!userDOB) {
    document.getElementById("invalidDOB").style.display = "block";
    document.getElementById("invalidDOB").innerHTML =
      "You did not enter your Date of Birth.";
    isValid = false;
  }
  // else {
  //   //check if user's DOB is valid
  //   if (!isNaN(userDOB)) {
  //     document.getElementById("invalidDOB").innerHTML = "Error: You entered an inisValid date.";
  //     isValid = false;
  //   }
  // }

  //------------------ user Address Verification ------------------//
  if (!userAddress) {
    document.getElementById("invalidAddress").style.display = "block";
    document.getElementById("invalidAddress").innerHTML =
      "You did not enter your address.";
    isValid = false;
  }

  //------------------ user City Verification ------------------//
  if (!userCity) {
    document.getElementById("invalidCity").style.display = "block";
    document.getElementById("invalidCity").innerHTML =
      "You did not enter your city.";
    isValid = false;
  } else if (!isNaN(userCity)) {
    document.getElementById("invalidCity").style.display = "block";
    document.getElementById("invalidCity").innerHTML =
      "Enter a isValid city name.";
    isValid = false;
  }

  //------------------ user Country Verification ------------------//
  if (userCountry == "select") {
    document.getElementById("invalidCountry").style.display = "block";
    document.getElementById("invalidCountry").innerHTML =
      "You did not select a country.";
    isValid = false;
  }

  //------------------ user Postal Verification ------------------//
  if (!userPostal) {
    document.getElementById("invalidPostal").style.display = "block";
    document.getElementById("invalidPostal").innerHTML =
      "You did not enter your postal code.";
    isValid = false;
  }

  //------------------ user Phone Verification ------------------//
  if (!userPhone) {
    document.getElementById("invalidPhone").style.display = "block";
    document.getElementById("invalidPhone").innerHTML =
      "You did not enter your phone number.";
    isValid = false;
  } else if (isNaN(userPhone)) {
    document.getElementById("invalidPhone").style.display = "block";
    document.getElementById("invalidPhone").innerHTML =
      "Phone number must only contain digits.";
    isValid = false;
  }

  //------------------ user Email Verification ------------------//
  if (!userEmail) {
    document.getElementById("invalidEmail").style.display = "block";
    document.getElementById("invalidEmail").innerHTML =
      "You did not enter your email.";
    isValid = false;
  } else {
    var validRegex =
      /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
      
    if (!validRegex.test(userEmail)) {
      document.getElementById("invalidEmail").style.display = "block";
      document.getElementById("invalidEmail").innerHTML =
        "You did not enter a valid email address.";
      isValid = false;
    }
  }

  return isValid;
}
