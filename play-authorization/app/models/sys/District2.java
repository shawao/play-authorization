package models.sys;

import play.db.jpa.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

/**
 * Desc  :
 * -----
 * Author: <a href="mailto:ember319@gmail.com">ember</a>
 * Date  : 12-1-25
 * Time  : 下午10:22
 */
@Entity
@Table(name = "l_zip_district_phonecode")
public class District2 extends Model {
    /*
  `K_ID` varchar(10) NOT NULL COMMENT '数据id',
  `P_ID` varchar(10) NOT NULL comment '省行政区码',
  `P_NM` varchar(50) NOT NULL,
  `C_ID` varchar(10) NOT NULL,
  `C_NM` varchar(50) NOT NULL,
  `A_ID` varchar(10) NOT NULL,
  `A_NM` varchar(50) NOT NULL,
  `FULL_NAME` varchar(100) NOT NULL,
  `DISCTRICT_CODE` varchar(10) NOT NULL,
  `ZIP_CODE` varchar(10) NOT NULL,
  `PHONE_CODE` varchar(10) NOT NULL    
     */

    @Column(length = 10,name = "K_ID")
    public String k_id;
    @Column(length = 10,name = "P_ID")
    public String p_id;
    @Column(length = 50,name = "P_NM")
    public String p_nm;
    @Column(length = 10,name = "C_ID")
    public String c_id;
    @Column(length = 50,name = "C_NM")
    public String c_nm;
    @Column(length = 10,name = "A_ID")
    public String a_id;
    @Column(length = 50,name = "A_NM")
    public String a_nm;
    @Column(length = 100,name = "FULL_NAME")
    public String fullName;
    @Column(length = 10,name = "DISCTRICT_CODE")
    public String districtCode;
    @Column(length = 10,name = "ZIP_CODE")
    public String phoneCode;
    @Column(length = 10,name = "PHONE_CODE")
    public String zipCode;


    /*
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
     */

    public static void load2district(){
        List<District2> provList=District2.find("select o from District2 o group by o.p_id order by o.p_id asc").fetch();;

        play.Logger.info("------------------------ LOADING DISTRICT ------------------------> start");
        for(District2 prov:provList){
            District disProv=new District(
                    prov.p_id,prov.districtCode,prov.p_nm,prov.fullName,
                    prov.zipCode,prov.phoneCode,1,null);
            disProv.save();
            play.Logger.info("[PROV]: "+disProv.toString());

            List<District2> cityList=District2.find("select o from District2 o where o.p_id=? group by o.c_id order by o.c_id asc", prov.p_id).fetch();
            for(District2 city:cityList){
//                String cityName=city.c_nm.trim().equals("市辖区")?prov.p_nm:city.c_nm;
//                String cityFullName=prov.p_nm+cityName;
                District disCity=new District(
                    city.c_id,city.districtCode,city.c_nm,city.fullName,
                    city.zipCode,city.phoneCode,2,prov.p_id);
                disCity.save();
                play.Logger.info("\t[CITY]: "+disCity.toString());

                List<District2> countyList=District2.find("select o from District2 o where o.c_id=? and o.a_nm !='市辖区' group by o.a_id order by o.a_id asc", city.c_id).fetch();
                for(District2 county:countyList){
                    District disCounty=new District(
                    county.a_id,county.districtCode,county.a_nm,county.fullName,
                    county.zipCode,county.phoneCode,3,city.c_id);
                    disCounty.save();
                    play.Logger.info("\t\t[COUNTY]: "+disCity.toString());
                }
            }
        }
        play.Logger.info("------------------------ LOADING DISTRICT ------------------------> end");
    }
}
