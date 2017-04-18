package cn.jsut.Oj_Judge_Server.Result;

import java.util.Map;

/**
 * 
 * 返回的结果，是否出错
 * @author siney
 *
 */
public class JudgeResult {
	@Override
	public String toString() {
		return "JudgeResult [status=" + status + ", judge_result=" + judge_result + ", judge_type=" + judge_type
				+ ", logs_content=" + logs_content + ", usr_error_output=" + usr_error_output + "]";
	}
	private int status;
	private String judge_result;//AE?PE?
	private String judge_type;//compile or compiling   judge or judging  一种是核心编译，一种是题目编译分开存储
	private String logs_content;//error content or successful content
	private Map<Integer,String> usr_error_output;//if your output is not correct,record your judge info
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getJudge_result() {
		return judge_result;
	}
	public void setJudge_result(String judge_result) {
		this.judge_result = judge_result;
	}
	public String getJudge_type() {
		return judge_type;
	}
	public void setJudge_type(String judge_type) {
		this.judge_type = judge_type;
	}
	public String getLogs_content() {
		return logs_content;
	}
	public void setLogs_content(String logs_content) {
		this.logs_content = logs_content;
	}
	public Map<Integer, String> getUsr_error_output() {
		return usr_error_output;
	}
	public void setUsr_error_output(Map<Integer, String> usr_error_output) {
		this.usr_error_output = usr_error_output;
	}
	public JudgeResult(int status, String judge_type, String logs_content) {
		super();
		this.status = status;
		this.judge_type = judge_type;
		this.logs_content = logs_content;
	}
	public JudgeResult(int status, String judge_result, String judge_type, String logs_content,
			Map<Integer, String> usr_error_output) {
		super();
		this.status = status;
		this.judge_result = judge_result;
		this.judge_type = judge_type;
		this.logs_content = logs_content;
		this.usr_error_output = usr_error_output;
	}
	public JudgeResult() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
