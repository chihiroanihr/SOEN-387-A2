function makeTable(i, row) {
  const courseCode = row.courseCode;
  const semester = row.semester;

  $("#studentTableContainer").append(
    "<h3>" +
      courseCode +
      " " +
      semester +
      "</h3>" +
      "<div class='wrap-table table-responsive mb-5' id='wrapStudentTable-" +
      i +
      "'>" +
      "<table class='table table-striped' id='studentTable-" +
      i +
      "'>" +
      "<thead>" +
      "<tr>" +
      "<th scope='col'>#</th>" +
      "<th scope='col'><strong>Student ID</strong></th>" +
      "<th scope='col'><strong>First Name</strong></th>" +
      "<th scope='col'><strong>Last Name</strong></th>" +
      "<th scope='col'><strong>Email</strong></th>" +
      "<th scope='col'></th>" +
      "</tr>" +
      "</thead>" +
      "<tbody></tbody>" +
      "</table>" +
      "</div>"
  );
}

// [Table] add courses to table
function addStudentToTable(i, j, row) {
  const studentID = row.personId;
  const studentFirstName = row.firstName;
  const studentLastName = row.lastNaeme;
  const studentEmail = row.email;

  $("#studentTable-" + i).append(
    "<tr>" +
      "<th scope='row'>" +
      j +
      "</th>" +
      "<td scope='col' id='studentID'>" +
      studentID +
      "</td>" +
      "<td scope='col' id='studentFirstName'>" +
      studentFirstName +
      "</td>" +
      "<td scope='col' id='studentLastName'>" +
      studentLastName +
      "</td>" +
      "<td scope='col' id='studentEmail'>" +
      studentEmail +
      "</td>" +
      "<td scope='col' class='align-middle'>" +
      "<a class='nav-link dropdown-toggle' href='#' id='tableRowDropdown' role='button' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'></a>" +
      "<div class='dropdown-menu' aria-labelledby='tableRowDropdown'>" +
      "</div>" +
      "</td>" +
      "</tr>"
  );
}
