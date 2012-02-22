/**
 * 
 */
package zb.eptools.ep;

import java.util.List;

/**
 * 业务对象
 * 
 * @author <a href="mailto:zou.b@neusoft.com">邹铂</a>
 * 
 */
public class Bo {

	public List<Bo> reliedBos;

	public String name;
	public String clazz;

	public List<MethodTransactionPolicy> methTransactionPolicies;

	/**
	 * 这里主要描述对事务的支持
	 */
	public class MethodTransactionPolicy {
		public String name;
		public String propagation;
	}
}
