CREATE TABLE `account`
(
    `uuid`     varchar(255) NOT NULL,
    `name`     varchar(20)  NOT NULL,
    `id`       tinytext     NOT NULL,
    `pw`       tinytext     NOT NULL,
    `birth`    date         NOT NULL,
    `email`    tinytext     NOT NULL,
    `tel`      varchar(12)  NOT NULL,
    `gender`   tinyint(1)   NOT NULL,
    `address1` tinytext     NOT NULL,
    `address2` tinytext     NULL
);

CREATE TABLE `oAuth`
(
    `oauth_key` varchar(255) NOT NULL,
    `uuid`      varchar(255) NOT NULL
);

CREATE TABLE `company`
(
    `companyId` varchar(255) NOT NULL,
    `id`        varchar(15)  NOT NULL,
    `name`      tinytext     NOT NULL,
    `admin`     varchar(10)  NOT NULL,
    `email`     tinytext     NOT NULL,
    `address1`  tinytext     NOT NULL,
    `address2`  tinytext     NULL,
    `tel`       varchar(12)  NOT NULL
);

CREATE TABLE `lecture`
(
    `lecture_id` Int          NOT NULL,
    `name`       tinytext     NOT NULL,
    `created_at` Date         NOT NULL,
    `closed_at`  Date         NOT NULL,
    `category`   Int          NOT NULL,
    `companyId`  varchar(255) NOT NULL
);

CREATE TABLE `lecture_list`
(
    `listId`     Int          NOT NULL,
    `uuid`       varchar(255) NOT NULL,
    `lecture_id` Int          NOT NULL
);

CREATE TABLE `attendance`
(
    `attendanceId` Int          NOT NULL,
    `uuid`         varchar(255) NOT NULL
);

CREATE TABLE `exam`
(
    `uuid`       varchar(36) NOT NULL,
    `lecture_id` Int         NOT NULL,
    `data`       text        NOT NULL
);

CREATE TABLE `rating`
(
    `id`         Int          NOT NULL,
    `lecture_id` Int          NOT NULL,
    `uuid`       varchar(255) NOT NULL
);

CREATE TABLE `survey`
(
    `id`         Int  NOT NULL,
    `lecture_id` Int  NOT NULL,
    `created_at` date NOT NULL,
    `updated_at` date NOT NULL
);

CREATE TABLE `category`
(
    `category_id` Int      NOT NULL,
    `name`        tinytext NOT NULL
);

CREATE TABLE `sub_category`
(
    `category`    Int      NOT NULL,
    `category_id` Int      NOT NULL,
    `name`        tinytext NOT NULL
);

CREATE TABLE `lecture_content`
(
    `id`          Int      NOT NULL,
    `lecture_id`  Int      NOT NULL,
    `name`        tinytext NOT NULL,
    `description` tinytext NOT NULL,
    `url`         tinytext NOT NULL
);

CREATE TABLE `company_list`
(
    `relationId` varchar(255) NOT NULL,
    `uuid`       varchar(255) NOT NULL,
    `companyId`  varchar(255) NOT NULL
);

CREATE TABLE `survey_content`
(
    `content_id` Int          NOT NULL,
    `id`         Int          NOT NULL,
    `content`    varchar(255) NOT NULL
);

ALTER TABLE `account`
    ADD CONSTRAINT `PK_ACCOUNT` PRIMARY KEY (
                                             `uuid`
        );

ALTER TABLE `oAuth`
    ADD CONSTRAINT `PK_OAUTH` PRIMARY KEY (
                                           `oauth_key`
        );

ALTER TABLE `company`
    ADD CONSTRAINT `PK_COMPANY` PRIMARY KEY (
                                             `companyId`
        );

ALTER TABLE `lecture`
    ADD CONSTRAINT `PK_LECTURE` PRIMARY KEY (
                                             `lecture_id`
        );

ALTER TABLE `lecture_list`
    ADD CONSTRAINT `PK_LECTURE_LIST` PRIMARY KEY (
                                                  `listId`
        );

ALTER TABLE `attendance`
    ADD CONSTRAINT `PK_ATTENDANCE` PRIMARY KEY (
                                                `attendanceId`
        );

ALTER TABLE `exam`
    ADD CONSTRAINT `PK_EXAM` PRIMARY KEY (
                                          `uuid`
        );

ALTER TABLE `rating`
    ADD CONSTRAINT `PK_RATING` PRIMARY KEY (
                                            `id`
        );

ALTER TABLE `survey`
    ADD CONSTRAINT `PK_SURVEY` PRIMARY KEY (
                                            `id`
        );

ALTER TABLE `category`
    ADD CONSTRAINT `PK_CATEGORY` PRIMARY KEY (
                                              `category_id`
        );

ALTER TABLE `sub_category`
    ADD CONSTRAINT `PK_SUB_CATEGORY` PRIMARY KEY (
                                                  `category`
        );

ALTER TABLE `lecture_content`
    ADD CONSTRAINT `PK_LECTURE_CONTENT` PRIMARY KEY (
                                                     `id`
        );

ALTER TABLE `company_list`
    ADD CONSTRAINT `PK_COMPANY_LIST` PRIMARY KEY (
                                                  `relationId`
        );

ALTER TABLE `survey_content`
    ADD CONSTRAINT `PK_SURVEY_CONTENT` PRIMARY KEY (
                                                    `content_id`
        );

ALTER TABLE `oAuth`
    ADD CONSTRAINT `FK_account_TO_oAuth_1` FOREIGN KEY (
                                                        `uuid`
        )
        REFERENCES `account` (
                              `uuid`
            );

ALTER TABLE `lecture`
    ADD CONSTRAINT `FK_sub_category_TO_lecture_1` FOREIGN KEY (
                                                               `category`
        )
        REFERENCES `sub_category` (
                                   `category`
            );

ALTER TABLE `lecture`
    ADD CONSTRAINT `FK_company_TO_lecture_1` FOREIGN KEY (
                                                          `companyId`
        )
        REFERENCES `company` (
                              `companyId`
            );

ALTER TABLE `lecture_list`
    ADD CONSTRAINT `FK_account_TO_lecture_list_1` FOREIGN KEY (
                                                               `uuid`
        )
        REFERENCES `account` (
                              `uuid`
            );

ALTER TABLE `lecture_list`
    ADD CONSTRAINT `FK_lecture_TO_lecture_list_1` FOREIGN KEY (
                                                               `lecture_id`
        )
        REFERENCES `lecture` (
                              `lecture_id`
            );

ALTER TABLE `attendance`
    ADD CONSTRAINT `FK_account_TO_attendance_1` FOREIGN KEY (
                                                             `uuid`
        )
        REFERENCES `account` (
                              `uuid`
            );

ALTER TABLE `exam`
    ADD CONSTRAINT `FK_lecture_TO_exam_1` FOREIGN KEY (
                                                       `lecture_id`
        )
        REFERENCES `lecture` (
                              `lecture_id`
            );

ALTER TABLE `rating`
    ADD CONSTRAINT `FK_lecture_TO_rating_1` FOREIGN KEY (
                                                         `lecture_id`
        )
        REFERENCES `lecture` (
                              `lecture_id`
            );

ALTER TABLE `rating`
    ADD CONSTRAINT `FK_account_TO_rating_1` FOREIGN KEY (
                                                         `uuid`
        )
        REFERENCES `account` (
                              `uuid`
            );

ALTER TABLE `survey`
    ADD CONSTRAINT `FK_lecture_TO_survey_1` FOREIGN KEY (
                                                         `lecture_id`
        )
        REFERENCES `lecture` (
                              `lecture_id`
            );

ALTER TABLE `sub_category`
    ADD CONSTRAINT `FK_category_TO_sub_category_1` FOREIGN KEY (
                                                                `category_id`
        )
        REFERENCES `category` (
                               `category_id`
            );

ALTER TABLE `lecture_content`
    ADD CONSTRAINT `FK_lecture_TO_lecture_content_1` FOREIGN KEY (
                                                                  `lecture_id`
        )
        REFERENCES `lecture` (
                              `lecture_id`
            );

ALTER TABLE `company_list`
    ADD CONSTRAINT `FK_account_TO_company_list_1` FOREIGN KEY (
                                                               `uuid`
        )
        REFERENCES `account` (
                              `uuid`
            );

ALTER TABLE `company_list`
    ADD CONSTRAINT `FK_company_TO_company_list_1` FOREIGN KEY (
                                                               `companyId`
        )
        REFERENCES `company` (
                              `companyId`
            );

ALTER TABLE `survey_content`
    ADD CONSTRAINT `FK_survey_TO_survey_content_1` FOREIGN KEY (
                                                                `id`
        )
        REFERENCES `survey` (
                             `id`
            );

