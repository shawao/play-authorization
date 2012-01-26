package models.fault;

import models.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Table;

/**
 * Desc  : 模块型号
 * -----
 * Author: <a href="mailto:ember319@gmail.com">ember</a>
 * Date  : 12-1-26
 * Time  : 下午12:15
 */
@Entity
@Table(name = "t_mod_type")
public class ModuleType extends AbstractEntity {

    @Column(length = 50)
    public String name;

    @Column(length = 200)
    public String remark;


    public int status = 1;//1：正常使用，2：禁用（禁止登录）

    public ModuleType(String name, String remark) {
        this.name = name;
        this.remark = remark;
    }
    
    public void force2delete(){
        EntityManager em=ModuleType.em();
        em.createNativeQuery("delete from rel_mod_type where type_id="+id).executeUpdate();
        em.flush();

        delete();
    }
}
