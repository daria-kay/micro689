CREATE TABLE "user"
(
    "user_id"    serial      NOT NULL,
    "login"      varchar(20) NOT NULL UNIQUE,
    "pw"         varchar(20) NOT NULL,
    "partner_id" integer     NOT NULL,
    "block_flag" BOOLEAN     NOT NULL,
    CONSTRAINT "user_pk" PRIMARY KEY ("user_id")
);



CREATE TABLE "partner"
(
    "id"   serial       NOT NULL,
    "name" varchar(200) NOT NULL,
    CONSTRAINT "partner_pk" PRIMARY KEY ("id")
);



CREATE TABLE "passport_info_bl"
(
    "id"         serial     NOT NULL,
    "creator_id" integer    NOT NULL,
    "pass_ser"   varchar(4) NOT NULL,
    "pass_num"   varchar(6) NOT NULL,
    CONSTRAINT "passport_info_bl_pk" PRIMARY KEY ("id")
);



CREATE TABLE "personal_info_bl"
(
    "id"          serial       NOT NULL,
    "creator_id"  integer      NOT NULL,
    "first_name"  varchar(100) NOT NULL,
    "second_name" varchar(100) NOT NULL,
    "surname"     varchar(100) NOT NULL,
    "birth_date"  DATE         NOT NULL,
    CONSTRAINT "personal_info_bl_pk" PRIMARY KEY ("id")
);



CREATE TABLE "inn_bl"
(
    "id"         serial     NOT NULL,
    "creator_id" integer    NOT NULL,
    "inn_num"    varchar(6) NOT NULL,
    CONSTRAINT "inn_bl_pk" PRIMARY KEY ("id")
) WITH (
      OIDS= FALSE
    );



CREATE TABLE "phone_bl"
(
    "id"         serial      NOT NULL,
    "creator_id" integer     NOT NULL,
    "phone"      varchar(12) NOT NULL,
    CONSTRAINT "phone_bl_pk" PRIMARY KEY ("id")
);



CREATE TABLE "email_bl"
(
    "id"         serial       NOT NULL,
    "creator_id" integer      NOT NULL,
    "email"      varchar(100) NOT NULL,
    CONSTRAINT "email_bl_pk" PRIMARY KEY ("id")
);


ALTER TABLE "user"
    ADD CONSTRAINT "user_fk0" FOREIGN KEY ("partner_id") REFERENCES "partner" ("id");

ALTER TABLE "passport_info_bl"
    ADD CONSTRAINT "passport_info_bl_fk0" FOREIGN KEY ("creator_id") REFERENCES "user" ("user_id");

ALTER TABLE "personal_info_bl"
    ADD CONSTRAINT "personal_info_bl_fk0" FOREIGN KEY ("creator_id") REFERENCES "user" ("user_id");

ALTER TABLE "inn_bl"
    ADD CONSTRAINT "inn_bl_fk0" FOREIGN KEY ("creator_id") REFERENCES "user" ("user_id");

ALTER TABLE "phone_bl"
    ADD CONSTRAINT "phone_bl_fk0" FOREIGN KEY ("creator_id") REFERENCES "user" ("user_id");

ALTER TABLE "email_bl"
    ADD CONSTRAINT "email_bl_fk0" FOREIGN KEY ("creator_id") REFERENCES "user" ("user_id");
