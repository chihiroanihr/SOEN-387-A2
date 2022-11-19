function addCourseToEnrollTable(i, row) {
  const courseCode = row.courseCode;
  const courseTitle = row.courseTitle;
  const semester = row.semester;
  const daysOfWeek = row.daysOfWeek ? arrDaysOfWeekToStr(row.daysOfWeek) : "";
  const startTime = row.startTime ? convertTimeTo12Hclock(row.startTime) : "";
  const endTime = row.endTime ? convertTimeTo12Hclock(row.endTime) : "";
  const room = row.room ? row.room : "";
  const startDate = row.startDate ? convertDate(row.startDate) : "";
  const endDate = row.endDate ? convertDate(row.endDate) : "";
  const adminID = row.adminID;
  const adminFirstName = row.firstName;
  const adminLastName = row.lastName;
  const adminEmail = row.email;

  const professor =
    adminFirstName +
    " " +
    adminLastName +
    "<br>" +
    "(" +
    adminID +
    ")" +
    "<br>" +
    adminEmail;

  $("#enrolledCourseTable").append(
    "<tr>" +
      "<th scope='row'>" +
      i +
      "</th>" +
      "<td scope='col' id='courseCode'>" +
      courseCode +
      "</td>" +
      "<td scope='col' id='courseTitle'>" +
      courseTitle +
      "</td>" +
      "<td scope='col' id='semester'>" +
      semester +
      "</td>" +
      "<td scope='col' id='daysOfWeek'>" +
      daysOfWeek +
      "</td>" +
      "<td scope='col' id='startTime'>" +
      startTime +
      "</td>" +
      "<td scope='col' id='endTime'>" +
      endTime +
      "</td>" +
      "<td scope='col' id='room'>" +
      room +
      "</td>" +
      "<td scope='col' id='startDate'>" +
      startDate +
      "</td>" +
      "<td scope='col' id='endDate'>" +
      endDate +
      "</td>" +
      "<td scope='col' id=" +
      adminID +
      ">" +
      professor +
      "</td>" +
      "</tr>"
  );
}

// [Table] add courses to table
function addCourseToAvailableTable(i, row) {
  const courseCode = row.courseCode;
  const courseTitle = row.courseTitle;
  const semester = row.semester;
  const daysOfWeek = row.daysOfWeek ? arrDaysOfWeekToStr(row.daysOfWeek) : "";
  const startTime = row.startTime ? convertTimeTo12Hclock(row.startTime) : "";
  const endTime = row.endTime ? convertTimeTo12Hclock(row.endTime) : "";
  const room = row.room ? row.room : "";
  const startDate = row.startDate ? convertDate(row.startDate) : "";
  const endDate = row.endDate ? convertDate(row.endDate) : "";
  const adminID = row.adminID;
  const adminFirstName = row.firstName;
  const adminLastName = row.lastName;
  const adminEmail = row.email;

  const professor =
    adminFirstName +
    " " +
    adminLastName +
    "<br>" +
    "(" +
    adminID +
    ")" +
    "<br>" +
    adminEmail;

  $("#availableCourseTable").append(
    "<tr>" +
      "<th scope='row'>" +
      i +
      "</th>" +
      "<td scope='col' id='courseCode'>" +
      courseCode +
      "</td>" +
      "<td scope='col' id='courseTitle'>" +
      courseTitle +
      "</td>" +
      "<td scope='col' id='semester'>" +
      semester +
      "</td>" +
      "<td scope='col' id='daysOfWeek'>" +
      daysOfWeek +
      "</td>" +
      "<td scope='col' id='startTime'>" +
      startTime +
      "</td>" +
      "<td scope='col' id='endTime'>" +
      endTime +
      "</td>" +
      "<td scope='col' id='room'>" +
      room +
      "</td>" +
      "<td scope='col' id='startDate'>" +
      startDate +
      "</td>" +
      "<td scope='col' id='endDate'>" +
      endDate +
      "</td>" +
      "<td scope='col' id=" +
      adminID +
      ">" +
      professor +
      "</td>" +
      "<td scope='col' class='align-middle'>" +
      "<button id='enrollButton' class='btn btn-secondary' data-toggle='modal' data-target='#modalWrapper'>Enroll</button>" +
      "</td>" +
      "</tr>"
  );
}

function addCourseToDropTable(i, row) {
  const courseCode = row.courseCode;
  const courseTitle = row.courseTitle;
  const semester = row.semester;
  const daysOfWeek = row.daysOfWeek ? arrDaysOfWeekToStr(row.daysOfWeek) : "";
  const startTime = row.startTime ? convertTimeTo12Hclock(row.startTime) : "";
  const endTime = row.endTime ? convertTimeTo12Hclock(row.endTime) : "";
  const room = row.room ? row.room : "";
  const startDate = row.startDate ? convertDate(row.startDate) : "";
  const endDate = row.endDate ? convertDate(row.endDate) : "";
  const adminID = row.adminID;
  const adminFirstName = row.firstName;
  const adminLastName = row.lastName;
  const adminEmail = row.email;

  const professor =
    adminFirstName +
    " " +
    adminLastName +
    "<br>" +
    "(" +
    adminID +
    ")" +
    "<br>" +
    adminEmail;

  $("#enrolledCourseTable").append(
    "<tr>" +
      "<th scope='row'>" +
      i +
      "</th>" +
      "<td scope='col' id='courseCode'>" +
      courseCode +
      "</td>" +
      "<td scope='col' id='courseTitle'>" +
      courseTitle +
      "</td>" +
      "<td scope='col' id='semester'>" +
      semester +
      "</td>" +
      "<td scope='col' id='daysOfWeek'>" +
      daysOfWeek +
      "</td>" +
      "<td scope='col' id='startTime'>" +
      startTime +
      "</td>" +
      "<td scope='col' id='endTime'>" +
      endTime +
      "</td>" +
      "<td scope='col' id='room'>" +
      room +
      "</td>" +
      "<td scope='col' id='startDate'>" +
      startDate +
      "</td>" +
      "<td scope='col' id='endDate'>" +
      endDate +
      "</td>" +
      "<td scope='col' id=" +
      adminID +
      ">" +
      professor +
      "</td>" +
      "<td scope='col' class='align-middle'>" +
      "<button id='dropButton' class='btn btn-secondary' data-toggle='modal' data-target='#modalWrapper'>Drop</button>" +
      "</td>" +
      "</tr>"
  );
}
