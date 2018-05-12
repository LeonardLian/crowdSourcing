/**
 * Created by Leonarda on 2018/3/24.
 */
function curveLabel() {
    CanvasExt.drawPen("canvas");
}

var layer=0;
CanvasExt={
    drawPen:function (canvasId) {
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

            var startdot=new Curvedot(startX,startY);
            var startjson=JSON.stringify(startdot);
            dotArray.push(startjson);

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

                var newdot=new Curvedot(moveX,moveY);
                var movejson=JSON.stringify(newdot);
                dotArray.push(movejson);

                var currX=e.clientX-canvasLeft;
                var currY=e.clientY-canvasTop;

                $("#"+canvasId).drawLine({
                    layer:true,
                    name:layerName,
                    strokeStyle:color,
                    strokeWidth:width,
                    x1:sourceX,y1:sourceY,
                    x2:currX,y2:currY
                })

                sourceX=currX;
                sourceY=currY;
            }
        }
        canvas.onmouseup=function (e) {
            $("#"+canvasId).drawLayers().saveCanvas();
            canvas.onmousemove=null;
            //alert(dotArray[0]);
            for(var i=0;i<dotArray.length;i++){
                $.ajax({
                    type:'POST',
                    data:dotArray[i],
                    contentType:'application/json',
                    dataType:'json',
                    url:'http://127.0.0.1:8080/CurveDot',
                    success:function (data) {
                        alert("success");
                    },
                    error:function(e){
                        alert("error");
                    }
                })
            }
        }
    }
}

function Curvedot(x,y) {
    this.x=x;
    this.y=y;
}