package models.sys;

import play.db.jpa.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

/**
 * Desc:
 * --------
 * SysUser: zou bo
 * Date: 12-1-18 上午9:25
 */
@Entity
@Table(name = "sys_function")
public class Function extends Model {
    // marked like style: 001001001
    @Column(name="func_key",length = 100,nullable = false,unique = true)
    public String key;

    @Column(nullable = false,length = 50)
    public String name;

    @Column(length = 200)
    public String remark;

    @ManyToOne
    public Function parent;

    public Date createDate=new Date();
    public Date lastUpdate;

    public Function(String key, String name, String remark, Function parent) {
        this.key = key;
        this.name = name;
        this.remark = remark;
        this.parent = parent;
    }
}
