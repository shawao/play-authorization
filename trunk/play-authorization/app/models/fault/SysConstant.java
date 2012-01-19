package models.fault;

import models.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Desc: 系统常量实体；以constType区分，保存各种常量
 *      包括：自动站传输方式，供电方式，自动站型号，地形特征，地面，站点级别，故障产生根源（产品质量、维护管理、不可抗拒、其他）
 * --------
 * Author: <a href="mailto:ember319@gmail.com">zou bo</a>
 * Date: 12-1-9 下午10:32
 */
@Entity
@Table(name="t_constant")
public class SysConstant extends AbstractEntity {

    public Long constType;//常量类型

    public Long constId;//常量ID
    @Column(length = 50)
    public String constValue;//常量值
    @Column(length = 50)
    public String constRemark;//常量备注

    @Column(length = 100)
    public String remark;//备注

}
