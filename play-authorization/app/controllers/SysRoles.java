package controllers;

import models.sys.Function;
import models.sys.SysRole;
import models.sys.SysUser;
import play.data.validation.Equals;
import play.data.validation.MinSize;
import play.data.validation.Required;
import play.data.validation.Unique;
import play.mvc.Controller;

import java.util.ArrayList;
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
        List<SysRole> entityList= SysRole.find("select o from SysRole o order by id desc")
                .from(from).fetch(pageSize);
        Long entityCount=SysRole.count();
        render(entityCount,entityList,pageSequence);
    }


    // return part of html string
    public static void ajaxList() {
        Integer pageSequence = 1;
        List<SysRole> entityList = SysRole.find("select o from SysRole o order by id desc").fetch();
        render(SysRole.count(), entityList, pageSequence);
    }


    public static void create(@Required(message = "Role name is required") String name,
                              @Required(message = "You MUST specify a unique key") String key,
                              String remark){
        log.info("<< create role: {name:"+name+", key:"+key+", remark:"+remark+"}");
        SysRole role=new SysRole(name,key,remark);
        role.save();
//        flash.success("创建角色成功");
        show(1);
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
        show(1);
    }


    public static void assignFunctions(Long roleId, Long[] functionId) {
        if (roleId == null) {
            flash.error("没有选定角色");
        } else if (functionId == null) {
            flash.error("没有选定功能");
        } else {
            SysRole role = SysRole.findById(roleId);

            List<Function> functions = new ArrayList<Function>();

            for (Long rid : functionId) {
                Function function = Function.findById(rid);
                functions.add(function);
            }

            // todo: 这里还需要考虑，已经关联此角色的用户，这些用户已经创建了各自的高效参照表：eff_user_func
            // 对此做批量更新吗？对管理员明确进行提示，表明后台在执行相关批处理，请暂停执行此类功能，直到结束？
            role.assignFunctions(functions);

            StringBuilder buf = new StringBuilder();
            for (Long rid : functionId) {
                buf.append(rid).append(",");
            }
            log.info("Role(" + roleId + ") assigned functions(" + buf.toString() + ") okay");
        }

        show(1);
    }
}
