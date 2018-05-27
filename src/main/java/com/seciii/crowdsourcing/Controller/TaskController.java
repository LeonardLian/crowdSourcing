package com.seciii.crowdsourcing.Controller;

import com.seciii.crowdsourcing.Dao.*;
import com.seciii.crowdsourcing.Dao.User;

import com.sun.org.apache.regexp.internal.RE;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

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

    //发布任务的基本信息
    @RequestMapping(value = "/releaseTaskInfo",method = RequestMethod.POST)
    public String releaseTask(@RequestBody Task task) throws IOException{
        String requestor=task.getRequestor();
        String tasktag=task.getTasktag();
        String description=task.getDescription();
        String mode=task.getMode();
        String numofNeeded=task.getNumOfNeeded();
        String point=task.getPoint();
        String deadline=task.getDeadline();


        Task task1=new Task(requestor,tasktag,description,mode,numofNeeded,point,deadline);
        String taskname=task1.getTaskname();

        String foldername="src/main/java/com/seciii/crowdsourcing/Data/TaskList/"+taskname;
        File folder=new File(foldername);
        if(!folder.exists()){
            folder.mkdirs();
        }

        String temporary="src/main/java/com/seciii/crowdsourcing/Data/TaskTemporaryFile/"+taskname;
        File temporaryFolder=new File(temporary);
        if(!temporaryFolder.exists()){
            temporaryFolder.mkdirs();
        }

        String taskInformationFile="src/main/java/com/seciii/crowdsourcing/Data/TaskList/"+taskname+"/description.txt";

        String alltaskInfo="src/main/java/com/seciii/crowdsourcing/Data/TaskInformation/TaskInformation.txt";

        String desStr=
                task1.getTaskname()+"#"+
                task1.getRequestor()+"#"+
                task1.getTasktag()+"#"+
                task1.getDescription()+"#"+
                task1.getMode()+"#"+
                task1.getNumOfNeeded()+"#"+
                task1.getNumOfPart()+"#"+
                task1.getPoint()+"#"+
                task1.getDeadline()+"\n";


        File file=new File(taskInformationFile);
        if(!file.exists()){
            file.createNewFile();
        }

        FileWriter writer=new FileWriter(taskInformationFile,false);
        BufferedWriter bw=new BufferedWriter(writer);
        bw.write(desStr);
        bw.close();


        FileWriter fw=new FileWriter(alltaskInfo,true);
        BufferedWriter bww=new BufferedWriter(fw);
        bww.write(desStr);
        bww.close();


        String userfile="src/main/java/com/seciii/crowdsourcing/Data/UserTaskIndexList/"+requestor+".txt";
        FileWriter fileWriter=new FileWriter(userfile,true);
        BufferedWriter bwww=new BufferedWriter(fileWriter);
        bwww.write("b"+task1.getTaskname()+" ");
        bwww.close();

        return taskname;
    }

    //发布任务的图像信息
    @RequestMapping(value = "/releaseTaskImg",method = RequestMethod.POST)
    @ResponseBody public String releaseTaskImg(@RequestParam("taskImg") MultipartFile file,@RequestParam("imgName") String imgName) throws IOException{
        String filename="src/main/java/com/seciii/crowdsourcing/Data/TaskId/TaskId.txt";
        File f=new File(filename);
        InputStreamReader srreader=new InputStreamReader(new FileInputStream(f));
        BufferedReader reader=new BufferedReader(srreader);
        String taskName=reader.readLine();

        String foldername="src/main/java/com/seciii/crowdsourcing/Data/TaskImg/"+taskName;
        File folder=new File(foldername);
        if(!folder.exists()){
            folder.mkdirs();
        }

        String taskFile="src/main/java/com/seciii/crowdsourcing/Data/TaskImg/"+taskName+"/"+imgName+".txt";
        File newfile=new File(taskFile);
        InputStream input=null;
        byte[] data=null;
        input=file.getInputStream();
        data=new byte[input.available()];
        input.read(data);
        input.close();

        BASE64Encoder encoder=new BASE64Encoder();
        String code= encoder.encode(data);

        FileWriter writer=new FileWriter(taskFile,false);
        BufferedWriter bw=new BufferedWriter(writer);
        bw.write(code);
        bw.close();
        return "success";

//        String foldername="/Users/Leonarda/Desktop/TaskImg/"+taskName;
//        File folder=new File(foldername);
//        if(!folder.exists()){
//            folder.mkdirs();
//        }
//
//        File newfile=new File("/Users/Leonarda/Desktop/TaskImg/"+taskName+"/"+imgName+".jpeg");
//
//        if(!newfile.exists()){
//            newfile.createNewFile();
//        }
//
//        file.transferTo(newfile);
//        return "success";
    }


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


    //查看对应任务的图片
    @RequestMapping(value = "/checkTaskImg",method = RequestMethod.POST)
    public String checkTaskImg(@RequestBody Task task) throws IOException{
        String taskName=task.getTaskname();
        String folderName="src/main/java/com/seciii/crowdsourcing/Data/TaskImg/"+taskName;
        File dir=new File(folderName);
        String[] fileList=dir.list();

        ArrayList<String> resultList=new ArrayList<>();

        for(int i=0;i<fileList.length;i++){
//            String string=fileList[i];
//            File file=new File(dir.getPath(),string);
//            InputStream input=null;
//            byte[] data=null;
//            input=new FileInputStream(file.getPath());
//            data=new byte[input.available()];
//            input.read(data);
//            input.close();
//
//
//            BASE64Encoder encoder=new BASE64Encoder();
//            String code=encoder.encode(data);
//            resultList.add(code);
            String string=fileList[i];
            File file=new File(dir.getPath(),string);
            InputStreamReader srreader=new InputStreamReader(new FileInputStream(file));
            BufferedReader reader=new BufferedReader(srreader);
            String line;
            String info="";
            while ((line=reader.readLine())!=null){
                info=info+line;
            }
            resultList.add(info);
        }
        return String.join(" ",resultList);
    }


    //判断用户与主页点击任务的关系
    @RequestMapping(value = "/judgeRelation",method = RequestMethod.POST)
    public String judgeRelation(@RequestBody Taskkey taskkey) throws IOException{
        String taskname=taskkey.getTaskname();
        String username=taskkey.getUsername();

        String filename="src/main/java/com/seciii/crowdsourcing/Data/UserTaskIndexList/"+username+".txt";
        File file=new File(filename);
        InputStreamReader srreader=new InputStreamReader(new FileInputStream(file));
        BufferedReader reader=new BufferedReader(srreader);
        String line=reader.readLine();
        if(line==null){
            return "0";                     //旁观
        }
        String[] taskList=line.split(" ");
        String relation="0";
        for(String task:taskList){
            String taskid=task.substring(1);
            if(taskid.equals(taskname)){
                if(task.charAt(0)=='b'){
                    relation="1";         //发布
                    break;
                }else {
                    relation="2";        //参与
                    break;
                }
            }else{
                continue;
            }
        }

        return relation;
    }

    //查看任务文字信息
    @RequestMapping(value = "/checkTaskInformation",method = RequestMethod.POST)
    public String checkTaskInformationAsLooker(@RequestBody Task task) throws IOException{
        String taskname=task.getTaskname();
        String filename="src/main/java/com/seciii/crowdsourcing/Data/TaskList/"+taskname+"/description.txt";
        File file=new File(filename);
        InputStreamReader reader=new InputStreamReader(new FileInputStream(file));
        BufferedReader br=new BufferedReader(reader);
        String line=br.readLine();
        return line;
    }

    //参与任务
    @RequestMapping(value = "/participateIn",method = RequestMethod.POST)
    public String participateIn(@RequestBody Taskkey taskkey) throws IOException{
        String task=taskkey.getTaskname();
        String worker=taskkey.getUsername();

        String filename="src/main/java/com/seciii/crowdsourcing/Data/TaskList/"+task+"/"+worker+".txt";
        File file=new File(filename);
        if(!file.exists()){
            file.createNewFile();
        }

        String userfile="src/main/java/com/seciii/crowdsourcing/Data/UserTaskIndexList/"+worker+".txt";
        FileWriter fileWriter=new FileWriter(userfile,true);
        BufferedWriter writer=new BufferedWriter(fileWriter);
        writer.write("j"+task+" ");

        String temFile="src/main/java/com/seciii/crowdsourcing/Data/TaskTemporaryFile/"+task+"/"+worker+".txt";
        File tem=new File(temFile);
        if(!tem.exists()){
            tem.createNewFile();
        }

        return "Success";
    }


    //返回工人在任务中保存的所有图片的临时标注
    @RequestMapping(value = "loadTemporaryFile",method = RequestMethod.POST)
    public String loadTemporaryFile(@RequestBody Taskkey taskkey) throws IOException{
        String taskname=taskkey.getTaskname();
        String username=taskkey.getUsername();
        String temporaryFile="src/main/java/com/seciii/crowdsourcing/Data/TaskTemporaryFile/"+taskname+"/"+username+".txt";
        File file=new File(temporaryFile);
        InputStreamReader reader=new InputStreamReader(new FileInputStream(file));
        BufferedReader br=new BufferedReader(reader);
        ArrayList<String> list=new ArrayList<>();
        String line;
        while((line=br.readLine())!=null){
            list.add(line);
        }
        String result=String.join("#",list);
        return result;
    }


//    //查看自己参与的任务
//    @RequestMapping(value = "checkJoinTasks",method = RequestMethod.POST)
//    public String checkJoinTasks(@RequestBody User user) throws IOException{
//        ArrayList<String> arrayList=new ArrayList<>();
//        String userfile="src/main/java/com/seciii/crowdsourcing/Data/UserTaskIndexList/"+user.getUsername()+".txt";
//        File file=new File(userfile);
//        InputStreamReader reader=new InputStreamReader(new FileInputStream(file));
//        BufferedReader br=new BufferedReader(reader);
//        String line=br.readLine();
//        String[] arr=line.split(" ");
//        for(String i:arr){
//            if(i.charAt(0)=='j'){
//                String taskname=i.substring(1);
//                arrayList.add(taskname);
//            }
//        }
//        String result=String.join("!",arrayList);
//        return result;
//    }
//
//
//    //查看自己发布的任务
//    @RequestMapping(value = "checkBuildTasks",method = RequestMethod.POST)
//    public String checkBuildTasks(@RequestBody User user) throws IOException{
//        ArrayList<String> arrayList=new ArrayList<>();
//        String userfile="src/main/java/com/seciii/crowdsourcing/Data/UserTaskIndexList/"+user.getUsername()+".txt";
//        File file=new File(userfile);
//        InputStreamReader reader=new InputStreamReader(new FileInputStream(file));
//        BufferedReader br=new BufferedReader(reader);
//        String line=br.readLine();
//        String[] arr=line.split(" ");
//        for(String i:arr){
//            if(i.charAt(0)=='b'){
//                String taskname=i.substring(1);
//                arrayList.add(taskname);
//            }
//        }
//        String result=String.join("!",arrayList);
//        return result;
//    }


    //提交自己的标注
    @RequestMapping(value = "/submittheLabel",method = RequestMethod.POST)
    public String submitLabel(@RequestBody Taskkey taskkey) throws IOException{
        String filename="src/main/java/com/seciii/crowdsourcing/Data/TaskTemporaryFile/"+taskkey.getTaskname()+"/"+taskkey.getUsername()+".txt";
        String labels="";
        File temfile=new File(filename);
        InputStreamReader reader=new InputStreamReader(new FileInputStream(temfile));
        BufferedReader br=new BufferedReader(reader);

        String file="src/main/java/com/seciii/crowdsourcing/Data/TaskList/"+taskkey.getTaskname()+"/"+taskkey.getUsername()+".txt";
        FileWriter fileWriter=new FileWriter(file,false);
        BufferedWriter writer=new BufferedWriter(fileWriter);

        String line=null;
        while ((line=br.readLine())!=null){
            writer.write(line+"\n");
        }

        writer.close();

        return "Success";
    }

    //查看一个工人的作品
    @RequestMapping(value = "/loadWorkerFile",method = RequestMethod.POST)
    public String checkWorker(@RequestBody Taskkey taskkey) throws IOException {
        String temporaryFile="src/main/java/com/seciii/crowdsourcing/Data/TaskList/"+taskkey.getTaskname()+"/"+taskkey.getUsername()+".txt";
        File file=new File(temporaryFile);
        InputStreamReader reader=new InputStreamReader(new FileInputStream(file));
        BufferedReader br=new BufferedReader(reader);
        ArrayList<String> list=new ArrayList<>();
        String line;
        while((line=br.readLine())!=null){
            list.add(line);
        }
        String result=String.join("#",list);
        return result;
    }

    //查看一个任务的分发情况
    @RequestMapping(value = "/checkTask",method = RequestMethod.POST)
    public String checkTask(@RequestBody Task task){
        String taskname=task.getTaskname();
        String foldername="src/main/java/com/seciii/crowdsourcing/Data/TaskList/"+taskname;
        File folder=new File(foldername);
        String[] files=folder.list();

        ArrayList<String> workerList=new ArrayList<>();
        for(String file:files){
            if(file.equals("description.txt")){
            }
            else{
                String id=file.split(".")[0];
                workerList.add(id);
            }
        }

        String result=String.join("#",workerList);
        return result;
    }

}
