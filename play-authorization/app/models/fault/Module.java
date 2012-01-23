package models.fault;

import models.AbstractEntity;
import models.sys.SysUser;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Desc: 自动站的模块，此类的每一个实例对应一个真实的模块，比方说某厂商生产的某型号湿度传感器
 *      包含类型、型号，也可以叫做组件，传感器等
 *      模块类型存储在SysConstant中（需要先行构建此实体数据）
 * --------
 * Author: <a href="mailto:ember319@gmail.com">zou bo</a>
 * Date: 12-1-8 下午9:02
 */
@Entity
@Table(name="t_module")
public class Module extends AbstractEntity {
    // -------------> 数据目前如下：
    // 采集器型号	温湿传感器型号
    // 气压传感器型号	雨量传感器型号	风向传感器型号	风速传感器型号
    // 地温传感器型号	太阳能板型号	总辐射传感器型号	净辐射传感器型号
    // 直接辐射传感器型号	反射辐射传感器型号	散射辐射传感器型号	紫外辐射传感器型号
    // 日照传感器型号	地温变送器型号	超声波蒸发量传感器型号	通讯模块型号
    // 电池规格	能见度型号	加密站本地显示情况


    @Column(nullable = false,length = 50,unique = true)
    public String name;//模块名称

    //每一个模块，都在常量表中对应一组类型；这里的type类型对应的是常量表中的类型
    public Long moduleType;//模块类型，关联常量表constType

    public Long model;//型号，关联常量表constCode

    @ManyToOne
    public Vendor vendor;//可能暂时不知道厂商是哪里，允许后补

    @Column(length = 200)
    public String remark;
    
    public int status;//暂不使用，保留项

    @ManyToOne(optional = false)
    public SysUser submitter;// who submit it

   /*
    * 举例一条记录如下：
     * name:雨量传感器, moduleType:对应的常量表中的constType（对应一组类型）
    *
    */
}
