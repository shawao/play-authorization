package zb.eptools.ep;

import java.util.List;

/**
 * 指包括页面（View）、控制器（Controller）和业务类（Model）的一组功能
 * @author <a href="mailto:zou.b@neusoft.com">邹铂</a>
 *
 */
public class Module {

	/**
	 * 对象关系描述：
	 * Module(1) ---- Component(1) ---- View(1-*)
	 * Module(1) ---- Component(1) ---- Bo(1-*)
	 */
	
	public String name;
	
	public List<View> views;
	
	/**
	 *  实际上，每个控制器对应一个模块；
	 *  View可能有多个页面（及每画面对应的js）
	 */
	public Component component;
}
