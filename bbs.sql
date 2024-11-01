CREATE SCHEMA `advanced_bbs`;

CREATE TABLE `advanced_bbs`.`boards`
(
    `id`   VARCHAR(10) NOT NULL,
    `text` VARCHAR(25) NOT NULL,
    CONSTRAINT PRIMARY KEY (`id`)
);

CREATE TABLE `advanced_bbs`.`articles`
(
    `index`      INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `board_id`   VARCHAR(10)  NOT NULL,
    `nickname`   VARCHAR(10)  NOT NULL,
    `password`   VARCHAR(128) NOT NULL,
    `title`      VARCHAR(100) NOT NULL,
    `content`    MEDIUMTEXT   NOT NULL,
    `view`       INT UNSIGNED NOT NULL DEFAULT 0,
    `created_at` DATETIME     NOT NULL DEFAULT NOW(),
    `updated_at` DATETIME     NULL     DEFAULT NULL,
    `deleted_at` DATETIME     NULL     DEFAULT NULL,
    CONSTRAINT PRIMARY KEY (`index`),
    CONSTRAINT FOREIGN KEY (`board_id`) REFERENCES `advanced_bbs`.`boards` (`id`)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

DROP TABLE `advanced_bbs`.`articles`;
