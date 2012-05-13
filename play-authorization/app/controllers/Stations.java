package controllers;

import models.fault.*;
import models.sys.SysUser;
import org.apache.commons.lang.StringUtils;
import play.Play;
import play.data.binding.As;
import play.data.validation.Required;
import utils.ConstUtil;
import utils.DistrictUtil;

import java.io.File;
import java.util.ArrayList;
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

        station.edit(
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
        show(1);
    }


    public static void delete(@Required Long id) {
        AutoStation entity = AutoStation.findById(id);

        if (entity != null) {
            try {
                AutoStation.force2delete(id);
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
    
    public static void uploadPhoto(Long stationId, File photo, Integer desc){
        AutoStation station=AutoStation.findById(stationId);
        StationAlbum album=StationAlbum.saveAlbum(station,photo,desc,connectedUser(),albumHomeDir);
        //album.save();
        log.info("Upload file successfully");
        log.info(album);
        renderText(album.id);
    }
    
    public static void photo(Long photoId){
        if (photoId == null) {
            log.error("No photo referred");
            renderText("No photo referred");
        } else {
            StationAlbum album = StationAlbum.findById(photoId);

            StringBuilder pathBuf = new StringBuilder();
            pathBuf.append(albumHomeDir.getAbsolutePath()).append(File.separator);
            pathBuf.append(album.station.id).append(File.separator).append(album.fileName);
            File photoFile = new File(pathBuf.toString());

            response.setContentTypeIfNotSet(album.contentType);
            renderBinary(photoFile);
        }
    }

    public static void deletePhoto(@Required Long id){
        StationAlbum album=StationAlbum.findById(id);
        if(album!=null){
            album.delete();
            renderText("success");
        }else{
            log.info("No photo found by ["+id+"]");
            renderText("failed");
        }
    }


    public static void modules(Long stationId){
        log.info("stationId = "+stationId);
        List<Module> modules=StationModule.findModulesByStationId(stationId);
        List<StationModule> stationModules=StationModule.find("byStationId",stationId).fetch();
        render(stationModules,modules, stationId);
    }
    
    
    public static void saveModules(Long stationId,Long[] moduleId){
        log.info("stationId = "+stationId);
        log.info("moduleId = "+ StringUtils.join(moduleId,","));

        if(moduleId!=null && moduleId.length>0){
            // remove all old StationModules
            StationModule.delete("stationId",stationId);
            SysUser user=connectedUser();

            for(Long mId:moduleId){
                Module module=Module.findById(mId);
                Long typeId=params._contains("moduleType_"+mId)?params.get("moduleType_"+mId,Long.class):null;
                if(typeId!=null){
                    ModuleType moduleType=ModuleType.findById(typeId);
                    StationModule staMod=new StationModule(stationId,module,moduleType,user);
                    staMod.save();
                    log.info(staMod);
                }
            }
        }
        log.info("success");
        renderText("success");
    }





    public static void test(){
        //todo
        render();
    }
}
