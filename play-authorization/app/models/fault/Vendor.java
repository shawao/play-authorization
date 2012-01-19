package models.fault;

import models.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Desc: 厂家
 * --------
 * Author: <a href="mailto:ember319@gmail.com">zou bo</a>
 * Date: 12-1-9 下午10:55
 */
@Entity
@Table(name = "t_vender")
public class Vendor extends AbstractEntity {

    @Column(nullable = false,length = 100)
    public String name;
    @Column(length = 250)
    public String address;

    @Column(length = 20)
    public String tel;
    @Column(length = 20)
    public String tel2;
    @Column(length = 20)
    public String fax;//传真

    //todo: 是否也需要作为我们系统的用户，并登录？
    @Column(length = 20,name = "cont_pers")
    public String contactPerson;
    @Column(length = 20,name = "cont_pers_mob")
    public String contactPersonMobile;

    @Column(length = 20,name = "cont_pers2")
    public String contactPerson2;
    @Column(length = 20,name = "cont_pers_mob2")
    public String contactPersonMobile2;

    @Column(length = 250)
    public String remark;

    public int status=1;//0：已不存在，1：正常

}
