package controllers;

import models.fault.AutoStation;
import models.fault.StationAlbum;
import models.fault.SysConstant;
import models.fault.Vendor;
import models.sys.SysUser;
import play.Play;
import play.data.binding.As;
import play.data.validation.Required;
import utils.ConstUtil;
import utils.DistrictUtil;

import java.io.File;
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

    static String ALBUM_HOME = Play.configuration.getProperty("station.album.home");
    static File albumHomeDir;

    static {
        albumHomeDir = new File(ALBUM_HOME);
    }

    public static void index(){
        show(1);
    }
    
    public static void show(Integer page){
        Integer pageSequence = page == null || page < 0 ? 1 : page;
        int from = (pageSequence - 1) * pageSize;

        List<AutoStation> stations = AutoStation.find("select o from AutoStation o order by name")
                .from(from).fetch(pageSize);
        Long entityCount = AutoStation.count();
        
        render(entityCount, stations, pageSequence);
    }


    public static void create(
            String name, String stationNo, String cardNo,
            String longitude, String latitude, double elevation,
            String address, String districtId, String location,
            String transModeId, String powerSupplyType, String stationTypeId,
            long vendorId, @As("dd/MM/yyyy") Date buildTime, Long contactUserId, Long contactUserId2,
            int elementNum, String observationElement, String terrainId,
            String groundId, String neighboringEnv, String observationFieldSize,
            String satLevelId, int assessOrNot, String weatherBureau, String accessPoints,
            String port, String ip, String history, String remark,
            File[] file,int[] photoDesc){

        SysUser user=connectedUser();
        List<SysConstant> constants=SysConstant.findAll();

        String powerSupplyType_foreignKey= ConstUtil.parseOrSave("powerSupplyType", params, constants, user);
        String transModeId_foreignKey= ConstUtil.parseOrSave("transModeId", params, constants, user);
        String stationTypeId_foreignKey= ConstUtil.parseOrSave("stationTypeId", params, constants, user);
        String elementNum_foreignKey= ConstUtil.parseOrSave("elementNum", params, constants, user);
        String observationElement_foreignKey= ConstUtil.parseOrSave("observationElement", params, constants, user);
        String terrainId_foreignKey= ConstUtil.parseOrSave("terrainId", params, constants, user);
        String groundId_foreignKey= ConstUtil.parseOrSave("groundId", params, constants, user);
        String satLevelId_foreignKey= ConstUtil.parseOrSave("satLevelId", params, constants, user);

        String districtId_foreignKey= DistrictUtil.parse("districtId", params);
        
        Vendor vendor=Vendor.findById(vendorId);

        AutoStation station =new AutoStation(
            name, stationNo, cardNo,
            longitude, latitude, elevation,
            address, districtId_foreignKey, location,
            transModeId_foreignKey, powerSupplyType_foreignKey, stationTypeId_foreignKey,
            vendor, buildTime, contactUserId, contactUserId2,
            elementNum_foreignKey, observationElement_foreignKey, terrainId_foreignKey,
            groundId_foreignKey, neighboringEnv, observationFieldSize,
            satLevelId_foreignKey, assessOrNot, weatherBureau, accessPoints,
            port, ip, history, remark, user);
        
        station.save();

        StationAlbum.saveAlbum(station,file,photoDesc,user,albumHomeDir);

        show(1);
    }
    
    
    
    public static void edit(
            Long id,String name, String stationNo, String cardNo,
            String longitude, String latitude, double elevation,
            String address, String districtId, String location,
            String transModeId, String powerSupplyType, String stationTypeId,
            long vendorId, Date buildTime, Long contactUserId, Long contactUserId2,
            int elementNum, String observationElement, String terrainId,
            String groundId, String neighboringEnv, String observationFieldSize,
            String satLevelId, int assessOrNot, String weatherBureau, String accessPoints,
            String port, String ip, String history, String remark){
        //
        AutoStation station =AutoStation.findById(id);

        SysUser user=connectedUser();
        List<SysConstant> constants=SysConstant.findAll();

        String powerSupplyType_foreignKey= ConstUtil.parseOrSave("powerSupplyType", params, constants, user);
        String transModeId_foreignKey= ConstUtil.parseOrSave("transModeId", params, constants, user);
        String stationTypeId_foreignKey= ConstUtil.parseOrSave("stationTypeId", params, constants, user);
        String elementNum_foreignKey= ConstUtil.parseOrSave("elementNum", params, constants, user);
        String observationElement_foreignKey= ConstUtil.parseOrSave("observationElement", params, constants, user);
        String terrainId_foreignKey= ConstUtil.parseOrSave("terrainId", params, constants, user);
        String groundId_foreignKey= ConstUtil.parseOrSave("groundId", params, constants, user);
        String satLevelId_foreignKey= ConstUtil.parseOrSave("satLevelId", params, constants, user);

        String districtId_foreignKey= DistrictUtil.parse("districtId", params);

        Vendor vendor=Vendor.findById(vendorId);
        Date buildDate=null;//todo

        station.edit(
                name, stationNo, cardNo,
                longitude, latitude, elevation,
                address, districtId_foreignKey, location,
                transModeId_foreignKey, powerSupplyType_foreignKey, stationTypeId_foreignKey,
                vendor, buildDate, contactUserId, contactUserId2,
                elementNum_foreignKey, observationElement_foreignKey, terrainId_foreignKey,
                groundId_foreignKey, neighboringEnv, observationFieldSize,
                satLevelId_foreignKey, assessOrNot, weatherBureau, accessPoints,
                port, ip, history, remark, user);
        station.save();
        show(1);
    }


    public static void delete(@Required Long id) {
        AutoStation entity = AutoStation.findById(id);

        if (entity != null) {
            try {
                entity.delete();
                flash.success("（" + entity.name + "）成功删除");
            } catch (Throwable cve) {
                flash.error("（" + entity.name + "）删除失败，该自动站已经被使用");
            }
        } else {
            flash.error("ID（" + id + "）自动站不存在");
        }
        show(1);
    }
    
    // for ajax loading new station tab
    public static void newStation(){
        render();
    }
    
    // for ajax loading new album tab
    public static void album(Long stationId){
        AutoStation station = AutoStation.findById(stationId);
        List<StationAlbum> albums=StationAlbum.findByStation(station);
        render(station,albums);
    }
}
