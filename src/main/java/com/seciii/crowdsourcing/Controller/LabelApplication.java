package com.seciii.crowdsourcing.Controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class LabelApplication {

	public static void main(String[] args) {

//		SpringApplication.run(LabelApplication.class, args);
		UrlController.task.setTaskname("0");
		UrlController.user.setUsername("123");
		TaskController tc=new TaskController();
		try{ tc.integration(); }catch (IOException e){}

	}
}
