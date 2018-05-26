/**
 * Created by Leonarda on 2018/5/26.
 */
function submittheLabel(){
    var url=decodeURI(window.location.href);
    var username=url.split("?")[1];
    var taskname=url.split("?")[2];

    var task=new Task(taskname,'aa','aa','aa','aa','aa','aa','aa','aa');
    var taskJson=JSON.stringify(task);

}