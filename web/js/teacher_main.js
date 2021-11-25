'use strict';
var MySpace = MySpace || {};
$(function () {
    //console.log(sessionStorage.getItem("json"));
    MySpace.data = $.parseJSON(sessionStorage.getItem("json"));
    const TeacherData = {
        data() {
            return {
                name: MySpace.data.name
            }
        }
    }
    Vue.createApp(TeacherData).mount('#top');

    Vue.createApp({
            data() {
                return {
                    account: 'a',
                    name: 'Vue.js',
                    schoolId: ''
                }
            },
            methods: {
                changeInformation() {

                }
            }
        }
    ).mount('form.i0');

    $('input[name=account]').val(MySpace.data.account);
    $('input[name=name]').val(MySpace.data.name);
    $('input[name=school-id]').val(MySpace.data.schoolId);
});

function myChangeInformation() {
    var accountInput = $('input[name=account]');
    var nameInput = $('input[name=name]');
    var buttonLeft = $('button[name=change]');
    if (buttonLeft.text() === "修改信息") {
        accountInput.attr("readonly", false);
        //nameInput.attr("readonly", false);
        accountInput.css('background-color', 'white');
        //nameInput.css('background-color', 'white');
        $('button[name=change]').text("提交信息");
    } else {
        accountInput.attr("readonly", true);
        nameInput.attr("readonly", true);
        accountInput.css('background-color', 'gray');
        nameInput.css('background-color', 'gray');
        var newData = {
            type: 'Information',
            id: MySpace.data.id,
            identity: 1,
            account: accountInput.val(),
            name: nameInput.val()
        };
        $.post('./UpdateInformationServlet', newData, function (data) {
            if (data[0] === '1') {
                MySpace.data.name = newData.name;
                MySpace.data.account = newData.account;
                sessionStorage.setItem("json", JSON.stringify(MySpace.data));
                alert('用户名修改成功');
            } else {
                alert('修改失败');
            }
        });
        $('button[name=change]').text("修改信息");
    }
}

function myChangeKey() {
    var buttonRight = $('button[name=changePw]');
    var showElements = $('form.i1');
    var oldPassword = $('input[name=oldPw]');
    var newPassword = $('input[name=newPw]');
    if (buttonRight.text() === '修改密码') {
        buttonRight.text("提交密码");
        showElements.css('display', 'inline');
    } else {
        if (oldPassword.val() === MySpace.data.password) {
            var newPw = {
                type: 'Password',
                id: MySpace.data.id,
                password: newPassword.val()
            };
            $.post('./UpdateInformationServlet', newPw, function (data) {
                if (data[0] === '1') {
                    alert('修改密码成功');
                    MySpace.data[4] = newPassword.val();
                    sessionStorage.setItem("json", JSON.stringify(MySpace.data));
                } else {
                    alert('修改密码失败');
                }
            });
        } else {
            alert('原密码输入错误');
        }
        showElements.css('display', 'none');
        buttonRight.text("修改密码");
    }
}
