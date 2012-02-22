/**
 * 
 */
package zb.eptools.ep;

import java.util.List;

/**
 * EP project structure
 * 
 * @author <a href="mailto:zou.b@neusoft.com">×Þ²¬</a>
 */
public class EPProject {

	// Project path exists in file system
	// other paths in this plugin defined are all "relative"
	public String absoluatePath;
	// Name of project
	public String name;

	// Preapred modules by EP self
	public List<Module> coreModules;

	// Modules developed by user
	public List<Module> customizedModules;

	public String configFile;
}
