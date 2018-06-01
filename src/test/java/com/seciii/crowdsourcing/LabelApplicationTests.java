package com.seciii.crowdsourcing;

import org.junit.Test;
import org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.seciii.crowdsourcing.Dao.Task;
import com.seciii.crowdsourcing.Controller.TaskController;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LabelApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test
	public void checkTaskInformationAsLookerTest() {
		TaskController tc = new TaskController();
		Task task = new Task();
		task.setTaskname("0");
		try{
			assertEquals("", tc.checkTaskInformationAsLooker(task));
		}catch(Exception e){
			e.printStackTrace();
		}
		//System.out.println(tc.checkTaskInformationAsLooker(task) throw IOException);

	}

}
