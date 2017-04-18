package cn.jsut.Language;

import cn.jsut.Oj_Judge_Server.Result.JudgeContent;

/**
 * C++语言类包
 * @author siney
 *
 */
public class Do_Cplusplus {
	/**
	 * 
	 * c++的编译参数设置
	 * @param path c++文件所在路径
	 * @param content 此时的判题对象
	 * @return 返回所需要的{PROPERTIES}参数
	 */
	public static String getCplusplusCompileCode(String path,JudgeContent content){
		StringBuffer sb = new StringBuffer();
		String compilePath= path+"/"+content.getJudgeId();
		sb.append("char error_path[] = \""+compilePath+"/error.log\";\n");
		sb.append("char *argvs[] = {\"/usr/bin/g++\",\"-DONLINE_JUDGE\",\"-O2\",\"-w\",\"-std=c++11\",\""+compilePath+"/main.cpp\",\"-lm\",\"-o\",\""+compilePath+"/main\",NULL};\n");
		sb.append("char command[] = \"/usr/bin/g++\";\n");
		return sb.toString();
	}
}
