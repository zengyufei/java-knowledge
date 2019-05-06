-- 默认的数据库查询SQL
-- SELECT KEY, VALUE from PROPERTIES where APPLICATION=? and PROFILE=? and LABEL=?

DROP TABLE IF EXISTS properties;
CREATE TABLE `properties` (
  `id`          int(11) NOT NULL,
  `key`         varchar(50)  DEFAULT NULL,
  `value`       varchar(500) DEFAULT NULL,
  `application` varchar(50)  DEFAULT NULL,
  `profile`     varchar(50)  DEFAULT NULL,
  `label`       varchar(50)  DEFAULT NULL
)