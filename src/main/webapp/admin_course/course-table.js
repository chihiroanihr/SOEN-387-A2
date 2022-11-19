// [Table] add courses to table
function addCourseToEditTable(i, row) {
  const courseCode = row.courseCode;
  const courseTitle = row.courseTitle;
  const semester = row.semester;
  const daysOfWeek = row.daysOfWeek ? arrDaysOfWeekToStr(row.daysOfWeek) : "";
  const startTime = row.startTime ? convertTimeTo12Hclock(row.startTime) : "";
  const endTime = row.endTime ? convertTimeTo12Hclock(row.endTime) : "";
  const room = row.room ? row.room : "";
  const startDate = row.startDate ? convertDate(row.startDate) : "";
  const endDate = row.endDate ? convertDate(row.endDate) : "";

  $("#courseTable").append(
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
      "<td scope='col' class='align-middle'><button id='editButton' class='btn btn-secondary'>Edit</button></td>" +
      "</tr>"
  );
}

function addCourseToDeleteTable(i, row) {
  const courseCode = row.courseCode;
  const courseTitle = row.courseTitle;
  const semester = row.semester;
  const daysOfWeek = row.daysOfWeek ? arrDaysOfWeekToStr(row.daysOfWeek) : "";
  const startTime = row.startTime ? convertTimeTo12Hclock(row.startTime) : "";
  const endTime = row.endTime ? convertTimeTo12Hclock(row.endTime) : "";
  const room = row.room ? row.room : "";
  const startDate = row.startDate ? convertDate(row.startDate) : "";
  const endDate = row.endDate ? convertDate(row.endDate) : "";

  $("#courseTable").append(
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
      "<td scope='col' class='align-middle'>" +
      "<button id='deleteButton' class='btn btn-secondary' data-toggle='modal' data-target='#modalWrapper'>Delete</button>" +
      "</td>" +
      "</tr>"
  );
}