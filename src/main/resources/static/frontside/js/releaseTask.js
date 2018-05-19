/**
 * Created by Leonarda on 2018/4/27.
 */
/*
TODO 删除上传的图片
TODO html界面调整
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
})

//即时显示上传的图片
$('#image').on('change',function () {
    var fileObj = document.getElementById("image");
    var fileList = fileObj.files;
    for(var i=0;i<fileList.length;i++){
        var src = window.URL.createObjectURL(this.files[i]);
        $('#showPic').prepend('<li> <img class="am-img-thumbnail am-img-bdrs" src="'+src+'" alt="failed" width="1000"/> </li>');
    }
})

function release() {
    // if(saveImagesOfTask()==0){
    //     return;
    // }
    saveInfoOfTask();
}

function saveImagesOfTask() {
    var img_file=document.getElementById("image");
    var fileList=img_file.files;

    if(img_file.files.length==0){
        alert("请上传至少一张图片");
        return 0;
    }

    for(var i=0;i<fileList.length;i++) {
        var formData=new FormData();
        var fileObj=img_file.files[0];
        var url=decodeURI(window.location.href);
        var username=url.split("?")[1];
        formData.append("classIcon",fileObj);
        formData.append("className",username);

        $.ajax({
            url:"http://127.0.0.1:8080/saveTaskImg",
            type:'POST',
            data:formData,
            async:false,
            processData:false,
            contentType:false,
            success:function (data) {

            },
            error:function (e) {
                alert("error");
            }
        });
    }
}

function saveInfoOfTask(){
    var url=decodeURI(window.location.href);
    var username=url.split("?")[1];
    var taskname=$('#taskname').val();
    var mode=$('#mode').val();
    var numOfNeeded=$('#numofneed').val();//TODO 关于人数和积分的输入规范问题仍待解决
    var point=$('#point').val();
    var deadline=$('#deadline').val();//TODO 时间规范
    var description=$('#description').val();

    if(taskname==""){
        alert('请输入任务名称');
        return;
    }
    if(taskname.length>20){
        alert('任务名称过长');
        return;
    }
    if(numOfNeeded==""){
        alert('请输入需要人数');
        return;
    }
    if(point==""){
        alert('请输入奖励分数');
        return;
    }
    if(deadline==""){
        alert('请输入截止日期');
        return;
    }
    if(description==""){
        alert('描述不能为空');
        return;
    }

    var task=new Task('aaa',username,taskname,description,mode,numOfNeeded,'aaa',point,deadline);
    var taskJson=JSON.stringify(task);

    $.ajax({
        type:'POST',
        data:taskJson,
        contentType:'application/json',
        dataType:'text',
        url:'http://127.0.0.1:8080/saveTaskInfo',//TODO
        success:function (data) {
            var task_builded='task_builded.html'+'?'+username;
            window.location.href=task_builded;
            alert("发布成功");
        },
        error:function(e){
            alert("error");
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
    this.deadline=deadline;//截止日期
}