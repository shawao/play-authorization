package controllers;

import models.sys.Organization;
import play.data.validation.Required;

import java.util.Date;
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


    public static void ajaxList() {
        Integer pageSequence = 1;
        List<Organization> entityList = Organization.find("select o from Organization o order by id desc").fetch();
        render(Organization.count(), entityList, pageSequence);
    }

    public static void create(@Required String name,
                              @Required String key,
                              String remark,
                              @Required Long parentId) {
        play.Logger.info("<< create organization: {name:" + name + ",...}");
        Organization parentEntity = null;
        if (parentId != null) {
            parentEntity = Organization.findById(parentId);
        }

        Organization organization = new Organization(name.trim(), key.trim(), remark == null ? null : remark.trim(), parentEntity);
        organization.save();
        show(1);
    }


    public static void delete(@Required Long id) {
        Organization entity = Organization.findById(id);

        if (entity != null) {
            try{
                entity.delete();
                flash.success("（" + entity.name + "）成功删除");
            }catch (Throwable cve){
                flash.error("（" + entity.name + "）删除失败，该组织已经被引用");
            }
        } else {
            flash.error("ID（" + id+ "）组织不存在");
        }
        show(1);
    }


    //ajax invoking
    public static void detailForEdit(@Required Long id) {
        Organization entity=null;
        String message=null;
        if (id == null) {
            message="没有选定组织";
        } else {
            entity = Organization.findById(id);
            log.info("==== Organizations.detailForEdit() > entity = "+entity);
            if (entity == null) {
                message="组织不存在";
            }
        }
        render(entity, message);
    }
    
    public static void edit(){
        Long id=params.get("id",Long.class);
        Long parentId=params.get("parentId",Long.class);
        
        Organization org=Organization.findById(id);

        Organization parent=null;
        if(parentId!=null)
            parent=Organization.findById(parentId);
        org.editOrganization(params.get("key"),params.get("name"),params.get("remark"),
                parent, 0, 0);
        org.lastUpdate=new Date();
        org.save();
        show(1);
    }
}
