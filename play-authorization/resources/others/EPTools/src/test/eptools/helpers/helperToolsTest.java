/**
 * 
 */
package test.eptools.helpers;

import java.io.File;

import org.apache.log4j.Logger;
import org.junit.Test;

import resource.component.Component;

import zb.eptools.helpers.HelperTools;

/**
 * @author <a href="mailto:zou.b@neusoft.com">×Þ²¬</a>
 *
 */
public class helperToolsTest {
	
	
	static Logger log=Logger.getLogger(HelperChainTest.class);

	@Test
	public void testMain(){
		testAutoParse();
	}

	
	public void testAutoParse(){
		try{
			String filePath="E:\\neusoft_projects\\prj_sgps\\content\\resource\\component\\ac\\cmp_ac_acct_age_manage.xml";
			File resourceFile=new File(filePath);
			
			Component component=HelperTools.autoParse(Component.class, resourceFile);
			log.debug("$$ "+component);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
