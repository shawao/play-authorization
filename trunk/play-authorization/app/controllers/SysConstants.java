package controllers;

import models.fault.SysConstant;
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
        List<SysConstant> constTypeList = SysConstant.allConstType();
        Long entityCount = SysConstant.count();
        render(entityCount, constTypeList, pageSequence);
    }


    public static void ajaxList() {
        Integer pageSequence = 1;
        List<SysConstant> entityList = SysConstant.find("select o from SysConstant o order by id desc").fetch();
        render(SysConstant.count(), entityList, pageSequence);
    }


    public static void constList(Long typeConstCode,String typeName) {
        String message=null;
        if (typeConstCode == null) {
            message="常量类型ID为空";
        } else if (typeConstCode < 1) {
            message="常量类型ID必须大于0";
        } else {
            Integer pageSequence = 1;
            List<SysConstant> entityList = SysConstant.find(
                    "select o from SysConstant o where o.constType=? order by o.constCode asc",
                    typeConstCode).fetch();
//            Long entityCount=SysConstant.count("select count(o) from SysConstant o where o.constType=?", typeConstCode);

            render(entityList, pageSequence,typeName);
        }
        render(message);
    }


    public static void createType(Long typeConstCode, String typeConstRemark){
        if (typeConstCode == null) {
            flash.error("常量类型ID为空");
        } else if (typeConstCode == 0) {
            flash.error("常量类型ID必须大于0");
        } else if (!SysConstant.validateTypeConstCode(typeConstCode)) {
            flash.error("常量类型ID被占用");
        }else if(typeConstRemark==null || typeConstRemark.trim().equals("")){
            flash.error("常量类型名称为空");
        }else{
            SysConstant typeConstant=SysConstant.createConstType(typeConstCode, typeConstRemark, connectedUser());
            typeConstant.save();
//            flash.success("常量类型（" + typeConstRemark + "，" + typeConstCode + "）创建成功");
            log.info("常量类型（" + typeConstRemark + "，" + typeConstCode + "）创建成功");
        }
        show(1);
    }


    /**
     * 获取某常量类别中，下一个常量CODE值（当前最大值+1）
     * @param constType 常量类别（不能等于0）
     */
    public static void nextConstCode(Long constType){
        Object nextCodeObj=SysConstant.em().createNativeQuery(
                "select max(o.constCode)+1 from t_constant o where o.constType="+constType)
                .getSingleResult();
        int nextCode=Integer.parseInt(nextCodeObj+"");
        log.info("{\"constType\":"+constType+",\"nextCode\':"+nextCode+"}");
        renderJSON(""+nextCode);
    }


    public static void create(Long constType, Long constCode, String constValue, String constRemark) {
        log.info("<< create SysConstant: {constRemark:" + constRemark + ",...}");
        if (!SysConstant.validateConstCode(constType, constCode)) {
            flash.error("常量ID被占用");
        }else {
            SysConstant constant=new SysConstant(constType,constCode,constValue,constRemark,null,connectedUser());
            constant.save();
            flash.success("常量（"+constCode+":"+constValue+"）保存成功");
        }
        show(1);
    }


    public static void delete(@Required Long id) {
        SysConstant entity = SysConstant.findById(id);

        if (entity != null) {
            entity.delete();
            String name=entity.constType==0?entity.constRemark:entity.constValue;
            String pre=entity.constType==0?"类型":"常量";
            flash.success(pre+"（"+name+"）删除成功");
        } else {
            flash.success("常量/类型（"+id+"）不存在");
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
