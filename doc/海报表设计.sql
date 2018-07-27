
/* Drop Tables */

DROP TABLE CONSUMER_POSTER;
DROP TABLE POSTER_MATERIAL;




/* Create Tables */

CREATE TABLE CONSUMER_POSTER
(
	ID bigint NOT NULL,
	USER__ID bigint,
	-- 检测结果ID
	MED_ID bigint NOT NULL,
	-- 海报图片路径
	POSTER_IMG varbinary(64),
	CDTE datetime DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (ID)
);


CREATE TABLE POSTER_MATERIAL
(
	ID bigint NOT NULL,
	-- 体质ID
	PHYSICAL_ID bigint NOT NULL,
	-- 名称
	NAME varchar(64),
	-- 类型（男:1、女:0）
	SEX integer,
	-- 素材图片路径
	IMG_URL varchar(64),
	-- 状态： 0 - 启用， 1 - 禁用， 2 - 删除
	STATUS bigint DEFAULT 0,
	-- 二维码X轴坐标
	X_QR bigint,
	-- 二维码Y轴坐标
	Y_QR bigint,
	WIDE_QR bigint,
	HIGH_QR bigint,
	X_STAR bigint,
	Y_STAR bigint,
	WIDE_STAR bigint,
	HIGH_STAR bigint,
	-- 操作人ID
	OPERATOR_ID bigint,
	-- 修改时间
	MDATE datetime DEFAULT CURRENT_TIMESTAMP,
	-- 创建时间
	CDATE datetime DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (ID)
);



