package models.sys;

import models.AbstractEntity;
import play.data.validation.Email;
import play.data.validation.Required;
import play.db.jpa.JPA;
import play.db.jpa.Model;
import play.libs.Codec;

import javax.persistence.*;
import java.util.*;

/**
 * Desc:
 * --------
 * SysUser: zou bo
 * Date: 12-1-18 上午9:25
 */
@Entity
@Table(name = "sys_user")
public class SysUser extends AbstractEntity {
    @Required
    @Column(nullable = false,length = 50,unique = true)
    public String loginName;

    // if empty input, using loginName instead (set nickName by loginName)
    @Column(length = 50)
    public String nickName;

    @Required
    @Column(nullable = false,length = 50)
    public String password;

    @Email
    @Column(length = 80)
    public String email;


    public int sex;//性别，0：未设定，1：男，2：女
    @ManyToOne
    public District district;


    public int userType;//0：未设定，1：系统管理员，2：气象局普通用户，3：厂商用户

    @Column(length = 100)
    public String signature;

    @Column(length = 250)
    public String address;

    @Column(nullable = false,length = 20)
    public String mobile;
    @Column(length = 20)
    public String phone;
    @Column(length = 20)
    public String phone2;


    public int status=1;//1：正常使用，2：禁用（禁止登录）

    @Column(length = 250)
    public String remark;

    public int loginTimes;//登录次数
    public Date lastLogin;


    @ManyToMany
    @JoinTable(name = "rel_user_org",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "org_id", referencedColumnName = "id"))
    public List<Organization> organizations;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "rel_user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    public List<SysRole> roles=new ArrayList<SysRole>();


    public SysUser(String loginName, String nickName, String password) {
        this.loginName = loginName;
        this.nickName = nickName;
        this.password = Codec.hexMD5(password);
//        create();
    }


    //todo: reduplicate roles should be filtered here
    public void assignRoles(SysRole ...rolesArray ){
        if(roles==null){
            roles=new ArrayList<SysRole>();
        }
        Collections.addAll(roles, rolesArray);

        this.save();

        // save in batch
        EntityManager entityManager=JPA.em();

        for(SysRole role:roles){
            for(Function function:role.functions){
                entityManager.persist(new UserFunction(id,function.id));
            }
        }
        entityManager.flush();
    }


    public void assignRoles(List<SysRole> rolesList ){
        roles=rolesList;
        this.save();

        // save in batch
        EntityManager entityManager=JPA.em();
        for(SysRole role:roles){
            for(Function function:role.functions){
                entityManager.persist(new UserFunction(id,function.id));
            }
        }
        entityManager.flush();
    }
}
