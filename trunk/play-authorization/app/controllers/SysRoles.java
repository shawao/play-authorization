package controllers;

import models.sys.SysRole;
import models.sys.SysUser;
import play.data.validation.Equals;
import play.data.validation.MinSize;
import play.data.validation.Required;
import play.data.validation.Unique;
import play.mvc.Controller;

import java.util.List;

/**
 * Desc:
 * --------
 * SysUser: zou bo
 * Date: 12-1-18 上午11:00
 */
public class SysRoles extends Application{

    public static void index(){
        show(1);
    }


    public static void show(Integer page){
        Integer pageSequence=page==null || page<0?1:page;
        int from=(pageSequence-1)*pageSize;
        List<SysRole> roleList= SysRole.all().from(from).fetch(pageSize);
        Long roleCount=SysRole.count();
        render(roleCount,roleList,pageSequence);
    }


    public static void create(@Required(message = "Role name is required") String name,
                              @Required(message = "You MUST specify a unique key") String key,
                              String remark){
        log.info("<< create role: {name:"+name+", key:"+key+", remark:"+remark+"}");
        SysRole role=new SysRole(name,key,remark);
        role.save();
        flash.success("created role okay");
        index();
    }


    //todo: check whether login user is admin
    public static void delete(@Required Long id){
        SysRole role=SysRole.findById(id);

        if(role!=null){
            role.delete();
            log.info(">> SysRole("+id+") deleted okay, and render page to index");
        }else{
            log.info(">> SysRole("+id+") doesn't exist");
        }
        index();
    }
}
