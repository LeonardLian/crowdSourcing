/**
 * Created by Leonarda on 2018/6/3.
 */
function givethePoint() {
    var usernameOfRequestor;
    $.ajax({
        type:'POST',
        dataType:'text',
        url:'/getUsername',
        async:false,
        success:function(data){
            usernameOfRequestor=data;
        },
        error:function (e) {
            alert("error!");
        }
    });
    var taskname;
    $.ajax({
        type:'POST',
        dataType:'text',
        url:'/getTaskname',
        async:false,
        success:function(data){
            taskname=data;
        },
        error:function (e) {
            alert("error!");
        }
    });
    var usernameOfWorker;
    $.ajax({
        type:'POST',
        dataType:'text',
        url:'/getUsernameOfWorker',
        async:false,
        success:function(data){
            usernameOfWorker=data;
        },
        error:function (e) {
            alert("error!");
        }
    });
}