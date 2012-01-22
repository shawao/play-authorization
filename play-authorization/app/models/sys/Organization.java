package models.sys;

import models.AbstractEntity;
import play.db.jpa.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

/**
 * Desc:
 * --------
 * SysUser: zou bo
 * Date: 12-1-18 上午9:26
 */
@Entity
@Table(name = "sys_organization")
public class Organization extends AbstractEntity {

    // marked like style: 001001001
    @Column(name = "org_key", length = 100, nullable = false, unique = true)
    public String key;

    @Column(nullable = false, length = 50)
    public String name;

    @Column(length = 200)
    public String remark;

    @ManyToOne
    public Organization parent;


    //所在级别，依据是否有父亲，及选中的父亲来自动计算级别
    //1：第一级，2：第二级，3依次类推
    public int grade = 1;

    //所在级中序号，依据数据库中已有数据自动计算
    //1：第一，2：第二，3依次类
    public int seqInGrade = 1;


    public Organization(String name, String key, String remark, Organization parent) {
        this.name = name;
        this.key = key;
        this.remark = remark;
        this.parent = parent;

        //todo: calculate "grade" and "seqInGrade"
    }


    public void  editOrganization(String key, String name, String remark, Organization parent, int grade, int seqInGrade) {
        this.key = key;
        this.name = name;
        this.remark = remark;
        this.parent = parent;
//        this.grade = grade;
//        this.seqInGrade = seqInGrade;
    }

    public static List<Organization> readInTreeOrder(){
        //todo: ordered by grade and seqInGrade
        return null;
    }
}
