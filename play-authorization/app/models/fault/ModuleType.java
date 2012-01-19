package models.fault;

import models.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Desc: 自动站的模块类型（不是型号!!!），也可以叫做组件，传感器等
 * --------
 * Author: <a href="mailto:ember319@gmail.com">zou bo</a>
 * Date: 12-1-8 下午9:02
 */
@Entity
@Table(name="t_module_type")
public class ModuleType extends AbstractEntity {
    // -------------> 数据目前如下：
    // 采集器型号	温湿传感器型号
    // 气压传感器型号	雨量传感器型号	风向传感器型号	风速传感器型号
    // 地温传感器型号	太阳能板型号	总辐射传感器型号	净辐射传感器型号
    // 直接辐射传感器型号	反射辐射传感器型号	散射辐射传感器型号	紫外辐射传感器型号
    // 日照传感器型号	地温变送器型号	超声波蒸发量传感器型号	通讯模块型号
    // 电池规格	能见度型号	加密站本地显示情况


    @Column(nullable = false,length = 50,unique = true)
    public String name;//组件名称

    // String,Numeric
    @Column(nullable = false,length = 20)
    public String valueType;//值类型

    @Column(length = 200)
    public String remark;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
