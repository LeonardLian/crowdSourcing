package com.seciii.crowdsourcing.Controller;

import com.seciii.crowdsourcing.Dao.CurveLabel;
import com.seciii.crowdsourcing.Dao.SquareLabel;
import com.seciii.crowdsourcing.Dao.WholeLabel;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.ArrayList;

/**
 * @author: pis
 * @description: 对标注的处理和管理
 * @date: create in 下午3:18 2018/4/8
 */

@CrossOrigin
@RestController
public class LabelManageController {

    //曲线标注

    //将一个用曲线标注的标签 保存到临时文件里
    @RequestMapping(value = "/saveCurveLabel",method = RequestMethod.POST)
    public String saveCurveLabel(@RequestBody CurveLabel curvelabel) throws IOException{
        String curveLabelStr="{"+
                "\"type\":"+curvelabel.getType()+","+
                "\"comment\":"+"\""+curvelabel.getComment()+"\""+","+
                "\"dotlist\""+"\""+curvelabel.getDotlist()+"\""+","+
                "\"taskname\""+"\""+curvelabel.getTaskname()+"\""+
                "}"+"\n";
        String filename="src/main/java/com/seciii/crowdsourcing/Data/TemporaryFile/"+curvelabel.getTaskname()+".txt";

        File file=new File(filename);
        if(!file.exists()){
            file.createNewFile();
        }
        FileWriter writer=new FileWriter(filename,true);
        BufferedWriter bw=new BufferedWriter(writer);
        bw.write(curveLabelStr);
        bw.close();

        return "success";
    }

    //删除一个用曲线标注的标签，保存到临时文件里
    @RequestMapping(value = "/deleteCurveLabel",method = RequestMethod.POST)
    public String deleteCurveLabel(@RequestBody CurveLabel curvelabel) throws IOException{
        String deleteStr="{"+
                "\"type\":"+curvelabel.getType()+","+
                "\"comment\":"+"\""+curvelabel.getComment()+"\""+","+
                "\"dotlist\""+"\""+curvelabel.getDotlist()+"\""+","+
                "\"taskname\""+"\""+curvelabel.getTaskname()+"\""+
                "}";
        Boolean succeed=false;
        ArrayList<String> allSquareLabelList=new ArrayList<>();
        String filename="src/main/java/com/seciii/crowdsourcing/Data/TemporaryFile/"+curvelabel.getTaskname()+".txt";
        File file=new File(filename);
        InputStreamReader reader=new InputStreamReader(new FileInputStream(file));
        BufferedReader br=new BufferedReader(reader);
        String line=null;
        while ((line=br.readLine())!=null){
            if(line.equals(deleteStr)){
                succeed=true;
            }else{
                allSquareLabelList.add(line);
            }
        }

        FileWriter writer=new FileWriter(filename,false);
        BufferedWriter bw=new BufferedWriter(writer);
        for(String a:allSquareLabelList){
            bw.write(a+"\n");
        }
        bw.close();

        if(succeed){
            return "删除成功";
        }else{
            return "?????";
        }
    }

    //修改一个曲线标签的注释内容，保存到临时文件里
    @RequestMapping(value = "/polishCurveLabel",method = RequestMethod.POST)
    public String polishCurveLabel(@RequestBody CurveLabel curvelabel) throws IOException{
        String polishStr="{"+
                "\"type\":"+curvelabel.getType()+","+
                "\"comment\":"+"\""+curvelabel.getComment()+"\""+","+
                "\"dotlist\""+"\""+curvelabel.getDotlist()+"\""+","+
                "\"taskname\""+"\""+curvelabel.getTaskname()+"\""+
                "}";
        Boolean succeed=false;
        ArrayList<String> allSquareLabelList=new ArrayList<>();
        String filename="src/main/java/com/seciii/crowdsourcing/Data/TemporaryFile/"+curvelabel.getTaskname()+".txt";
        File file=new File(filename);
        InputStreamReader reader=new InputStreamReader(new FileInputStream(file));
        BufferedReader br=new BufferedReader(reader);
        String line=null;
        while ((line=br.readLine())!=null){
            if((line.split(",")[0].equals(polishStr.split(",")[0]))&&
                    (line.split(",")[2].equals(polishStr.split(",")[2]))
                    ){
                allSquareLabelList.add(polishStr);
                succeed=true;
            }else{
                allSquareLabelList.add(line);
            }
        }

        FileWriter writer=new FileWriter(filename,false);
        BufferedWriter bw=new BufferedWriter(writer);
        for(String a:allSquareLabelList){
            bw.write(a+"\n");
        }
        bw.close();

        if(succeed){
            return "修改成功";
        }else{
            return "?????";
        }
    }


    //从后端取得所有的曲线标注标签，传到前端
//    @RequestMapping(value = "/sendAllCurveLabel",method =RequestMethod.POST)
//    public String sendAllCurveLabel(@RequestBody CurveLabel curveLabel){
//        return null;
//    }


    //方框标注

    //将一个用方框标注的标签 保存到临时文件里
    @RequestMapping(value = "/saveSquareLabel",method = RequestMethod.POST)
    public String saveSquareLabel(@RequestBody SquareLabel squarelabel) throws IOException{
        String squareLabelStr="{"+
                "\"type\":"+squarelabel.getType()+","+
                "\"startX\":"+squarelabel.getStartX()+","+
                "\"startY\":"+squarelabel.getStartY()+","+
                "\"width\":"+"\""+squarelabel.getWidth()+"\""+","+
                "\"height\""+"\""+squarelabel.getHeight()+"\""+","+
                "\"comment\""+"\""+squarelabel.getComment()+"\""+","+
                "\"taskname\""+"\""+squarelabel.getTaskname()+"\""+
                "}"+"\n";
        String filename="src/main/java/com/seciii/crowdsourcing/Data/TemporaryFile/"+squarelabel.getTaskname()+".txt";

        File file=new File(filename);
        if(!file.exists()){
            file.createNewFile();
        }
        FileWriter writer=new FileWriter(filename,true);
        BufferedWriter bw=new BufferedWriter(writer);
        bw.write(squareLabelStr);
        bw.close();

        return "success";
    }

    //删除一个用方框标注的标签 保存到临时文件里
    @RequestMapping(value = "/deleteSquareLabel",method = RequestMethod.POST)
    public String deleteSquareLabel(@RequestBody SquareLabel squarelabel) throws IOException{
        String deleteStr="{"+
                "\"type\":"+squarelabel.getType()+","+
                "\"startX\":"+squarelabel.getStartX()+","+
                "\"startY\":"+squarelabel.getStartY()+","+
                "\"width\":"+"\""+squarelabel.getWidth()+"\""+","+
                "\"height\""+"\""+squarelabel.getHeight()+"\""+","+
                "\"comment\""+"\""+squarelabel.getComment()+"\""+","+
                "\"taskname\""+"\""+squarelabel.getTaskname()+"\""+
                "}";
        Boolean succeed=false;
        ArrayList<String> allSquareLabelList=new ArrayList<>();
        String filename="src/main/java/com/seciii/crowdsourcing/Data/TemporaryFile/"+squarelabel.getTaskname()+".txt";
        File file=new File(filename);
        InputStreamReader reader=new InputStreamReader(new FileInputStream(file));
        BufferedReader br=new BufferedReader(reader);
        String line=null;
        while ((line=br.readLine())!=null){
            if(line.equals(deleteStr)){
                succeed=true;
            }else{
                allSquareLabelList.add(line);
            }
        }

        FileWriter writer=new FileWriter(filename,false);
        BufferedWriter bw=new BufferedWriter(writer);
        for(String a:allSquareLabelList){
            bw.write(a+"\n");
        }
        bw.close();

        if(succeed){
            return "删除成功";
        }else{
            return "?????";
        }
    }

    //修改一个方框标注的注释内容 保存到临时文件里
    @RequestMapping(value = "/polishSquareLabel",method = RequestMethod.POST)
    public String polishSquareLabel(@RequestBody SquareLabel squarelabel) throws IOException{
        String polishStr="{"+
                "\"type\":"+squarelabel.getType()+","+
                "\"startX\":"+squarelabel.getStartX()+","+
                "\"startY\":"+squarelabel.getStartY()+","+
                "\"width\":"+"\""+squarelabel.getWidth()+"\""+","+
                "\"height\""+"\""+squarelabel.getHeight()+"\""+","+
                "\"comment\""+"\""+squarelabel.getComment()+"\""+","+
                "\"taskname\""+"\""+squarelabel.getTaskname()+"\""+
                "}";
        Boolean succeed=false;
        ArrayList<String> allSquareLabelList=new ArrayList<>();
        String filename="src/main/java/com/seciii/crowdsourcing/Data/TemporaryFile/"+squarelabel.getTaskname()+".txt";
        File file=new File(filename);
        InputStreamReader reader=new InputStreamReader(new FileInputStream(file));
        BufferedReader br=new BufferedReader(reader);
        String line=null;
        while ((line=br.readLine())!=null){
            if((line.split(",")[1].equals(polishStr.split(",")[1]))&&
                    (line.split(",")[2].equals(polishStr.split(",")[2]))&&
                    (line.split(",")[3].equals(polishStr.split(",")[3]))&&
                    (line.split(",")[4].equals(polishStr.split(",")[4]))
                    ){
                allSquareLabelList.add(polishStr);
                succeed=true;
            }else{
                allSquareLabelList.add(line);
            }
        }

        FileWriter writer=new FileWriter(filename,false);
        BufferedWriter bw=new BufferedWriter(writer);
        for(String a:allSquareLabelList){
            bw.write(a+"\n");
        }
        bw.close();

        if(succeed){
            return "修改成功";
        }else{
            return "?????";
        }
    }


    //从后端取得所有的方框标注标签，传到前端
//    @RequestMapping(value = "/sendAllSquareLabel",method = RequestMethod.POST)
//    public String sendAllSquareLabel(@RequestBody SquareLabel squareLabel){
//        return null;
//    }




    //整体标注

    //将整体标注 保存到临时文件里
    @RequestMapping(value = "/saveWholeLabel",method = RequestMethod.POST)
    public String saveWholeLabel(@RequestBody WholeLabel wholelabel) throws IOException{
        String wholeLabelStr="{"+
                "\"type\":"+"\""+wholelabel.getType()+"\""+","+
                "\"comment\":"+"\""+wholelabel.getComment()+"\""+","+
                "\"taskname\""+"\""+wholelabel.getTaskname()+"\""+
                "}"+"\n";
        String filename="src/main/java/com/seciii/crowdsourcing/Data/TemporaryFile/"+wholelabel.getTaskname()+".txt";

        File file=new File(filename);
        if(!file.exists()){
            file.createNewFile();
        }
        FileWriter writer=new FileWriter(filename,true);
        BufferedWriter bw=new BufferedWriter(writer);
        bw.write(wholeLabelStr);
        bw.close();

        return "success";
    }

    //删除整体标注 保存到临时文件里
    @RequestMapping(value = "/deleteWholeLabel",method = RequestMethod.POST)
    public String deleteWholeLabel(@RequestBody WholeLabel wholelabel) throws IOException{
        String deleteStr="{"+
                "\"type\":"+"\""+wholelabel.getType()+"\""+","+
                "\"comment\":"+"\""+wholelabel.getComment()+"\""+","+
                "\"taskname\""+"\""+wholelabel.getTaskname()+"\""+
                "}";
        Boolean succeed=false;
        ArrayList<String> allSquareLabelList=new ArrayList<>();
        String filename="src/main/java/com/seciii/crowdsourcing/Data/TemporaryFile/"+wholelabel.getTaskname()+".txt";
        File file=new File(filename);
        InputStreamReader reader=new InputStreamReader(new FileInputStream(file));
        BufferedReader br=new BufferedReader(reader);
        String line=null;
        while ((line=br.readLine())!=null){
            if(line.equals(deleteStr)){
                succeed=true;
            }else{
                allSquareLabelList.add(line);
            }
        }

        FileWriter writer=new FileWriter(filename,false);
        BufferedWriter bw=new BufferedWriter(writer);
        for(String a:allSquareLabelList){
            bw.write(a+"\n");
        }
        bw.close();

        if(succeed){
            return "删除成功";
        }else{
            return "?????";
        }
    }

    //修改整体标注的内容 保存到临时文件里
    @RequestMapping(value = "/polishWholeLabel",method = RequestMethod.POST)
    public String polishWholeLabel(@RequestBody WholeLabel wholelabel) throws IOException{
        String polishStr="{"+
                "\"type\":"+"\""+wholelabel.getType()+"\""+","+
                "\"comment\":"+"\""+wholelabel.getComment()+"\""+","+
                "\"taskname\""+"\""+wholelabel.getTaskname()+"\""+
                "}";
        Boolean succeed=false;
        ArrayList<String> allSquareLabelList=new ArrayList<>();
        String filename="src/main/java/com/seciii/crowdsourcing/Data/TemporaryFile/"+wholelabel.getTaskname()+".txt";
        File file=new File(filename);
        InputStreamReader reader=new InputStreamReader(new FileInputStream(file));
        BufferedReader br=new BufferedReader(reader);
        String line=null;
        while ((line=br.readLine())!=null){
            if((line.split(",")[0].equals(polishStr.split(",")[0]))&&
                    (line.split(",")[2].equals(polishStr.split(",")[2]))
                    ){
                allSquareLabelList.add(polishStr);
                succeed=true;
            }else{
                allSquareLabelList.add(line);
            }
        }

        FileWriter writer=new FileWriter(filename,false);
        BufferedWriter bw=new BufferedWriter(writer);
        for(String a:allSquareLabelList){
            bw.write(a+"\n");
        }
        bw.close();

        if(succeed){
            return "修改成功";
        }else{
            return "?????";
        }
    }


    //从后端取得所有整体标注，传到前端
//    @RequestMapping(value = "/sendAllWholeLabel",method = RequestMethod.POST)
//    public String sendAllWholeLabel(@RequestBody WholeLabel wholeLabel){
//        return null;
//    }


}
