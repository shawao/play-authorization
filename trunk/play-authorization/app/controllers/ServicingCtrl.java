package controllers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import models.fault.*;
import models.sys.SysUser;

import java.util.Date;
import java.util.List;

/**
 * Desc  :
 * -----
 * Author: <a href="mailto:ember319@gmail.com">ember</a>
 * Date  : 12-3-14
 * Time  : 下午11:46
 */
public class ServicingCtrl extends Application{

    public static void index(){
        show(1);
    }

    public static void show(Integer page){
        List<SysUser> users=SysUser.findAll();
        render(users);
    }



    public static void stationSel(Integer page){
        Integer pageSequence = page == null || page < 0 ? 1 : page;
        int from = (pageSequence - 1) * pageSize;

        List<AutoStation> stations = AutoStation.find("select o from AutoStation o order by name")
                .from(from).fetch(pageSize);
        Long entityCount = AutoStation.count();

        render(entityCount, stations, pageSequence);
    }

    public static void moduleSel(long stationId) {
        List<StationModule> modules = Module.find(
                "select s from StationModule s where s.stationId=? order by id desc", stationId).fetch();

        JsonArray ja=new JsonArray();
        for(StationModule m:modules){
            JsonObject jsonObject=new JsonObject();
            jsonObject.addProperty("ID",""+m.module.id);
            jsonObject.addProperty("NAME",m.module.name);
            ja.add(jsonObject);
        }
        
        JsonObject modulesJson=new JsonObject();
        modulesJson.add("MODULES",ja);
        String jsonString=modulesJson.toString();
        log.info(">> ja.toString() = "+jsonString);
        renderJSON(jsonString);
    }


    public static void create(long stationId,
                              long moduleId,long moduleId2,long moduleId3,
                              long userId,long userId2,long userId3,
                              String behavior,String faultRemark,
                              Date startTime,Date endTime,long reasonId,
                              String process,String result,String servicingRemark,
                              String servicingChk,int fixed){
        AutoStation station=AutoStation.findById(stationId);
        Module module=moduleId>0?(Module)Module.findById(moduleId):null;
        Module module2=moduleId>0?(Module)Module.findById(moduleId2):null;
        Module module3=moduleId>0?(Module)Module.findById(moduleId3):null;

        Fault fault=new Fault(station,module,module2,module3,0,behavior,faultRemark,null,connectedUser());
        fault.save();

        if("1".equals(servicingChk)){
            SysUser user=moduleId>0?(SysUser)SysUser.findById(userId):null;
            SysUser user2=moduleId>0?(SysUser)SysUser.findById(userId2):null;
            SysUser user3=moduleId>0?(SysUser)SysUser.findById(userId3):null;
            Servicing servicing=new Servicing(station,fault,user,user2,user3,
                    startTime,endTime,process,result,reasonId,servicingRemark,fixed,connectedUser());

            servicing.save();

            flash.success("维修报告保存成功");
        }

        show(1);
    }
}
