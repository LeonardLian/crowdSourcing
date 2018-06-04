/**
 * Created by Leonarda on 2018/4/22.
 */
/*
* 对应mainpage.html
*
* 初始化header的用户名
* 加载所有任务，包括图片和任务信息
 */
$(function () {
    var username;

    $.get("http://127.0.0.1:8080/getUsername",function (data) {
        username=data;
        new Vue({
            el:'#user',
            data:{
                username:username
            }
        });
    });

    $.get("http://127.0.0.1:8080/checkAllTasks",function (data) {
        var tasknum=0;

        var arr=data.split('!');

        if(data.length==0){
            return;

        }
        else{
            for(var x in arr){
                var infoList=arr[x].split('#');
                var taskname=infoList[0];
                var tasktag=infoList[2];
                var numOfNeeded=infoList[5];
                var numOfPart=infoList[6];
                var deadline=infoList[8];

                var task=new Task(taskname,'aaa','aaa','aaa','aaa','aaa','aaa','aaa','aaa');
                var taskJson=JSON.stringify(task);

                var src;
                $.ajax({//获取任务封面图案
                    type:'POST',
                    data:taskJson,
                    contentType:'application/json',
                    dataType:'text',
                    url:'http://127.0.0.1:8080/checkTaskImg',
                    async:false,
                    success:function (data) {
                        src=data.split(" ")[0];
                     },
                    error:function (e) {
                        alert('error');
                    }
                });

                var taskkey=new Taskkey(taskname,username);
                var taskkeyJson=JSON.stringify(taskkey);

                var url='#';
                $.ajax({//判断用户和任务的关系：旁观者、参与者、发布者 TODO
                    type:'POST',
                    data:taskkeyJson,
                    contentType:'application/json',
                    dataType:'text',
                    url:'http://127.0.0.1:8080/judgeRelation',
                    async:false,
                    success:function (data) {
                        if(data=='0'){//旁观者
                            url='taskdetails'+'/'+taskname;
                        }else if(data=='2'){//参与者
                            url='work'+'/'+taskname;
                        }else if(data=='1'){//发布者
                            url='taskdetails_requestor'+'/'+taskname;
                        }
                    },
                    error:function (e) {
                        alert('error');
                    }
                });

                $('#taskList').prepend('<li id="app"> <a href="'+url+'"> <img class="am-img-thumbnail am-img-bdrs" src="data:image/jpeg;base64,'+src+'" alt=""/> <div class="gallery-title">'+tasktag+'</div> <div class="gallery-desc">人数：'+numOfPart+'/'+numOfNeeded+'</div> <div class="gallery-desc">截止：'+deadline+'</div> </a> </li>');

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


function releaseTaskClick() {
    window.location.href="/releaseTask";
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

function Taskkey(taskname,username) {
    this.taskname=taskname;
    this.username=username;
}