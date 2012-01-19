package models.fault;

import models.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Desc: 关联表；维修和故障可能是多对多
 *      即：一个故障可以维修多次；也可以一次维修多个故障
 * --------
 * Author: <a href="mailto:ember319@gmail.com">zou bo</a>
 * Date: 12-1-9 上午12:35
 */
@Entity
@Table(name="t_rel_fault_serv")
public class RelFaultServ extends AbstractEntity {

    public Long servId;
    public Long faultId;
}
