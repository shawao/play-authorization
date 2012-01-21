package controllers;

import models.sys.SysRole;
import models.sys.SysUser;
import play.data.validation.Equals;
import play.data.validation.MinSize;
import play.data.validation.Required;

import java.util.ArrayList;
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

        List<SysRole> roleList = SysRole.findAll();

        // rend them to page
        render(userCount, userList, pageSequence, roleList);
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
        SysUser user = new SysUser(loginName.trim(),
                nickName == null ? loginName.trim() : nickName.trim(),
                password,
                mobile == null ? null : mobile.trim());
        user.save();
        show(1);
    }


    //todo: check whether login user is admin
    public static void delete(@Required Long id) {
        SysUser user = SysUser.findById(id);

        if (user != null) {
            user.force2delete();
            play.Logger.info(">> SysUser(" + id + ") deleted okay, and render page to index");
        } else {
            play.Logger.info(">> SysUser(" + id + ") doesn't exist");
        }
        show(1);
    }


    public static void assignRoles(@Required Long userId, Long[] roleId) {
        if (userId == null) {
            flash.error("没有选定用户");
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
}
