package controllers;

import models.sys.SysUser;
import org.apache.log4j.Logger;
import play.data.validation.Email;
import play.data.validation.Equals;
import play.data.validation.MinSize;
import play.data.validation.Required;
import play.mvc.Controller;

import java.util.List;

/**
 * Desc:
 * --------
 * SysUser: zou bo
 * Date: 12-1-18 上午11:01
 */
public class SysUsers extends Controller implements AppConstants{

//    static Logger log=Logger.getLogger(SysUsers.class);



    public static void index(){
        show(1);
    }


    public static void show(Integer page){
        Integer pageSequence=page==null || page<0?1:page;
        // Parameters input
//        log.info("<< received page = "+page+", pageSequence = "+pageSequence);
        play.Logger.info("<< received page = " + page + ", pageSequence = " + pageSequence);
        // users specified by page number and page size
        List<SysUser> userList=SysUser.all().fetch(pageSequence,SYS_USER_PAGE_SIZE);
        // record count of SysUser
        Long userCount=SysUser.count();
        // rend them to page
        render(userCount,userList,pageSequence);
    }

    public static void create(@Required(message = "Login name is required") String loginName,
                              @Required(message = "You MUST specify a password") @MinSize(5) String password,
                              @Equals("password") String password2,
                              @Required String nickName){
        play.Logger.info("create user: {loginName:"+loginName+",...}");
        SysUser user=new SysUser(loginName,nickName==null?loginName:nickName,password);
        user.save();
        index();
    }


    //todo: check whether login user is admin
    public static void delete(@Required Long id){
        SysUser user=SysUser.findById(id);
        
        if(user!=null){
            user.delete();
            play.Logger.info("SysUser("+id+") deleted okay, and render page to index");
        }else{
            play.Logger.info("SysUser("+id+") doesn't exist");
        }
        index();
    }
}
