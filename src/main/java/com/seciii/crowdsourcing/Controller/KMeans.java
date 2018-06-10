package com.seciii.crowdsourcing.Controller;

import com.seciii.crowdsourcing.Dao.SquareLabel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class KMeans {
    private int k;
    private ArrayList<SquareLabel> dataSet;
    private Map<SquareLabel,ArrayList<SquareLabel>> oldCluster=new HashMap<>();
    private Map<SquareLabel,ArrayList<SquareLabel>> newCluster=new HashMap<>();

    public KMeans(){}

    //构造函数，用dataSet的前k个label作为键，初始化oldCluster，newCluster
    public KMeans(ArrayList<SquareLabel> dataSet,int k){
        this.k=k;
        this.dataSet=dataSet;

        for(int i=0;i<k;i++){
            oldCluster.put(dataSet.get(i),new ArrayList<SquareLabel>());
        }
        for(int i=0;i<dataSet.size();i++){
            SquareLabel close=getTheMostCloseSquareLabel(dataSet.get(i));
            oldCluster.get(close).add(dataSet.get(i));
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
            resultList.addAll(newCluster.get(key));
        }
        return resultList;
    }

    //获取距离label最近的键
    private SquareLabel getTheMostCloseSquareLabel(SquareLabel label){
        ArrayList<SquareLabel> oldKeyList=(ArrayList<SquareLabel>)oldCluster.keySet();
        double newX=Double.parseDouble(label.getStartX());
        double newY=Double.parseDouble(label.getStartY());
        SquareLabel result=oldKeyList.get(0);
        double oldDistance=0;
        for(SquareLabel key:oldKeyList){
            double oldX=Double.parseDouble(key.getStartX());
            double oldY=Double.parseDouble(key.getStartY());
            double newDistance=Math.sqrt((oldX-newX)*(oldX-newX)+(oldY-newY)*(oldY-newY));
            if(newDistance<oldDistance){
                result=key;
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
        ArrayList<SquareLabel> oldKey=(ArrayList<SquareLabel>)oldCluster.keySet();
        ArrayList<SquareLabel> newKey=(ArrayList<SquareLabel>)newCluster.keySet();

        for(int i=0;i<oldKey.size();i++){
            double oldX=Double.parseDouble(oldKey.get(i).getStartX());
            double oldY=Double.parseDouble(oldKey.get(i).getStartY());
            double newX=Double.parseDouble(newKey.get(i).getStartX());
            double newY=Double.parseDouble(newKey.get(i).getStartY());
            double distance=Math.sqrt((oldX-newX)*(oldX-newX)+(oldY-newY)*(oldY-newY));
            if(distance>1){
                return false;
            }
        }
        return true;
    }
}
