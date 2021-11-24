'use strict';
console.log(sessionStorage.getItem("json"))
var data = $.parseJSON(sessionStorage.getItem("json"));
const TeacherData = {
    data() {
        return {
            name: data[1]
        }
    }
}
Vue.createApp(TeacherData).mount('#top')