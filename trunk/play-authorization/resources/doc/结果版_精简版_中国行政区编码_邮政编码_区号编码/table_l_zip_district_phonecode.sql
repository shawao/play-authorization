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
)
tablespace AMLTP
  pctfree 10
  pctused 40
  initrans 1
  maxtrans 255
  storage
  (
    initial 80
    minextents 1
    maxextents unlimited
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
-- Create/Recreate primary, unique and foreign key constraints 
alter table L_Zip_District_PhoneCode
  add constraint ZipCode_DistrictCode_PK primary key (K_ID)
  using index 
  tablespace AMLTP
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );