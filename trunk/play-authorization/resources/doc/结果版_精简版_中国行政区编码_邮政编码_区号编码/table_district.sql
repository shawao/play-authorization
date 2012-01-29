-- Create table
create table L_Zip_District_PhoneCode
(
  K_ID  VARCHAR(10)   not null,
  P_ID  VARCHAR(10)   not null,
  P_NM  VARCHAR(50)   not null,
  C_ID  VARCHAR(10)   not null,
  C_NM  VARCHAR(50)   not null,
  A_ID  VARCHAR(10)   not null,
  A_NM  VARCHAR(50)   not null,
  FULL_NAME VARCHAR(100)   not null,
  DISCTRICT_CODE  VARCHAR(10)  not null,
  ZIP_CODE  VARCHAR(10)   not null,
  PHONE_CODE VARCHAR(10)   not null
);

-- Add comments to the columns 
comment on column L_Zip_District_PhoneCode.K_ID
  is '数据id';
comment on column L_Zip_District_PhoneCode.P_ID
  is '省行政区码';
comment on column L_Zip_District_PhoneCode.P_NM
  is '省行政区名称';
comment on column L_Zip_District_PhoneCode.C_ID
  is '市行政区码';
comment on column L_Zip_District_PhoneCode.C_NM
  is '市行政区名称';
comment on column L_Zip_District_PhoneCode.A_ID
  is '区域行政区码';
comment on column L_Zip_District_PhoneCode.A_NM
  is '区域行政区名称';
comment on column L_Zip_District_PhoneCode.FULL_NAME
  is '行政区域全名';
comment on column L_Zip_District_PhoneCode.DISCTRICT_CODE
  is '行政区域编码';
comment on column L_Zip_District_PhoneCode.ZIP_CODE
  is '行政区域邮政编码';
comment on column L_Zip_District_PhoneCode.PHONE_CODE
  is '行政区域区号编码';
  

  
/*
insert into t_district 
(id,createDate,provinceId,provinceName,cityId,cityName,countyId,countyName,fullName,districtCode,zipCode,phoneCode) 
select k_id,now(),p_id,p_nm,c_id,c_nm,a_id,a_nm,full_name,disctrict_code,zip_code,phone_code 
from L_Zip_District_PhoneCode order by k_id;






select count(1) from L_Zip_District_PhoneCode;

-- provinces
select p_id,p_nm from L_Zip_District_PhoneCode group by p_id;
select * from L_Zip_District_PhoneCode group by p_id;

-- cities
SELECT c_id,c_nm from L_Zip_District_PhoneCode where p_id='310000' group by c_id;

-- counties
SELECT a_id,a_nm from L_Zip_District_PhoneCode where p_id='210000' and c_id='210200' group by a_id;

-- delete from L_Zip_District_PhoneCode;
comment on column L_Zip_District_PhoneCode.K_ID is '数据id';


select d.* from t_district d group by d.provinceId;


select * from t_District where provinceId='110000' group by cityId;

select * from t_district where provinceId='310000' group by cityId;

select o.* from t_District o group by o.provinceId order by o.provinceId asc;





*/
