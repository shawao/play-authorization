package controllers;

import models.fault.Vendor;
import play.data.validation.Required;

import java.util.Date;
import java.util.List;

/**
 * Desc:
 * --------
 * User: zou bo
 * Date: 12-1-20 下午3:58
 */
public class Vendors extends Application{
    
    public static void index(){
        show(1);
    }

    public static void show(Integer page) {
            Integer pageSequence = page == null || page < 0 ? 1 : page;
            int from = (pageSequence - 1) * pageSize;
            List<Vendor> entityList = Vendor.find("select o from Vendor o order by name asc")
                    .from(from).fetch(pageSize);
            Long entityCount = Vendor.count();
            render(entityCount, entityList, pageSequence);
        }


        public static void ajaxList() {
            Integer pageSequence = 1;
            List<Vendor> entityList = Vendor.find("select o from Vendor o order by name asc").fetch();
            render(Vendor.count(), entityList, pageSequence);
        }

        public static void create(@Required String name,
                                  @Required String key,
                                  String remark,
                                  @Required Long parentId) {
            play.Logger.info("<< create organization: {name:" + name + ",...}");
            Vendor parentEntity = null;
            if (parentId != null) {
                parentEntity = Vendor.findById(parentId);
            }

//            Vendor organization = new Vendor(name.trim(), key.trim(), remark == null ? null : remark.trim(), parentEntity);
//            organization.save();
            show(1);
        }


        public static void delete(@Required Long id) {
            Vendor entity = Vendor.findById(id);

            if (entity != null) {
                try{
                    entity.delete();
                    flash.success("（" + entity.name + "）成功删除");
                }catch (Throwable cve){
                    flash.error("（" + entity.name + "）删除失败，该厂商已经被引用");
                }
            } else {
                flash.error("ID（" + id+ "）厂商不存在");
            }
            show(1);
        }


        //ajax invoking
        public static void detailForEdit(@Required Long id) {
            Vendor entity=null;
            String message=null;
            if (id == null) {
                message="没有选定组织";
            } else {
                entity = Vendor.findById(id);
                log.info("==== Vendor.detailForEdit() > entity = "+entity);
                if (entity == null) {
                    message="组织不存在";
                }
            }
            render(entity, message);
        }

        public static void edit(){
            Long id=params.get("id",Long.class);

            Vendor org=Vendor.findById(id);

//            org.editOrganization(params.get("key"),params.get("name"),params.get("remark"),
//                    parent, 0, 0);
            org.lastUpdate=new Date();
            org.save();
            show(1);
        }
}
