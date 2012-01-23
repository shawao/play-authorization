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
public class Vendors extends Application {

    public static void index() {
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

    public static void create(
            String name, String address, String phone, String phone2, String fax,
            String contactPerson, String contactPersonMobile,
            String contactPerson2, String contactPersonMobile2,
            String remark) {
        log.info("<< create Vendor: {name:" + name + ",...}");
        Vendor vendor = new Vendor(name, address, phone, phone2, fax,
                contactPerson, contactPersonMobile,
                contactPerson2, contactPersonMobile2, remark, connectedUser());
        vendor.save();
        show(1);
    }


    public static void delete(@Required Long id) {
        Vendor entity = Vendor.findById(id);

        if (entity != null) {
            try {
                entity.delete();
                flash.success("（" + entity.name + "）成功删除");
            } catch (Throwable cve) {
                flash.error("（" + entity.name + "）删除失败，该厂商已经被引用");
            }
        } else {
            flash.error("ID（" + id + "）厂商不存在");
        }
        show(1);
    }


    //ajax invoking
    public static void detailForEdit(@Required Long id) {
        Vendor entity = null;
        String message = null;
        if (id == null) {
            message = "没有选定厂商";
        } else {
            entity = Vendor.findById(id);
            log.info("==== Vendor.detailForEdit() > entity = " + entity);
            if (entity == null) {
                message = "厂商不存在";
            }
        }
        render(entity, message);
    }

    public static void edit(Long id,
                String name, String address, String phone, String phone2, String fax,
                String contactPerson, String contactPersonMobile,
                String contactPerson2, String contactPersonMobile2,
                String remark) {
        Vendor ven = Vendor.findById(id);
        ven.editVendor(name, address, phone, phone2, fax,
                    contactPerson, contactPersonMobile,
                    contactPerson2, contactPersonMobile2,
                    remark);
        ven.lastUpdate = new Date();
        ven.save();
        show(1);
    }


    public static void delete(@Required Long... id) {
        if (id == null || id.length == 0) {
            flash.error("没有选中商家");
        } else {
            for (Long entityId : id) {
                Vendor vendor = Vendor.findById(entityId);

                StringBuilder buf = new StringBuilder();
                if (vendor != null) {
                    //user.force2delete();
                    vendor.delete();
                    log.info(">> SysUser(" + entityId + ") deleted okay, and render page to index");
                } else {
                    buf.append("商家（" + entityId + "）不存在");
                    log.info(">> Vendor(" + entityId + ") doesn't exist");
                }
                String info = buf.toString().trim();
                if (!info.isEmpty())
                    flash.error(info);
            }
        }
        show(1);
    }
}
