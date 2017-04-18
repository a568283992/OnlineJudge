package cn.jsut.Oj_Judge_Server;

import java.io.File;
import org.apache.thrift.TException;
import com.alibaba.fastjson.JSON;

import cn.jsut.Language.Do_C;
import cn.jsut.Language.Do_Cplusplus;
import cn.jsut.Language.Do_Java;
import cn.jsut.Oj_Judge_Server.Result.JudgeContent;
import cn.jsut.Oj_Judge_Server.Result.JudgeResult;
import cn.jsut.Tools.Tools;

/**
 * 测评处
 * @author siney
 *
 */
public class JudgeServerImp implements JudgeServer.Iface{
	
	/**
	 * @param content 传输测评所需要的数据等
	 */
	private static String JUDGER_PATH = null; 
	private static String JUDGER_LOGS = null; 
	private static String log_file = null;
	private static final String judgeFile = "/var/judgeFile.c";
	private static final String compileFile = "/var/compileFile.c";
	static{
		//创建判题根目录以及创建log日志
		JUDGER_PATH = System.getenv("judger_path");
		JUDGER_LOGS = System.getenv("judger_logs");
		File path = new File(JUDGER_PATH);
		File log_path = new File(JUDGER_LOGS);
		log_file = JUDGER_LOGS+"/judgeServer.log";
		if(!path.exists())path.mkdir();
		if(!log_path.exists())log_path.mkdir();
	}
	
	/**
	 * 判题入口
	 */
	public String startJudge(String content) throws TException {
		System.out.println("judging....");

		JudgeResult judge = judge(content);
		System.out.println("over.....");
		System.out.println(judge);
		return JSON.toJSONString(judge);
	}
	
	private JudgeResult judge(String content) {
		JudgeResult result = new JudgeResult();
		
		return result;
	}

	/**编译入口
	 * @param content 传输测评所需要的数据等
	 */
	public String compileJudge(String content) throws TException {
		JudgeResult result = compile(content);
		if(result==null){
			return startJudge(content);		//如果编译成功，进入判题
		}else{
			return JSON.toJSONString(result);	//失败就直接返回吧，再见再见
		}
	}
	
	/**
	 * 进行compile和compiling2个编译，并返回结果，通过status判断是否编译成功
	 * 编译文件，并返回结果
	 * @param content 传入的编译文件内容
	 * @return 返回编译后的结果  错误 or 成功的结果
	 */
	public JudgeResult compile(String content){
		JudgeResult result = null;
		try{
			JudgeContent compileContent = JSON.parseObject(content,JudgeContent.class);
			File file = new File(JUDGER_PATH+"/"+compileContent.getJudgeId());
			if(file.exists())Tools.deleteAllFile(file);
			file.mkdirs();
			
			
			//收集判题数据,因为是编译，所以设置都为默认即可
			String main = null;
			switch (compileContent.getLanguage()) {
			case "java":main = Do_Java.getCompileCode(JUDGER_PATH, compileContent);break;
			case "c":main = Do_C.getCCompileCode(JUDGER_PATH, compileContent);break;
			case "c++":main = Do_Cplusplus.getCplusplusCompileCode(JUDGER_PATH, compileContent);break;
			default:break;
			}
			File source = new File(compileFile);
			File Dest = new File(file.toString()+"/"+source.getName());
			Tools.cpTo(source, Dest);
			Tools.instead(Dest, "{PROPERTIES}", main);
			Tools.createMain(file,compileContent);
			
			
			//开始执行编译
			String compiles = "gcc "+file.toString()+"/compileFile.c -ldl -lseccomp -o "+file.toString()+"/compileFile";
			Process process = Runtime.getRuntime().exec(compiles);
			int status = process.waitFor();
			if(status==0){
				compiles = file.toString()+"/compileFile";
				process = Runtime.getRuntime().exec(compiles);
				status = process.waitFor();
				result = Tools.compileResult(process,compileContent,new File(file.toString()+"/error.log"));
			}else{
				result = Tools.compileResult(process,status,compileContent);
			}
			Tools.deleteAllFile(file);
		}catch(Exception e){
			Tools.writeToFile(e.getMessage(), new File(log_file));
		}
		return result;
	}

	public static void main(String[] args) throws TException {
		JudgeContent content = new JudgeContent();
		content.setJudgeId("asdfc232rdsaf");
		content.setLanguage("java");
		String code = "public class Main{public static void main(String[] args){System.out.println(\"hello\");}}";
		content.setCode(code);
	}
}
