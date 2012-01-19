package models.sys;

import play.db.jpa.Model;

import javax.persistence.*;
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
public class SysRole extends Model {
    // marked like style: 001001001
    @Column(name="role_key",length = 100,nullable = false,unique = true)
    public String key;

    @Column(nullable = false,length = 50)
    public String name;

    @Column(length = 200)
    public String remark;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "rel_role_func",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "func_id", referencedColumnName = "id"))
    public List<Function> functions;

    public Date createDate=new Date();
    public Date lastUpdate;


    public SysRole(String name,String key,String remark) {
        this.name = name;
        this.key = key;
        this.remark = remark;
    }
}
