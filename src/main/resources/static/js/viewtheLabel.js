/*
* 加载任务简介及当前批阅的工人、TODO 加载第一张图片
* TODO 查看上一张
* TODO 查看下一张
* TODO 给予积分奖励
 */
$(function () {
    var url=decodeURI(window.location.href);
    var username=url.split("?")[1];
    var taskname=url.split("?")[2];
    var usernameOfWorker=url.split("?")[3];

    var task=new Task(taskname,'aaa','aaa','aaa','aaa','aaa','aaa','aaa','aaa');
    var taskJson=JSON.stringify(task);
    $.ajax({
        type:'POST',
        data:taskJson,
        contentType:'application/json',
        dataType:'text',
        url:'http://127.0.0.1:8080/checkTaskInformation',
        success:function (data) {
            var infoList=data.split("#");
            new Vue({
                el:"#taskInfo",
                data:{
                    taskTag:infoList[2],
                    taskDescription:infoList[3],
                    worker:usernameOfWorker
                }
            });
        },
        error:function(e){
            alert("error");
        }
    });


});

function rewardClick() {

}

function lastClick() {

}

function nextClick() {

}