'use strict'
var MySpace = MySpace || {};
MySpace.studentInfo = $.parseJSON(sessionStorage.getItem("registerInfo"));
let accountInput, passwordInput;
$(function () {
    initializeStudentInfo()
    $('#student').on('click', function () {
        $('#teacherContent').hide();
        $('#studentContent').show();
    });
    $('#teacher').on('click', function () {
        $('#studentContent').hide();
        $('#teacherContent').show();
    })
    $('#teacherContent').hide();
    $('#studentContent').show();

    accountInput = $('#account');
    accountInput.blur(function () {
        const re1 = new RegExp('\\w{1,10}');
        if (!re1.test(accountInput.val())) {
            $('.a1').text('账号必须是1~10位数字或密码');
            accountInput.css('border-color', 'rgb(198,33,33)');
            MySpace.checkA = false;
        } else {
            $('.a1').text('');
            accountInput.css('border-color', '');
            MySpace.checkA = true;
        }
    });
    passwordInput = $('#password');
    passwordInput.blur(function () {
        const re1 = new RegExp('.{6,21}');
        if (!re1.test(passwordInput.val())) {
            $('.a2').text('密码必须为6~21位');
            passwordInput.css('border-color', 'rgb(198,33,33)');
            MySpace.checkB = false;
        } else {
            $('.a2').text('');
            passwordInput.css('border-color', '');
            MySpace.checkB = true;
        }
    })
});

function initializeStudentInfo() {
    let htmlSpan;
    const classChoice = $('#classChoice');
    for (let i = 0; i < MySpace.studentInfo.classes[0].length; i++) {
        htmlSpan = '<option value="' + MySpace.studentInfo.classes[0][i].id +
            '">' + MySpace.studentInfo.classes[0][i].className +
            '</option>';
        classChoice.append(htmlSpan);
    }
    const majorChoice = $('#majorChoice');
    for (let i = 0; i < MySpace.studentInfo.majors[0].length; i++) {
        htmlSpan = '<option value="' + MySpace.studentInfo.majors[0][i].id +
            '">' + MySpace.studentInfo.majors[0][i].majorName +
            '</option>';
        majorChoice.append(htmlSpan);
    }
}

function registerAccount() {
    if (MySpace.checkA && MySpace.checkB) {
        const registerData = {
            service: 'register',
            identity: $('input[type=radio]:checked').val(),
            account: $('input[name=account]').val(),
            password: $('input[name=password]').val(),
            name: $('input[name=accountName]').val()
        };
        if (registerData.identity === '0') {
            registerData.year = $('#year').val();
            registerData.classId = $('#classChoice>option:selected').val();
            registerData.majorId = $('#majorChoice>option:selected').val();
            registerData.birthday = $('#birthday').val();
        }
        $.post('./RegisterServlet', registerData, function (data) {
            if (data[0] === '1') {
                alert('注册账号成功');
            } else {
                alert("注册账号失败");
            }
        });
    }
}

function getStudentClassId() {

}

function getStudentMajorId() {

}