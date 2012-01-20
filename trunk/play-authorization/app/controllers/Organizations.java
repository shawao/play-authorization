package controllers;

import models.sys.Organization;
import play.data.validation.Required;

import java.util.List;

/**
 * Desc:
 * --------
 * SysUser: zou bo
 * Date: 12-1-18 上午10:22
 */
public class Organizations extends Application {

    public static void index() {
        render();
    }


    public static void show(Integer page) {
        Integer pageSequence = page == null || page < 0 ? 1 : page;
        int from = (pageSequence - 1) * pageSize;
        List<Organization> entityList = Organization.find("select o from Organization o order by id desc")
                .from(from).fetch(pageSize);
        Long entityCount = Organization.count();
        render(entityCount, entityList, pageSequence);
    }


    public static void create(@Required String name,
                              @Required String key,
                              String remark,
                              @Required Long parentId) {
        play.Logger.info("<< create function: {name:" + name + ",...}");
        Organization parentEntity = null;
        if(parentId!=null){
            parentEntity=Organization.findById(parentId);
        }

        Organization organization = new Organization(name.trim(), key.trim(), remark == null ? null : remark.trim(), parentEntity);
        organization.save();
        show(1);
    }


    public static void delete(@Required Long id) {
        Organization entity = Organization.findById(id);

        if (entity != null) {
            entity.delete();
            log.info(">> Organization(" + id + ") deleted okay, and render page to index");
        } else {
            log.info(">> Organization(" + id + ") doesn't exist");
        }
        show(1);
    }
}
