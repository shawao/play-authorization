package controllers;

import models.fault.Fault;
import play.data.validation.Required;

import java.util.List;

/**
 * Desc  :
 * -----
 * Author: <a href="mailto:ember319@gmail.com">ember</a>
 * Date  : 12-2-19
 * Time  : 上午5:11
 */
public class Faults extends Application{

    public static void index(){
        show(1);
    }

    public static void show(Integer page){
        Integer pageSequence = page == null || page < 0 ? 1 : page;
        int from = (pageSequence - 1) * pageSize;

        List<Fault> faults = Fault.find("select o from Fault o order by id desc")
                .from(from).fetch(pageSize);
        Long entityCount = Fault.count();

        render(entityCount, faults, pageSequence);
    }


    public static void delete(@Required Long id) {
        Fault entity = Fault.findById(id);

        if (entity != null) {
            try {
                entity.delete();
                flash.success("成功删除");
            } catch (Throwable cve) {
                flash.error("删除失败，该自动站已经被使用");
            }
        } else {
            flash.error("ID（" + id + "）自动站不存在");
        }
        show(1);
    }
}
