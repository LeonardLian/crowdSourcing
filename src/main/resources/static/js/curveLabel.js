/**
 * Created by Leonarda on 2018/3/24.
 */
function curveLabel(taskname,username) {
    CanvasExt.drawPen("myCanvas",taskname,username);
}

var layer=0;
CanvasExt={
    drawPen:function (canvasId,taskname,username) {
        var canvas=document.getElementById(canvasId);
        var canvasRect=canvas.getBoundingClientRect();

        var canvasLeft=canvasRect.left;
        var canvasTop=canvasRect.top;

        var sourceX=0;
        var sourceY=0;

        var layerIndex=layer;
        var layerName="layer";

        var dotArray=new Array();

        canvas.onmousedown=function (e) {

            var color='#000000';
            var width=2+'px';

            var startX=e.clientX;
            var startY=e.clientY;

            // var startstr='{' +'"x":' + startX + ',' + '"y":' +startY +'}';
            // var startjson=eval('(' + startstr + ')');

            // var startdot=new Curvedot(startX,startY);
            // var startjson=JSON.stringify(startdot);
            // dotArray.push(startjson);

            var startStr=startX+','+startY;
            dotArray.push(startStr)

            sourceX=e.clientX-canvasLeft;
            sourceY=e.clientY-canvasTop;

            canvas.onmousemove=function(e){
                layerIndex++;
                layer++;
                layerName=layerName+layerIndex;

                var moveX=e.clientX;
                var moveY=e.clientY;

                // var moveStr='{' +'"x":' + moveX + ',' + '"y":' +moveY +'}';
                //var movejson=eval('(' + moveStr + ')');

                // var newdot=new Curvedot(moveX,moveY);
                // var movejson=JSON.stringify(newdot);

                var dotStr=moveX+','+moveY;
                dotArray.push(dotStr);

                var currX=e.clientX-canvasLeft;
                var currY=e.clientY-canvasTop;

                $("#"+canvasId).drawLine({
                    layer:true,
                    name:layerName,
                    strokeStyle:color,
                    strokeWidth:width,
                    x1:sourceX,y1:sourceY,
                    x2:currX,y2:currY
                });

                sourceX=currX;
                sourceY=currY;
            }
        }
        canvas.onmouseup=function (e) {
            $("#"+canvasId).drawLayers().saveCanvas();
            canvas.onmousemove=null;
            //alert(dotArray[0]);
            var dotString=dotArray.join(' ');

            var curveLabel=new CurveLabel('2',comment,dotString,taskname,username);
            var curveLabelJson=JSON.stringify(curveLabel);
            $.ajax({
                type:'POST',
                data:curveLabelJson,
                contentType:'application/json',
                dataType:'json',
                url:'http://127.0.0.1:8080/saveCurveLabel',
                success:function (data) {
                    alert("success");
                },
                error:function(e){
                    alert("error");
                }
            });

        }
    }
}

function CurveLabel(type,comment,dotlist,taskname,username){
    this.type=type;
    this.comment=comment;
    this.dotlist=dotlist;
    this.taskname=taskname;
    this.username=username;
}