----------------DROP DATABASE----------------------------
DROP DATABASE gift_certificates_task;
---------------------------------------------------------


----------------CREATE TABLE GIFT-CERTIFICATES------------
CREATE TABLE gift_certificates
(
    id              bigserial           NOT NULL,
    name            character(255)      NOT NULL,
    description     character(255),
    price           double precision,
    duration        serial,
    create_date     date NOT NULL,
    last_update_date date NOT NULL,
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
    id   bigserial NOT NULL,
    name character NOT NULL,
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
create table gift_certificates_tags
(
    id                   BIGSERIAL,
    gift_certificates_id BIGINT NOT NULL REFERENCES gift_certificates (id),
    tags_id              BIGINT NOT NULL REFERENCES tags (id),
    UNIQUE (gift_certificates_id, tags_id)
);
------------------------------------------------------------

DROP TABLE gift_certificates_tags;