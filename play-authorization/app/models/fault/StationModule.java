package models.fault;

import models.AbstractEntity;
import models.sys.SysUser;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Desc  :
 * -----
 * Author: <a href="mailto:ember319@gmail.com">ember</a>
 * Date  : 12-1-26
 * Time  : 下午12:17
 */
@Entity
@Table(name = "t_sta_module")
public class StationModule extends AbstractEntity {

    public Long stationId;

    @ManyToOne
    public Module module;

    @ManyToOne
    public ModuleType moduleType;

    @ManyToOne
    public Vendor vendor;//可能暂时不知道厂商是哪里，允许后补

    @Column(length = 200)
    public String remark;

    public int status;//暂不使用，保留项

    @ManyToOne
    public SysUser submitter;// who submit it
}
