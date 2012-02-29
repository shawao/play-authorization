package controllers;

import models.fault.Module;
import models.fault.ModuleType;
import play.data.validation.Required;

import java.util.List;

/**
 * Desc:
 * --------
 * User: zou bo
 * Date: 12-1-20 下午4:08
 */
public class Modules extends Application {

    public static void index() {
        show(1);
    }


    public static void show(Integer page) {
        Integer pageSequence = page == null || page < 0 ? 1 : page;
        int from = (pageSequence - 1) * pageSize;

        List<Module> modules = Module.find("select o from Module o order by id desc")
                .from(from).fetch(pageSize);
        Long entityCount = Module.count();

        List<Module> allModules = Module.findAll();
        render(entityCount, modules, pageSequence,allModules);
    }


    public static void create(String name, String remark) {
        Module module = new Module(name, remark, connectedUser());
        module.save();
        flash.success("模块\"" + name + "\"保存成功");
        show(1);
    }

    public static void createType(Long moduleId, String name, String remark) {
        Module module = Module.findById(moduleId);
        if (module == null) {
            flash.error("模块不存在");
        } else {
            module.add(new ModuleType(name, remark));
            module.save();
            flash.success("模块型号\""+module.name+"--" + name + "\"保存成功");
        }

        show(1);
    }


    /**
     * 支持批量删除
     *
     * @param id 数组对象，支持批量删除
     */
    public static void delete(@Required Long... id) {
        if (id == null || id.length == 0) {
            flash.error("没有选中模块");
        } else {
            for (Long entityId : id) {
                Module module = Module.findById(entityId);

                StringBuilder buf = new StringBuilder();
                if (module != null) {
                    module.delete();
                    log.info(">> Module(" + entityId + ") deleted okay, and render page to index");
                } else {
                    buf.append("模块（" + entityId + "）不存在");
                    log.info(">> 模块(" + entityId + ") 不存在");
                }
                String info = buf.toString().trim();
                if (!info.isEmpty())
                    flash.error(info);
            }
        }
        show(1);
    }

    public static void deleteType(Long id){
        ModuleType moduleType=ModuleType.findById(id);
        if(moduleType==null){
            flash.error("模块型号不存在");
        }else{
            moduleType.force2delete();
            flash.success("删除成功");
        }
        show(1);
    }


    public static void typeList(Long moduleId) {
        String message=null;
        Module module=Module.findById(moduleId);
        if(module==null){
            message="模块不存在";
        }else{
            render(module);
        }
        render(message);
    }
}
