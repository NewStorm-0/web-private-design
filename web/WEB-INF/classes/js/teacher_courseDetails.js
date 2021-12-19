'use strict'
var MySpace = MySpace || {};
$(function () {
    MySpace.data = $.parseJSON(sessionStorage.getItem("allCourses"));
    MySpace.information = $.parseJSON(sessionStorage.getItem("json"));
    MySpace.courseInformation = $.parseJSON(sessionStorage.getItem("courseInformation"));
    //console.log(MySpace.courseInformation);
    const TeacherData = {
        data() {
            return {
                name: MySpace.information.name
            }
        }
    }
    Vue.createApp(TeacherData).mount('#top');

    var tableCourse = $('table.a0');
    var studentNumber = Object.getOwnPropertyNames(MySpace.courseInformation).length - 1;
    var cp = MySpace.courseInformation[0].coursePosition;
    var classTime = '';
    for (let j = 0; j < MySpace.data[cp].day.length; j++) {
        var lesson = [MySpace.data[cp].time[0]];
        for (let k = j + 1; k < MySpace.data[cp].day.length; k++) {
            if (MySpace.data[cp].day[j] === MySpace.data[cp].day[k]) {
                lesson.length++;
                lesson[lesson.length - 1] = MySpace.data[cp].time[k];
            } else {
                break;
            }
        }
        j = j + lesson.length - 1;
        classTime = classTime + '周' + MySpace.data[cp].day[j] + '第' + lesson[0];
        for (let k = 1; k < lesson.length; k++) {
            classTime = classTime + ',' + lesson[k];
        }
        classTime = classTime + '节;'

    }
    var temp = "<tr><td>" +
        MySpace.data[cp].courseName +
        "</td><td>" + MySpace.data[cp].credit +
        "</td><td>" + MySpace.data[cp].description +
        "</td><td>" + classTime +
        "</td><td>" + studentNumber +
        "</td>" +
        "</tr>";
    tableCourse.append(temp);

    var tableStudents = $('table.a1');
    for (let i = 0; i < studentNumber; i++) {
        var ttt = "<tr><td>" + MySpace.courseInformation[i].schoolId +
            "</td><td>" + MySpace.courseInformation[i].name +
            "</td><td>" + MySpace.courseInformation[i].className +
            "</td><td>" + MySpace.courseInformation[i].majorName +
            "</td><td><input class='grade' type='number' value='" + MySpace.courseInformation[i].studentGrade +
            "' name=s'" + i +
            "'></td></tr>";
        tableStudents.append(ttt);
    }
});

function submitGrades() {
    var gradeInputs = $('input[type=number]');
    if (gradeInputs.length !== (Object.getOwnPropertyNames(MySpace.courseInformation).length - 1)) {
        alert('出现错误');
        return;
    }
    var finalData = {
        service: "submitGrades",
        courseId: MySpace.data[MySpace.courseInformation[0].coursePosition].id,
        data: null
    };
    var arrayData = [];
    for (let i = 0; i < gradeInputs.length; i++) {
        var a = $(gradeInputs[i]);
        if (a.val() >= 0 && a.val() <= 100) {
            var temp = {
                studentId: MySpace.courseInformation[i].studentId,
                grade: a.val()
            };
            console.log(typeof temp.studentId);
            console.log(typeof temp.grade);
            arrayData.push(temp);
        } else {
            alert('分数不符合规范');
            return;
        }
    }
    finalData.data = JSON.stringify(arrayData);
    //console.log(finalData);
    $.post('./TeacherEnterResultsServlet', finalData, function (data) {
        if (data === '1') {
            alert("分数提交成功");
        }
        else {
            alert('分数提交失败');
        }
    });
}