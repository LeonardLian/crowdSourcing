package com.seciii.crowdsourcing.Controller;

import com.seciii.crowdsourcing.Dao.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;

/**
 * @author: pis
 * @description: 用户管理和处理
 * @date: create in 下午8:18 2018/3/29
 */
@CrossOrigin
@RestController
public class UserController {


    //保存个人信息
    @RequestMapping(value="/json/saveUserInformation.json",method = RequestMethod.POST)
    public String saveUserInformation(@RequestBody User user) throws IOException{

        return null;
    }

    //保存个人头像
    @RequestMapping(value="/saveUserImg",method = RequestMethod.POST)
    @ResponseBody
    public String saveUserImg(@RequestParam("classIcon") MultipartFile file) throws IOException{
        File newfile=new File("/Users/Leonarda/Desktop/Img/a.jpeg");
        if(!newfile.exists()){
            newfile.createNewFile();
        }

        file.transferTo(newfile);
        return "success";
    }

    //显示个人信息
    @RequestMapping(value="/showUserInformation",method = RequestMethod.POST)
    public String showUserInformation(@RequestBody User user) throws IOException{
        return null;
    }

    //将注册的新用户保存
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@RequestBody User user) throws IOException{
        String username=user.getUsername();
        String password=user.getPassword();
        Boolean canregister=true;

        String filename="src/main/java/com/seciii/crowdsourcing/Data/UserList/UserList.txt";
        File file=new File(filename);
        InputStreamReader srreader=new InputStreamReader(new FileInputStream(file));
        BufferedReader reader=new BufferedReader(srreader);
        String line=null;
        while((line=reader.readLine())!=null){
            String usern=line.split(" ")[0];
            if(usern.equals(username)){
                canregister=false;
                break;
            }
        }

        if(canregister){
            User newuser=new User(username,password);
            String saveStr=newuser.getUsername()+" "+newuser.getPassword()+" "+newuser.getPoint()+" "+newuser.getTaskAddress()+"\n";
            FileWriter writer=new FileWriter(filename,true);
            BufferedWriter bw=new BufferedWriter(writer);
            bw.write(saveStr);
            bw.close();
            return "注册成功！";
        }else{
            return "用户名已被使用！";
        }
    }



    //登录时判断是否正确
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(@RequestBody User user) throws IOException{
        String username=user.getUsername();
        String password=user.getPassword();
        Boolean isright=false;

        String filename="src/main/java/com/seciii/crowdsourcing/Data/UserList/UserList.txt";
        File file=new File(filename);
        InputStreamReader srreader=new InputStreamReader(new FileInputStream(file));
        BufferedReader reader=new BufferedReader(srreader);
        String line=null;
        while((line=reader.readLine())!=null){
            String usern=line.split(" ")[0];
            if(usern.equals(username)){
                String pas=line.split(" ")[1];
                if(password.equals(pas)){
                    isright=true;
                    break;
                }else{
                    isright=false;
                    break;
                }
            }
        }

        if(isright){
            return "登录成功";
        }else{
            return "密码错误";
        }
        //return line;
    }


    //修改账户密码
    @RequestMapping(value = "/polishPassword",method = RequestMethod.POST)
    public String polishPassword(@RequestBody User user) throws IOException{
        String username=user.getUsername();
        String password=user.getPassword();
        Boolean succeed=false;

        ArrayList<String> oldone=new ArrayList<>();

        String filename="src/main/java/com/seciii/crowdsourcing/Data/UserList/UserList.txt";
        File file=new File(filename);
        InputStreamReader srreader=new InputStreamReader(new FileInputStream(file));
        BufferedReader reader=new BufferedReader(srreader);
        String line=null;
        while((line=reader.readLine())!=null){
            String usern=line.split(" ")[0];
            if(usern.equals(username)){
                String newuser=line.split(" ")[0]+" "+password+" "+line.split(" ")[2]+" "+line.split(" ")[3];
                oldone.add(newuser);
                succeed=true;
            }else{
                oldone.add(line);
            }
        }

        FileWriter writer=new FileWriter("src/main/java/com/seciii/crowdsourcing/Data/UserList/UserList.txt",false);
        BufferedWriter bw=new BufferedWriter(writer);
        for(String str:oldone){
            bw.write(str+"\n");
        }
        bw.close();

        if(succeed){
            return "修改成功";
        }else{
            return "？？？？";
        }
    }

}
