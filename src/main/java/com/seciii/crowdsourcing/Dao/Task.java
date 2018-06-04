package com.seciii.crowdsourcing.Dao;

import java.io.*;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 下午8:30 2018/3/29
 */
public class Task {

    private String taskname;
    private String requestor;
    private String tasktag;
    private String description;
    private String mode;  //整体标注，方框标注，局部标注
    private String numOfNeeded;
    private String numOfPart;
    private String point;
    private String deadline;
    private String type;//任务类型
    private String labels;//标签
    //private String isClose;//任务状态，是否关闭

    public Task(){}

    public Task(String taskname,String requestor,String tasktag,String description,String mode,String numOfNeeded,String numOfPart,String point,String deadline,String type,String labels){
        this.taskname=taskname;
        this.requestor=requestor;
        this.tasktag=tasktag;
        this.description=description;
        this.mode=mode;
        this.numOfNeeded=numOfNeeded;
        this.numOfPart=numOfPart;
        this.point=point;
        this.deadline=deadline;
        this.type=type;
        this.labels=labels;
    }


    public Task(String requestor,String tasktag,String description,String mode,String numOfNeeded,String point,String deadline){
        String taskname="";
        try {
            String filename="src/main/java/com/seciii/crowdsourcing/Data/TaskId/TaskId.txt";
            File file=new File(filename);
            InputStreamReader srreader=new InputStreamReader(new FileInputStream(file));
            BufferedReader reader=new BufferedReader(srreader);
            taskname=reader.readLine();
            int newnum=Integer.parseInt(taskname)+1;
            System.out.print(newnum);

            FileWriter writer=new FileWriter(filename,false);
            BufferedWriter bw=new BufferedWriter(writer);
            bw.write(String.valueOf(newnum));
            bw.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        this.taskname=taskname;

        this.tasktag=tasktag;
        this.mode=mode;
        this.point=point;
        this.description=description;
        this.requestor=requestor;
        this.numOfNeeded=numOfNeeded;
        this.numOfPart="0";
        this.deadline=deadline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTasktag(String tasktag) {
        this.tasktag = tasktag;
    }

    public String getTasktag() {
        return tasktag;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getPoint() {
        return point;
    }

    public String getTaskname() {
        return taskname;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    public void setNumOfPart(String numOfPart) {
        this.numOfPart = numOfPart;
    }

    public void setNumOfNeeded(String numOfNeeded) {
        this.numOfNeeded = numOfNeeded;
    }

    public String getNumOfPart() {
        return numOfPart;
    }

    public String getNumOfNeeded() {
        return numOfNeeded;
    }

    public void setRequestor(String requestor) {
        this.requestor = requestor;
    }

    public String getRequestor() {
        return requestor;
    }

    public String getDeadline() {
        return deadline;
    }

    public String getMode() {
        return mode;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

//    public String getIsClose() {
//        return isClose;
//    }

    public String getLabels() {
        return labels;
    }

    public String getType() {
        return type;
    }

//    public void setIsClose(String isClose) {
//        this.isClose = isClose;
//    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public void setType(String type) {
        this.type = type;
    }
}

