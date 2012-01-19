package models.fault;

import models.AbstractEntity;

import javax.persistence.*;

/**
 * Desc: 系统个功能表，对系统的菜单编号，用来关联角色
 *          功能可以看做一棵树型结构
 * --------
 * Author: <a href="mailto:ember319@gmail.com">zou bo</a>
 * Date: 12-1-8 下午11:26
 */
@Entity
@Table(name = "sys_function")
public class Function extends AbstractEntity {
    // 人为定义的有意义的唯一键
    @Column(nullable = false,length = 50,unique = true,name = "func_key")
    public String key;

    @Column(nullable = false,length = 50,unique = true)
    public String name;

    @Column(length = 250)
    public String remark;
    
    public Function parent;//父功能ID

    public int status;

}
