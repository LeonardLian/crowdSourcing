package com.seciii.crowdsourcing.Dao;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 下午1:25 2018/4/14
 */
public class Taskkey {
    private String taskId;
    private String workerId;
    private String point;

    public Taskkey(){}
    public Taskkey(String taskId,String workerId,String point){
        this.taskId=taskId;
        this.workerId=workerId;
        this.point=point;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getPoint() {
        return point;
    }
}