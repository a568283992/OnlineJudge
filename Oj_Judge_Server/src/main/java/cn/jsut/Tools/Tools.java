package cn.jsut.Tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

import cn.jsut.Oj_Judge_Server.Result.JudgeContent;
import cn.jsut.Oj_Judge_Server.Result.JudgeResult;


/**
 * 工具包，里面包括一切共有的方法
 * @author siney
 *
 */
public class Tools {
	
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss -> ");

	/**
	 * 删除所有文件
	 * @param file
	 */
	public static void deleteAllFile(File file){
		File[] listFiles = file.listFiles();
		for(int i = 0;i<listFiles.length;i++){
			if(listFiles[i].isDirectory())
				deleteAllFile(listFiles[i]);
			listFiles[i].delete();	
		}
		file.delete();
	}
	
	/**
	 * 将日志内容输出的制定日志
	 * @param content 输入需要的内容
	 * @param file	输入目的文件(remenber file is a file not a path)
	 */
	public static void writeToFile(String content,File file){
		try {
			//如果出现异常，我们写入log日志中
			BufferedWriter br = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true),"UTF-8"));
			br.write(dateFormat.format(Calendar.getInstance().getTime())+content+"\n\n");
			br.close();
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
	}
	
	/**
	 * 将仓库中备用的函数拷贝到判题目录中进行判题
	 * @param source	原仓库路径
	 * @param destFile	目的判题目录路径
	 */
	public static void cpTo(File source,File destFile){
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(source), "utf-8"));
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(destFile),"utf-8"));
			StringBuffer sb = new StringBuffer();
			String s = null;
			while((s=br.readLine())!=null){
				sb.append(s+"\n");
			}
			bw.write(sb.toString());
			bw.close();
			br.close();
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	/**
	 * 将核心的运行文件中的{PROPERTIES}参数进行替换
	 * @param file		判题的路径
	 * @param before	需要替换的参数->{PROPERTIES}
	 * @param after		替换后的参数
	 */
	public static void instead(File file,String before,String after){
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
			StringBuffer sb = new StringBuffer();
			String s = null;
			while((s=br.readLine())!=null){
				sb.append(s+"\n");
			}
			br.close();
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"utf-8"));
			bw.write(sb.toString().replace(before, after));
			bw.close();
		}catch(Exception e){
			System.out.println(e);
		}
	}
	/**
	 * 创建主函数
	 * @param file 传入的文件路径
	 * @param content 传入的用户对象
	 * @throws Exception 如果创建时出现异常，则抛出异常
	 */
	public static void createMain(File file,JudgeContent content)throws Exception{
		switch(content.getLanguage()){
		case "java":file = new File(file.toString()+"/Main.java");break;
		case "c":file = new File(file.toString()+"/main.c");break;
		case "c++":file = new File(file.toString()+"/main.cpp");break;
		default:throw new RuntimeException("create Main error!");
		}
		file.createNewFile();
		
		//将用户的代码放入
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
		bw.write(content.getCode());
		bw.close();
	}
	
	/**
	 * 此为编译判题核心文件时使用：
	 * 当status状态不为0时，使用此方法直接放回错误信息
	 * @param process 当前错误进程
	 * @param status 当前返回的核心代码编译时错误状态
	 * @param content 当前的判题的对象
	 * @return 错误结果
	 * @throws Exception
	 */
	public static JudgeResult compileResult(Process process,int status,JudgeContent content) throws Exception{
		BufferedReader readStderr = new BufferedReader(new InputStreamReader(process.getErrorStream())); 
		StringBuffer sb = new StringBuffer();
		String s = "";
		while((s = readStderr.readLine())!=null){
			sb.append(s+"\n");
		}
		readStderr.close();
		return new JudgeResult(status,"compile",sb.toString());
	}
	
	/**此为判题核心文件调用用户文件时使用
	 * compling编译判断，如果返回为null，那么说明过程没有出现错误
	 * @param process 运行结束后的进程
	 * @param content 此时进入判题的对象
	 * @param file	error.log文件所在的路径
	 * @return	返回Result结果，但是如果status为0那么返回为null
	 * @throws Exception
	 */
	public static JudgeResult compileResult(Process process,JudgeContent content,File file) throws Exception{
		BufferedReader readStdout = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String[][] containing = new String[4][2];
		String s = "";
		int t = 0;
		while((s = readStdout.readLine())!=null){
			containing[t++] = s.trim().split("=");
		}
		readStdout.close();
		
		//如果状态不为0，那么就需要读取error.log内容了！
		int status = Integer.parseInt(containing[0][1].trim());
		if(status!=0){
			readStdout = new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));
			StringBuffer sb = new StringBuffer();
			while((s = readStdout.readLine())!=null){
				sb.append(s+"\n");
			}
			readStdout.close();
			return new JudgeResult(status,"compiling",sb.toString());
		}
		return null;
	}
}
