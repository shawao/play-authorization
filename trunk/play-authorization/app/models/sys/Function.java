package models.sys;

import models.AbstractEntity;
import play.db.jpa.JPA;
import play.db.jpa.Model;

import javax.persistence.*;
import java.util.Date;

/**
 * Desc:
 * --------
 * SysUser: zou bo
 * Date: 12-1-18 上午9:25
 */
@Entity
@Table(name = "sys_function")
public class Function extends AbstractEntity {
    // marked like style: 001001001
    @Column(name = "func_key", length = 100, nullable = false, unique = true)
    public String key;

    @Column(nullable = false, length = 50)
    public String name;

    @Column(length = 200)
    public String remark;

    @ManyToOne
    public Function parent;


    public int status;//0：未被使用，1：正常使用，2：禁用


    public Function(String name, String key, String remark, Function parent) {
        this.key = key;
        this.name = name;
        this.remark = remark;
        this.parent = parent;
    }


    public void force2delete() {
        EntityManager entityManager = JPA.em();

        Query deleteQuery = entityManager.createNativeQuery("delete from rel_role_func where func_id=" + id);
        int delCount = deleteQuery.executeUpdate();

        play.Logger.info("delete from rel_role_func: " + delCount);

        entityManager.remove(this);
        entityManager.flush();
    }
}
