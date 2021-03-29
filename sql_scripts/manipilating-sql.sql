-------------get tags list by gift id--------------

SELECT t.id, t.name FROM tags t
INNER JOIN gift_certificates_tags gct
ON t.id=gct.tag_id WHERE gct.gift_certificate_id=1;
----------------------------------------------------