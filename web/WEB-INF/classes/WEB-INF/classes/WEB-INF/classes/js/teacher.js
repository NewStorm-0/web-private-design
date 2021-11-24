'use strict';
var data = JSON.parse(sessionStorage.getItem("json"));
const TeacherData = {
    data() {
        return {
            name: data[4]
        }
    }
}
Vue.createApp(TeacherData).mount('#teacher-name')