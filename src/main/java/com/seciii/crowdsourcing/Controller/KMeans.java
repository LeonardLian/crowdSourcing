package com.seciii.crowdsourcing.Controller;

import com.seciii.crowdsourcing.Dao.SquareLabel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class KMeans {
    private int k;
    private ArrayList<SquareLabel> dataSet=new ArrayList<SquareLabel>();
    private Map<SquareLabel,ArrayList<SquareLabel>> oldCluster=new HashMap<>();
    private Map<SquareLabel,ArrayList<SquareLabel>> newCluster=new HashMap<>();

    public KMeans(){}

    //构造函数，用dataSet的前k个label作为键，初始化oldCluster，newCluster
    public KMeans(ArrayList<SquareLabel> dataSet,int k){
        this.k=k;
        for(SquareLabel label:dataSet){
            this.dataSet.add(new SquareLabel(label.getType(),label.getStartX(),label.getStartY(),label.getWidth(),label.getHeight(),label.getComment(),UrlController.task.getTaskname(),UrlController.user.getUsername()));
        }

        for(int i=0;i<k;i++){
            oldCluster.put(this.dataSet.get(i),new ArrayList<SquareLabel>());
        }
        for(int i=0;i<this.dataSet.size();i++){
            SquareLabel close=getTheMostCloseSquareLabel(this.dataSet.get(i));
            oldCluster.get(close).add(this.dataSet.get(i));
        }

        newCluster=oldCluster;
    }

    //获取聚类结果，当newCluster和oldCluster差距足够小时，将newCluster作为结果返回，迭代计算newCluster
    public ArrayList<SquareLabel> getResult() {
        while(!isCloseEnough()){
            calculateNewCluster();
        }
        ArrayList<SquareLabel> resultList=new ArrayList<>();
        for(SquareLabel key:newCluster.keySet()){
            resultList.add(key);
        }
        return resultList;
    }

    //获取距离label最近的键
    private SquareLabel getTheMostCloseSquareLabel(SquareLabel label){
        ArrayList<SquareLabel> oldKeyList=new ArrayList<>();
        for(SquareLabel key:oldCluster.keySet()){
            oldKeyList.add(key);
        }
        double newX=Double.parseDouble(label.getStartX());
        double newY=Double.parseDouble(label.getStartY());
        SquareLabel result=oldKeyList.get(0);
        double oldDistance=1000000;
        for(SquareLabel key:oldKeyList){
            double oldX=Double.parseDouble(key.getStartX());
            double oldY=Double.parseDouble(key.getStartY());
            double newDistance=Math.sqrt((oldX-newX)*(oldX-newX)+(oldY-newY)*(oldY-newY));
            if(newDistance<oldDistance){
                result=key;
                oldDistance=newDistance;
            }
        }
        return result;
    }

    //迭代计算newCluster
    private void calculateNewCluster(){
        oldCluster=newCluster;
        newCluster.clear();
        for(SquareLabel key:oldCluster.keySet()){
            double sumOfX=0;
            double sumOfY=0;
            SquareLabel newKey=new SquareLabel();
            ArrayList<SquareLabel> tmp=oldCluster.get(key);
            for(SquareLabel label:tmp){
                sumOfX+=Double.parseDouble(label.getStartX());
                sumOfY+=Double.parseDouble(label.getStartY());
            }
            newKey.setStartX(""+sumOfX/tmp.size());
            newKey.setStartY(""+sumOfY/tmp.size());
            newCluster.put(newKey,new ArrayList<SquareLabel>());
        }

        for(SquareLabel label:dataSet){
            SquareLabel close=getTheMostCloseSquareLabel(label);
            newCluster.get(close).add(label);
        }
    }

    private boolean isCloseEnough(){
        ArrayList<SquareLabel> oldKey=new ArrayList<>();
        for(SquareLabel key:oldCluster.keySet()){
            oldKey.add(key);
        }
        ArrayList<SquareLabel> newKey=new ArrayList<>();
        for(SquareLabel key:newCluster.keySet()){
            newKey.add(key);
        }

        for(int i=0;i<oldKey.size();i++){
            double oldX=Double.parseDouble(oldKey.get(i).getStartX());
            double oldY=Double.parseDouble(oldKey.get(i).getStartY());
            double newX=Double.parseDouble(newKey.get(i).getStartX());
            double newY=Double.parseDouble(newKey.get(i).getStartY());
            double distance=Math.sqrt((oldX-newX)*(oldX-newX)+(oldY-newY)*(oldY-newY));
            if(distance>0.1){
                return false;
            }
        }
        return true;
    }
}
