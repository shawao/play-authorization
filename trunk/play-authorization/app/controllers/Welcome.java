package controllers;

import play.mvc.Controller;

/**
 * Desc  :
 * -----
 * Author: <a href="mailto:ember319@gmail.com">ember</a>
 * Date  : 12-1-19
 * Time  : 下午9:34
 */
public class Welcome extends Application{

    public static void index(){
        render();
    }
    
    public static void index(Long userId){
        render();
    }
}
