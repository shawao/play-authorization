/**
 * 
 */
package zb.eptools.ep;

import java.io.File;

/**
 * 
 * @author <a href="mailto:zou.b@neusoft.com">×Þ²¬</a>
 *
 */
public interface Configurable {

	// Parsing from config file path
	void fromConfigFile(String filePath);
	
	// Parsing from config file
	void fromConfigFile(File file);
	
	// Write into specified config file
	void toConfigFile(String filePath);
}
