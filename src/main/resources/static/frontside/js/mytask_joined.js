/**
 * mxf
 */
$(function(){
    var url = decodeURI(window.location.href);
    var username = url.split("?")[1];

    new Vue({
        el:'#user',
        data:{
            username:username
        }
    });

    Vue.component('myJoinedTask', {
        tenplate:' <li><a href = "taskdetails.html"> <img class="am-img-thumbnail am-img-bdrs" v-bind:src="imgaddress" alt=""/> ' +
        '<div class="gallery-title">{{description}}</div> <div class="gallery-desc-time">{{time}}</div> <div class="gallery-desc-process">{{process}}</div></a> </li>',
        props:['imgaddress','description','time','process']
    });

    new Vue({
        el : '#myJoinedTaskList'
    });

    $.get("http://127.0.0.1:8080/checkJoinedTasks",function (data) {

        var joinedTaskNum = 0;

        var arr = data.split("!");
        for(x in arr){
            var taskarr = arr[x].split(" ");
            var imgAddress = taskarr[3];
            var description = taskarr[2];
            var partofneeded = '需要人数： '+ taskarr[4];
            var taskProcess = '完成进度： ' + taskarr[5]
            var taskname = taskarr[0];

            $('#myJoinedTaskList').prepend('<li> <a href="taskdetails.html?'+taskname+'"'+'> <img class="am-img-thumbnail am-img-bdrs" src="'+imgAddress+'" alt=""/> <div class="gallery-title">'+description+'</div> <div class="gallery-desc">'+partofneeded+'</div>  </a> </li>');

            joinedTaskNum = joinedTaskNum + 1;

            new Vue({
                el: '#joinedTaskNum',
                data:{
                    joinedTaskNum: JoinedTaskNum
                }
            })
        }

    })
});