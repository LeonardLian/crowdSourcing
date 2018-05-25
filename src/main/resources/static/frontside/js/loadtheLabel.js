/**
 * Created by Leonarda on 2018/5/23.
 */
$(function () {
    var url=decodeURI(window.location.href);
    var username=url.split("?")[1];
    var taskname=url.split("?")[2];

    var task=new Task(taskname,'aa','aa','aa','aa','aa','aa','aa','aa');
    var taskJson=JSON.stringify(task);

    var imgBase=null;
    $.ajax({
        type:'POST',
        data:taskJson,
        contentType:'application/json',
        dataType:'text',
        url:'http://127.0.0.1:8080/checkTaskImg',
        async:false,
        success:function (data) {
            imgBase=data.split(" ");
        },
        error:function (e) {
            alert("img error");
        }
    });


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


    var c = document.getElementById("myCanvas");
    var ctx=c.getContext("2d");
    var img = new Image();
    img.src = 'data:image/jpeg;base64,'+imgBase[0];

    img.onload = function() {
        var FitWidth = c.width;
        var FitHeight = c.height;
        var _width = img.width;
        var _height = img.height;
        if(_width <= FitWidth && _height <= FitHeight){
            var offset_w = (FitWidth - _width)/2;
            var offset_h = (FitHeight - _height)/2;
            ctx.drawImage(img, offset_w, offset_h);

        }
        else {
            if (_width / _height >= FitWidth / FitHeight) {
                if (_width > FitWidth) {
                    img.width = FitWidth;
                    img.height = (_height * FitWidth) / _width;
                }
            }
            else {
                if (_height > FitHeight) {
                    img.height = FitHeight;
                    img.width = (_width * FitHeight) / _height;
                }
            }
            ctx.drawImage(img, 0, 0);
        }
    };


    //显示之前保存的标注
    var labelList;
    var tKey=new Taskkey(username,taskname);
    var keyJson=JSON.stringify(tKey);
    $.ajax({
        type:'POST',
        data:keyJson,
        contentType:'application/json',
        dataType:'text',
        url:'http://127.0.0.1:8080/loadTemporaryFile',
        async:false,
        success:function(data){
            if(data==null){
                labelList=null;
            }else {
                labelList=data.split('#');
            }
        },
        error:function (e) {
            alert("error!");
        }
    });

    if(labelList==null){
    }
    else{
        for(var i=0;i<labelList.length;i++){
            var jsonLabel=eval('(' + labelList[i] + ')');
            var mode=jsonLabel.type;

            if(mode=='0'){
                //TODO
            }
            else if(mode=='1'){
                var oDiv=document.createElement("div");
                oDiv.style.top=jsonLabel.startY+'px';
                oDiv.style.left=jsonLabel.startX+'px';
                oDiv.style.width=jsonLabel.width;
                oDiv.style.height=jsonLabel.height;
                oDiv.style.background='transparent';
                oDiv.style.border='3px solid black';
                oDiv.style.position='absolute';
                document.body.appendChild(oDiv);

            }
            else if(mode=='2'){
                var dotList=(jsonLabel.dotlist).split(' ');
                for(var j=0;j<dotList.length;j++){
                    var x=dotList[j].split(',')[0];
                    var y=dotList[j].split(',')[1];
                    var oDiv=document.createElement("div");
                    oDiv.style.top=y+'px';
                    oDiv.style.left=x+'px';
                    oDiv.style.background='black';
                    oDiv.style.width='2px';
                    oDiv.style.height='2px';
                    oDiv.dragging='false';
                    oDiv.style.position='absolute';
                    document.body.appendChild(oDiv);
                }
            }
            else{}

        }
    }


    //工作界面敲定
    if(taskmode=="整体标注"){
        wholeLabel();
    }
    else if(taskmode=="方框标注"){
        squarelabel();
    }
    else if(taskmode=="局部标注"){
        curveLabel();
    }
    else{}

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
