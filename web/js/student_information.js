'use strict';
var MySpace = MySpace || {};
$(function () {
    console.log(sessionStorage.getItem("json"))
    MySpace.data = $.parseJSON(sessionStorage.getItem("json"));
    const TeacherData = {
        data() {
            return {
                name: 'sb',//data[1]
                mail: '202000400178@sdu.edu.com',
                phone: '18772270718',
                address: '山东大学软件学院'
            }
        }
    }
    Vue.createApp(TeacherData).mount('#top');

    Vue.createApp({
            data() {
                return {
                    name: 'Vue.js',
                    schoolId: '',
                    mail: '202000300989@sdu.com',
                    phone: '17605437788',
                    address: '山东大学软件学院'
                }
            },
            methods: {
                changeInformation() {

                }
            }
        }
    ).mount('form.i0');
});
function isTelOrMobile(telephone) {
    var teleReg = /^((0\d{2,3})-)(\d{7,8})$/;  
    var mobileReg =/^1[358]\d{9}$/;   
    if (!teleReg.test(telephone) && !mobileReg.test(telephone)){  
        return false;  
    }else{  
        return true;  
    }  
}
function myChangeInformation() {
    var accountInput = $('input[name=account]');
    var nameInput = $('input[name=name]');
    var mailInput = $('input[name=mail]');
    var phoneInput = $('input[name=phone]');
    var addressInput = $('input[name=address]');
    var buttonLeft = $('button[name=change]');
    if (buttonLeft.text() === "修改信息") {
        accountInput.attr("readonly", false);
        nameInput.attr("readonly", false);
        mailInput.attr("readonly", false);
        phoneInput.attr("readonly", false);
        addressInput.attr("readonly", false);
        accountInput.css('background-color', 'white');
        nameInput.css('background-color', 'white');
        mailInput.css('background-color', 'white');
        phoneInput.css('background-color', 'white');
        addressInput.css('background-color', 'white');
        $('button[name=change]').text("提交信息");
    } else {
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
        $('button[name=change]').text("修改信息");
    }
}