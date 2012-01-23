package controllers;

import models.sys.Function;
import models.sys.SysRole;
import play.data.validation.Required;

import java.util.List;

/**
 * Desc:
 * --------
 * SysUser: zou bo
 * Date: 12-1-18 上午11:00
 */
public class Functions extends Application {

    public static void index() {
        render();
    }

    public static void show(Integer page) {
        Integer pageSequence = page == null || page < 0 ? 1 : page;
        int from = (pageSequence - 1) * pageSize;

        log.info("##"+Function.class.getSimpleName());
        
        List<Function> entityList = Function.find("select o from Function o order by key asc")
                .from(from).fetch(pageSize);
        Long entityCount = Function.count();

        render(entityCount, entityList, pageSequence);
    }

    // return part of html string，单选列表
    public static void ajaxList() {
        List<Function> entityList = Function.find("select o from Function o order by key asc").fetch();
        Long entityCount = Function.count();
        render(entityCount, entityList);
    }

    // return part of html string，复选列表
    public static void ajaxListByRoleId(Long roleId) {
        List<Function> entityList = Function.find("select o from Function o order by key asc").fetch();
        Long entityCount = Function.count();

        SysRole role = null;
        if (roleId != null) {
            role = SysRole.findById(roleId);
            log.info("got role(" + roleId + ")");
        }
        render(entityCount, entityList, role);
    }


    public static void create(@Required String name,
                              @Required String key,
                              String remark,
                              @Required Long parentId) {
        play.Logger.info("<< create function: {name:" + name + ",...}");
        Function parentFunc = null;
        if(parentId!=null){
            parentFunc=Function.findById(parentId);
        }

        Function function = new Function(name.trim(), key.trim(), remark == null ? null : remark.trim(), parentFunc);
        function.save();
        show(1);
    }


    public static void delete(@Required Long id) {
        Function function = Function.findById(id);

        if (function != null) {
            function.force2delete();
            play.Logger.info(">> Function(" + id + ") deleted okay, and render page to index");
        } else {
            play.Logger.info(">> Function(" + id + ") doesn't exist");
        }
        show(1);
    }
}
