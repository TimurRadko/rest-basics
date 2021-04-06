----------------DROP DATABASE----------------------------
DROP DATABASE gift_certificates_task;
---------------------------------------------------------


----------------CREATE TABLE GIFT-CERTIFICATES------------
CREATE TABLE gift_certificates
(
    id               bigserial    NOT NULL,
    name             varchar(255) NOT NULL,
    description      varchar(255),
    price            double precision,
    duration         serial,
    create_date      timestamp         NOT NULL,
    last_update_date timestamp        NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE gift_certificates
    OWNER to postgres;
---------------------------------------------------------


---------------DROP TABLE GIFT-CERTIFICATES--------------
DROP TABLE gift_certificates;
---------------------------------------------------------


----------------CREATE TABLE TAGS------------------------
CREATE TABLE tags
(
    id   bigserial    NOT NULL,
    name varchar(255) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (name)
);

ALTER TABLE tags
    OWNER to postgres;
-------------------------------------------------------


----------------DROP TABLE TAGS------------------------
DROP TABLE tags;
-------------------------------------------------------


------------CREATING UNIQUE INDEX--------------------------
CREATE UNIQUE INDEX name_case_insensitive_unique_index ON tags (LOWER(name));
-----------------------------------------------------------


------------CREATE TABLE CONNECTING TWO OTHERS------------
CREATE TABLE gift_certificates_tags
(
    id                  BIGSERIAL,
    gift_certificate_id BIGINT NOT NULL REFERENCES gift_certificates (id),
    tag_id              BIGINT NOT NULL REFERENCES tags (id),
    UNIQUE (gift_certificate_id, tag_id)
);
------------------------------------------------------------


DROP TABLE gift_certificates_tags;