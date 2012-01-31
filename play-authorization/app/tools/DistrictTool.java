package tools;

import models.sys.District;
import org.apache.log4j.Logger;
import play.mvc.Scope;

import java.util.List;
import java.util.TreeMap;

/**
 * Desc  :
 * -----
 * Author: <a href="mailto:ember319@gmail.com">ember</a>
 * Date  : 12-1-31
 * Time  : 下午9:31
 */
public class DistrictTool {

    static Logger log = Logger.getLogger(DistrictTool.class);
    
    
    public static String parse(String name,Scope.Params params){
        String provId=params.get(name+"_provinceId");
        String cityId=params.get(name+"_cityId");
        String countyId=params.get(name+"_countyId");

        return new StringBuffer().append(provId).append("_").append(cityId).append("_").append(countyId).toString();
    }
    
}
