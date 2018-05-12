package com.seciii.crowdsourcing.Dao;

import java.util.ArrayList;


/**
 * @author: pis
 * @description: good good study
 * @date: create in 下午3:39 2018/4/8
 */

public class CurveLabel {
    private String type;
    private String comment;
    private String dotlist;
    private String taskname;
    private String worker;

    public CurveLabel(String comment,String dotlist,String taskname,String worker){
        this.type="2";
        this.comment=comment;
        this.dotlist=dotlist;
        this.taskname=taskname;
        this.worker=worker;
    }
    public CurveLabel(){}

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setDotlist(String dotlist) {
        this.dotlist = dotlist;
    }

    public String getDotlist() {
        return dotlist;
    }

    public String getType() {
        return type;
    }

    public String getTaskname() {
        return taskname;
    }

    public String getWorker() {
        return worker;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }
}
