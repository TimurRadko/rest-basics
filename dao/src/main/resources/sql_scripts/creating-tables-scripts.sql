----------------CREATE TABLE GIFT-CERTIFICATES------------
CREATE TABLE gift_certificates_task_dev.gift_certificates
(
    id               SERIAL       NOT NULL,
    name             varchar(255) NOT NULL,
    description      varchar(255),
    price            double precision,
    duration         integer,
    create_date      timestamp    NOT NULL,
    last_update_date timestamp    NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE gift_certificates_task_dev.gift_certificates
    OWNER to postgres;
---------------------------------------------------------


----------------CREATE TABLE TAGS------------------------
CREATE TABLE gift_certificates_task_dev.tags
(
    id   SERIAL       NOT NULL,
    name varchar(255) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (name)
);

ALTER TABLE gift_certificates_task_dev.tags
    OWNER to postgres;
-------------------------------------------------------


------------CREATING UNIQUE INDEX--------------------------
CREATE UNIQUE INDEX gift_certificates_task_dev.name_case_insensitive_unique_index ON tags (LOWER(name));
-----------------------------------------------------------


------------CREATE TABLE GIFT-CERTIFICATES-TAGS------------
CREATE TABLE gift_certificates_task_dev.gift_certificates_tags
(
    id                  SERIAL,
    gift_certificate_id BIGINT NOT NULL REFERENCES gift_certificates (id),
    tag_id              BIGINT NOT NULL REFERENCES tags (id),
    UNIQUE (gift_certificate_id, tag_id),
    PRIMARY KEY (id)
);
------------------------------------------------------------


-------------CREATE TABLE USERS-----------------------------
CREATE TABLE gift_certificates_task_dev.users
(
    id       SERIAL       NOT NULL,
    login    varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    account  double precision,
    PRIMARY KEY (id),
    UNIQUE (login)
);

ALTER TABLE gift_certificates_task_dev.users
    OWNER to postgres;
--------------------------------------------------------------

-------------CREATE TABLE ORDERS-----------------------------
CREATE TABLE gift_certificates_task_dev.orders
(
    id         SERIAL    NOT NULL,
    user_id    BIGINT    NOT NULL REFERENCES users (id),
    cost       double precision,
    order_date timestamp NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE gift_certificates_task_dev.orders
    OWNER to postgres;
-----------------------------------------------------------------

------------CREATE TABLE ORDERS-GIFT-CERTIFICATES------------
CREATE TABLE gift_certificates_task_dev.orders_gift_certificates
(
    id                  SERIAL,
    order_id            BIGINT NOT NULL REFERENCES orders (id),
    gift_certificate_id BIGINT NOT NULL REFERENCES gift_certificates (id),
    PRIMARY KEY (id)
);
------------------------------------------------------------

------------CREATE TABLE AUDIT-HISTORY-OPERATIONS------------------------
CREATE TABLE gift_certificates_task_dev.audit_history_operations
(
    id          SERIAL,
    action      varchar(255) NOT NULL,
    content     varchar(255) NOT NULL,
    create_date timestamp    NOT NULL,
    PRIMARY KEY (id)
);
---------------------------------------------------------------------------