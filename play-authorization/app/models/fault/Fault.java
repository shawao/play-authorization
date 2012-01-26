package models.fault;

import models.AbstractEntity;
import models.sys.SysUser;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Desc: 故障信息表(故障单)；
 *      包括故障表现（特征），故障解决方案，维修次数等
 *      注意：一个自动站同时有多个模块发生故障，需要分别填写故障单
 * --------
 * Author: <a href="mailto:ember319@gmail.com">zou bo</a>
 * Date: 12-1-9 上午12:09
 */
@Entity
@Table(name = "t_fault")
public class Fault extends AbstractEntity {
    @ManyToOne(optional = false)
    public AutoStation station;

    @ManyToOne(optional = false)
    public Module module;//故障模块(with type and model)

    //
    @ManyToOne(optional = false)
    public Module module2;//故障模块(with type and model)

    @ManyToOne(optional = false)
    public Module module3;//故障模块(with type and model)


    public int fixed;//0:待维修, 1:修好, 2:未修好

    @Column(nullable = false,length = 1000)
    public String behavior;//故障现象


    @Column(length = 500)
    public String remark;//备注


    public String status;// standby column

    @ManyToOne
    public SysUser submitter;// who submit it
}
