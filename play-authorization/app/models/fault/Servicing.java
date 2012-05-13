package models.fault;

import models.AbstractEntity;
import models.sys.SysUser;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

/**
 * Desc: 维修历史；每维修一次故障都做一次记录，包括没有修好
 *      审核通过可以作为一张故障单
 * --------
 * Author: <a href="mailto:ember319@gmail.com">zou bo</a>
 * Date: 12-1-9 上午12:08
 */
@Entity
@Table(name = "t_servicing")
public class Servicing extends AbstractEntity {

    @ManyToOne(optional = false)
    public AutoStation station;

    @ManyToOne(optional = false)
    public Fault fault;

    // todo:维修可以多个人？！
    @ManyToOne(optional = false)
    public SysUser user;//登录人, major

    @ManyToOne
    public SysUser user2;

    @ManyToOne
    public SysUser user3;

    @Column
    public Date startTime;
    
    @Column
    public Date endTime;

    @Column(name = "proc",length = 1000)
    public String process;//维修过程
    
    @Column(name = "result",length = 1000)
    public String result;//文字描述维修结果

    public Long reason;//关联常量表中的故障根源类型的某ID

    @Column(length = 1000)
    public String remark;//其他描述

    public int fixed;//1:yes, 2:no，是否修好



    public int audited;//0:待审核 1:通过, 2:未通过, 是否通过审核

    @ManyToOne
    public SysUser auditor;

    @Column(length = 500)
    public String auditComment;


    @ManyToOne
    public SysUser submitter;// who submit it


    public Servicing(AutoStation station, Fault fault, SysUser user, SysUser user2, SysUser user3,
                     Date startTime, Date endTime, String process, String result, Long reason,
                     String remark, int fixed, int audited, SysUser auditor, String auditComment,
                     SysUser submitter) {
        this.station = station;
        this.fault = fault;
        this.user = user;
        this.user2 = user2;
        this.user3 = user3;
        this.startTime = startTime;
        this.endTime = endTime;
        this.process = process;
        this.result = result;
        this.reason = reason;
        this.remark = remark;
        this.fixed = fixed;
        this.audited = audited;
        this.auditor = auditor;
        this.auditComment = auditComment;
        this.submitter = submitter;
    }


    public Servicing(AutoStation station, Fault fault, SysUser user, SysUser user2, SysUser user3,
                     Date startTime, Date endTime, String process, String result, Long reason,
                     String remark, int fixed,SysUser submitter) {
        this.station = station;
        this.fault = fault;
        this.user = user;
        this.user2 = user2;
        this.user3 = user3;
        this.startTime = startTime;
        this.endTime = endTime;
        this.process = process;
        this.result = result;
        this.reason = reason;
        this.remark = remark;
        this.fixed = fixed;
        this.submitter = submitter;
    }
}
