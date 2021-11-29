'use strict';
var MySpace = MySpace || {};

$(function () {
    MySpace.data = $.parseJSON(sessionStorage.getItem("json"));
    initializeTimetable();

});

function initializeTimetable() {
    var courseNumber = Object.getOwnPropertyNames(MySpace.data).length - 1;
    for (let i = 0; i < courseNumber; i++) {
        if (!isOver(i)) {
            continue;
        }
        for (let j = 0; j < MySpace.data[i].day.length; j++) {
            var day = MySpace.data[i].day[j];
            var time = getRowDom(MySpace.data[i].time[j]);
            var content = MySpace.data[i].courseName;
            var studentClassElement;
            if (day === '1') {
                studentClassElement = $(time + '>td.Monday');
            } else if (day === '2') {
                studentClassElement = $(time + '>td.Tuesday');
            } else if (day === '3') {
                studentClassElement = $(time + '>td.Wednesday');
            } else if (day === '4') {
                studentClassElement = $(time + '>td.Thursday');
            } else {
                studentClassElement = $(time + '>td.Friday');
            }
            studentClassElement.addClass('course').attr('rowspan', '2').text(content);
        }
    }
}

function getRowDom(time) {
    if (time === '1') {
        return 'tr.first';
    } else if (time === '2') {
        return 'tr.second';
    } else if (time === '3') {
        return 'tr.third';
    } else {
        return 'tr.fourth';
    }
}

function isOver(x) {
    var now = new Date();
    var year = parseInt(MySpace.data[x].selectionDeadline.substr(
        MySpace.data[x].selectionDeadline.indexOf(',') + 2,
        4
    ));
    var month = parseInt(MySpace.data[x].selectionDeadline.substring(
        0,
        MySpace.data[x].selectionDeadline.indexOf('月')
    ));
    var day = parseInt(MySpace.data[x].selectionDeadline.substring(
        MySpace.data[x].selectionDeadline.indexOf(' ') + 1,
        MySpace.data[x].selectionDeadline.indexOf(',')
    ));
    var deadline = new Date(year, month - 1, day, 0, 0, 0, 0);
    console.log(now);
    console.log(deadline);
    return compareDate(now, deadline);
}

function compareDate(d1, d2) {
    if (d1.getFullYear() > d2.getFullYear()) {
        return true;
    } else if (d1.getFullYear() < d2.getFullYear()) {
        return false;
    } else {
        if (d1.getMonth() > d2.getMonth()) {
            return true;
        } else if (d1.getMonth() < d2.getMonth()) {
            return false;
        } else {
            if (d1.getDate() >= d2.getDate()) {
                return true;
            } else {
                return false;
            }
        }
    }
}

function toInformation() {
    var e;
    $.post('./StudentInformationServlet', e, function (data) {
        sessionStorage.setItem("information", JSON.stringify(data));
        window.location.href = "student_information.html";
    });
}

function toChooseCourse() {
    var e;
    $.post('./StudentCourseServlet', e, function (data) {
        sessionStorage.setItem("optionalCourses", JSON.stringify(data));
        window.location.href = "student_choose_course.html";
    });

}