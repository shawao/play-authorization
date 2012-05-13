package controllers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import models.sys.District;
import models.sys.Organization;
import models.sys.SysRole;
import models.sys.SysUser;
import play.data.validation.Equals;
import play.data.validation.MinSize;
import play.data.validation.Required;
import play.libs.Codec;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Desc:
 * --------
 * SysUser: zou bo
 * Date: 12-1-18 上午11:01
 */
public class SysUsers extends Application {


    public static void index() {
        show(1);
    }


    public static void show(Integer page) {
        Integer pageSequence = page == null || page < 0 ? 1 : page;
        // users specified by page number and page size
        int from = (pageSequence - 1) * pageSize;
        List<SysUser> userList = SysUser.find("select o from SysUser o order by id desc")
                .from(from).fetch(pageSize);
        // record count of SysUser
        Long userCount = SysUser.count();

        List<Organization> organizations = Organization.findAll();

        // rend them to page
        render(userCount, userList, pageSequence,organizations);
    }


    // return part of html string
    public static void ajaxList() {
        Integer pageSequence = 1;
        int from = (pageSequence - 1) * pageSize;
        List<SysUser> entityList = SysUser.find("select o from SysUser o order by id desc").fetch();
        Long entityCount = SysUser.count();
        render(entityCount, entityList, pageSequence);
    }


    public static void create(@Required(message = "Login name is required") String loginName,
                              @Required(message = "You MUST specify a password") @MinSize(5) String password,
                              @Equals("password") String password2,
                              @Required String nickName,
                              String mobile) {
        play.Logger.info("<< create user: {loginName:" + loginName + ",...}");

        SysUser user = new SysUser(loginName.trim(),password);

        District district = null;
        List<Organization> organizations = null;
        List<SysRole> roles = null;

        Long organizationId = params.get("organizationId", Long.class);
        if (organizationId != null) {
            organizations = new ArrayList<Organization>();
            Organization organization = Organization.findById(organizationId);
            organizations.add(organization);
        }

        // 此处不修改password, loginTimes,lastLogin,password,roles
        user.editSysUser(
                params.get("loginName", String.class), nickName == null ? loginName.trim() : nickName.trim(),
                null, params.get("email", String.class),
                params.get("sex", Integer.class), district, params.get("userType", Integer.class),
                params.get("signature", String.class), params.get("address", String.class),
                params.get("mobile", String.class), params.get("phone", String.class),
                params.get("phone2", String.class), params.get("status", Integer.class),
                params.get("remark", String.class),
                0, null, organizations, roles);

        user.save();
        show(1);
    }

    /**
     * 支持批量删除
     * @param id 数组对象，支持批量删除
     */
    public static void delete(@Required Long... id) {
        if (id == null || id.length == 0) {
            flash.error("没有选中用户");
        } else {
            for (Long entityId : id) {
                SysUser user = SysUser.findById(entityId);

                StringBuilder buf=new StringBuilder();
                if (user != null) {
                    user.force2delete();
                    play.Logger.info(">> SysUser(" + entityId + ") deleted okay, and render page to index");
                } else {
                    buf.append("用户（" + entityId + "）不存在");
                    play.Logger.info(">> SysUser(" + entityId + ") doesn't exist");
                }
                String info=buf.toString().trim();
                if(!info.isEmpty())
                    flash.error(info);
            }
        }
        show(1);
    }


    /**
     * 支持同时为某用户赋予多个角色
     * @param userId 用户ID
     * @param roleId 角色ID数组
     */
    public static void assignRoles(@Required Long userId, Long[] roleId) {
        if (userId == null) {
            flash.error("没有选定用户1");
        } else if (roleId == null) {
            flash.error("没有选定角色");
        } else {
            SysUser user = SysUser.findById(userId);

            List<SysRole> roles = new ArrayList<SysRole>();

            for (Long rid : roleId) {
                SysRole sysRole = SysRole.findById(rid);
                roles.add(sysRole);
            }

            user.assignRoles(roles);

            StringBuilder buf = new StringBuilder();
            for (Long rid : roleId) {
                buf.append(rid).append(",");
            }
            log.info("User(" + userId + ") assigned roles(" + buf.toString() + ") okay");
        }

        show(1);
    }

    /**
     * Remove all roles of a user
     *
     * @param id user id
     */
    public static void cleanRoles(@Required Long id) {
        if (id == null) {
            flash.error("没有选定用户2");
        } else {
            SysUser user = SysUser.findById(id);
            if (user == null) {
                flash.error("用户不存在");
            } else if (user.roles == null || user.roles.isEmpty()) {
                flash.error("该用户没有被赋予角色");
            } else {
                user.assignRoles(null);
            }
        }
        show(1);
    }


    public static void forbid(Long id) {
        SysUser user = SysUser.findById(id);
        if (user != null) {
            user.changeStatus(2);
        }
        show(1);
    }

    public static void resume(Long id) {
        SysUser user = SysUser.findById(id);
        if (user != null) {
            user.changeStatus(1);
        }
        show(1);
    }


    //ajax invoking
    public static void detailForEdit(@Required Long id) {
        SysUser entity=null;
        String message=null;
        Organization orgSel=null;
        if (id == null) {
            message="没有选定用户";
        } else {
            entity = SysUser.findById(id);
            if (entity == null) {
                message="用户不存在";
            }else{
                orgSel=entity.organizations==null||entity.organizations.size()==0
                        ?null:entity.organizations.get(0);
            }
        }


        List<Organization> organizations = Organization.findAll();
        render(entity, message,organizations,orgSel);
    }

    
    public static void newPassword(Long id, String password){
        if (id == null) {
            flash.error("没有选定用户，不能修改密码");
        } else {
            SysUser user = SysUser.findById(id);

            if (user == null) {
                flash.error("用户不存在，不能修改密码");
            } else {
                user.password= Codec.hexMD5(password);
                user.lastUpdate=new Date();
                user.save();
                flash.success("用户（"+user.nickName+"）密码已经被修改为["+password+"]，此消息仅出现一次");
            }
        }
        show(1);
    }
    
    public static void edit(){
        SysUser sysUser=SysUser.findById(params.get("id",Long.class));
        if(params.get("password", String.class)!=null)
            sysUser.password=Codec.hexMD5(params.get("password", String.class));

        District district=null;
        List<Organization> organizations=null;
        List<SysRole> roles=null;
        
        Long organizationId=params.get("organizationId",Long.class);
        if(organizationId!=null){
            organizations = new ArrayList<Organization>();
            Organization organization=Organization.findById(organizationId);
            organizations.add(organization);
        }

        // 此处不修改loginTimes,lastLogin,password,roles
        sysUser.editSysUser(
                params.get("loginName", String.class), params.get("nickName", String.class),
                null,params.get("email", String.class),
                params.get("sex", Integer.class), district, params.get("userType", Integer.class),
                params.get("signature", String.class) , params.get("address", String.class) ,
                params.get("mobile", String.class) , params.get("phone", String.class) ,
                params.get("phone2", String.class) , params.get("status", Integer.class) ,
                params.get("remark", String.class) ,
                0, null, organizations, roles);

        sysUser.lastUpdate=new Date();
        sysUser.save();
        flash.success("用户（"+sysUser.nickName+"）信息修改成功");
        log.info("User("+sysUser.loginName+") saved okay");
        show(1);
    }


    public static void usersSel() {
        List<SysUser> entityList = SysUser.find("select o from SysUser o order by id desc").fetch();

        JsonArray ja=new JsonArray();
        for(SysUser m:entityList){
            JsonObject jsonObject=new JsonObject();
            jsonObject.addProperty("ID",""+m.id);
            jsonObject.addProperty("NAME",m.nickName);
            ja.add(jsonObject);
        }

        JsonObject modulesJson=new JsonObject();
        modulesJson.add("USERS",ja);
        String jsonString=modulesJson.toString();
        log.info(">> ja.toString() = "+jsonString);
        renderJSON(jsonString);
    }
}
