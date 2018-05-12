/**
 * Created by Leonarda on 2018/3/22.
 */
// function curvelabel(){
//     var oimg=document.getElementById('exampleImg');
//     oimg.style.opacity=0.5;
//     oimg.style.filter='alpha(opacity:50)';
//
//     var odiv=document.getElementById('top');
//     odiv.style.display='block';
//     function a(){
//         var paint={
//             init:function()
//             {
//                 this.load();
//
//             },
//             load:function()
//             {
//                 this.x=[];
//                 this.y=[];
//                 this.clickDrag=[];
//                 this.lock=false;
//                 this.isEraser=false;
//                 this.storageColor="#000000";
//                 this.eraserRadius=15;
//                 this.color=["#000000","#FF0000","#80FF00","#00FFFF","#808080","#FF8000","#408080","#8000FF","#CCCC00"];
//                 this.fontWeight=[2,5,8];
//                 this.$=function(id){return typeof id=="string"?document.getElementById(id):id;};
//                 this.canvas=this.$("canvas");
//                 if (this.canvas.getContext) {
//                 } else {
//                     alert("您的浏览器不支持 canvas 标签");
//                     return;
//                 }
//                 this.cxt=this.canvas.getContext('2d');
//                 this.cxt.lineJoin = "round";
//                 this.cxt.lineWidth = 5;
//                 this.iptClear=this.$("clear");
//                 this.revocation=this.$("revocation");
//                 this.imgurl=this.$("imgurl");
//                 this.w=this.canvas.width;
//                 this.h=this.canvas.height;
//                 this.touch =("createTouch" in document);
//                 this.StartEvent = this.touch ? "touchstart" : "mousedown";
//                 this.MoveEvent = this.touch ? "touchmove" : "mousemove";
//                 this.EndEvent = this.touch ? "touchend" : "mouseup";
//                 this.bind();
//             },
//             bind:function()
//             {
//                 var t=this;
//
//
//                 this.iptClear.onclick=function()
//                 {
//                     t.clear();
//                 };
//
//
//                 this.canvas['on'+t.StartEvent]=function(e)
//                 {
//                     var touch=t.touch ? e.touches[0] : e;
//                     var _x=touch.clientX - touch.target.offsetLeft;
//
//                     var _y=touch.clientY - touch.target.offsetTop;
//                     if(t.isEraser)
//                     {
//                         t.resetEraser(_x,_y,touch);
//                     }else
//                     {
//                         t.movePoint(_x,_y);
//                         t.drawPoint();
//                     }
//                     t.lock=true;
//                 };
//
//
//                 this.canvas['on'+t.MoveEvent]=function(e)
//                 {
//                     var touch=t.touch ? e.touches[0] : e;
//                     if(t.lock)
//                     {
//                         var _x=touch.clientX - touch.target.offsetLeft;
//                         var _y=touch.clientY - touch.target.offsetTop;
//                         if(t.isEraser)
//                         {
//                             //if(t.Timer)clearInterval(t.Timer);
//                             //t.Timer=setInterval(function(){
//                             t.resetEraser(_x,_y,touch);
//                             //},10);
//                         }
//                         else
//                         {
//                             t.movePoint(_x,_y,true);
//                             t.drawPoint();
//                         }
//                     }
//                 };
//
//
//                 this.canvas['on'+t.EndEvent]=function(e)
//                 {
//                     t.lock=false;
//                     t.x=[];
//                     t.y=[];
//                     t.clickDrag=[];
//                     clearInterval(t.Timer);
//                     t.Timer=null;
//
//                 };
//
//
//                 this.revocation.onclick=function()
//                 {
//                     t.redraw();
//                 };
//
//
//                 this.changeColor();
//
//
//                 this.imgurl.onclick=function()
//                 {
//                     t.getUrl();
//                 };
//
//
//                 this.$("eraser").onclick=function(e)
//                 {
//                     t.isEraser=true;
//                     t.$("error").style.color="red";
//                     t.$("error").innerHTML="";
//                 };
//             },
//             movePoint:function(x,y,dragging)
//             {
//                 this.x.push(x);
//                 this.y.push(y);
//                 this.clickDrag.push(y);
//             },
//
//             drawPoint:function(x,y,radius)
//             {
//                 for(var i=0; i < this.x.length; i++)
//                 {
//                     this.cxt.beginPath();
//
//                     if(this.clickDrag[i] && i){
//                         this.cxt.moveTo(this.x[i-1], this.y[i-1]);
//                     }else{
//                         this.cxt.moveTo(this.x[i]-1, this.y[i]);
//                     }
//                     this.cxt.lineTo(this.x[i], this.y[i]);
//                     this.cxt.closePath();
//                     this.cxt.stroke();
//                 }
//             },
//             clear:function()
//             {
//                 this.cxt.clearRect(0, 0, this.w, this.h);
//             },
//             redraw:function()
//             {
//
//                 this.cxt.restore();
//
//             },
//             preventDefault:function(e){
//
//                 var touch=this.touch ? e.touches[0] : e;
//                 if(this.touch)touch.preventDefault();
//                 else window.event.returnValue = false;
//             },
//             changeColor:function()
//             {
//
//                 var t=this,iptNum=this.$("color").getElementsByTagName("input"),fontIptNum=this.$("font").getElementsByTagName("input");
//                 for(var i=0,l=iptNum.length;i<l;i++)
//                 {
//                     iptNum[i].index=i;
//                     iptNum[i].onclick=function()
//                     {
//                         t.cxt.save();
//                         t.cxt.strokeStyle = t.color[this.index];
//                         t.storageColor=t.color[this.index];
//                         t.$("error").style.color="#000";
//                         t.$("error").innerHTML="清除所有标记";
//                         t.cxt.strokeStyle = t.storageColor;
//                         t.isEraser=false;
//                     }
//                 }
//                 for(var i=0,l=fontIptNum.length;i<l;i++)
//                 {
//                     t.cxt.save();
//                     fontIptNum[i].index=i;
//                     fontIptNum[i].onclick=function()
//                     {
//                         t.changeBackground(this.index);
//                         t.cxt.lineWidth = t.fontWeight[this.index];
//                         t.$("error").style.color="#000";
//                         t.$("error").innerHTML="清除所有标记";
//                         t.isEraser=false;
//                         t.cxt.strokeStyle = t.storageColor;
//                     }
//                 }
//             },
//             changeBackground:function(num)
//             {
//                 var fontIptNum=this.$("font").getElementsByTagName("input");
//                 for(var j=0,m=fontIptNum.length;j<m;j++)
//                 {
//                     fontIptNum[j].className="";
//                     if(j==num) fontIptNum[j].className="grea";
//                 }
//             },
//             getUrl:function()
//             {
//                 this.$("html").innerHTML=this.canvas.toDataURL();
//             },
//             resetEraser:function(_x,_y,touch)
//             {
//                 var t=this;
//                 t.cxt.globalCompositeOperation = "destination-out";
//                 t.cxt.beginPath();
//                 t.cxt.arc(_x, _y, t.eraserRadius, 0, Math.PI * 2);
//                 t.cxt.strokeStyle = "rgba(250,250,250,0)";
//                 t.cxt.fill();
//                 t.cxt.globalCompositeOperation = "source-over"
//             }
//
//
//         };
//         paint.init();
//     };
//     a();
// }