package controllers;

import models.fault.AutoStation;
import models.fault.SysConstant;
import models.fault.Vendor;
import tools.ConstSelectInputTool;

import java.util.Date;
import java.util.List;

/**
 * Desc  :
 * -----
 * Author: <a href="mailto:ember319@gmail.com">ember</a>
 * Date  : 12-1-27
 * Time  : 上午1:25
 */
public class Stations extends Application{
    
    public static void index(){
        show(1);
    }
    
    public static void show(Integer page){
        Integer pageSequence = page == null || page < 0 ? 1 : page;
        int from = (pageSequence - 1) * pageSize;

        List<AutoStation> stations = AutoStation.find("select o from AutoStation o order by id desc")
                .from(from).fetch(pageSize);
        Long entityCount = AutoStation.count();


        render(entityCount, stations, pageSequence);
    }


    public static void create(
            String name, String stationNo, String cardNo,
            String longitude, String latitude, double elevation,
            String address, String districtId, String location,
            String transModeId, String powerSupplyType, String stationTypeId,
            Vendor vendor, Date buildTime, Long contactUserId, Long contactUserId2,
            int elementNum, String observationElement, String terrainId,
            String groundId, String neighboringEnv, String observationFieldSize,
            String satLevelId, int assessOrNot, String weatherBureau, String accessPoints,
            String port, String ip, String history, String remark){
        
        List<SysConstant> constants=SysConstant.findAll();


        ConstSelectInputTool.ConstSelectValue powerSupplyTypeValue
                = ConstSelectInputTool.parse("powerSupplyType", params, constants);
        log.info("powerSupplyTypeValue = "+powerSupplyTypeValue);

        //transModeId
        ConstSelectInputTool.ConstSelectValue transModeIdValue
                = ConstSelectInputTool.parse("transModeId", params, constants);
        log.info("transModeId = "+transModeIdValue);
        show(1);
    }
    
    
    
    public static void edit(
            Long id,String name, String stationNo, String cardNo,
            String longitude, String latitude, double elevation,
            String address, String districtId, String location,
            String transModeId, String powerSupplyType, String stationTypeId,
            Vendor vendor, Date buildTime, Long contactUserId, Long contactUserId2,
            int elementNum, String observationElement, String terrainId,
            String groundId, String neighboringEnv, String observationFieldSize,
            String satLevelId, int assessOrNot, String weatherBureau, String accessPoints,
            String port, String ip, String history, String remark){
        //
        show(1);
    }
}
