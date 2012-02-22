/**
 * 
 */
package zb.eptools.helpers;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

/**
 * @author <a href="mailto:zou.b@neusoft.com">×Þ²¬</a>
 *
 */
public class HelperChain {
	
	static Logger log=Logger.getLogger(HelperChain.class);

	private List<ResourceHelper> helpers=new LinkedList<ResourceHelper>();
	
	private Map<Class<?>,List<Object>> result=new TreeMap<Class<?>,List<Object>>();
	
	
	private List<File> failedFiles=new LinkedList<File>();
	
	private int successCount=0;
	private int failedCount=0;
	private int allExists=0;

	public HelperChain() {
	}
	
	
	public HelperChain append(ResourceHelper helper){
		if(!helpers.contains(helper))
			helpers.add(helper);
		return this;
	}
	
	
	public void process(String... resourceFilePaths){
		if(resourceFilePaths!=null && resourceFilePaths.length>0){
			for(String resourceFilePath:resourceFilePaths){
				processSingle(new File(resourceFilePath));
			}
		}
	}
	
	public void process(List<File> resourceFiles){
		if(resourceFiles!=null && resourceFiles.size()>0){
			for(File resourceFile:resourceFiles){
				processSingle(resourceFile);
			}
		}
	}
	
	private void processSingle(File resourceFile){
		if(!resourceFile.exists()){
			log.info("File ("+resourceFile.getAbsolutePath()+") doesn't exist");
			return;
		}
		
		for(ResourceHelper helper:helpers){
			helper.process(resourceFile);
		}
		allExists++;
	}
	


	/**
	 * @return the successCount
	 */
	public int getSuccessCount() {
		return successCount;
	}


	/**
	 * @return the failedCount
	 */
	public int getFailedCount() {
		return failedCount;
	}


	/**
	 * @return the allExists
	 */
	public int getAllExists() {
		return allExists;
	}


	/**
	 * @return the failedFiles
	 */
	public List<File> getFailedFiles() {
		return failedFiles;
	}


	public Map<Class<?>,List<Object>> getResult(){
		return result;
	}
	
	public List<ResourceHelper> getHelpers(){
		return helpers;
	}
	
}
