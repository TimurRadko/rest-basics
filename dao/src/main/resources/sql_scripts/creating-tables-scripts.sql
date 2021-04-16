----------------CREATE TABLE GIFT-CERTIFICATES------------
CREATE TABLE gift_certificates
(
    id               bigserial    NOT NULL,
    name             varchar(255) NOT NULL,
    description      varchar(255),
    price            double precision,
    duration         integer,
    create_date      timestamp    NOT NULL,
    last_update_date timestamp    NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE gift_certificates
    OWNER to postgres;
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


-------------CREATE TABLE USERS-----------------------------
CREATE TABLE users
(
    id       bigserial    NOT NULL,
    login    varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    account  double precision
);

ALTER TABLE users
    OWNER to postgres;
-----------------------------------------------------------
