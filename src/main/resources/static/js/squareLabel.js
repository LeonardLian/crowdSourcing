/**
 * Created by Leonarda on 2018/3/22.
 */

function squarelabel(taskname,username) {

    // var oimg=document.getElementById('exampleImg');
    // oimg.style.opacity=0.5;
    // oimg.style.filter='alpha(opacity:50)';



    label();

    function label(e) {
        e=e||window.event;
        var startX, startY, diffX, diffY;
        var dragging=false;

        document.onmousedown = function(e) {
            startX = e.pageX;
            startY = e.pageY;

            if(e.target.id.match(/delbtn/)){
            }

            else if(e.target.className.match(/box/)) {

                dragging = true;

                if(document.getElementById("moving_box") !== null) {
                    document.getElementById("moving_box").removeAttribute("id");
                }
                e.target.id = "moving_box";

                diffX = startX - e.target.offsetLeft;
                diffY = startY - e.target.offsetTop;

            }
            else {
                var active_box = document.createElement("div");
                active_box.id = "active_box";
                active_box.className = "box";
                active_box.style.top = startY + 'px';
                active_box.style.left = startX + 'px';

                // var can=document.getElementById('canvas');
                // can.appendChild(active_box);
                document.body.appendChild(active_box);
                active_box = null;
            }
        };

        document.onmousemove = function(e) {
            if(document.getElementById("active_box") !== null) {
                var ab = document.getElementById("active_box");
                ab.style.width = e.pageX - startX + 'px';
                ab.style.height = e.pageY - startY + 'px';
            }

            if(document.getElementById("moving_box") !== null && dragging) {
                var mb = document.getElementById("moving_box");
                mb.style.top = e.pageY - diffY + 'px';
                mb.style.left = e.pageX - diffX + 'px';
            }
        };

        document.onmouseup = function(e) {

            dragging = false;
            if(document.getElementById("active_box") !== null) {
                var ab = document.getElementById("active_box");

                var delbtn=document.createElement("button");
                delbtn.id='delbtn';
                delbtn.value="X";
                delbtn.style.top=startY+'px';
                delbtn.style.left=startX+'px';
                delbtn.style.width=20+'px';
                delbtn.style.height=20+'px';
                ab.appendChild(delbtn);

                // var txt=document.createElement("textarea");
                // txt.id='write';
                // txt.style.top=startY+'px';
                // txt.style.left==(parseInt(startX)+21)+'px';
                // txt.style.height=20+'px';
                // txt.style.width=60+'px';
                // ab.appendChild(txt);
                var comment_box = document.createElement("textarea");
                comment_box.id = 'write';
                comment_box.style.top = startY + 'px';
                comment_box.style.left = (parseInt(startX)+21) + 'px';
                comment_box.style.height = ab.height;    //20 + 'px';
                comment_box.style.width = ab.width;     //20 + 'px';
                ab.appendChild(comment_box);

                var squareLabel=new SquareLabel('1',startX,startY,ab.style.width,ab.style.height,comment,taskname,username);
                var squareLabelJson=JSON.stringify(squareLabel);



                $.ajax({
                    type:'POST',
                    data:squareLabelJson,
                    contentType:'application/json',
                    dataType:'json',
                    url:'http://127.0.0.1:8080/saveSquareLabel',
                    success:function (data) {
                        alert("OK");
                    },
                    error:function(e){
                        alert("error");
                    }
                });


                delbtn.onclick=function () {
                    document.body.removeChild(ab);
                };
                //ab.removeAttribute("id");


                if(ab.offsetWidth < 10 || ab.offsetHeight < 10) {
                    document.body.removeChild(ab);
                }
            }
        };

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
}