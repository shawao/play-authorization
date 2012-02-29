package utils;

import play.mvc.Scope;

/**
 * Desc  :
 * -----
 * Author: <a href="mailto:ember319@gmail.com">ember</a>
 * Date  : 12-2-27
 * Time  : 下午9:17
 */
public class ParamUtil {
    
    public static <T> T getParameter(String paramName, Scope.Params params, Class<T> clazz){
        return params._contains(paramName)?params.get(paramName,clazz):null;
    }
}
