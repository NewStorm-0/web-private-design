'use strict';
//TODO 老师开设课程
var MySpace = MySpace || {};
MySpace.classNumber = 1;

$(function () {
    MySpace.data = $.parseJSON(sessionStorage.getItem("json"));
    const TeacherData = {
        data() {
            return {
                name: MySpace.data.name
            }
        }
    }
    Vue.createApp(TeacherData).mount('#top');

});

function addClassTime() {
    if (MySpace.classNumber >= 20) {
        alert('课都占满了，差不多得了吧😅吧');
        return;
    }
    MySpace.classNumber++;
    var a1 = $('fieldset');
    a1.append('<br><br><select id="s' +
        MySpace.classNumber + '">\n' +
        '                <option>周一</option>\n' +
        '                <option>周二</option>\n' +
        '                <option>周三</option>\n' +
        '                <option>周四</option>\n' +
        '                <option>周五</option>\n' +
        '            </select>\n' +
        '            &nbsp;&nbsp;&nbsp;\n' +
        '            <select id="t' +
        MySpace.classNumber + '">\n' +
        '                <option>第一，二节</option>\n' +
        '                <option>第三，四节</option>\n' +
        '                <option>第五，六节</option>\n' +
        '                <option>第七，八节</option>\n' +
        '            </select>');
}

function releaseCourse() {
    var day = '', time = '';
    var courseData = {
        courseName: $('input[name=name]').val(),
        courseDescription: $('textarea[name=description]').val(),
        deadline: $('input[name=date]').val(),
        credit: $('input[name=score]').val(),
        day: day,
        time: time
    };
    for (let i = 1; i <= MySpace.classNumber; i++) {
        var selectDay = $('#s' + i + '>option:selected');
        var selectTime = $('#t' + i + '>option:selected');
        day = day + convertToDay(selectDay);
        time = time + convertToDayTime(selectTime);
    }
    courseData.day = day;
    courseData.time = time;
    //console.log(courseData);
    $.post('./TeacherAddCourseServlet', courseData, function (data) {
        if (data[0] === '1') {
            alert('发布课程成功');
        } else {
            alert("发布课程失败");
        }
    });
}

function convertToDay(x) {
    if (x.text() === '周一') {
        return 1;
    } else if (x.text() === '周二') {
        return 2;
    } else if (x.text() === '周三') {
        return 3;
    } else if (x.text() === '周四') {
        return 4;
    } else if (x.text() === '周五') {
        return 5;
    } else {
        throw new Error('错误值：' + x.text());
    }
}

function convertToDayTime(x) {
    if (x.text() === '第一，二节') {
        return 1;
    } else if (x.text() === '第三，四节') {
        return 2;
    } else if (x.text() === '第五，六节') {
        return 3;
    } else if (x.text() === '第七，八节') {
        return 4;
    } else {
        throw new Error('错误值：' + x.text());
    }
}