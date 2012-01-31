package models.fault;

import models.AbstractEntity;
import models.sys.District;
import models.sys.SysUser;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Desc: 自动站实体
 * --------
 * Author: <a href="mailto:ember319@gmail.com">zou bo</a>
 * Date: 12-1-8 下午8:26
 */
@Entity
@Table(name="t_auto_station")
public class AutoStation extends AbstractEntity {
    // 站名
    @Column(nullable = false,length = 50)
    public String name;
    // 站号
    @Column(nullable = false,length = 20)
    public String stationNo;
    // 卡号
    @Column(length = 20)
    public String cardNo;
    // 经度
    @Column(length = 20)
    public String longitude;
    // 纬度
    @Column(length = 20)
    public String latitude;
    // 海拔高度
    @Basic
    public double elevation;
    // 站址
    @Column(length = 100)
    public String address;
    // 所属行政区
    @Column(length = 30)
    public String districtId;//province_city_county(disId)
    // 具体地址位置?  所属乡镇
    @Column(length = 100)
    public String location;

    // 传输方式，以编码的方式保存，并冗余名称
    @Column(length = 20)
    public String transModeId;//constType_constCode

    // 供电方式
    @Column(length = 20)
    public String powerSupplyType;//constType_constCode

    // 型号
    @Column(length = 20)
    public String stationTypeId;//constType_constCode
    
    // 生产厂家
    @ManyToOne
    public Vendor vendor;
    // 建设时间
    public Date buildTime;
    
    // 照片全景
    // 照片东 照片西 照片南	照片北（通过AutoStaAlbumEntity关联存储）

    // 联系人保存在系统用户表里
    // 日常维护人	联系电话1	站点联系人	联系电话2
    public Long contactUserId;

    public Long contactUserId2;

    // 要素：关联常量
    @Column(length = 20)
    public String elementNumId;
    // 观测要素：关联常量
    @Column(length = 50)
    public String observationElement;
    // 地形特征：关联常量
    @Column(length = 20)
    public String terrainId;//constType_constCode
    // 地面：关联常量
    @Column(length = 20)
    public String groundId;
    // 周边环境neighboring environment
    @Column(length = 50,name = "nei_env")
    public String neighboringEnv;
    // 观测场规格
    @Column(length = 20,name = "obs_fie_siz")
    public String observationFieldSize;
    // 站点级别（关联常量实体）
    @Column(length = 20)
    public String satLevelId;
    // 是否考核（2不考核，1考核，0未设置）
    @Column(name = "ass_or_not")
    public int assessOrNot;
    // 所有权,weather bureau
    @Column(length = 50)
    public String weatherBureau ;
    // 接入点
    @Column(length = 50)
    public String accessPoints ;
    // 端口号
    @Column(length = 20)
    public String port;
    // IP地址
    @Column(length = 20)
    public String ip;
    // 历史沿革
    @Column(length = 500)
    public String history;
    // 备注
    @Column(length = 500)
    public String remark;



//    @ManyToMany
//    @JoinTable(name = "rel_sta_mod",
//            joinColumns = @JoinColumn(name = "station_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "module_id", referencedColumnName = "id"))
//    public List<Module> modules;


    // status
    public int status=1;//1:works, 2:broken


    @ManyToOne
    public SysUser submitter;// who submit it


    public AutoStation(
            String name, String stationNo, String cardNo,
            String longitude, String latitude, double elevation,
            String address, String districtId, String location,
            String transModeId, String powerSupplyType, String stationTypeId,
            Vendor vendor, Date buildTime, Long contactUserId, Long contactUserId2,
            String elementNumId, String observationElement, String terrainId,
            String groundId, String neighboringEnv, String observationFieldSize,
            String satLevelId, int assessOrNot, String weatherBureau, String accessPoints,
            String port, String ip, String history, String remark, SysUser submitter) {
        this.name = name;
        this.stationNo = stationNo;
        this.cardNo = cardNo;
        this.longitude = longitude;
        this.latitude = latitude;
        this.elevation = elevation;
        this.address = address;
        this.districtId = districtId;
        this.location = location;
        this.transModeId = transModeId;
        this.powerSupplyType = powerSupplyType;
        this.stationTypeId = stationTypeId;
        this.vendor = vendor;
        this.buildTime = buildTime;
        this.contactUserId = contactUserId;
        this.contactUserId2 = contactUserId2;
        this.elementNumId = elementNumId;
        this.observationElement = observationElement;
        this.terrainId = terrainId;
        this.groundId = groundId;
        this.neighboringEnv = neighboringEnv;
        this.observationFieldSize = observationFieldSize;
        this.satLevelId = satLevelId;
        this.assessOrNot = assessOrNot;
        this.weatherBureau = weatherBureau;
        this.accessPoints = accessPoints;
        this.port = port;
        this.ip = ip;
        this.history = history;
        this.remark = remark;
        this.submitter = submitter;
    }



    public void editAutoStation(
            String name, String stationNo, String cardNo,
            String longitude, String latitude, double elevation,
            String address, String districtId, String location,
            String transModeId, String powerSupplyType, String stationTypeId,
            Vendor vendor, Date buildTime, Long contactUserId, Long contactUserId2,
            String elementNumId, String observationElement, String terrainId,
            String groundId, String neighboringEnv, String observationFieldSize,
            String satLevelId, int assessOrNot, String weatherBureau, String accessPoints,
            String port, String ip, String history, String remark, SysUser submitter) {
        this.name = name;
        this.stationNo = stationNo;
        this.cardNo = cardNo;
        this.longitude = longitude;
        this.latitude = latitude;
        this.elevation = elevation;
        this.address = address;
        this.districtId = districtId;
        this.location = location;
        this.transModeId = transModeId;
        this.powerSupplyType = powerSupplyType;
        this.stationTypeId = stationTypeId;
        this.vendor = vendor;
        this.buildTime = buildTime;
        this.contactUserId = contactUserId;
        this.contactUserId2 = contactUserId2;
        this.elementNumId = elementNumId;
        this.observationElement = observationElement;
        this.terrainId = terrainId;
        this.groundId = groundId;
        this.neighboringEnv = neighboringEnv;
        this.observationFieldSize = observationFieldSize;
        this.satLevelId = satLevelId;
        this.assessOrNot = assessOrNot;
        this.weatherBureau = weatherBureau;
        this.accessPoints = accessPoints;
        this.port = port;
        this.ip = ip;
        this.history = history;
        this.remark = remark;
        this.submitter = submitter;
    }
}
