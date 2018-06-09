package com.seciii.crowdsourcing.Controller;

import com.alibaba.fastjson.JSONObject;
import com.seciii.crowdsourcing.Dao.*;
import com.seciii.crowdsourcing.Dao.User;

import com.sun.org.apache.regexp.internal.RE;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

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


    //查看任务图片信息
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
        writer.close();

        String temFile="src/main/java/com/seciii/crowdsourcing/Data/TaskTemporaryFile/"+task+"/"+worker+".txt";
        File tem=new File(temFile);
        if(!tem.exists()){
            tem.createNewFile();
        }

        //参与人数+1
        String desFile="src/main/java/com/seciii/crowdsourcing/Data/TaskList/"+task+"/description.txt";
        File dFile=new File(desFile);
        InputStreamReader reader=new InputStreamReader(new FileInputStream(dFile));
        BufferedReader br=new BufferedReader(reader);
        String line1=br.readLine();
        String[] arr=line1.split("#");
        arr[6]=String.valueOf(Integer.parseInt(arr[6])+1);

        String newLine=String.join("#",arr);
        FileWriter desFileWriter=new FileWriter(desFile,false);
        BufferedWriter desWriter=new BufferedWriter(desFileWriter);
        desWriter.write(newLine);
        desWriter.close();


        String TaskInfoFile="src/main/java/com/seciii/crowdsourcing/Data/TaskInformation/TaskInformation.txt";
        File infoFile=new File(TaskInfoFile);
        InputStreamReader reader1=new InputStreamReader(new FileInputStream(infoFile));
        BufferedReader br1=new BufferedReader(reader1);

        ArrayList<String> taskList=new ArrayList<>();
        String line;
        while ((line=br1.readLine())!=null){
            String taskname=line.split("#")[0];
            if(taskname.equals(task)){
                String[] array=line.split("#");
                array[6]=String.valueOf(Integer.parseInt(array[6])+1);
                String newtask=String.join("#",array);
                taskList.add(newtask);
            }else{
                taskList.add(line);
            }
        }
        FileWriter tFile=new FileWriter(TaskInfoFile,false);
        BufferedWriter twriter=new BufferedWriter(tFile);
        String newTaskList=String.join("\n",taskList);
        System.out.println(newTaskList);
        twriter.write(newTaskList);
        twriter.close();

        return "part success";
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
                arrayList.add(taskname);
            }
        }
        String result;
        if(arrayList.isEmpty()){
            result="";
        }else {
            result = String.join("!", arrayList);
        }
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
                arrayList.add(taskname);
            }
        }
        String result;
        if(arrayList.isEmpty()){
            result="";
        }else {
            result = String.join("!", arrayList);
        }
        return result;
    }


    //提交自己的标注
    @RequestMapping(value = "/submittheLabel",method = RequestMethod.POST)
    public String submitLabel(@RequestParam("label") String label,@RequestParam("username") String username,@RequestParam("taskname") String taskname) throws IOException{

        String file="src/main/java/com/seciii/crowdsourcing/Data/TaskList/"+taskname+"/"+username+".txt";
        FileWriter fileWriter=new FileWriter(file,false);
        BufferedWriter writer=new BufferedWriter(fileWriter);

//        InputStream input=null;
//        byte[] data=null;
//        input=labelFile.getInputStream();
//        data=new byte[input.available()];
//        input.read(data);
//        input.close();
//
//        BASE64Encoder encoder=new BASE64Encoder();
//        String code= encoder.encode(data);
//
//        writer.write(code);
//        writer.close();
        writer.write(label);
        writer.close();

        return "Success";
    }


    //查看一个工人的作品
    @RequestMapping(value = "/loadWorkerFile",method = RequestMethod.POST)
    public String checkWorker(@RequestBody Taskkey taskkey) throws IOException {
        String workerFile="src/main/java/com/seciii/crowdsourcing/Data/TaskList/"+taskkey.getTaskname()+"/"+taskkey.getUsername()+".txt";
        File file=new File(workerFile);
        String info="";

        InputStreamReader srreader=new InputStreamReader(new FileInputStream(file));
        BufferedReader reader=new BufferedReader(srreader);
        String line;
        while ((line=reader.readLine())!=null){
            info=info+line;
        }

        return info;
    }


    //查看一个任务的分发情况
    @RequestMapping(value = "/checkAllWorker",method = RequestMethod.POST)
    public String checkTask(@RequestBody Task task){
        String taskname=task.getTaskname();
        String foldername="src/main/java/com/seciii/crowdsourcing/Data/TaskList/"+taskname;
        File folder=new File(foldername);
        String[] files=folder.list();

        //System.out.println(folder);
        ArrayList<String> workerList=new ArrayList<>();
        for(String file:files){
            //System.out.println(file);
            if(file.equals("description.txt")){
            }
            else{
                String id=file.split("\\.")[0];
                workerList.add(id);
            }
        }

        String result;
        if(workerList.isEmpty()){
            result="";
        }else {
            result = String.join("#", workerList);
        }
        return result;
    }

    //前端获取任务编号
    @RequestMapping(value="/getTaskname")
    public String getTasknameForHTML() throws IOException{
        return UrlController.task.getTaskname();
    }

    //整合标注
    @RequestMapping(value = "/closeTask",method = RequestMethod.POST)
    public String closeTask(@RequestBody Task task) throws IOException{

        //读取所有参与者的标注信息
        String temporaryFile="src/main/java/com/seciii/crowdsourcing/Data/TaskList/"+task.getTaskname()+"/";
        File file=new File(temporaryFile);
        File[] tempList=file.listFiles();

        InputStreamReader reader;
        BufferedReader br;
        String line;
        int numOfWorker=tempList.length-1;//参与者人数

        ArrayList<ArrayList<SquareLabel>> labelList=new ArrayList<ArrayList<SquareLabel>>();//labelList按照comment分类
        for(File worker:tempList){
            if(worker.getName().equals("description.txt")){}
            else {
                reader = new InputStreamReader(new FileInputStream(worker));
                br = new BufferedReader(reader);
                while ((line = br.readLine()) != null) {//按行读取json，转存为SquareLabel对象，存入labelList中
                    JSONObject json = JSONObject.parseObject(line);
                    SquareLabel label = new SquareLabel(json.getString("startX"), json.getString("startY"), json.getString("width"), json.getString("height"), json.getString("comment"), json.getString("taskname"), json.getString("username"));
                    if(labelList.size()==0){//若labelList为空，则添加一个ArrayList，并加入label
                        labelList.add(new ArrayList<SquareLabel>());
                        labelList.get(0).add(label);
                    }else {//遍历labelList，检查是否存在相同comment存在，存在则直接添加label，不存在则添加ArrayList后再添加label
                        boolean exist=false;
                        for(int i=0;i<labelList.size();i++){
                            if(label.getComment().equals(labelList.get(i).get(0).getComment())) {
                                labelList.get(i).add(label);
                                exist=true;
                                break;
                            }
                        }
                        if(!exist){
                            labelList.add(new ArrayList<SquareLabel>());
                            labelList.get(labelList.size()-1).add(label);
                        }
                    }
                }
            }
        }

        //由于相同comment也许对应对个方框，根据参与人数分组
        ArrayList<ArrayList<SquareLabel>> labelsToBeCalculated=new ArrayList<ArrayList<SquareLabel>>();
        for(ArrayList<SquareLabel> list:labelList){
            if(list.size()<=1.5*numOfWorker){//如果方框数量小于等于参与者数量的1.5倍，则假定该comment只对应一个方框
                labelsToBeCalculated.add(list);
            }else{//否则假定该comment对应多个方框，计算x，y的方差，并根据方差较大值，分为多个ArrayList TODO

            }
        }

        //整合所有方框
        ArrayList<SquareLabel> resultLabels=new ArrayList<SquareLabel>();
        for(int i=0;i<labelList.size();i++){
            resultLabels.add(calculateSquarel2(labelsToBeCalculated.get(i)));
        }

        //将得到的整合方框以发起者明明，并写回
        String resultFilePath=temporaryFile+UrlController.user.getUsername()+".txt";
        File resultFile=new File(resultFilePath);
        if(!file.exists()){
            file.createNewFile();
        }
        FileWriter fw=new FileWriter(resultFilePath,true);
        BufferedWriter bw=new BufferedWriter(fw);
        for(SquareLabel label:resultLabels){
            String squareLabelStr="{"+
                    "\"type\":"+label.getType()+","+
                    "\"startX\":"+label.getStartX()+","+
                    "\"startY\":"+label.getStartY()+","+
                    "\"width\":"+"\""+label.getWidth()+"\""+","+
                    "\"height\""+"\""+label.getHeight()+"\""+","+
                    "\"comment\""+"\""+label.getComment()+"\""+","+
                    "\"taskname\""+"\""+label.getTaskname()+"\""+","+
                    "\"username\""+"\""+label.getUsername()+"\""+
                    "}"+"\n";
            bw.write(squareLabelStr);
        }
        bw.close();
        fw.close();

        return "Success";
    }

    //整合方框2，取中间的80%，分为8组，中央数值加权平均
    private SquareLabel calculateSquarel2(ArrayList<SquareLabel> labels){
        ArrayList<Double> x=new ArrayList<Double>();
        ArrayList<Double> y=new ArrayList<Double>();
        ArrayList<Double> width=new ArrayList<Double>();
        ArrayList<Double> height=new ArrayList<Double>();

        for(SquareLabel label:labels){
            x.add(Double.parseDouble(label.getStartX()));
            y.add(Double.parseDouble(label.getStartY()));
            width.add(Double.parseDouble(label.getWidth()));
            height.add(Double.parseDouble(label.getHeight()));
        }

        return new SquareLabel(""+calculateAverage(x),""+calculateAverage(y),""+calculateAverage(width),""+calculateAverage(height),labels.get(0).getComment(),UrlController.task.getTaskname(),UrlController.user.getUsername());
    }

    private double calculateAverage(ArrayList<Double> list){
        Collections.sort(list);//将list升序排列
        int[] vote={0,0,0,0,0,0,0,0};
        int length=list.size();
        double min=list.get(length/10),//去掉最小的10%
                max=list.get(length*9/10),//去掉最大的10%
                gap=(max-min)/8;//将剩下的数据的等分为八个区间

        //遍历去掉最高最低后的list，统计八个区间的频数
        for(int i=length/10;i<length*9/10;i++){
            for(int j=1;j<9;j++){
                if(list.get(i)<min+j*gap){
                    vote[j-1]++;
                    break;
                }
            }
        }

        //以频率为概率，计算期望。（加权平均）
        double sum=0;
        for(int i=0;i<8;i++){
            sum+=(min+gap/2+i*gap)*vote[i];
        }
        double average=sum/(length*8/10);

        return average;
    }

    //评估工人对某个任务的标注准确性
    @RequestMapping(value="/checkCertainLabel", method = RequestMethod.POST)
    public double checkCertainLabel(@RequestBody Task task, User user) throws IOException{
        String username = user.getUsername();
        String taskname = task.getTaskname();
        String temporaryFile="src/main/java/com/seciii/crowdsourcing/Data/TaskTemporaryFile/"+taskname+"/"+username+".txt";
        File file=new File(temporaryFile);
        InputStreamReader reader=new InputStreamReader(new FileInputStream(file));
        BufferedReader br=new BufferedReader(reader);

        //得到一个工人在一个任务中的所有方框的信息
        SquareLabel[] labels = new SquareLabel[20];
        int numOfSquares = 0;
        String line;
        while((line=br.readLine())!=null){
            JSONObject json = JSONObject.parseObject(line);
            SquareLabel label = new SquareLabel(json.getString("startX"), json.getString("startY"), json.getString("width"),
                    json.getString("height"), json.getString("comment"), json.getString("taskname"), json.getString("username"));
            labels[numOfSquares] = label;
            numOfSquares ++;
        }

        //调用整合的方法得到该任务中每个方框的平均值
        SquareLabel std_labels = new SquareLabel();

        //对工人的方框和整合的方框作比较并打分
        int[] isIn = null;
        double[] scores = null;
        for(int i=0;i<labels.length;i++){
            SquareLabel label = labels[i];
            //for(int j=0;j<std_squares.length;i++){
            //   SquareLabel l = std_squares[j];
//                 if(label.getComment() == l.getComment()){
//                     double gapX = Math.abs(Double.parseDouble(label.getStartX())-Double.parseDouble(l.getStartX()));
//                     double gapY = Math.abs(Double.parseDouble(label.getStartY())-Double.parseDouble(l.getStartY()));
//                     double gapW = Math.abs(Double.parseDouble(label.getWidth())-Double.parseDouble(l.getWidth()));
//                     double gapH = Math.abs(Double.parseDouble(label.getHeight())-Double.parseDouble(l.getHeight()));
//
//                     if(gapX == 0 && gapY == 0){
//                         scores[i] = 1;
//                         isIn[i] = 1;
//                     }
//                     else if(gapX <= 10 && gapY <= 10 && gapW <= 10 && gapH <= 10){
//                         scores[i] = 1;
//                         isIn[i] = 1;
//                     }
//                     else if(gapX <= 30 && gapY <= 30 && gapW <= 30 && gapH <= 30){
//                         scores[i] = 0.9;
//                         isIn[i] = 1;
//
//                     }else{
//                         scores[i] = 0;
//                         isIn[i] = 0;
//                     }
//                 }
            //}
        }
        double sum = 0.0;
        for(int i=0;i<labels.length;i++){
            sum += scores[i];
        }
        double avg = sum/labels.length;

        return avg;
    }

    //为某个用户推荐任务
    @RequestMapping(value = "/recommendTasks",method = RequestMethod.POST)
    public String recommendTasks(@RequestBody User user) throws IOException{
        String filename="src/main/java/com/seciii/crowdsourcing/Data/TaskInformation/TaskInformation.txt";
        File file=new File(filename);
        InputStreamReader reader=new InputStreamReader(new FileInputStream(file));
        BufferedReader br=new BufferedReader(reader);
        ArrayList<String> strlist=new ArrayList<>();
        String line=null;
        while((line=br.readLine())!=null){
            strlist.add(line);
        }

        ArrayList<String> tmpList = new ArrayList<>();
        String types = "";//user.getType();
        String[] type_arr = types.split(",");
        for(int i=0;i<type_arr.length;i++){
            for(int j=0;j<strlist.size();j++){
                String task = strlist.get(j);
                String[] task_arr = task.split("#");

                // if(task.type == type_arr[10] ){        //the index of type is 10.
                //tmpList.add(tmp);
                //strlist.remove(j);
                //}
            }
        }


        tmpList.addAll(strlist);
        String list = String.join("!", tmpList);

        return list;
    }

}
