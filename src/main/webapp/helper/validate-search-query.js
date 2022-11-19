function validateSearchQuery(input) {
    let isValid = true;

    // default: do not display validation error message
    document.getElementById("invalidInput").style.display = "none";

    // validate search box query input
    if (input === "") {
        document.getElementById("invalidInput").style.display = "block";
        document.getElementById("invalidInput").innerHTML = "Enter Student ID."
        isValid = false;
    } else if (isNaN(input)) {
        document.getElementById("invalidInput").style.display = "block";
        document.getElementById("invalidInput").innerHTML = "Student ID must only contain digits."

        isValid = false;
    }

    return isValid;
}
