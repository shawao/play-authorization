package models.fault;

import models.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Desc: 保存一个自动站都有哪些模块
 * --------
 * Author: <a href="mailto:ember319@gmail.com">zou bo</a>
 * Date: 12-1-8 下午10:38
 */
@Entity
@Table(name="t_sta_mod")
public class StationModule extends AbstractEntity {
    @Column(name="mod_type_id")
    public Long moduleTypeId;
    @Column(name="mod_type_name",length = 50)
    public String moduleTypeName;//冗余存储，模块类型名，即ModuleType.name

    @Column(nullable = false,length = 20,name="mod_val_type")
    public String moduleValueType;//值类型

    @Column(name="mod_type_value",length = 50)
    public String moduleTypeValue;//模块类型确定后，还需要保存类型；比如风向传感器型号的值

    @Column(name="sta_id")
    public Long stationId;

}
