// [Table] add courses to table
function addCourseToStudentTable(i, row) {
  var courseCode = row.courseCode;
  var semester = row.semester;
  var adminID = row.adminID;

  $("#studentCourseTable").append(
    "<tr>" +
      "<th scope='row'>" +
      i +
      "</th>" +
      "<td scope='col' id='courseCode'>" +
      courseCode +
      "</td>" +
      "<td scope='col' id='semester'>" +
      semester +
      "</td>" +
      "<td scope='col' id='adminID'>" +
      adminID +
      "</td>" +
      "<td scope='col' class='align-middle'>" +
      "<a class='nav-link dropdown-toggle' href='#' id='tableRowDropdown' role='button' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'></a>" +
      "<div class='dropdown-menu' aria-labelledby='tableRowDropdown'>" +
      "</div>" +
      "</td>" +
      "</tr>"
  );
}