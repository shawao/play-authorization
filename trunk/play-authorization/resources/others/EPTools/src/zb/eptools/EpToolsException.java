/**
 * 
 */
package zb.eptools;

/**
 * ·â×°²å¼þÒì³£
 * @author <a href="mailto:zou.b@neusoft.com">×Þ²¬</a>
 *
 */
public class EpToolsException extends Exception{

	private static final long serialVersionUID = 2908280128821788050L;

	/**
	 * 
	 */
	public EpToolsException() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public EpToolsException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public EpToolsException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public EpToolsException(Throwable arg0) {
		super(arg0);
	}
}
