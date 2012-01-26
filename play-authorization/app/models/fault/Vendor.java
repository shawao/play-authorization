package models.fault;

import models.AbstractEntity;
import models.sys.SysUser;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

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
    public String phone;
    @Column(length = 20)
    public String phone2;
    @Column(length = 20)
    public String fax;//传真

    @Column(length = 20,name = "cont_per")
    public String contactPerson;
    @Column(length = 20,name = "cont_per_mob")
    public String contactPersonMobile;

    @Column(length = 20,name = "cont_per2")
    public String contactPerson2;
    @Column(length = 20,name = "cont_per_mob2")
    public String contactPersonMobile2;

    @Column(length = 250)
    public String remark;

    public int status=1;//1：正常，2：已不存在

    @ManyToOne
    public SysUser submitter;// who submit it


    public Vendor(
            String name, String address, String phone, String phone2, String fax,
            String contactPerson, String contactPersonMobile,
            String contactPerson2, String contactPersonMobile2,
            String remark, SysUser submitter) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.phone2 = phone2;
        this.fax = fax;
        this.contactPerson = contactPerson;
        this.contactPersonMobile = contactPersonMobile;
        this.contactPerson2 = contactPerson2;
        this.contactPersonMobile2 = contactPersonMobile2;
        this.remark = remark;
        this.submitter = submitter;
    }

    public void editVendor(
            String name, String address, String phone, String phone2, String fax,
            String contactPerson, String contactPersonMobile,
            String contactPerson2, String contactPersonMobile2,
            String remark) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.phone2 = phone2;
        this.fax = fax;
        this.contactPerson = contactPerson;
        this.contactPersonMobile = contactPersonMobile;
        this.contactPerson2 = contactPerson2;
        this.contactPersonMobile2 = contactPersonMobile2;
        this.remark = remark;
        this.lastUpdate = new Date();
    }
}
