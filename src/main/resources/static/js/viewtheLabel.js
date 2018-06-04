/*
* 加载任务简介及当前批阅的工人、TODO 加载第一张图片
* TODO 查看上一张
* TODO 查看下一张
* TODO 给予积分奖励
 */
$(function () {
    var url=decodeURI(window.location.href);
    var usernameOfRequestor=url.split("?")[1];
    var taskname=url.split("?")[2];
    var username=url.split("?")[3];

    var task=new Task(taskname,'aa','aa','aa','aa','aa','aa','aa','aa');
    var taskJson=JSON.stringify(task);

    var taskInformation=null;
    $.ajax({
        type:'POST',
        data:taskJson,
        contentType:'application/json',
        dataType:'text',
        url:'http://127.0.0.1:8080/checkTaskInformation',
        async:false,
        success:function (data) {
            taskInformation=data.split('#');
        },
        error:function (e) {
            alert("info error");
        }
    });

    var tasktag=taskInformation[2];
    var taskdescription=taskInformation[3];
    var taskmode=taskInformation[4];
    var deadline=taskInformation[8];

    new Vue({
        el:'#taskInfo',
        data:{
            taskTag:tasktag,
            taskMode:taskmode,
            taskDescription:taskdescription,
            taskDeadline:deadline
        }
    })

    var workimg=null;
    var tKey=new Taskkey(taskname,username);
    var keyJson=JSON.stringify(tKey);
    $.ajax({
        type:'POST',
        data:keyJson,
        contentType:'application/json',
        dataType:'text',
        url:'http://127.0.0.1:8080/loadWorkerFile',
        async:false,
        success:function(data){
            if(data==""){
                workimg=null;
            }else {
                workimg=data;
            }
        },
        error:function (e) {
            alert("error!");
        }
    });

    if(workimg==null){
        alert("该工人还未提交作品");
    }
    else{
        $('#userWork').prepend('<li><img class="am-img-thumbnail am-img-bdrs" src="data:image/png;base64,'+workimg+'" alt=""/> </li>');
    }
});


function Taskkey(taskname,username) {
    this.taskname=taskname;
    this.username=username;
}

function Task(taskname,requestor,tasktag,description,mode,numOfNeeded,numOfPart,point,deadline){
    this.taskname=taskname;
    this.requestor=requestor;
    this.tasktag=tasktag;
    this.description=description;
    this.mode=mode;
    this.numOfNeeded=numOfNeeded;
    this.numOfPart=numOfPart;
    this.point=point;
    this.deadline=deadline;
}

function User(username, password, point, name, email, phone, description, taskAddress){
    this.username = username;
    this.password = password;
    this.point = point;
    this.name = name;
    this.email = email;
    this.phone = phone;
    this.description = description;
    this.taskAddress = taskAddress;
}




function rewardClick() {

}

function lastClick() {

}

function nextClick() {

}