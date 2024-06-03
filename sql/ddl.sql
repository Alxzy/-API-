-- 创建库
create database if not exists openapi;

-- 切换库
use openapi;

-- 用户表
create table if not exists user
(
    id           bigint auto_increment comment 'id' primary key,
    userName     varchar(256)                           null comment '用户昵称',
    userAccount  varchar(256)                           not null comment '账号',
    userAvatar   varchar(1024)                          null comment '用户头像',
    gender       tinyint                                null comment '性别',
    userRole     varchar(256) default 'user'            not null comment '用户角色：user / admin',
    userPassword varchar(512)                           not null comment '密码',
    accessKey    varchar(512)                           not null comment 'accessKey',
    secretKey    varchar(512)                           not null comment 'secretKey',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除',
    constraint uni_userAccount
        unique (userAccount)
) comment '用户';

-- 帖子表
create table if not exists post
(
    id            bigint auto_increment comment 'id' primary key,
    age           int comment '年龄',
    gender        tinyint  default 0                 not null comment '性别（0-男, 1-女）',
    education     varchar(512)                       null comment '学历',
    place         varchar(512)                       null comment '地点',
    job           varchar(512)                       null comment '职业',
    contact       varchar(512)                       null comment '联系方式',
    loveExp       varchar(512)                       null comment '感情经历',
    content       text                               null comment '内容（个人介绍）',
    photo         varchar(1024)                      null comment '照片地址',
    reviewStatus  int      default 0                 not null comment '状态（0-待审核, 1-通过, 2-拒绝）',
    reviewMessage varchar(512)                       null comment '审核信息',
    viewNum       int                                not null default 0 comment '浏览数',
    thumbNum      int                                not null default 0 comment '点赞数',
    userId        bigint                             not null comment '创建用户 id',
    createTime    datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime    datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete      tinyint  default 0                 not null comment '是否删除'
) comment '帖子';

-- 接口信息表
create table if not exists openapi.`interface_info`
(
    `id`             bigint                               not null auto_increment comment '主键' primary key,
    `name`           varchar(256)                         not null comment '名称',
    `createTime`     datetime default 'CURRENT_TIMESTAMP' not null comment '创建时间',
    `updateTime`     datetime default 'CURRENT_TIMESTAMP' not null on update CURRENT_TIMESTAMP comment '更新时间',
    `isDeleted`      tinyint  default 0                   not null comment '是否删除(0-未删, 1-已删)',
    `description`    varchar(256)                         null comment '描述',
    `requestparams`  text                                 null comment '请求参数',
    `url`            varchar(512)                         not null comment '接口地址',
    `requestHeader`  text                                 null comment '请求头',
    `responseHeader` text                                 null comment '响应头',
    `userId`         bigint                               not null comment '创建用户id',
    `status`         int      default 0                   not null comment '接口状态（0 - 关闭，1 - 开启）',
    `method`         varchar(256)                         not null comment '请求类型'
) comment '接口信息表';

insert into openapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `userId`,
                                      `status`, `method`)
values ('tUFhZ', 'GL', 'www.magnolia-greenfelder.biz', '9w', 'Dsw9i', 9895, 0, 'li');
insert into openapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `userId`,
                                      `status`, `method`)
values ('u2P9m', 'OAXT4', 'www.lenard-mraz.co', 'cj', '2TuC', 407541608, 0, 'qs');
insert into openapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `userId`,
                                      `status`, `method`)
values ('jYTiK', 'XJ7r', 'www.daryl-klein.name', 'jac', 'uh', 1423, 0, 'KH');
insert into openapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `userId`,
                                      `status`, `method`)
values ('1Gc0', 'JEW6D', 'www.everett-schaden.info', 'YWR', '7l6c', 53636614, 0, 'GsIy');
insert into openapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `userId`,
                                      `status`, `method`)
values ('xnLIC', 'St', 'www.brett-denesik.info', 'LE3d', 'IjY', 4377132, 0, 'a3U6R');
insert into openapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `userId`,
                                      `status`, `method`)
values ('C3j', 'PHY', 'www.matthew-osinski.com', 'ivsD', '2mR0', 30837, 0, '7Aci');
insert into openapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `userId`,
                                      `status`, `method`)
values ('Xp', 'e09', 'www.dawne-reinger.info', 'k7G8E', '2n', 6, 0, 'KxhG');
insert into openapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `userId`,
                                      `status`, `method`)
values ('Dy', 'HkZWT', 'www.conrad-bahringer.org', 'zxSl', 'pY7yZ', 532254294, 0, '5hSIp');
insert into openapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `userId`,
                                      `status`, `method`)
values ('Cm29H', 'Ai', 'www.stevie-hilpert.co', 'yE', 'Xr', 52455, 0, '1SnB');
insert into openapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `userId`,
                                      `status`, `method`)
values ('wti', 'hG7', 'www.wilfredo-goodwin.name', 'Mp4', 'B0b8', 58185868, 0, 'E4jlb');
insert into openapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `userId`,
                                      `status`, `method`)
values ('24', 'bO7W5', 'www.loni-dubuque.net', 'zpjd', 'l4qLn', 36059, 0, 'sH');
insert into openapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `userId`,
                                      `status`, `method`)
values ('bCy', 'GJZp', 'www.jules-upton.biz', 'cD', 'OO', 1841, 0, 'RnDop');
insert into openapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `userId`,
                                      `status`, `method`)
values ('jE', 'Ua', 'www.willy-macejkovic.io', 'JR', 'Y0V', 292486834, 0, 'Fbm');
insert into openapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `userId`,
                                      `status`, `method`)
values ('KUzp', '3V9EN', 'www.frieda-dicki.io', '5eKJI', 'XiLm', 2714905551, 0, 'lsDhK');
insert into openapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `userId`,
                                      `status`, `method`)
values ('pzrM', '9w', 'www.kelvin-lind.name', 'hQ0HA', 'd9Nqd', 8, 0, 'IsV');
insert into openapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `userId`,
                                      `status`, `method`)
values ('qmX', 'ApCWn', 'www.margarett-konopelski.co', 'HoO61', '2Mk', 28731, 0, '0DN4r');
insert into openapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `userId`,
                                      `status`, `method`)
values ('Lls', 'kNz', 'www.grant-wisozk.info', '0J', 'oV', 668, 0, '0L');
insert into openapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `userId`,
                                      `status`, `method`)
values ('dznf', 'QWOX', 'www.gaston-donnelly.net', '0xr', '5YSc', 702, 0, '0Ll');
insert into openapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `userId`,
                                      `status`, `method`)
values ('hHV0', 's0dx', 'www.elia-braun.org', 'qQk', 'ZJg0', 8015466296, 0, '1x');
insert into openapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `userId`,
                                      `status`, `method`)
values ('is', '0Aa', 'www.eddie-stroman.co', 'A15d', 'K1R', 5463, 0, 'hzhZi');