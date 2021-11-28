'use strict'
var MySpace = MySpace || {};
$(function () {
    MySpace.data = $.parseJSON(sessionStorage.getItem("allCourses"));
    MySpace.information = $.parseJSON(sessionStorage.getItem("json"));
    //console.log(MySpace.data);
    const TeacherData = {
        data() {
            return {
                name: MySpace.information.name
            }
        }
    }
    Vue.createApp(TeacherData).mount('#top');

    var table = $('table.a0');
    var courseNumber = Object.getOwnPropertyNames(MySpace.data).length - 1;
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
        var temp = "<tr><td>" +
            MySpace.data[i].courseName +
            "</td><td>" + MySpace.data[i].credit +
            "</td><td>" + MySpace.data[i].description +
            "</td><td>" + MySpace.data[i].selectionDeadline +
            "</td><td>" + classTime +
            "</td><td><a href=\"javascript:void(0);\" onclick=\"enterResults(" + i +
            ")\" class=\"course\">录入成绩</a></td>" +
            "</tr>";
        table.append(temp);
    }
});

function enterResults(x) {
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
    if (compareDate(now, deadline)) {
        //TODO 教师录入成绩
        alert("进入页面");
    } else {
        alert("还未到选课截止时间");
    }
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