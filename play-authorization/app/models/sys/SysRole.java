package models.sys;

import models.AbstractEntity;
import play.db.jpa.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Desc:
 * --------
 * SysUser: zou bo
 * Date: 12-1-18 上午9:25
 */
@Entity
@Table(name = "sys_role")
public class SysRole extends AbstractEntity {
    // marked like style: 001001001
    @Column(name="role_key",length = 100,nullable = false,unique = true)
    public String key;

    @Column(nullable = false,length = 50)
    public String name;

    @Column(length = 200)
    public String remark;


    public int status = 0;//0:未用,1:已应用,2:禁止


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "rel_role_func",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "func_id", referencedColumnName = "id"))
    public List<Function> functions=new ArrayList<Function>();

    public SysRole(String name,String key,String remark) {
        this.name = name;
        this.key = key;
        this.remark = remark;
    }
}
