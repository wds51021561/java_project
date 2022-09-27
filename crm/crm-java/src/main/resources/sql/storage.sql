# create table dic(
#                     id int primary key auto_increment comment '字典id',
#                     type_id int not null comment '类型id',
#                     type_name varchar(20) not null comment '类型名',
#                     status_id int not null comment '类型状态',
#                     status_name varchar(20) not null comment '类型状态名'
# );
#
# insert into dic
#     value
#     (null,1,'出入库类型',1,'采购入库'),
#     (null,1,'出入库类型',2,'销售入库'),
#     (null,1,'出入库类型',3,'售后入库'),
#     (null,1,'出入库类型',4,'换机入库'),
#     (null,1,'出入库类型',5,'其他入库'),
#     (null,1,'出入库类型',6,'采购退库'),
#     (null,1,'出入库类型',7,'销售出库'),
#     (null,1,'出入库类型',8,'售后出库'),
#     (null,1,'出入库类型',9,'换机出库'),
#     (null,1,'出入库类型',10,'其他出库'),
#     (null,2,'出入库状态',1,'待入库'),
#     (null,2,'出入库状态',2,'已入库'),
#     (null,2,'出入库状态',3,'退回待入库'),
#     (null,2,'出入库状态',4,'待出库'),
#     (null,2,'出入库状态',5,'已出库'),
#     (null,2,'出入库状态',6,'退回待出库'),
#     (null,2,'出入库状态',7,'录入完毕'),
#     (null,3,'订单类型',1,'内部订单'),
#     (null,3,'订单类型',2,'商城订单'),
#     (null,3,'订单类型',3,'Wap订单'),
#     (null,3,'订单类型',4,'电话订单'),
#     (null,3,'订单类型',5,'竞拍订单'),
#     (null,3,'订单类型',6,'号卡订单'),
#     (null,3,'订单类型',7,'秒杀订单'),
#     (null,4,'支付方式',1,'货到付款'),
#     (null,4,'支付方式',2,'网上支付'),
#     (null,4,'支付方式',3,'在线支付平台'),
#     (null,4,'支付方式',4,'易宝支付'),
#     (null,4,'支付方式',5,'支付宝'),
#     (null,4,'支付方式',6,'银行汇款'),
#     (null,4,'支付方式',7,'快钱支付'),
#     (null,4,'支付方式',8,'上门自提'),
#     (null,4,'支付方式',9,'在线银行支付'),
#     (null,5,'仓库类别',1,'铺货代销库'),
#     (null,5,'仓库类别',2,'账期与现金采购库'),
#     (null,5,'仓库类别',3,'集采库'),
#     (null,5,'仓库类别',4,'低值易耗品'),
#     (null,5,'仓库类别',5,'自有库'),
#     (null,5,'仓库类别',6,'残品库'),
#     (null,5,'仓库类别',7,'返厂维修销售库'),
#     (null,5,'仓库类别',8,'借出库');
#


# create table if not exists storage_audit
# (
#     id           int primary key auto_increment comment '审核id',
#     audit_form   varchar(30) unique            not null comment '审核的表单编号',
#     storage_code varchar(30)                   not null comment '仓库表单编号',
#     audit_level  tinyint                       not null comment '审核级别',
#     audit_state  tinyint                       not null default 0 comment '审核状态',
#     auditor      varchar(30)                   not null comment '审核人',
#     remark       varchar(255) comment '备注',
#     create_time  datetime default current_date not null comment '创建时间',
#     modify_time  datetime default current_date not null comment '修改时间'
# );
#
#
#
# create table if not exists storage_form
# (
#     id            int primary key auto_increment comment '出入仓库单id',
#     storage_code  varchar(50) unique comment '出入仓库单编号',
#     order_code    varchar(50) comment '订单编号',
#     storage_id    tinyint                       not null comment '出入库id',
#     storage_type  tinyint                       not null comment '出入库类型',
#     storage_state tinyint                       not null comment '出入库状态',
#     storage_staff varchar(30)                   not null comment '出入库员',
#     remark        varchar(255) comment '备注',
#     create_time   datetime default current_date not
#                                                     null comment '出入库单创建时间',
#     modify_time   datetime default current_date not null comment '出入库单修改时间'
# ) comment '出入库表';
#
# create table if not exists good_storage
# (
#     id            int primary key auto_increment comment '商品库存表id',
#     good_id       int                           not null comment '商品类型id',
#     good_serial   varchar(30)                   not null comment '商品串号',
#     storage_id    tinyint                       not null comment '商品所在仓库',
#     good_business tinyint                       not null comment '商品业务',
#     good_state    tinyint                       not null comment '商品库存状态, 0-待入库,1-入库, 2-待出库,3出库',
#     create_time   datetime default current_date not null comment '创建时间',
#     modify_time   datetime default current_date not null comment '修改时间'
# );
#
# create table storage_good
# (
#     id              int primary key key auto_increment comment '商品出入库记录关联表id',
#     good_storage_id varchar(30) not null comment '商品库存id',
#     storage_code    varchar(30) not null comment '库存单编号'
# );
#
# create table inventory
# (
#     id             int primary key auto_increment comment '盘点记录',
#     inventory_code varchar(30) unique            not null comment '盘点记录编码',
#     storage_id     int                           not null comment '盘点仓库id',
#     storage_staff  varchar(30)                   not null comment '盘点员',
#     remark         varchar(255) comment '备注',
#     create_time    datetime default current_date not null comment '创建时间',
#     modify_time    datetime default current_date not null comment '修改时间'
# );
#
# create table if not exists inventory_good
# (
#     id             int primary key auto_increment comment '盘点id',
#     good_id        int         not null comment '盘点商品id',
#     inventory_code varchar(30) not null comment '关联盘点记录编码',
#     predict        int         not null comment '应有库存',
#     actual         int         not null comment '实际库存'
# );


insert into good_storage
values (default,1,'11111112',5,7,3,default,default),
 (default,1,'11111112',5,7,3,default,default),
 (default,2,'11111113',5,7,3,default,default),
 (default,3,'11111114',5,7,3,default,default),
 (default,4,'11111115',5,7,3,default,default),
 (default,1,'11111116',5,7,3,default,default),
 (default,2,'11111117',5,7,3,default,default),
 (default,3,'11111118',5,7,3,default,default),
 (default,4,'11111119',5,7,3,default,default),
 (default,1,'11111120',5,7,3,default,default),
 (default,2,'11111121',5,7,3,default,default),
 (default,3,'11111122',5,7,3,default,default),
 (default,4,'11111123',5,7,3,default,default),
 (default,1,'11111124',5,7,3,default,default),
 (default,2,'11111126',5,7,3,default,default);

