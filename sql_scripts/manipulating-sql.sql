---------------------------get tags list by gift id-------------------------

SELECT t.id, t.name
FROM tags t
         INNER JOIN gift_certificates_tags gct
                    ON t.id = gct.tag_id
WHERE gct.gift_certificate_id = 1;
----------------------------------------------------------------------------

-------------------------get gift certificates by tag name-------------------
SELECT gc.id,
       gc.name,
       gc.description,
       gc.price,
       gc.duration,
       gc.create_date,
       gc.last_update_date
FROM gift_certificates gc
         INNER JOIN gift_certificates_tags gct ON gc.id = gct.gift_certificate_id
         INNER JOIN tags t ON t.id = gct.tag_id
WHERE t.name = 'tag2';
----------------------------------------------------------------------------

SELECT tag_id FROM gift_certificates_tags WHERE gift_certificate_id=1;

SELECT gct.tag_id, t.name FROM gift_certificates_tags gct
INNER JOIN tags t ON t.id = gct.tag_id WHERE gct.gift_certificate_id = 2 AND gct.tag_id = 4;

SELECT * FROM gift_certificates_tags WHERE tag_id=1;

SELECT * FROM gift_certificates WHERE id=13;

SELECT gift_certificates.id   AS certificate_id,
       gift_certificates.name AS certificate_name,
       gift_certificates.description,
       gift_certificates.price,
       gift_certificates.duration,
       gift_certificates.create_date,
       gift_certificates.last_update_date,
       tags.id           AS tag_id,
       tags.name         AS tag_name
FROM gift_certificates
         LEFT JOIN gift_certificates_tags ON gift_certificates.id = gift_certificates_tags.gift_certificate_id
         LEFT JOIN tags ON gift_certificates_tags.tag_id = tags.id
WHERE gift_certificates.name = '%1%';

SELECT * FROM tags ORDER BY name DESC;

SELECT * FROM gift_certificates;

SELECT *
FROM gift_certificates gc
WHERE gc.name ILIKE concat('%', 'certificate', '%');