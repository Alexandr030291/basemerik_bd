CREATE TABLE `Info`(
  `ip` INT,
  `cpu_busy` BIGINT,
  `cpu_work` BIGINT,
  `mem_free` BIGINT,
  `mem_total` BIGINT,
  `net_receive` BIGINT,
  `net_transmit` BIGINT,
  `time` BIGINT,
  `rps` BIGINT
);

CREATE TABLE `NameInfo`(
  `name` VARCHAR(50),
  `ip` INT UNIQUE KEY
);

CREATE INDEX `ip_time` ON `Info`(`ip`,`time`);