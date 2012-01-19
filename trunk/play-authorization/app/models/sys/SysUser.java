package models.sys;

import play.data.validation.Required;
import play.db.jpa.Model;
import play.libs.Codec;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Desc:
 * --------
 * SysUser: zou bo
 * Date: 12-1-18 上午9:25
 */
@Entity
@Table(name = "sys_user")
public class SysUser extends Model {
    @Required
    @Column(nullable = false,length = 50,unique = true)
    public String loginName;

    // if empty input, using loginName instead (set nickName by loginName)
    @Column(length = 50)
    public String nickName;

    @Required
    @Column(nullable = false,length = 50)
    public String password;

    @ManyToMany
    @JoinTable(name = "rel_user_org",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "org_id", referencedColumnName = "id"))
    public List<Organization> organizations;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "rel_user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    public List<SysRole> roles;

    public Date createDate=new Date();
    public Date lastUpdate;



    public SysUser(String loginName, String nickName, String password) {
        this.loginName = loginName;
        this.nickName = nickName;
        this.password = Codec.hexMD5(password);
        create();
    }


    //todo: reduplicate roles should be filtered here
    public void assignRoles(SysRole ...rolesArray ){
        if(roles==null){
            roles=new ArrayList<SysRole>();
            Collections.addAll(roles, rolesArray);
        }else{
            Collections.addAll(roles, rolesArray);
        }
    }
}
