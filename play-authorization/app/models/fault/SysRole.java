package models.fault;

import models.AbstractEntity;

import javax.persistence.*;
import java.util.List;

/**
 * Desc: 系统角色表，关联角色和功能
 * --------
 * Author: <a href="mailto:ember319@gmail.com">zou bo</a>
 * Date: 12-1-8 下午11:22
 */
@Entity
@Table(name = "sys_role")
public class SysRole extends AbstractEntity {
    @Column(nullable = false, length = 50, unique = true)
    public String name;
    @Column(length = 250)
    public String remark;

    public int status = 0;//0:未用,1:已应用,2:禁止

    @ManyToMany
    @JoinTable(name = "r_user_role",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "func_id", referencedColumnName = "id"))
    public List<Function> functions;
}
