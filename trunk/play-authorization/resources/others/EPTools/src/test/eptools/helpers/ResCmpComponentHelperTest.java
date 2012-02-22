/**
 * 
 */
package test.eptools.helpers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;

import zb.eptools.helpers.ResCmpComponentHelper;
import zb.eptools.utils.FileUtil;

/**
 * @author <a href="mailto:zou.b@neusoft.com">×Þ²¬</a>
 *
 */
public class ResCmpComponentHelperTest {

	static Logger log=Logger.getLogger(ResCmpComponentHelperTest.class);
	
	
	@Test
	public void testMain(){
		test_ParseFile();
	}
	
	
	public void test_ParseFile(){
		String filePath="E:\\neusoft_projects\\prj_sgps\\content\\resource\\component\\ac\\cmp_ac_acct_age_manage.xml";
		File resourceFile=new File(filePath);
		log.debug(FileUtil.readFile(resourceFile,"GBK"));
		
		ResCmpComponentHelper rccHelper=new ResCmpComponentHelper();
		if (rccHelper.fit(resourceFile)){
			rccHelper.process(resourceFile);
		}
	}
	
	
	//@Test
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
			
			
			ResCmpComponentHelper rccHelper=new ResCmpComponentHelper();
			
			// write all fitted files to file
			bw=new BufferedWriter(new FileWriter("E:\\neusoft_projects\\prj_sgps\\xml_list"));
			
			int rccNumber=0;
			for(File file:files){
				//System.out.println(file.getAbsolutePath());
				if(rccHelper.fit(file)){
					bw.append(file.getAbsolutePath());
					bw.newLine();
					rccNumber++;
				}
			}
			System.out.println("Count: "+rccNumber);
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
