package com.seciii.crowdsourcing.Dao;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 下午4:07 2018/4/8
 */
public class SquareLabel {
    private String type;
    private String startX;
    private String startY;
    private String width;
    private String height;
    private String comment;
    private String taskname;
    private String worker;

    public SquareLabel(){}

    public SquareLabel(String startX,String startY,String width,String height,String comment,String taskname,String worker){
        this.type="1";
        this.startX=startX;
        this.startY=startY;
        this.width=width;
        this.height=height;
        this.comment=comment;
        this.taskname=taskname;
        this.worker=worker;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getWidth() {
        return width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getStartX() {
        return startX;
    }

    public String getStartY() {
        return startY;
    }

    public String getTaskname() {
        return taskname;
    }

    public String getType() {
        return type;
    }

    public void setStartX(String startX) {
        this.startX = startX;
    }

    public void setStartY(String startY) {
        this.startY = startY;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    public String getWorker() {
        return worker;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }
}
