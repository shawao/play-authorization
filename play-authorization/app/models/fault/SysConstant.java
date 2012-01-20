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

    @Column(nullable =false)
    public Long constCode;//常量CODE
    @Column(length = 50)
    public String constValue;//常量值
    @Column(length = 50)
    public String constRemark;//常量备注，可以保存名称

    @Column(length = 200)
    public String remark;//备注
    
    public int status;//0:未用,1:已应用,2:禁止


    public SysConstant(Long constType, Long constCode, String constValue, String constRemark, String remark) {
        this.constType = constType;
        this.constCode = constCode;
        this.constValue = constValue;
        this.constRemark = constRemark;
        this.remark = remark;
    }
}
