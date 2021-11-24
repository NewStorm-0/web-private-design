'use strict';
console.log(sessionStorage.getItem("json"))
var data = $.parseJSON(sessionStorage.getItem("json"));
const TeacherData = {
    data() {
        return {
            name: data[4]
        }
    }
}
Vue.createApp(TeacherData).mount('#teacher-name')