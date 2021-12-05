'use strict';
var MySpace = MySpace || {};
MySpace.data = $.parseJSON(sessionStorage.getItem("optionalCourses"));
$(function () {

});

var courseNumber = Object.getOwnPropertyNames(MySpace.data).length - 1;
var course_description = [];
var teacher_name = [];
var course_name = [];
var class_time = [];
var isSelected = [];
var cols = 4; //4列
var rows = courseNumber; //4行
for (let i = 0; i < courseNumber; i++) {
    var classTime = '';
    for (let j = 0; j < MySpace.data[i].day.length; j++) {
        var lesson = [MySpace.data[i].time[0]];
        for (let k = j + 1; k < MySpace.data[i].day.length; k++) {
            if (MySpace.data[i].day[j] === MySpace.data[i].day[k]) {
                lesson.length++;
                lesson[lesson.length - 1] = MySpace.data[i].time[k];
            } else {
                break;
            }
        }
        j = j + lesson.length - 1;
        classTime = classTime + '周' + MySpace.data[i].day[j] + '第' + lesson[0];
        for (let k = 1; k < lesson.length; k++) {
            classTime = classTime + ',' + lesson[k];
        }
        classTime = classTime + '节;'

    }
    course_description.length++;
    course_description[i] = MySpace.data[i].description;
    teacher_name.length++;
    teacher_name[i] = MySpace.data[i].teacherName;
    course_name.length++;
    course_name[i] = MySpace.data[i].courseName;
    class_time.length++;
    class_time[i] = classTime;
    isSelected.length++;
    isSelected[i] = MySpace.data[i].selected;
}
var htmlstr = "<table border='1' cellpadding='10' cellspacing='0'>";
for (let i = 1; i <= rows; i++) {
    htmlstr += "<tr class=cell>";
    for (let j = 1; j <= cols; j++) {
        // htmlstr += "<td>" + i + "行" + j + "列" + "</td>";
        if (j === 1) {
            if (!(isSelected[i - 1] === "true")) {
                htmlstr += "<td rowspan='1' style='width: 80px; color: black;'>" + '<input type="checkbox" class="optional" name="' + i + '">' + "</td>";
            } else {
                htmlstr += "<td rowspan='1' style='width: 80px; color: black;'>" + '<input class="hadSelected" type="checkbox" name="' + i + '" checked onclick="return false;">' + "</td>";
            }
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

function chooseCourse() {
    var boxes = $('input.optional');
    var chooseData = [];
    for (let i = 0; i < boxes.length; i++) {
        var a = $(boxes[i]);
        if (a.prop('checked') === true) {
            chooseData.push(MySpace.data[parseInt(a.attr('name')) - 1].courseId);
        }
    }
    var reqData = {service: 'chooseCourses', coursesId: chooseData};
    $.post('./StudentCourseServlet', reqData, function (data) {
        if (data[0] === '1') {
            for (let i = 0; i < boxes.length; i++) {
                var a = $(boxes[i]);
                if (a.prop('checked') === true) {
                    a.addClass('hadSelected');
                    a.attr('onclick', 'return false;');
                }
            }
            alert('选课成功');
        } else alert('选课失败');
    });
}

function toInformation() {
    var e;
    $.post('./StudentInformationServlet', e, function (data) {
        sessionStorage.setItem("information", JSON.stringify(data));
        window.location.href = "student_information.html";
    });
}

function toDropCourse() {
    var e = {service: 'getDropCourses'};
    $.post('./StudentCourseServlet', e, function (data) {
        sessionStorage.setItem("dropCourses", JSON.stringify(data));
        window.location.href = "student_drop_course.html";
    });
}

function toCheckScore() {
    var e = {service: 'getGrade'};
    $.post('./StudentCourseServlet', e, function (data) {
        sessionStorage.setItem("grades", JSON.stringify(data));
        window.location.href = "student_check_score.html";
    });
}