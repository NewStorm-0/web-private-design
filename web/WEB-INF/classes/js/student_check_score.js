'use strict';
var MySpace = MySpace || {};
MySpace.grades = $.parseJSON(sessionStorage.getItem("grades"));
initialize();

function initialize() {
    var gradesNumber = MySpace.grades.length;
    var coursesNames = [];
    var teachersNames = [];
    var coursesGrades = [];
    var classRanks = [];
    var majorRanks = [];
    const cols = 4;
    const rows = MySpace.grades.length;
    for (let i = 0; i < gradesNumber; i++) {
        coursesNames.push(MySpace.grades[i].courseName);
        teachersNames.push(MySpace.grades[i].teacherName);
        coursesGrades.push(MySpace.grades[i].studentGrade);
        classRanks.push(MySpace.grades[i].classRank);
        majorRanks.push(MySpace.grades[i].majorRank);
    }
    var htmlstr = "<table border='1' cellpadding='10' cellspacing='0'>";
    for (let i = 1; i <= rows; i++) {
        htmlstr += "<tr class=cell>";
        for (let j = 1; j <= cols; j++) {
            // htmlstr += "<td>" + i + "行" + j + "列" + "</td>";
            if (j === 1) {
                htmlstr += "<td rowspan='1' style='width: 160px; color: black;'>" + coursesNames[i - 1] + "</td>";

                htmlstr += "<td rowspan='1' style='width: 160px; color: black;'>" + teachersNames[i - 1] + "</td>";
            } else if (j === 2) {
                htmlstr += "<td rowspan='1' style='width: 160px; color: black;'>" + coursesGrades[i - 1] + "</td>";
            } else if (j === 3) {
                htmlstr += "<td rowspan='1' style='width: 160px; color: black;'>" + classRanks[i - 1] + "</td>";
            } else {
                htmlstr += "<td rowspan='1' style='width: 160px; color: black;'>" + majorRanks[i - 1] + "</td>";
            }
        }
        htmlstr += "</tr>";
    }
    htmlstr += "</table>";
    console.log(htmlstr);
    document.write(htmlstr);
}


function toInformation() {
    var e;
    $.post('./StudentInformationServlet', e, function (data) {
        sessionStorage.setItem("information", JSON.stringify(data));
        window.location.href = "student_information.html";
    });
}

function toChooseCourse() {
    var e = {service: "getOptionalCourses"};
    $.post('./StudentCourseServlet', e, function (data) {
        sessionStorage.setItem("optionalCourses", JSON.stringify(data));
        window.location.href = "student_choose_course.html";
    });
}

function toDropCourse() {
    var e = {service: 'getDropCourses'};
    $.post('./StudentCourseServlet', e, function (data) {
        sessionStorage.setItem("dropCourses", JSON.stringify(data));
        window.location.href = "student_drop_course.html";
    });
}