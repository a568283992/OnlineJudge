package cn.jsut.Oj_Judge_Server.Result;

import java.util.List;

/**
 * 判题的封装类
 * @author siney
 *
 */
public class JudgeContent {
	private String judgeId;
	private String language;
	private String code;
	private boolean specialJudge;
	private List<String> input;
	private List<String> output;
	private long cpu_limit;
	private long ram_limit;
	public String getJudgeId() {
		return judgeId;
	}
	public void setJudgeId(String judgeId) {
		this.judgeId = judgeId;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public boolean isSpecialJudge() {
		return specialJudge;
	}
	public void setSpecialJudge(boolean specialJudge) {
		this.specialJudge = specialJudge;
	}
	public List<String> getInput() {
		return input;
	}
	public void setInput(List<String> input) {
		this.input = input;
	}
	public List<String> getOutput() {
		return output;
	}
	public void setOutput(List<String> output) {
		this.output = output;
	}
	public long getCpu_limit() {
		return cpu_limit;
	}
	public void setCpu_limit(long cpu_limit) {
		this.cpu_limit = cpu_limit;
	}
	public long getRam_limit() {
		return ram_limit;
	}
	public void setRam_limit(long ram_limit) {
		this.ram_limit = ram_limit;
	}
	public JudgeContent(String judgeId, String language, String code, boolean specialJudge, List<String> input,
			List<String> output, long cpu_limit, long ram_limit) {
		super();
		this.judgeId = judgeId;
		this.language = language;
		this.code = code;
		this.specialJudge = specialJudge;
		this.input = input;
		this.output = output;
		this.cpu_limit = cpu_limit;
		this.ram_limit = ram_limit;
	}
	public JudgeContent() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "JudgeContent [judgeId=" + judgeId + ", language=" + language + ", code=" + code + ", specialJudge="
				+ specialJudge + ", input=" + input + ", output=" + output + ", cpu_limit=" + cpu_limit + ", ram_limit="
				+ ram_limit + "]";
	}
	
	
	
}
