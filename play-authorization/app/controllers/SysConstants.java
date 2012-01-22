package controllers;

import models.fault.SysConstant;
import models.sys.Organization;
import play.data.validation.Required;

import java.util.Date;
import java.util.List;

/**
 * Desc:
 * --------
 * User: zou bo
 * Date: 12-1-20 下午4:19
 */
public class SysConstants extends Application {

    public static void index() {
        show(1);
    }

    public static void show(Integer page) {
        Integer pageSequence = page == null || page < 0 ? 1 : page;
        int from = (pageSequence - 1) * pageSize;
        List<SysConstant> entityList = Organization.find("select o from SysConstant o order by id desc")
                .from(from).fetch(pageSize);
        Long entityCount = SysConstant.count();
        render(entityCount, entityList, pageSequence);
    }


    public static void ajaxList() {
        Integer pageSequence = 1;
        List<SysConstant> entityList = SysConstant.find("select o from SysConstant o order by id desc").fetch();
        render(SysConstant.count(), entityList, pageSequence);
    }

    public static void create(Long constType, Long constCode, String constValue, String constRemark, String remark) {
        play.Logger.info("<< create SysConstant: {remark:" + remark + ",...}");
//        SysConstant parentEntity = null;
//        if (parentId != null) {
//            parentEntity = SysConstant.findById(parentId);
//        }

//        SysConstant organization = new SysConstant(name.trim(), key.trim(), remark == null ? null : remark.trim(), parentEntity);
//        SysConstant.save();
        show(1);
    }


    public static void delete(@Required Long id) {
        SysConstant entity = SysConstant.findById(id);

        if (entity != null) {
            entity.delete();
            log.info(">> SysConstant(" + id + ") deleted okay, and render page to index");
        } else {
            log.info(">> SysConstant(" + id + ") doesn't exist");
        }
        show(1);
    }


    //ajax invoking
    public static void detailForEdit(@Required Long id) {
        SysConstant entity = null;
        String message = null;
        if (id == null) {
            message = "没有选定组织";
        } else {
            entity = SysConstant.findById(id);
            if (entity == null) {
                message = "组织不存在";
            }
        }
        render(entity, message);
    }

    public static void edit() {
        Long id = params.get("id", Long.class);

        SysConstant cont = SysConstant.findById(id);
        cont.editSysConstant(params.get("constType",Long.class), params.get("constCode",Long.class),
                params.get("constValue"), params.get("constRemark"), params.get("remark") ,params.get("status",Integer.class));
        cont.lastUpdate = new Date();
        cont.save();
        show(1);
    }
}
