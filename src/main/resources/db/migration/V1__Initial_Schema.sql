/**
  - Schemas to create the initial data model of the QBank database.
  - TODO: Add the foreign key constraints at a later stage
 */

CREATE TABLE IF NOT EXISTS product (

  `id`                        BIGINT(20)           NOT NULL AUTO_INCREMENT,
  `created`                   TIMESTAMP            DEFAULT CURRENT_TIMESTAMP,
  `updated`                   TIMESTAMP            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `uuid`                      VARCHAR(36)          NOT NULL,
  `name`                      VARCHAR(255)         NOT NULL,

  PRIMARY KEY (`id`)
);

create unique index `uk0_product_uuid`
  on product (`uuid`);

create unique index `uk1_product_name`
  on product (`name`);


CREATE TABLE IF NOT EXISTS category (

   `id`                        BIGINT(20)           NOT NULL AUTO_INCREMENT,
   `created`                   TIMESTAMP            DEFAULT CURRENT_TIMESTAMP,
   `updated`                   TIMESTAMP            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   `uuid`                      VARCHAR(36)          NOT NULL,
   `name`                      VARCHAR(255)         NOT NULL,
   `subject_id`                BIGINT(20)           NOT NULL,

   PRIMARY KEY (`id`)
);

create unique index `uk0_category_uuid`
  on category (`uuid`);

create unique index `uk1_category_name`
  on category (`name`);

create index `idx01_category_sid`
  on category (`subject_id`);


CREATE TABLE IF NOT EXISTS answer_choices (

  `id`                        BIGINT(20)           NOT NULL AUTO_INCREMENT,
  `created`                   TIMESTAMP            DEFAULT CURRENT_TIMESTAMP,
  `updated`                   TIMESTAMP            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `choice`                    VARCHAR(255)         NOT NULL,
  `correct`                   BIT(1)               NOT NULL,
  `question_id`               BIGINT(20)           NOT NULL,

  PRIMARY KEY (`id`)
);

create index `idx01_answer_choices_qid`
  on answer_choices (`question_id`);


CREATE TABLE IF NOT EXISTS key_points (

  `id`                        BIGINT(20)           NOT NULL AUTO_INCREMENT,
  `created`                   TIMESTAMP            DEFAULT CURRENT_TIMESTAMP,
  `updated`                   TIMESTAMP            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `key_point`                 VARCHAR(255)         NOT NULL,
  `question_id`               BIGINT(20)           NOT NULL,

  PRIMARY KEY (`id`)
);

create index `idx01_key_points_qid`
  on key_points (`question_id`);


CREATE TABLE IF NOT EXISTS reference (

  `id`                        BIGINT(20)           NOT NULL AUTO_INCREMENT,
  `created`                   TIMESTAMP            DEFAULT CURRENT_TIMESTAMP,
  `updated`                   TIMESTAMP            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `reference`                 VARCHAR(255)         NOT NULL,
  `question_id`               BIGINT(20)           NOT NULL,

  PRIMARY KEY (`id`)
);

create index `idx01_reference_qid`
  on reference (`question_id`);


CREATE TABLE IF NOT EXISTS reinforcement (

  `id`                        BIGINT(20)           NOT NULL AUTO_INCREMENT,
  `created`                   TIMESTAMP            DEFAULT CURRENT_TIMESTAMP,
  `updated`                   TIMESTAMP            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `question`                  VARCHAR(255)         NOT NULL,
  `answer`                    VARCHAR(255)         NOT NULL,
  `question_id`               BIGINT(20)           NOT NULL,

  PRIMARY KEY (`id`)
);

create index `idx01_reinforcement_qid`
  on reinforcement (`question_id`);


CREATE TABLE IF NOT EXISTS questions (

  `id`                        BIGINT(20)           NOT NULL AUTO_INCREMENT,
  `created`                   TIMESTAMP            DEFAULT CURRENT_TIMESTAMP,
  `updated`                   TIMESTAMP            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `uuid`                      VARCHAR(36)          NOT NULL,
  `product_id`                BIGINT(20)           NOT NULL,
  `subject_id`                BIGINT(20)           NOT NULL,
  `category_id`               BIGINT(20)           NOT NULL,
  `learning_objective`        VARCHAR(255)         NOT NULL,
  `question_stem`             VARCHAR(255)         NOT NULL,
  `explanation`               VARCHAR(255)         NOT NULL,

  PRIMARY KEY (`id`)
);

create unique index `uk0_questions_uuid`
  on questions (`uuid`);
