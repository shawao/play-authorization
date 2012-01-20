package models.sys;

import models.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Desc: 地址位置表，以树的形式存储。省市区，或更小的级别地区
 * --------
 * Author: <a href="mailto:ember319@gmail.com">zou bo</a>
 * Date: 12-1-8 下午8:39
 */
@Entity
@Table(name = "t_district")
public class District extends AbstractEntity {

    @Column(nullable = false,unique = true,length = 50,name = "dis_key")
    public String key;//位置编码，形如“001001001”，每三位表示一级

    @Column(nullable = false,length = 50)
    public String name;//名称

    @Column(nullable = false,length = 100)
    public String fullName;//省市区全称


    public int status = 1;//0:未用,1:已应用,2:禁止


    public District parent;
}
