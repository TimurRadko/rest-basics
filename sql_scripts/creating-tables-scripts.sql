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
    create_date      date         NOT NULL,
    last_update_date date         NOT NULL,
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

-------------------STORED FUNCTION SEARCHING FOR PART NAME OR DESCRIPTION---------------
CREATE OR REPLACE FUNCTION get_gifts_by_parts(part_name VARCHAR(255) DEFAULT '',
                                              part_description VARCHAR(255) DEFAULT '',
                                              sort VARCHAR(255) DEFAULT 'id')
RETURNS SETOF gift_certificates AS $$
BEGIN

    CASE

        WHEN ((part_name NOT ILIKE '') AND (part_name IS NOT NULL) AND (part_description NOT ILIKE '')
        AND (part_description IS NOT NULL))
        THEN
            RETURN QUERY
                SELECT gc.id, gc.name, gc.description, gc.price, gc.duration, gc.create_date, gc.last_update_date
                FROM gift_certificates gc
                WHERE (gc.name LIKE concat('%', part_name, '%'))
                  AND (gc.description LIKE concat('%', part_description, '%'))
                ORDER BY CASE WHEN sort = 'name-asc' THEN gc.name END ASC,
                         CASE WHEN sort = 'name-desc' THEN gc.name END DESC,
                         CASE WHEN sort = 'description-asc' THEN gc.description END,
                         CASE WHEN sort = 'description-desc' THEN gc.description END DESC,
                         CASE WHEN sort = 'last-modified-desc' THEN TO_CHAR(gc.last_update_date, 'YYYY-MM-DD') END DESC,
                         CASE WHEN sort = 'creation-date-desc' THEN TO_CHAR(gc.create_date, 'YYYY-MM-DD') END DESC,
                         gc.id ASC;

        WHEN ((part_name NOT ILIKE '') AND (part_name IS NOT NULL))
            THEN
                RETURN QUERY
                    SELECT gc.id, gc.name, gc.description, gc.price, gc.duration, gc.create_date, gc.last_update_date
                    FROM gift_certificates gc
                    WHERE gc.name LIKE concat('%', part_name, '%')
                    ORDER BY CASE WHEN sort = 'name-asc' THEN gc.name END ASC,
                             CASE WHEN sort = 'name-desc' THEN gc.name END DESC,
                             CASE WHEN sort = 'description-asc' THEN gc.description END ASC,
                             CASE WHEN sort = 'description-desc' THEN gc.description END DESC,
                             CASE
                                 WHEN sort = 'last-update-date-desc'
                                     THEN TO_CHAR(gc.last_update_date, 'YYYY-MM-DD') END DESC,
                             CASE
                                 WHEN sort = 'create-date-desc' THEN TO_CHAR(gc.create_date, 'YYYY-MM-DD') END DESC,
                             gc.id ASC;

        WHEN ((part_description NOT ILIKE '') AND (part_description IS NOT NULL))
            THEN
                RETURN QUERY
                    SELECT gc.id, gc.name, gc.description, gc.price, gc.duration, gc.create_date, gc.last_update_date
                    FROM gift_certificates gc
                    WHERE gc.description LIKE concat('%', part_description, '%')
                    ORDER BY CASE WHEN sort = 'name-asc' THEN gc.name END ASC,
                             CASE WHEN sort = 'name-desc' THEN gc.name END DESC,
                             CASE WHEN sort = 'description-asc' THEN gc.description END ASC,
                             CASE WHEN sort = 'description-desc' THEN gc.description END DESC,
                             CASE
                                 WHEN sort = 'last-update-date-desc'
                                     THEN TO_CHAR(gc.last_update_date, 'YYYY-MM-DD') END DESC,
                             CASE
                                 WHEN sort = 'create-date-desc'
                                     THEN TO_CHAR(gc.create_date, 'YYYY-MM-DD') END DESC,
                             gc.id ASC;

        ELSE RAISE EXCEPTION 'Wasn''t found any Gift Certificate';

        END CASE;
END;

$$ language 'plpgsql';
--------------------------------------------------------------------------------------------------------------------