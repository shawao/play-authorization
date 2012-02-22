/**
 * 
 */
package zb.eptools.utils;

/**
 * @author <a href="mailto:zou.b@neusoft.com">邹铂</a>
 * 字符串工具方法集
 */
public class StringUtil {

	public static boolean isEmpty(String str){
		return str==null || str.trim().equals("");
	}
	
	
	public static boolean equals(String s1,String s2){
		return s1==null?s2==null:s1.equals(s2);
	}
	
	public static String toString(String[] ss){
		if(ss==null) return null;
		StringBuilder buf=new StringBuilder();
		for(int i=0;i<ss.length;i++){
			buf.append(ss[i]);
			if(i<(ss.length-1))
				buf.append(",");
		}
		return buf.toString();
	}
}
