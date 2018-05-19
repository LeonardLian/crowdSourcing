/**
 * Created by Leonarda on 2018/4/22.
 */

$(function () {
    var url=decodeURI(window.location.href);
    var username=url.split("?")[1];

    new Vue({
        el:'#user',
        data:{
            username:username
        }
    });

    $.get("http://127.0.0.1:8080/checkAllTasks",function (data) {

        //alert(data);

        var tasknum=0;

        var arr=data.split('!');

        if(data.length==0){
            return;
        }
        else{
            for(var x in arr){
                var infoList=arr[x].split('#');
                //alert(infoList.length);
                var taskname=infoList[0];
                //alert(taskname);
                var tasktag=infoList[2];
                //alert(tasktag);
                var numOfNeeded=infoList[5];
                //alert(numOfNeeded);
                var numOfPart=infoList[6];
                var deadline=infoList[8];

                var task=new Task(taskname,'aaa','aaa','aaa','aaa','aaa','aaa','aaa','aaa');
                var taskJson=JSON.stringify(task);

                // $.ajax({//判断用户和任务的关系：旁观者、参与者、发布者
                //
                // });

                $.ajax({//获取任务封面图案
                    type:'POST',
                    data:taskJson,
                    contentType:'application/json',
                    dataType:'text',
                    url:'http://127.0.0.1:8080/checkTaskImg',
                    success:function (data) {
                        var src=data.split(" ")[0];
                        $('#taskList').prepend('<li> <a href="javascript:taskdetailsClick(username,taskname);"> <img class="am-img-thumbnail am-img-bdrs" src="data:image/jpeg;base64,'+src+'" alt=""/> <div class="gallery-title">'+tasktag+'</div> <div class="gallery-desc">人数：'+numOfPart+'/'+numOfNeeded+'</div> <div class="gallery-desc">截止：'+deadline+'</div> </a> </li>');
                    },
                    error:function (e) {
                        alert('error');
                    }
                });

                //$('#taskList').prepend('<li> <a href="javascript:taskdetailsClick(username,taskname);"> <img class="am-img-thumbnail am-img-bdrs" src="data:image/jpeg;base64,'+result+'" alt=""/> <div class="gallery-title">任务名称：'+tasktag+'</div> <div class="gallery-desc">需要人数：'+numOfPart+'/'+numOfNeeded+'</div> <div class="gallery-desc">截止日期：'+deadline+'</div> </a> </li>');

                tasknum=tasknum+1;
            }
        }

        new Vue({
            el:'#tasknum',
            data:{
                tasknum:tasknum
            }
        })
    })
});

function taskdetailsClick(username,taskname){
    alert('true');
}

function Task(taskname,requestor,tasktag,description,mode,numOfNeeded,numOfPart,point,deadline) {
    this.taskname=taskname;//任务ID，由后端自主分配
    this.requestor=requestor;//发起者的username
    this.tasktag=tasktag;//任务名称，由发布任务时填写
    this.description=description;//任务描述
    this.mode=mode;//标注模式
    this.numOfNeeded=numOfNeeded;//需要人数，由发布任务时填写
    this.numOfPart=numOfPart;//参与人数，由后端统计修改
    this.point=point;//积分
    this.deadline=deadline;//截止日期
}