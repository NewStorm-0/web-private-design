'use strict'
var account, password, check, signData, userData;

$(function () {
    account = $('#account');
    password = $('#password');
    signData = {account: '', password: '', identity: 0};
    account.blur(function () {
        var a = $('.a1');
        var re1 = new RegExp('\\w{1,10}');
        if (!re1.test(account.val())) {
            a.text('账号必须是1~10位数字或密码');
            account.css('border-color', 'rgb(198,33,33)');
            check = false;
        } else {
            a.text('');
            account.css('border-color', '');
            signData.account = account.val();
            check = true;
        }
    });
    password.blur(function () {
        var a = $('.a2');
        var re1 = new RegExp('.{6,21}');
        if (!re1.test(password.val())) {
            a.text('密码必须为6~21位');
            password.css('border-color', 'rgb(198,33,33)');
            check = false;
        } else {
            a.text('');
            password.css('border-color', '');
            signData.password = password.val();
            check = true;
        }
    });
});

// 参数(消息类型):error, correct
function message(type) {
    //根据类型展示消息
}

function logIn() {
    if (!check) {
        alert('账号或密码不规范');
        return;
    }
    signData.identity = $('input[type=radio]:checked').val();
    $.post('./LoginServlet', signData, function (data) {
        userData = data;
        if (data[0] === '-' && data[1] === '1')
            alert('账号或密码错误');
        else {
            //console.log("JSON.stringify(data) = " + JSON.stringify(data));
            sessionStorage.setItem("json", JSON.stringify(data));
            if (data.identity === '0') {
                window.location.href = "student_information.html";
            } else {
                window.location.href = "teacher_main.html";
            }
        }
    });
}

function register() {
    alert("register");
}