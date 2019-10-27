CREATE TABLE "user"
(
    "id"    serial      NOT NULL,
    "login"      varchar(20) NOT NULL UNIQUE,
    "pw"         varchar(20) NOT NULL,
    "partner_id" integer     NOT NULL,
    "block_flag" BOOLEAN     NOT NULL,
    CONSTRAINT "user_pk" PRIMARY KEY ("id")
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
    "pass_ser"   varchar(4) NOT NULL,
    "pass_num"   varchar(6) NOT NULL,
    CONSTRAINT "passport_info_bl_pk" PRIMARY KEY ("id")
);



CREATE TABLE "personal_info_bl"
(
    "id"          serial       NOT NULL,
    "first_name"  varchar(100) NOT NULL,
    "second_name" varchar(100) NOT NULL,
    "surname"     varchar(100) NOT NULL,
    "birth_date"  DATE         NOT NULL,
    CONSTRAINT "personal_info_bl_pk" PRIMARY KEY ("id")
);



CREATE TABLE "inn_bl"
(
    "id"         serial     NOT NULL,
    "inn_num"    varchar(6) NOT NULL,
    CONSTRAINT "inn_bl_pk" PRIMARY KEY ("id")
) WITH (
      OIDS= FALSE
    );



CREATE TABLE "phone_bl"
(
    "id"         serial      NOT NULL,
    "phone"      varchar(12) NOT NULL,
    CONSTRAINT "phone_bl_pk" PRIMARY KEY ("id")
);



CREATE TABLE "email_bl"
(
    "id"         serial       NOT NULL,
            "email"      varchar(100) NOT NULL,
    CONSTRAINT "email_bl_pk" PRIMARY KEY ("id")
);

CREATE TABLE "record" (
                          "id" serial NOT NULL,
                          "creator_id" integer NOT NULL,
                          "personal_id" integer,
                          "passport_id" integer,
                          "inn_id" integer,
                          "phone_id" integer,
                          "email_id" integer,
                          CONSTRAINT "record_pk" PRIMARY KEY ("id")
);

ALTER TABLE "record" ADD CONSTRAINT "record_fk0" FOREIGN KEY ("creator_id") REFERENCES "user"("id");
ALTER TABLE "record" ADD CONSTRAINT "record_fk1" FOREIGN KEY ("personal_id") REFERENCES "personal_info_bl"("id");
ALTER TABLE "record" ADD CONSTRAINT "record_fk2" FOREIGN KEY ("passport_id") REFERENCES "passport_info_bl"("id");
ALTER TABLE "record" ADD CONSTRAINT "record_fk3" FOREIGN KEY ("inn_id") REFERENCES "inn_bl"("id");
ALTER TABLE "record" ADD CONSTRAINT "record_fk4" FOREIGN KEY ("phone_id") REFERENCES "phone_bl"("id");
ALTER TABLE "record" ADD CONSTRAINT "record_fk5" FOREIGN KEY ("email_id") REFERENCES "email_bl"("id");


ALTER TABLE "user"
    ADD CONSTRAINT "user_fk0" FOREIGN KEY ("partner_id") REFERENCES "partner" ("id");
