'use strict';
var MySpace = MySpace || {};
MySpace.coursesData = $.parseJSON(sessionStorage.getItem("dropCourses"));

$(function () {

});

var courseNumber = Object.getOwnPropertyNames(MySpace.coursesData).length - 1;
var course_description = [];
var teacher_name = [];
var course_name = [];
var class_time = [];
var cols = 4; //4列
var rows = courseNumber; //4行
for (let i = 0; i < courseNumber; i++) {
    var classTime = '';
    for (let j = 0; j < MySpace.coursesData[i].day.length; j++) {
        var lesson = [MySpace.coursesData[i].time[0]];
        for (let k = j + 1; k < MySpace.coursesData[i].day.length; k++) {
            if (MySpace.coursesData[i].day[j] === MySpace.coursesData[i].day[k]) {
                lesson.length++;
                lesson[lesson.length - 1] = MySpace.coursesData[i].time[k];
            } else {
                break;
            }
        }
        j = j + lesson.length - 1;
        classTime = classTime + '周' + MySpace.coursesData[i].day[j] + '第' + lesson[0];
        for (let k = 1; k < lesson.length; k++) {
            classTime = classTime + ',' + lesson[k];
        }
        classTime = classTime + '节;'

    }
    course_description.length++;
    course_description[i] = MySpace.coursesData[i].description;
    teacher_name.length++;
    teacher_name[i] = MySpace.coursesData[i].teacherName;
    course_name.length++;
    course_name[i] = MySpace.coursesData[i].courseName;
    class_time.length++;
    class_time[i] = classTime;
}
var htmlstr = "<table border='1' cellpadding='10' cellspacing='0'>";
for (let i = 1; i <= rows; i++) {
    htmlstr += "<tr class=cell>";
    for (let j = 1; j <= cols; j++) {
        // htmlstr += "<td>" + i + "行" + j + "列" + "</td>";
        if (j === 1) {
            htmlstr += "<td rowspan='1' style='width: 80px; color: black;'>" + '<input class="hadSelected" type="checkbox" name="' + i + '" checked>' + "</td>";

            htmlstr += "<td rowspan='1' style='width: 160px; color: black;'>" + teacher_name[i - 1] + "</td>";
        } else if (j == 2) {
            htmlstr += "<td rowspan='1' style='width: 160px; color: black;'>" + course_name[i - 1] + "</td>";
        } else if (j == 3) {
            htmlstr += "<td rowspan='1' style='width: 160px; color: black;'>" + course_description[i - 1] + "</td>";
        } else {
            htmlstr += "<td rowspan='1' style='width: 160px; color: black;'>" + class_time[i - 1] + "</td>";
        }
    }
    htmlstr += "</tr>";
}
htmlstr += "</table>";
document.write(htmlstr);

function dropCourse() {
    var boxes = $('input.hadSelected');
    var dropData = [];
    for (let i = 0; i < boxes.length; i++) {
        var a = $(boxes[i]);
        if (a.prop('checked') === false) {
            dropData.push(MySpace.coursesData[parseInt(a.attr('name')) - 1].courseId);
        }
    }
    const reqData = {service: 'dropCourses', coursesId: dropData};
    $.post('./StudentCourseServlet', reqData, function (data) {
        if (data[0] === '1') {
            alert('退课成功');
        } else alert('退课失败');
    });
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

function toCheckScore() {
    var e = {service: 'getGrade'};
    $.post('./StudentCourseServlet', e, function (data) {
        sessionStorage.setItem("grades", JSON.stringify(data));
        window.location.href = "student_check_score.html";
    });
}