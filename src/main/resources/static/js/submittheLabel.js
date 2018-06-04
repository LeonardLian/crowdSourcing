/**
 * Created by Leonarda on 2018/5/26.
 */
function submittheLabel(){
    var url=decodeURI(window.location.href);
    var username=url.split("?")[1];
    var taskname=url.split("?")[2];

    var formdata=new FormData();
    var canvas=document.getElementById("myCanvas");
    var basestr=canvas.toDataURL("image/png");

    formdata.append("label",basestr);
    formdata.append("username",username);
    formdata.append("taskname",taskname);

    $.ajax({
        url:"http://127.0.0.1:8080/submittheLabel",
        type:'POST',
        data:formdata,
        async:false,
        processData:false,
        contentType:false,
        success:function (data) {
            alert("success");
        },
        error:function (e) {
            alert("error");
        }
    });

}