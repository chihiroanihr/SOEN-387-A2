// [Form] retrieve checkboxed values for POST
function getDaysOfWeekSelected() {
  daysOfWeekArray = [];
  $("#daysOfWeek input[type=checkbox]:checked+label").each(function () {
    daysOfWeekArray.push($(this).text());
  });
  return daysOfWeekArray;
}

// [Form] generate 3 years of semesters
function generate3yearsOfSemesters() {
  // get years array
  var yearList = generate3Years();
  // seasons array
  var seasonList = [
    ["W", "Winter"],
    ["F", "Fall"],
    ["S", "Summer"],
  ];
  // semesters dictionary
  var semesterList = {};

  for (var year of yearList) {
    for (var season of seasonList) {
      var key = season[0] + year;
      var value = season[1] + " " + year;
      semesterList[key] = value;
    }
  }
  return semesterList;
}
function generate3Years() {
  var min = new Date().getFullYear();
  var max = min + 3;
  var years = [];

  for (var i = min; i <= max; i++) {
    years.push(i);
  }
  return years;
}

// [Form] add 3 years of semesters generated to the course form select section
function addSemestersToSelectOptions() {
  // get select form id
  var semesterSelect = $("select#semester");
  // get semesters list
  var semesterList = generate3yearsOfSemesters();

  let entries = Object.entries(semesterList);
  entries.map(([key, value] = entry) => {
    // create option
    var option = document.createElement("option");
    option.value = key;
    option.text = value;
    // append to select form
    semesterSelect.append(option);
  });
}