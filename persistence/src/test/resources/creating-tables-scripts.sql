CREATE TABLE gift_certificates
(
    id               bigserial    NOT NULL,
    name             varchar(255) NOT NULL,
    description      varchar(255),
    price            double precision,
    duration         serial,
    create_date      date         NOT NULL,
    last_update_date date         NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE tags
(
    id   bigserial    NOT NULL,
    name varchar(255) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (name)
);

CREATE UNIQUE INDEX name_case_insensitive_unique_index ON tags (LOWER(name));

CREATE TABLE gift_certificates_tags
(
    id                  BIGSERIAL,
    gift_certificate_id BIGINT NOT NULL REFERENCES gift_certificates (id),
    tag_id              BIGINT NOT NULL REFERENCES tags (id),
    UNIQUE (gift_certificate_id, tag_id)
);