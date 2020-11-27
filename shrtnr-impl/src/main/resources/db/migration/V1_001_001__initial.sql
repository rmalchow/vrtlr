CREATE TABLE `shrtnd` (
  `id` varchar(10),
  `url` varchar(2048),
  `title` varchar(2048),
  `description`  mediumtext,
  `image`  mediumtext,
  `hash` varchar(512),
  PRIMARY KEY (`id`),
  KEY `IDX_IDENTIY_KEY` (`id`)
)
