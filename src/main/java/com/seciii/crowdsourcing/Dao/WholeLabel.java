package com.seciii.crowdsourcing.Dao;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 下午5:19 2018/4/8
 */
public class WholeLabel {
    private String type;
    private String comment;
    private String taskname;
    private String worker;

    public WholeLabel(String comment,String taskname,String worker){
        this.type="0";
        this.comment=comment;
        this.taskname=taskname;
        this.worker=worker;
    }
    public WholeLabel(){}

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public String getWorker() {
        return worker;
    }

    public String getTaskname() {
        return taskname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }
}
