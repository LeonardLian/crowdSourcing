//TODO 初始化，显示任务详细信息
//TODO 参与任务后的相关响应：用户与任务的相关参数修改，页面跳转至工作界面

$(function () {
    var url=decodeURI(window.location.href);
    var username=url.split("?")[1];
    var taskname=url.split("?")[2];

    new Vue({
        el:'#username',
        data:{
            username:username
        }
    });


});