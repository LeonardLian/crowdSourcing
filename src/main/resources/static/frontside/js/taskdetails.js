/*
* 初始化，显示任务详细信息，包括图片和信息
* 参与任务后的相关响应：用户与任务的相关参数修改，页面跳转至工作界面
*/
$(function () {
    var url=decodeURI(window.location.href);
    var username=url.split("?")[1];
    var taskname=url.split("?")[2];

    new Vue({
        el:'#user',
        data:{
            username:username
        }
    });

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
                el:"#info",
                data:{
                    requester:infoList[1],
                    taskName:infoList[2],
                    mode:infoList[4],
                    numOfNeeded:infoList[5],
                    numOfPart:infoList[6],
                    point:infoList[7],
                    deadline:infoList[8]
                }
            });
            new Vue({
                el:"#description",
                data:{
                    description:infoList[3]
                }
            });
        },
        error:function(e){
            alert("error");
        }
    });

    $.ajax({
        type:'POST',
        data:taskJson,
        contentType:'application/json',
        dataType:'text',
        url:'http://127.0.0.1:8080/checkTaskImg',
        success:function (data) {
            var imageList=data.split(" ");
            for(var i in imageList){
                var src='data:image/jpeg;base64,'+imageList[i];
                $('#showImage').prepend('<li> <img class="am-img-thumbnail am-img-bdrs" src="'+src+'" alt="failed" width="1000"/> </li>');
            }
            $('#cover').attr("src","data:image/jpeg;base64,"+imageList[0]);
        },
        error:function(e){
            alert("error");
        }
    });

});

function join() {
    var url=decodeURI(window.location.href);
    var username=url.split("?")[1];
    var taskname=url.split("?")[2];

    var taskkey=new Taskkey(username,taskname);
    var taskkeyJson=JSON.stringify(taskkey);

    $.ajax({
        type:'POST',
        data:taskkeyJson,
        contentType:'application/json',
        dataType:'text',
        url:'http://127.0.0.1:8080/participateIn',
        success:function (data) {
            var work='work.html'+'?'+username+'?'+taskname;
            window.location.href=work;
        },
        error:function (e) {
            alert('error');
        }
    });
}

function Task(taskname,requestor,tasktag,description,mode,numOfNeeded,numOfPart,point,deadline) {
    this.taskname=taskname;//任务ID，由后端自主分配
    this.requestor=requestor;//发起者的username
    this.tasktag=tasktag;//任务名称，由发布任务时填写
    this.description=description;//任务描述
    this.mode=mode;//标注模式
    this.numOfNeed=numOfNeeded;//需要人数，由发布任务时填写
    this.numOfPart=numOfPart;//参与人数，由后端统计修改
    this.point=point;//积分
    this.deadline=deadline;//截止日期，格式为xxxx-xx-xx
}

function Taskkey(username,taskname){
    this.username=username;
    this.taskname=taskname;
}