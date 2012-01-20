package controllers;

import models.sys.Function;
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
        
        List<Function> entityList = Function.find("select o from Function o order by id desc")
                .from(from).fetch(pageSize);
        Long entityCount = Function.count();

        render(entityCount, entityList, pageSequence);
    }

    // return part of html string
    public static void ajaxList(Integer page) {
        Integer pageSequence = page == null || page < 0 ? 1 : page;
        int from = (pageSequence - 1) * pageSize;
        List<Function> entityList = Function.all().from(from).fetch(pageSize);
        Long entityCount = Function.count();
        render(entityCount, entityList, pageSequence);
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
            play.Logger.info(">> SysUser(" + id + ") deleted okay, and render page to index");
        } else {
            play.Logger.info(">> SysUser(" + id + ") doesn't exist");
        }
        show(1);
    }
}
