package controllers;

import models.sys.District;
import models.sys.District2;

import java.util.List;
import java.util.Map;

/**
 * Desc:
 * --------
 * User: zou bo
 * Date: 12-1-20 下午3:56
 */
public class Districts extends Application {

    public static void index() {
        show();
    }


    public static void show() {
        List<District> provList = District.allProvinces();
        render(provList);
    }


    public static void create() {
        show();
    }

    public static void edit() {
        show();
    }


    public static void cityList(String provinceId) {
        log.info("provinceId = "+provinceId);
        List<District> districts=District.allCities(provinceId);
        render(districts);
    }

    public static void citySelectList(String provinceId) {
        log.info("provinceId = "+provinceId);
        List<District> districts=District.availableCities(provinceId);
        render(districts);
    }


    public static void countyList(String cityId) {
        log.info("cityId = " + cityId);
        List<District> districts = District.allCounties(cityId);
        render(districts);
    }

    public static void countySelectList(String cityId) {
        log.info("cityId = " + cityId);
        List<District> districts = District.availableCounties(cityId);
        render(districts);
    }


    public static void allProvinces() {
        Map<String, String> resultMap = District.allProvincesMap();
        log.info(resultMap);
        renderJSON(resultMap);
    }


    public static void allCities(String provinceId) {
        log.info("Got provinceId = " + provinceId);
        Map<String, String> resultMap = District.allCitiesMap(provinceId);
        log.info(resultMap);
        renderJSON(resultMap);
    }


    public static void allCounties(String cityId) {
        Map<String, String> resultMap = District.allCountiesMap(cityId);
        log.info(resultMap);
        renderJSON(resultMap);
    }
    
    
    public static void forbid(Integer level,String districtId){
        log.info("<< level = "+level+", districtId = "+districtId);
        District district=District.findByDisId(districtId);
        District.forbid(district,level);
        if(district==null){
            flash.error("被禁用行政区不存在");
        }else if(District.forbid(district,level)){
            flash.success("禁用成功");
        }else {
            flash.error("禁用失败");
        }

        refreshMemProvinces();
        show();
    }

    public static void resume(Integer level,String districtId){
        log.info("<< level = "+level+", districtId = "+districtId);
        District district=District.findByDisId(districtId);
        if(district==null){
            flash.error("被恢复行政区不存在");
        }else if(District.resume(district,level)){
            flash.success("恢复成功");
        }else {
            flash.error("恢复失败");
        }

        refreshMemProvinces();
        show();
    }

    public static void init(){
        District2.load2district();
    }
}
