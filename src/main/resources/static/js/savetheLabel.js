/**
 * Created by Leonarda on 2018/6/1.
 */
function savetheLabel(){
    var url=decodeURI(window.location.href);
    var username=url.split("?")[1];
    var taskname=url.split("?")[2];

    var bot=document.getElementById("bottom");
    var canvas=document.getElementById("myCanvas");

    var task=new Task(taskname,'aa','aa','aa','aa','aa','aa','aa','aa');
    var taskJson=JSON.stringify(task);
    //alert(taskJson);
    var taskInformation=null;
    $.ajax({
        type:'POST',
        data:taskJson,
        contentType:'application/json',
        dataType:'text',
        url:'http://127.0.0.1:8080/checkTaskInformation',
        async:false,
        success:function (data) {
            alert("success");
            taskInformation=data.split('#');
        },
        error:function (e) {
            alert("info error");
        }
    });

    var taskmode=taskInformation[4];

    //alert(taskmode)
    if(taskmode=='方框标注'){
        var layers=$('#myCanvas').getLayers();
        for(var i=0;i<layers.length;i++){
            var name=layers[i].name;
            var x=layers[i].x;
            var y=layers[i].y;
            var width=layers[i].width;
            var height=layers[i].height;

            //alert(name);

            var comment=$('#'+name).val();

            var squareLabel=new SquareLabel('1',x,y,width,height,comment,taskname,username);
            var squareLabelJson=JSON.stringify(squareLabel);

            //alert(squareLabelJson);

            $.ajax({
                type:'POST',
                data:squareLabelJson,
                contentType:'application/json',
                dataType:'text',
                url:'http://127.0.0.1:8080/saveSquareLabel',
                success:function(data){
                    alert(data);
                },
                error:function (e) {
                    alert("error!????");
                }
            });
        }
    }
    else if(taskmode=='整体标注'){
        var text=$('#description').html();

        var wholeLabel=new WholeLabel('0',text,taskname,username);
        var wholeLabelJson=JSON.stringify(wholeLabel);
        $.ajax({
            type:'POST',
            data:wholeLabelJson,
            contentType:'application/json',
            url:'http://127.0.0.1:8080/saveWholeLabel',
            dataType:'text',
            async:false,
            success:function(data){
                alert(data);
            },
            error:function (e) {
                alert("error!!!!!");
            }
        });
    }
    else if(taskmode=='局部标注'){
        var layers=$('#myCanvas').getLayers();

        var index=0;

        for(var i=0;i<layers.length;i++){
            var name=layers[i].name;
            // var x1=layers[i].x1;
            // var y1=layers[i].y1;
            // var x2=layers[i].x2;
            // var y2=layers[i].y2;

            if(document.getElementById(name)){
                var array=new Array();
                var comment=$('#'+name).val();

                for(var j=index;j<i;j++){
                    var x1=layers[j].x1;
                    var y1=layers[j].y1;
                    var dot=x1+','+y1;
                    array.push(dot);
                }
                var xi=layers[i].x1;
                var yi=layers[i].y1;
                var x2=layers[i].x2;
                var y2=layers[i].y2;
                var dot1=xi+','+yi;
                var dot2=x2+','+y2;
                array.push(dot1);
                array.push(dot2);
                index=i+1;

                var dotList=array.join('!');

                var curveLabel=new CurveLabel('2',comment,dotList,taskname,username);
                var curveLabelJson=JSON.stringify(curveLabel);

                $.ajax({
                    type:'POST',
                    data:curveLabelJson,
                    contentType:'application/json',
                    url:'http://127.0.0.1:8080/saveCurveLabel',
                    dataType:'text',
                    async:false,
                    success:function(data){
                        alert(data);
                    },
                    error:function (e) {
                        alert("error!");
                    }
                });
            }
            else{
                alert("!!!!");
            }
        }
    }
    else{alert("?>???");}
}

function SquareLabel(type,startX,startY,width,height,comment,taskname,username) {
    this.type=type;
    this.startX=startX;
    this.startY=startY;
    this.width=width;
    this.height=height;
    this.comment=comment;
    this.taskname=taskname;
    this.username=username;
}

function WholeLabel(type,comment,taskname,username){
    this.type=type;
    this.comment=comment;
    this.taskname=taskname;
    this.username=username;
}

function CurveLabel(type,comment,dotlist,taskname,username) {
    this.type=type;
    this.comment=comment;
    this.dotlist=dotlist;
    this.taskname=taskname;
    this.username=username;
}
