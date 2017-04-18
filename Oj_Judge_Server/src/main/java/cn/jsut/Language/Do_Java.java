package cn.jsut.Language;

import cn.jsut.Oj_Judge_Server.Result.JudgeContent;

/**
 * java语言类包
 * @author siney
 *
 */
public class Do_Java {
	/**
	 * java的编译参数设置
	 * @param path java文件所在路径
	 * @param content 此时的判题对象
	 * @return 返回所需要的{PROPERTIES}参数
	 */
	public static String getCompileCode(String path,JudgeContent content){
		StringBuffer sb = new StringBuffer();
		String compilePath= path+"/"+content.getJudgeId();
		sb.append("char error_path[] = \""+path+"/"+content.getJudgeId()+"/error.log\";\n");
		sb.append("char *argvs[] = {\"/usr/bin/javac\",\""+compilePath+"/Main.java\",\"-d\",\""+compilePath+"\",NULL};\n");
		sb.append("char command[] = \"/usr/bin/javac\";\n");
		return sb.toString();
	}
}
