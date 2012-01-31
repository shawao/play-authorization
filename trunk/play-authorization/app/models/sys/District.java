package models.sys;

import models.AbstractEntity;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Desc: 地址位置表，以树的形式存储。省市区，或更小的级别地区
 * --------
 * Author: <a href="mailto:ember319@gmail.com">zou bo</a>
 * Date: 12-1-8 下午8:39
 */
@Entity
@Table(name = "t_district")
public class District extends AbstractEntity {

    @Column(nullable = false, length = 10)
    public String disId;//省/市/区域行政区码
    @Column(length = 10)
    public String disCode;//行政区域编码
    @Column(nullable = false, length = 50)
    public String disName;//省/市/区域行政区名称

    @Column(length = 100)
    public String fullName;//行政区域全名


    @Column(length = 10)
    public String zipCode;//行政区域邮政编码
    @Column(length = 10)
    public String phoneCode;//行政区域区号编码

    public int level;//1:province, 2:city, 3:county

    public String parentDisId;

    public int status = 2;//1:已开启,2:关闭,3:部分开启


    public District(
            String disId, String disCode,
            String disName, String fullName,
            String zipCode, String phoneCode,
            int level, String parentDisId) {
        this.disId = disId;
        this.disCode = disCode;
        this.disName = disName;
        this.fullName = fullName;
        this.zipCode = zipCode;
        this.phoneCode = phoneCode;
        this.level = level;
        this.parentDisId = parentDisId;
    }


    /**
     * 变更行政区状态
     *
     * @param district 行政区对象
     * @param level    1：省份，2：城市，3：区县
     * @return 返回是否成功
     */
    public static boolean forbid(District district, int level) {
        if (level == 3) {
            //更新自身状态，并更新上两级状态为3（部分可用）
            district.status = 2;
            district.save();

            int level2num = Integer.parseInt("" + District.em().createNativeQuery(
                    "select count(1) from t_district o where o.level=3 and o.status!=2 and o.parentDisId='"+
                    district.parentDisId+"'").getSingleResult());

            District parent = District.findByDisId(district.parentDisId);
            parent.status = level2num > 0 ? 3 : 2;
            parent.save();

            int level1num = Integer.parseInt("" + District.em().createNativeQuery(
                    "select count(1) from t_district o where o.level=2 and o.status!=2 and o.parentDisId='"+
                    parent.parentDisId+"'").getSingleResult());
            District parentParent = District.findByDisId(parent.parentDisId);
            parentParent.status = level1num > 0 ? 3 : 2;
            parentParent.save();
        } else if (level == 2) {
            //更新自身和所有下级状态，并更新上级为3
            district.status = 2;
            district.save();

            int level1num = Integer.parseInt("" + District.em().createNativeQuery(
                    "select count(1) from t_district o where o.level=2 and o.status!=2 and o.parentDisId='"+
                    district.parentDisId+"'").getSingleResult());

            District parent = District.findByDisId(district.parentDisId);
            parent.status = level1num > 0 ? 3 : 2;
            parent.save();

            EntityManager em = District.em();
            em.createNativeQuery(
                    "update t_district set status=" + 2 + " where parentDisId='" + district.disId + "'")
                    .executeUpdate();
            em.flush();
        } else if (level == 1) {
            //更新自身和所有下级状态
            EntityManager em = District.em();
            em.createNativeQuery(
                    "update t_district set status=" + 2 + " where disId like '" + district.disId.substring(0, 2) + "%'")
                    .executeUpdate();
            em.flush();
        }
        return true;
    }


    /**
     * 变更行政区状态
     *
     * @param district 行政区对象
     * @param level    1：省份，2：城市，3：区县
     * @return 返回是否成功
     */
    public static boolean resume(District district, int level) {
        if (level == 3) {
            //更新自身状态，并更新上两级状态为3（部分可用）
            district.status = 1;
            district.save();

            int level2num = Integer.parseInt("" + District.em().createNativeQuery(
                    "select count(1) from t_district o where o.level=3 and o.status=2 and o.parentDisId='"+
                    district.parentDisId+"'").getSingleResult());

            District parent = District.findByDisId(district.parentDisId);
            parent.status = level2num > 0 ? 3 : 1;
            parent.save();

            int level1num = Integer.parseInt("" + District.em().createNativeQuery(
                    "select count(1) from t_district o where o.level=2 and o.status!=1 and o.parentDisId='"+
                    parent.parentDisId+"'").getSingleResult());
            District parentParent = District.findByDisId(parent.parentDisId);
            parentParent.status = level1num > 0 ? 3 : 1;
            parentParent.save();
        } else if (level == 2) {
            //更新自身和所有下级状态，并更新上级为3
            district.status = 1;
            district.save();

            int level1num = Integer.parseInt("" + District.em().createNativeQuery(
                    "select count(1) from t_district o where o.level=2 and o.status!=1 and o.parentDisId='"+
                    district.parentDisId+"'").getSingleResult());

            District parent = District.findByDisId(district.parentDisId);
            parent.status = level1num > 0 ? 3 : 1;
            parent.save();

            EntityManager em = District.em();
            em.createNativeQuery(
                    "update t_district set status=" + 1 + " where parentDisId='" + district.disId + "'")
                    .executeUpdate();
            em.flush();
        } else if (level == 1) {
            //更新自身和所有下级状态
            EntityManager em = District.em();
            em.createNativeQuery(
                    "update t_district set status=" + 1 + " where disId like '" + district.disId.substring(0, 2) + "%'")
                    .executeUpdate();
            em.flush();
        }
        return true;
    }


    public static List<District> allProvinces() {
        return District.find("select o from District o where o.level=1 order by o.disId asc").fetch();
    }


    public static List<District> availableProvinces() {
        return District.find("select o from District o where o.level=1 and o.status in (1,3) order by o.disId asc").fetch();
    }

    public static Map<String, String> allProvincesMap() {
        List<District> districts = allProvinces();
        Map<String, String> resultMap = new TreeMap<String, String>();
        if (districts != null && districts.size() > 0) {
            for (District o : districts) {
                resultMap.put(o.disId, o.disName);
            }
        }
        return resultMap;
    }

    public static List<District> allCities(String provinceId) {
        return District.find("select o from District o where o.level=2 and o.parentDisId=? order by o.disId asc", provinceId).fetch();
    }

    public static List<District> availableCities(String provinceId) {
        return District.find("select o from District o where o.level=2 and o.parentDisId=? and o.status in (1,3) order by o.disId asc",provinceId).fetch();
    }


    public static Map<String, String> allCitiesMap(String provinceId) {
        List<District> districts = allCities(provinceId);

        Map<String, String> resultMap = new TreeMap<String, String>();
        if (districts != null && districts.size() > 0) {
            for (District o : districts) {
                resultMap.put(o.disId, o.disName);
            }
        }
        return resultMap;
    }

    public static List<District> allCounties(String cityId) {
        return District.find("select o from District o where o.level=3 and o.parentDisId=? order by o.disId asc", cityId).fetch();
    }

    public static List<District> availableCounties(String cityId) {
        return District.find("select o from District o where o.level=3 and o.parentDisId=? and o.status=1 order by o.disId asc",cityId).fetch();
    }

    public static Map<String, String> allCountiesMap(String cityId) {
        List<District> districts = allCounties(cityId);

        Map<String, String> resultMap = new TreeMap<String, String>();
        if (districts != null && districts.size() > 0) {
            for (District o : districts) {
                resultMap.put(o.disId, o.disName);
            }
        }
        return resultMap;
    }
    
    
    public static TreeMap<String,String> availableMap(){
        List<District> districts=District.find("select o from District o where o.status in (1,3) order by o.disId asc").fetch();
        
        TreeMap<String,String> map=new TreeMap<String,String>();
        for(District d:districts){
            map.put(d.disId,d.disName);
        }
        return map;
    }
    

    public String showStatus() {
        String remark = "";
        if (status == 1)
            remark = "已开启";
        else if (status == 2)
            remark = "关闭";
        else if (status == 3)
            remark = "部分开启";
        return remark;
    }

    public String cssByStatus() {
        String css = "";
        if (status == 1)
            css = "status_available";
        else if (status == 2)
            css = "status_unavailable";
        else if (status == 3)
            css = "status_part_available";
        return css;
    }


    public static District findByDisId(String districtId) {
        String sql = "select o from District o where o.disId=?";

        List<District> districts = District.find(sql, districtId).fetch();
        if (districts != null && districts.size() > 0) {
            if (districts.size() > 1)
                play.Logger.error("!!! sql: " + sql);
            return districts.get(0);
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        return "District{" +
                "disId='" + disId + '\'' +
                ", disCode='" + disCode + '\'' +
                ", disName='" + disName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", phoneCode='" + phoneCode + '\'' +
                ", level=" + level +
                ", parentDisId='" + parentDisId + '\'' +
                ", status=" + status +
                '}';
    }
}
