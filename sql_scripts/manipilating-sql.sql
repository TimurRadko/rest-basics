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