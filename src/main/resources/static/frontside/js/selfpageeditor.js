function saveImage() {
    //var form=document.getElementById("imageForm")
    //var formData=new formData(form);
    var formData=new formData();
    var img_file=document.getElementById("image");
    var fileObj=img_file.files[0];
    var url=decodeURI(window.location.href);
    var username=url.split("?")[1];
    formData.append("classIcon",fileObj);
    /*formData.append("classDescribe",username);*/

    $.ajax({
        url:"/json/saveUserImg.json",
        type:'POST',
        data:formData,
        async:false,
        processData:false,
        contentType:false,
        success:function (e) {
            alert("success");
        },
        error:function (e) {
            alert("error")
        }
    });
}