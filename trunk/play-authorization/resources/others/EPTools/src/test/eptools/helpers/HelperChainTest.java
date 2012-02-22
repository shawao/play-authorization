/**
 * 
 */
package test.eptools.helpers;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;

import zb.eptools.helpers.HelperChain;
import zb.eptools.helpers.ResCmpComponentHelper;
import zb.eptools.utils.FileUtil;

/**
 * @author <a href="mailto:zou.b@neusoft.com">×Þ²¬</a>
 *
 */
public class HelperChainTest {
	
	static Logger log=Logger.getLogger(HelperChainTest.class);

	@Test
	public void testMain(){
		testHelperChain();
	}
	
	
	public void testHelperChain(){
		
		
		String projectPath="E:\\neusoft_projects\\prj_sgps\\content\\resource";
		//String projectPath="E:\\neusoft_projects\\YJSellCode\\content\\resource";
		
		
		try{
			log.info("findAllXml------------------------start");
			long start=System.currentTimeMillis();
			List<File> files=FileUtil.findFileByExtensionRecurse(projectPath, "xml");
			log.info("files got: "+files.size()+", "+(System.currentTimeMillis()-start) +" ms cost");
			log.info("findAllXml------------------------end");
			
			
			HelperChain helperChain=new HelperChain();
			helperChain.append(new ResCmpComponentHelper());
			
			long parseStart=System.currentTimeMillis();
			log.info("parseXml------------------------start");
			helperChain.process(files);
			log.info("parseXml------------------------end (cost: "+(System.currentTimeMillis()-parseStart)+")");
			
			log.info(helperChain.getSuccessCount()+"/"+helperChain.getAllExists()+" success files processed");
			log.info(helperChain.getFailedCount()+"/"+helperChain.getAllExists()+" failed files processed");
			
			for(File ff:helperChain.getFailedFiles()){
				log.info(ff.getAbsolutePath());
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
		}
	}
}
