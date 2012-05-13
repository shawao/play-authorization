package models.fault;

import models.AbstractEntity;
import models.sys.SysUser;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Desc  :
 * -----
 * Author: <a href="mailto:ember319@gmail.com">ember</a>
 * Date  : 12-1-26
 * Time  : 下午12:17
 */
@Entity
@Table(name = "t_sta_module")
public class StationModule extends AbstractEntity {

    public Long stationId;

    @ManyToOne
    public Module module;//模块

    @ManyToOne(fetch = FetchType.EAGER)
    public ModuleType moduleType;//模块型号


    @Column(length = 200)
    public String remark;

    public int status=1;//1：有效，2：删除

    @ManyToOne
    public SysUser submitter;// who submit it

    public StationModule(
            Long stationId, Module module,
            ModuleType moduleType,
            String remark, int status, SysUser submitter) {
        this.stationId = stationId;
        this.module = module;
        this.moduleType = moduleType;
        this.remark = remark;
        this.status = status;
        this.submitter = submitter;
    }

    public StationModule(Long stationId, Module module, ModuleType moduleType, SysUser submitter) {
        this.stationId = stationId;
        this.module = module;
        this.moduleType = moduleType;
        this.submitter = submitter;
    }

    public static List<Module> findModulesByStationId(Long stationId){
        List<StationModule> stationModules= StationModule.find("byStationId",stationId).fetch();
        List<Module> modules=new ArrayList<Module>();
        if(stationModules!=null && stationModules.size()>0){
            for(StationModule staMod:stationModules){
                modules.add(staMod.module);
            }
        }
        return modules;
    }
    
    public static int deleteAllByStationId(Long stationId){
        return StationModule.delete("delete from StationModule o where o.stationId=?",stationId);
        //return StationModule.em().createNativeQuery("delete from t_sta_module where stationId="+stationId).executeUpdate();
    }
    

    @Override
    public String toString() {
        return "StationModule{" +
                "id="+id+
                ", stationId=" + stationId +
                ", module=" + module +
                ", moduleType=" + moduleType +
                ", remark='" + remark + '\'' +
                ", status=" + status +
                ", submitter=" + submitter +
                '}';
    }
}
