package cn.jsut.Language;

import cn.jsut.Oj_Judge_Server.Result.JudgeContent;

/**
 * C语言类包
 * @author siney
 *
 */
public class Do_C {
	/**
	 * 获取c编译所需要的所有参数
	 * @param path 获取判题根路径
	 * @param content	获取此对象
	 * @return 返回所需要的{PROPERTIES}参数
	 */
	public static String getCCompileCode(String path,JudgeContent content){
		StringBuffer sb = new StringBuffer();
		String compilePath= path+"/"+content.getJudgeId();
		sb.append("char error_path[] = \""+compilePath+"/error.log\";\n");
		sb.append("char *argvs[] = {\"/usr/bin/gcc\",\"-DONLINE_JUDGE\",\"-O2\",\"-w\",\"-std=c99\",\""+compilePath+"/main.c\",\"-lm\",\"-o\",\""+compilePath+"/main\",NULL};\n");
		sb.append("char command[] = \"/usr/bin/gcc\";\n");
		return sb.toString();
	}
}
