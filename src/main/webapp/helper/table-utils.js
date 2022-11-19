// convert from 24h to 12h representation
function convertTimeTo12Hclock(time) {
    const [hourString, minute] = time.split(":");
    const hour = +hourString % 24;

    return (hour % 12 || 12) + ":" + minute + (hour < 12 ? " AM" : " PM");
}

// convert from 12h to 24h representation
function convertTimeTo24Hclock(time) {
    let hours = Number(time.match(/^(\d+)/)[1]);
    let minutes = Number(time.match(/:(\d+)/)[1]);
    let AMPM = time.match(/\s(.*)$/)[1];
    if(AMPM == "PM" && hours<12) hours = hours+12;
    if(AMPM == "AM" && hours==12) hours = hours-12;
    let strHours = hours.toString();
    let strMinutes = minutes.toString();
    if(hours<10) strHours = "0" + strHours;
    if(minutes<10) strMinutes = "0" + strMinutes;
    return `${strHours}:${strMinutes}`;
}

// convert from sql date format to yyyy-mm-dd
function convertDate(date) {
    let d = new Date(date),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2)
        month = '0' + month;
    if (day.length < 2)
        day = '0' + day;

    return [year, month, day].join('-');
}

// convert array of days into string when adding to course table
function arrDaysOfWeekToStr(arr) {
    daysOfWeekStr = "";

    JSON.parse(arr).forEach((element) => (daysOfWeekStr += element + "<br>"));
    return daysOfWeekStr;
}
