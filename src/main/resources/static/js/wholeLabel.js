/**
 * Created by Leonarda on 2018/5/26.
 */

function wholeLabel(taskname,username) {
    var comment=$('#description').text();

    var wholelabel=new WholeLabel('0',comment,taskname,username);
    var wholelabelJson=JSON.stringify(wholelabel);
    $.ajax({
        type:'POST',
        data:wholelabelJson,
        contentType:'application/json',
        dataType:'json',
        url:'http://127.0.0.1:8080/saveWholeLabel',
        success:function (data) {
            alert("success");
        },
        error:function(e){
            alert("error");
        }
    });

}


function WholeLabel(type,comment,taskname,username) {
    this.type=type;
    this.comment=comment;
    this.taskname=taskname;
    this.username=username;
}