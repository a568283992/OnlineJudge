package cn.jsut.Oj_Judge_Server;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.junit.Test;

import com.alibaba.fastjson.JSON;

import cn.jsut.Oj_Judge_Server.Result.JudgeContent;
import cn.jsut.Oj_Judge_Server.Result.JudgeResult;

public class Junit {
	
	@Test
	public void test(){
		JudgeContent test = new JudgeContent();
		test.setCode("Code1");
		test.setCpu_limit(112);
		String jsonString = JSON.toJSONString(test);
		System.out.println(jsonString);
		JudgeContent result = JSON.parseObject(jsonString,JudgeContent.class);
		System.out.println(result);
	}
	
	@Test
	public void client(){
		String SERVER_IP = "172.17.0.2";
		int PORT = 8888;
		int TIME_OUT = 300000;
		try{
			//获取会话
			TTransport  transport = new TSocket(SERVER_IP,PORT,TIME_OUT);
			//选择会话协议，注意要和服务端一致，不然无法通信
			TProtocol protocol = new TCompactProtocol(transport);
			JudgeServer.Client client = new JudgeServer.Client(protocol);
			//打开会话
			transport.open();
			
			//调用服务端方法，并返回
			JudgeContent content = new JudgeContent();
			content.setJudgeId("asdfc232rdsaf");
			content.setLanguage("c");
			String code = "#include<stdio.h>\nint main(){\nprintf(\"hello\");\nreturn 0;\n}";
			content.setCode(code);
			System.out.println(client.compileJudge(JSON.toJSONString(content)));
			transport.close();
		}catch (Exception e){
			System.out.println(e);
		}
	}
	
	@Test
	public void test3(){
		Calendar c = Calendar.getInstance();
		Date time = c.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss -> ");
		String format2 = format.format(time);
		System.out.println(format2);
		
	}
	
	@Test
	public void test4() throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("/var/judgeFile.c"), "utf-8"));
		StringBuffer sb = new StringBuffer();
		String s = null;
		while((s=br.readLine())!=null){
			sb.append(s+"\n");
		}
		System.out.println(sb.toString());
		br.close();
	}
	
	//map测试
	@Test
	public void test5() throws Exception{
		JudgeResult result = new JudgeResult();
		Map<Integer, String> map = new TreeMap<Integer,String>();
		for(int i = 1;i<=10;i++){
			map.put(i, "wer");
		}
		
		Set<Integer> keySet = map.keySet();
		result.setUsr_error_output(map);
		System.out.println(JSON.toJSONString(result));
		JudgeResult r2 = JSON.parseObject(JSON.toJSONString(result),JudgeResult.class);
		System.out.println(r2.getUsr_error_output().size());
	}
}
