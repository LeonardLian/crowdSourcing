package com.seciii.crowdsourcing.Controller;

import com.seciii.crowdsourcing.Dao.*;
import com.seciii.crowdsourcing.Dao.User;
import org.apache.tomcat.jni.*;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.io.File;
import java.util.ArrayList;

/**
 * @author: pis
 * @description: 任务管理和处理
 * @date: create in 上午9:53 2018/4/9
 */

@CrossOrigin
@RestController
public class TaskController {


    //发布任务
    @RequestMapping(value = "/releaseTask",method = RequestMethod.POST)
    public String releaseTask(@RequestBody Task task) throws IOException{
        String requestor=task.getRequestor();
        String description=task.getDescription();
        String img=task.getImg();
        String numofNeeded=task.getNumOfNeeded();
        String tasktag=task.getTasktag();
        String point=task.getPoint();

        Task task1=new Task(requestor,tasktag,description,img,numofNeeded,point);

        String taskname=task1.getTaskname();
        String foldername="src/main/java/com/seciii/crowdsourcing/Data/TaskList/"+taskname;
        File folder=new File(foldername);
        if(!folder.exists()){
            folder.mkdirs();
        }


        String taskInformationFile="src/main/java/com/seciii/crowdsourcing/Data/TaskList/"+taskname+"/description.txt";

        String alltaskInfo="src/main/java/com/seciii/crowdsourcing/Data/TaskInformation/TaskInformation.txt";

        String desStr="{"+
                "\"taskname\":"+"\""+task.getTaskname()+"\""+","+
                "\"requestor\""+"\""+task.getRequestor()+"\""+","+
                "\"description\""+"\""+task.getDescription()+"\""+","+
                "\"img\""+"\""+task.getImg()+"\""+","+
                "\"numOfNeeded\""+"\""+task.getNumOfNeeded()+"\""+","+
                "\"numOfPart\""+"\""+task.getNumOfPart()+"\""+
                "}";

        File file=new File(taskInformationFile);
        if(!file.exists()){
            file.createNewFile();
        }
        FileWriter writer=new FileWriter(taskInformationFile,false);
        BufferedWriter bw=new BufferedWriter(writer);
        bw.write(desStr);
        bw.close();

        String str=task.getTaskname()+" "+task.getRequestor()+" "+task.getDescription()+" "+task.getImg()+" "+task.getNumOfNeeded()+" "+task.getNumOfPart()+"\n";
        FileWriter fw=new FileWriter(alltaskInfo,true);
        BufferedWriter bww=new BufferedWriter(fw);
        bww.write(str);
        bww.close();

        String userfile="src/main/java/com/seciii/crowdsourcing/Data/UserTaskIndexList/"+requestor+".txt";
        FileWriter fileWriter=new FileWriter(userfile,true);
        BufferedWriter bwww=new BufferedWriter(fileWriter);
        bwww.write("b"+task.getTaskname()+" ");

        return "Success";
    }


    //查看一个任务的分发情况
    @RequestMapping(value = "/checkTask",method = RequestMethod.POST)
    public String checkTask(@RequestBody Task task){
        String taskname=task.getTaskname();
        String foldername="src/main/java/com/seciii/crowdsourcing/Data/TaskList/"+taskname;
        File folder=new File(foldername);
        String[] files=folder.list();

        String workerId="";
        for(String file:files){
            if(file.equals("description.txt")){
            }
            else{
                String id=file.split(".")[0];
                workerId=workerId+id+" ";
            }
        }

        return workerId;
    }


    //查看一个工人的作品
    @RequestMapping(value = "/checkWorker",method = RequestMethod.POST)
    public String checkWorker(@RequestBody Taskkey taskkey) throws IOException{
        String task=taskkey.getTaskId();
        String worker=taskkey.getWorkerId();

        String filename="src/main/java/com/seciii/crowdsourcing/Data/TaskList/"+task+"/"+worker+".txt";
        File file=new File(filename);
        InputStreamReader reader=new InputStreamReader(new FileInputStream(file));
        BufferedReader br=new BufferedReader(reader);
        String line=br.readLine();

        return line;
    }


//    //修改自己发布的任务要求
//    @RequestMapping(value = "/polishDemand",method = RequestMethod.POST)
//    public String polishDemand(@RequestBody Task task){
//
//        return null;
//    }

//    //分发积分
//    @RequestMapping(value = "/givePoint",method = RequestMethod.POST)
//    public String givePoint(@RequestBody Taskkey taskkey){
//
//        return null;
//    }


    //查看所有的任务
    @RequestMapping(value = "/checkAllTasks")
    public String checkAllTasks() throws IOException{
        String filename="src/main/java/com/seciii/crowdsourcing/Data/TaskInformation/TaskInformation.txt";
        File file=new File(filename);
        InputStreamReader reader=new InputStreamReader(new FileInputStream(file));
        BufferedReader br=new BufferedReader(reader);
        ArrayList<String> strlist=new ArrayList<>();
        String line=null;
        while((line=br.readLine())!=null){
            strlist.add(line);
        }
        String list=String.join("!",strlist);

        return list;
    }



    //参与任务
    @RequestMapping(value = "/participateIn",method = RequestMethod.POST)
    public String participateIn(@RequestBody Taskkey taskkey) throws IOException{
        String task=taskkey.getTaskId();
        String worker=taskkey.getWorkerId();

        String filename="src/main/java/com/seciii/crowdsourcing/Data/TaskList/"+task+"/"+worker+".txt";
        File file=new File(filename);
        if(!file.exists()){
            file.createNewFile();
        }

        String userfile="src/main/java/com/seciii/crowdsourcing/Data/UserTaskIndexList/"+worker+".txt";
        FileWriter fileWriter=new FileWriter(userfile,true);
        BufferedWriter writer=new BufferedWriter(fileWriter);
        writer.write("j"+task+" ");

        return "Success";
    }


    //提交自己的标注
    @RequestMapping(value = "/submitLabel",method = RequestMethod.POST)
    public String submitLabel(@RequestBody Taskkey taskkey) throws IOException{
        String filename="src/main/java/com/seciii/crowdsourcing/Data/TemporaryFile/"+taskkey.getTaskId()+".txt";
        String labels="";
        File temfile=new File(filename);
        InputStreamReader reader=new InputStreamReader(new FileInputStream(temfile));
        BufferedReader br=new BufferedReader(reader);
        String line=null;
        while ((line=br.readLine())!=null){
            labels=labels+line+" ";
        }

        String file="src/main/java/com/seciii/crowdsourcing/Data/TaskList/"+taskkey.getTaskId()+"/"+taskkey.getWorkerId()+".txt";
        FileWriter fileWriter=new FileWriter(file,false);
        BufferedWriter writer=new BufferedWriter(fileWriter);
        writer.write(labels);
        writer.close();

        return "Success";
    }


    //查看自己参与的任务
    @RequestMapping(value = "checkJoinTasks",method = RequestMethod.POST)
    public String checkJoinTasks(@RequestBody User user) throws IOException{
        ArrayList<String> arrayList=new ArrayList<>();
        String userfile="src/main/java/com/seciii/crowdsourcing/Data/UserTaskIndexList/"+user.getUsername()+".txt";
        File file=new File(userfile);
        InputStreamReader reader=new InputStreamReader(new FileInputStream(file));
        BufferedReader br=new BufferedReader(reader);
        String line=br.readLine();
        String[] arr=line.split(" ");
        for(String i:arr){
            if(i.charAt(0)=='j'){
                String taskname=i.substring(1);

                String alltaskInfo="src/main/java/com/seciii/crowdsourcing/Data/TaskInformation/TaskInformation.txt";
                File infofile=new File(alltaskInfo);
                InputStreamReader reader1=new InputStreamReader(new FileInputStream(infofile));
                BufferedReader bufferedReader=new BufferedReader(reader1);
                String line1=null;
                while ((line1=bufferedReader.readLine())!=null){
                    if(line1.split(" ")[0].equals(taskname)){
                        arrayList.add(line1);
                    }
                }
            }
        }
        String result=String.join("!",arrayList);
        return result;
    }

    //查看自己发布的任务
    @RequestMapping(value = "checkBuildTasks",method = RequestMethod.POST)
    public String checkBuildTasks(@RequestBody User user) throws IOException{
        ArrayList<String> arrayList=new ArrayList<>();
        String userfile="src/main/java/com/seciii/crowdsourcing/Data/UserTaskIndexList/"+user.getUsername()+".txt";
        File file=new File(userfile);
        InputStreamReader reader=new InputStreamReader(new FileInputStream(file));
        BufferedReader br=new BufferedReader(reader);
        String line=br.readLine();
        String[] arr=line.split(" ");
        for(String i:arr){
            if(i.charAt(0)=='b'){
                String taskname=i.substring(1);
                String alltaskInfo="src/main/java/com/seciii/crowdsourcing/Data/TaskInformation/TaskInformation.txt";
                File infofile=new File(alltaskInfo);
                InputStreamReader reader1=new InputStreamReader(new FileInputStream(infofile));
                BufferedReader bufferedReader=new BufferedReader(reader1);
                String line1;
                while ((line1=bufferedReader.readLine())!=null){
                    if(line1.split(" ")[0].equals(taskname)){
                        arrayList.add(line1);
                    }
                }
            }
        }
        String result=String.join("!",arrayList);
        return result;
    }



}
