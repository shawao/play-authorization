package models.fault;

import models.AbstractEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Desc: 系统用户表，也就是气象系统中的真实用户信息
 * --------
 * Author: <a href="mailto:ember319@gmail.com">zou bo</a>
 * Date: 12-1-8 下午11:14
 */
@Entity
@Table(name = "sys_user")
public class SysUser extends AbstractEntity {
    @Column(nullable = false,length = 50)
    public String name;
    @Column(nullable = false,length = 50)
    public String loginName;//如果不输入，默认使用身份证名称
    @Column(nullable = false,length = 100)
    public String password;

    public int sex;//性别，0：未设定，1：男，2：女

    public int userType;//1：系统管理员，2：气象局普通用户，3：厂商用户

    public Long orgId;//所属机构
    
    @Column(length = 50,name = "sign")
    public String signature;

    public Long districtId;

    @Column(length = 250)
    public String address;

    @Column(length = 20)
    public String mobile;
    @Column(length = 20)
    public String tel;
    @Column(length = 20)
    public String tel2;

    @Column(length = 250)
    public String remark;

    public int loginTimes;//登录次数
    public Date lastLogin;

    public int status=1;//1：正常使用，2：禁用（禁止登录）

    @ManyToMany
    @JoinTable(name = "r_user_role",
            joinColumns =@JoinColumn(name="user_id", referencedColumnName="id"),
            inverseJoinColumns =@JoinColumn(name = "role_id",referencedColumnName = "id") )
    public List<SysRole> roles;
}
