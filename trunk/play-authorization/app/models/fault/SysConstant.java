package models.fault;

import models.AbstractEntity;
import models.sys.SysUser;

import javax.persistence.*;
import java.util.List;

/**
 * Desc: 系统常量实体；以constType区分，保存各种常量
 * 包括：自动站传输方式，供电方式，自动站型号，地形特征，地面，站点级别，故障产生根源（产品质量、维护管理、不可抗拒、其他）
 * --------
 * Author: <a href="mailto:ember319@gmail.com">zou bo</a>
 * Date: 12-1-9 下午10:32
 */
@Entity
@Table(name = "t_constant")
public class SysConstant extends AbstractEntity {

    public Long constType;//常量类型

    @Column(nullable = false)
    public Long constCode;//常量CODE
    @Column(length = 50)
    public String constValue;//常量值
    @Column(length = 50)
    public String constRemark;//常量备注，可以保存名称

    @Column(length = 200)
    public String remark;//备注

    public int status=1;//1：正常，2：禁用

    @ManyToOne
    public SysUser submitter;// who submit it


    /*
    * 这张常量表，首先第一部分（头部）定义自身都有哪些常量类型种类，其后的部分是按照头部
    * 定义的常量种类，为每一种常量类定义该类目下的各个常量
    *
    * constType=1被占用，用作描述自身，每一种constCode和constRemark表示一种常量编码和名称
    * 之后的每一条常量记录，constType对应一条constType=1时的一个constCode值
    */
    public SysConstant(Long constType, Long constCode, String constValue, String constRemark, String remark, SysUser submitter) {
        this.constType = constType;
        this.constCode = constCode;
        this.constValue = constValue;
        this.constRemark = constRemark;
        this.remark = remark;
        this.submitter = submitter;
    }


    public static SysConstant createConstType(Long constCode, String constRemark, SysUser submitter) {
        return new SysConstant(0L, constCode, null, constRemark, "常量类型", submitter);
    }


    public static List<SysConstant> allConstType() {
        return SysConstant.find(
                "select o from SysConstant o where o.constType=0 order by o.constCode asc").fetch();
    }


    public static SysConstant findConstTypeByCode(Long code) {
        List<SysConstant> typeList = SysConstant.find(
                "select o from SysConstant o where o.constType=0 and o.constCode=? order by o.constCode asc", code).fetch();
        if (typeList != null && typeList.size() > 0) {
            return typeList.get(0);
        } else {
            return null;
        }

    }


    public void editSysConstant(Long constType, Long constCode, String constValue, String constRemark, String remark, int status) {
        this.constType = constType;
        this.constCode = constCode;
        this.constValue = constValue;
        this.constRemark = constRemark;
        this.remark = remark;
        this.status = status;
    }

    public static boolean validateTypeConstCode(Long typeConstCode) {
        long count = SysConstant.count(
                "select count(o) from SysConstant o where o.constType=0 and o.constCode=?", typeConstCode);
        //如果count>0，意味着已经被占用，返回失败
        return count == 0;
    }


    public static boolean validateConstCode(Long typeConstCode, Long constCode) {
        long count = SysConstant.count(
                "select count(o) from SysConstant o where o.constType=? and o.constCode=?", typeConstCode, constCode);
        //如果count>0，意味着已经被占用，返回失败
        return count == 0;
    }

    public void deleteSelfAndRelated(){
        if (constType == 0) {
            EntityManager entityManager = SysConstant.em();
            entityManager.createNativeQuery("delete from t_constant where constType=" + this.constCode).executeUpdate();
            entityManager.flush();
        }
        delete();
    }

    public String showStatus() {
        String remark = "";
        if (status == 1)
            remark = "正常";
        else if (status == 2)
            remark = "禁用";
        return remark;
    }

    @Override
    public String toString() {
        return "SysConstant{" +
                "constType=" + constType +
                ", constCode=" + constCode +
                ", constValue='" + constValue + '\'' +
                ", constRemark='" + constRemark + '\'' +
                ", remark='" + remark + '\'' +
                ", status=" + status +
                ", submitter=" + submitter +
                '}';
    }
}
