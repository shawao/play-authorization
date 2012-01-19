package models.fault;

import models.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Desc: 维修历史；每维修一次故障都做一次记录，包括没有修好
 *      todo:一次可能到一个站修理几个故障怎么办
 *      审核通过可以作为一张故障单
 * --------
 * Author: <a href="mailto:ember319@gmail.com">zou bo</a>
 * Date: 12-1-9 上午12:08
 */
@Entity
@Table(name = "t_servicing")
public class Servicing extends AbstractEntity {

    @Column(name="sta_id")
    public Long stationId;
    @Column(name = "fau_id")
    public Long faultId;

    // todo:维修可以多个人？！
    @Column(nullable = false)
    public Long userId;//登录人
    public Long userId2;
    public Long userId3;
    public Long userId4;

    @Column(name = "serv_start_time")
    public Date serviceStartTime;
    @Column(name = "serv_end_time")
    public Date serviceEndTime;

    @Column(name = "proc",length = 1000)
    public String process;//维修过程
    @Column(name = "result",length = 1000)
    public String result;//文字描述维修结果
    
    public Long reasonId;//关联常量表中的故障根源类型的某ID
    @Column(length = 1000)
    public String remark;//其他描述

    public int fixed;//1:yes, 2:no，是否修好
    public int audited;//0:待审核 1:通过, 2:未通过, 是否通过审核

}
