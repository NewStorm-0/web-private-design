'use strict';
var MySpace = MySpace || {};
$(function () {
    //console.log(sessionStorage.getItem("json"))
    MySpace.data = $.parseJSON(sessionStorage.getItem("json"));
    const StudentData = {
        data() {
            return {
                name: MySpace.data.name
            }
        }
    }
    Vue.createApp(StudentData).mount('#studentName');

    $('input[name=name]').val(MySpace.data.name);
    $('input[name=school-id]').val(MySpace.data.schoolId);
    $('input[name=mail]').val(MySpace.data.mail);
    $('input[name=phone]').val(MySpace.data.phone);
    $('input[name=address]').val(MySpace.data.address);
});

function myChangeInformation() {
    var accountInput = $('input[name=account]');
    var nameInput = $('input[name=name]');
    var mailInput = $('input[name=mail]');
    var phoneInput = $('input[name=phone]');
    var addressInput = $('input[name=address]');
    var buttonLeft = $('button[name=change]');
    if (buttonLeft.text() === "修改信息") {
        accountInput.attr("readonly", false);
        mailInput.attr("readonly", false);
        phoneInput.attr("readonly", false);
        addressInput.attr("readonly", false);
        accountInput.css('background-color', 'white');
        mailInput.css('background-color', 'white');
        phoneInput.css('background-color', 'white');
        addressInput.css('background-color', 'white');
        $('button[name=change]').text("提交信息");
    } else {
        if (!isTelOrMobile(phoneInput.val())) {
            alert('手机号码格式不对');
            return;
        }
        accountInput.attr("readonly", true);
        nameInput.attr("readonly", true);
        mailInput.attr("readonly", true);
        phoneInput.attr("readonly", true);
        addressInput.attr("readonly", true);
        accountInput.css('background-color', 'gray');
        nameInput.css('background-color', 'gray');
        mailInput.css('background-color', 'gray');
        phoneInput.css('background-color', 'gray');
        addressInput.css('background-color', 'gray');
        var newData = {
            type: 'Information',
            id: MySpace.data.id,
            identity: 0,
            //account: accountInput.val(),
            mail: mailInput.val(),
            phone: phoneInput.val(),
            address: addressInput.val()
        };
        $.post('./UpdateInformationServlet', newData, function (data) {
            if (data[0] === '1') {
                //MySpace.data.name = newData.name;
                //MySpace.data.account = newData.account;
                MySpace.data.mail = newData.mail;
                MySpace.data.phone = newData.phone;
                MySpace.data.adress = newData.address;
                sessionStorage.setItem("json", JSON.stringify(MySpace.data));
                alert('修改成功');
            } else {
                alert('修改失败');
            }
        });
        $('button[name=change]').text("修改信息");
    }
}

function myChangeKey() {
    var buttonChangePw = $('button[name=changePw]');
    var showElements = $('form.i1');
    var oldPassword = $('input[name=oldPw]');
    var newPassword = $('input[name=newPw]');
    console.log(buttonChangePw.text());
    if (buttonChangePw.text() === "修改密码") {
        buttonChangePw.text("提交密码");
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
                    MySpace.data.password = newPassword.val();
                    sessionStorage.setItem("json", JSON.stringify(MySpace.data));
                } else {
                    alert('修改密码失败');
                }
            });
        } else {
            alert('原密码输入错误');
        }
        showElements.css('display', 'none');
        buttonChangePw.text("修改密码");
    }
}

function isTelOrMobile(telephone) {
    var teleReg = /^((0\d{2,3})-)(\d{7,8})$/;
    var mobileReg = /^1[358]\d{9}$/;
    return !(!teleReg.test(telephone) && !mobileReg.test(telephone));
}