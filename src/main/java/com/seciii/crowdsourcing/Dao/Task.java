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
    private String img; //地址
    private String numOfNeeded;
    private String numOfPart;
    private String kindOfLabel;
    private String point;

    public Task(){}

    public Task(String taskname,String requestor,String tasktag,String description,String img,String numOfNeeded,String numOfPart,String kindOfLabel,String point){
        this.taskname=taskname;
        this.requestor=requestor;
        this.tasktag=tasktag;
        this.description=description;
        this.img=img;
        this.numOfNeeded=numOfNeeded;
        this.numOfPart=numOfPart;
        this.kindOfLabel=kindOfLabel;
        this.point=point;
    }

    public Task(String requestor,String tasktag,String description,String img,String numOfNeeded,String point,String kindOfLabel){
        String taskname="";
        try {
            String filename="src/main/java/com/seciii/crowdsourcing/Data/TaskId/TaskId.txt";
            File file=new File(filename);
            InputStreamReader srreader=new InputStreamReader(new FileInputStream(file));
            BufferedReader reader=new BufferedReader(srreader);
            taskname=reader.readLine();
            int newnum=Integer.parseInt(taskname)+1;

            FileWriter writer=new FileWriter(filename,false);
            BufferedWriter bw=new BufferedWriter(writer);
            bw.write(newnum);
            bw.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        this.taskname=taskname;

        this.tasktag=tasktag;
        this.point=point;
        this.description=description;
        this.img=img;
        this.requestor=requestor;
        this.numOfNeeded=numOfNeeded;
        this.numOfPart="0";
        this.kindOfLabel=kindOfLabel;
    }

    public String getKindOfLabel() {
        return kindOfLabel;
    }

    public void setKindOfLabel(String kindOfLabel) {
        this.kindOfLabel = kindOfLabel;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getTasktag() {
        return tasktag;
    }

    public void setTasktag(String tasktag) {
        this.tasktag = tasktag;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDescription() {
        return description;
    }

    public String getRequestor() {
        return requestor;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRequestor(String requestor) {
        this.requestor = requestor;
    }

    public String getNumOfNeeded() {
        return numOfNeeded;
    }

    public String getNumOfPart() {
        return numOfPart;
    }

    public void setNumOfNeeded(String numOfNeeded) {
        this.numOfNeeded = numOfNeeded;
    }

    public void setNumOfPart(String numOfPart) {
        this.numOfPart = numOfPart;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    public String getTaskname() {
        return taskname;
    }
}
