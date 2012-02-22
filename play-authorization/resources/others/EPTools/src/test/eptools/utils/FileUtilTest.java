/**
 * 
 */
package test.eptools.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.logging.Logger;

import org.junit.Test;

import zb.eptools.utils.FileUtil;

/**
 * @author <a href="mailto:zou.b@neusoft.com">×Þ²¬</a>
 *
 */
public class FileUtilTest {
	
	static Logger log=Logger.getLogger(FileUtilTest.class.getName());

	@Test
	public void test_findFileByExtensionRecurse(){
		log.info("hello junit test");
		String projectPath="E:\\neusoft_projects\\prj_sgps\\content\\resource";
		//String projectPath="E:\\neusoft_projects\\YJSellCode\\content\\resource";
		
		
		BufferedWriter bw=null;
		try{
			System.out.println("------------------------start");
			long start=System.currentTimeMillis();
			List<File> files=FileUtil.findFileByExtensionRecurse(projectPath, "xml");
			log.info("files got: "+files.size()+", "+(System.currentTimeMillis()-start) +" ms cost");
			
			bw=new BufferedWriter(new FileWriter("E:\\neusoft_projects\\prj_sgps\\xml_list"));
			for(File file:files){
				//System.out.println(file.getAbsolutePath());
				bw.append(file.getAbsolutePath());
				bw.newLine();
			}
			System.out.println("------------------------end");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(bw!=null){
				try{
					bw.close();
				}catch(Exception e){}
			}
		}
	}
}
