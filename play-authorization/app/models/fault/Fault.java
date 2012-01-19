package models.fault;

import models.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
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
    @Column(nullable = false,name="sta_id")
    public Long stationId;

    // todo:考虑到可能同时有几件设备发生故障，这种情况开机张故障单？
    @Column(name="fau_mod_type")
    public Long faultModuleType;//故障模块类型
    @Column(name="fau_mod_type_name")
    public Long faultModuleTypeName;//故障模块类型名称(冗余)
    @Column(name="fau_mod_type_value")
    public Long faultModuleTypeValue;//故障模块类型--具体型号

    public int fixed;//0:待维修, 1:修好, 2:未修好

    @Column(name = "beha",length = 1000,nullable = false)
    public String behavior;//故障现象
    @Column(length = 250)
    public String remark;//备注


    //public String status;//1：可用，2：标记删除

}
