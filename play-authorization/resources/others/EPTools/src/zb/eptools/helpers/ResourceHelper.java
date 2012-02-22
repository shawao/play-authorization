/**
 * 
 */
package zb.eptools.helpers;

import java.io.File;
import java.util.List;

/**
 * @author <a href="mailto:zou.b@neusoft.com">邹铂</a>
 *
 */
public interface ResourceHelper {
	
	public static final String FILE_ENCODING="GBK";
	

	/**
	 * 由实现类自行过滤掉不需要处理的资源，直接return即可
	 * @param resourcePath
	 */
	void process(File resourceFile);
	
	/**
	 * 验证文件是否符合Helper类型
	 */
	boolean fit(File resourceFile);
	
	/**
	 * 获取处理结果
	 * @return
	 */
	List<Object> getResources();
	
	/**
	 * 可以通过getResourceClass指定class类型，来获取转型后的数据
	 * @param clazz
	 * @return
	 */
	<T> List<T> getResources(Class<T> clazz);
	
	/**
	 * 获取资源类型
	 * @return
	 */
	Class<?> getResourceClass();
}
