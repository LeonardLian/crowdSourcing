/**
 * Created by Leonarda on 2018/5/26.
 */
function submittheLabel(){
    var url=decodeURI(window.location.href);
    var username=url.split("?")[1];
    var taskname=url.split("?")[2];



    var taskkey=new Taskkey(taskname,username);
    var taskkeyJson=JSON.stringify(taskkey);

    $.ajax({
        type:'POST',
        data:taskkeyJson,
        contentType:'application/json',
        dataType:'text',
        url:'http://127.0.0.1:8080/submittheLabel',
        async:false,
        success:function (data) {
            alert(data);
        },
        error:function (e) {
            alert("error");
        }
    });

}

function Taskkey(taskname,username) {
    this.taskname=taskname;
    thsi.username=username;
}