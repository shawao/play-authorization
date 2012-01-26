package models.fault;

import models.AbstractEntity;
import models.sys.SysUser;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Desc: 自动站的模块
 *      ModuleType
 * --------
 * Author: <a href="mailto:ember319@gmail.com">zou bo</a>
 * Date: 12-1-8 下午9:02
 */
@Entity
@Table(name="t_module")
public class Module extends AbstractEntity {
    // -------------> 模块类型  数据目前如下：
    // 采集器型号	温湿传感器型号
    // 气压传感器型号	雨量传感器型号	风向传感器型号	风速传感器型号
    // 地温传感器型号	太阳能板型号	总辐射传感器型号	净辐射传感器型号
    // 直接辐射传感器型号	反射辐射传感器型号	散射辐射传感器型号	紫外辐射传感器型号
    // 日照传感器型号	地温变送器型号	超声波蒸发量传感器型号	通讯模块型号
    // 电池规格	能见度型号	加密站本地显示情况

    @Column(length = 50)
    public String name;//模块名称

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "rel_mod_type",
            joinColumns = @JoinColumn(name = "module_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "type_id", referencedColumnName = "id"))
    public List<ModuleType> types=new ArrayList<ModuleType>();

    @Column(length = 200)
    public String remark;
    
    public int status;//暂不使用，保留项

    @ManyToOne
    public SysUser submitter;// who submit it

   /*
    * 举例一条记录如下：
     * name:雨量传感器, moduleType:对应的常量表中的constType（对应一组类型）
    *
    */

    public Module(String name, String remark, SysUser submitter) {
        this.name = name;
        this.remark = remark;
        this.submitter = submitter;
    }


    public void add(ModuleType moduleType){
        types.add(moduleType);
    }
}
