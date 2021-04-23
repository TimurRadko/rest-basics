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

SELECT tag_id
FROM gift_certificates_tags
WHERE gift_certificate_id = 1;

SELECT gct.tag_id, t.name
FROM gift_certificates_tags gct
         INNER JOIN tags t ON t.id = gct.tag_id
WHERE gct.gift_certificate_id = 2
  AND gct.tag_id = 4;

SELECT *
FROM gift_certificates_tags
WHERE tag_id = 1;

SELECT *
FROM gift_certificates
WHERE id = 13;

SELECT gift_certificates.id   AS certificate_id,
       gift_certificates.name AS certificate_name,
       gift_certificates.description,
       gift_certificates.price,
       gift_certificates.duration,
       gift_certificates.create_date,
       gift_certificates.last_update_date,
       tags.id                AS tag_id,
       tags.name              AS tag_name
FROM gift_certificates
         LEFT JOIN gift_certificates_tags ON gift_certificates.id = gift_certificates_tags.gift_certificate_id
         LEFT JOIN tags ON gift_certificates_tags.tag_id = tags.id
WHERE gift_certificates.name = '%tag%';

SELECT *
FROM tags
ORDER BY name DESC;

SELECT *
FROM gift_certificates;

SELECT *
FROM gift_certificates gc
WHERE gc.name ILIKE concat('%', 'sec', '%');

SELECT gc.id,
       gc.name,
       gc.description,
       gc.price,
       gc.duration,
       gc.create_date,
       gc.last_update_date
FROM gift_certificates gc
WHERE (gc.name ILIKE concat('%', 'sec', '%'));

INSERT INTO tags(name)
VALUES ('tag6');

SELECT *
FROM tags;

SELECT id, gift_certificate_id, tag_id
FROM gift_certificates_tags
WHERE gift_certificate_id = 2;

SELECT *
FROM gift_certificates_tags
WHERE tag_id = 2;

SELECT *
FROM orders;

SELECT gc.id,
       gc.name,
       gc.description,
       gc.price,
       gc.duration,
       gc.create_date,
       gc.last_update_date
FROM gift_certificates gc
         JOIN orders_gift_certificates ogc
              ON gc.id = ogc.gift_certificate_id
WHERE ogc.order_id = 2;

SELECT gc.id,
       gc.name,
       gc.description,
       gc.price,
       gc.duration,
       gc.create_date,
       gc.last_update_date
FROM gift_certificates gc
         JOIN orders_gift_certificates ogc
              ON gc.id = ogc.gift_certificate_id;


SELECT t.id, t.name
FROM tags t
         JOIN gift_certificates_tags gct
              ON t.id = gct.tag_id
WHERE gct.gift_certificate_id = 2;

SELECT t.id, t.name
FROM tags t
         JOIN gift_certificates_tags gct
              ON t.id = gct.tag_id
WHERE gct.gift_certificate_id = 2;

select distinct giftcertif0_.id               as id1_0_0_,
                tag2_.id                      as id1_3_1_,
                giftcertif0_.create_date      as create_d2_0_0_,
                giftcertif0_.description      as descript3_0_0_,
                giftcertif0_.duration         as duration4_0_0_,
                giftcertif0_.last_update_date as last_upd5_0_0_,
                giftcertif0_.name             as name6_0_0_,
                giftcertif0_.price            as price7_0_0_,
                tag2_.name                    as name2_3_1_,
                tags1_.gift_certificate_id    as gift_cer1_1_0__,
                tags1_.tag_id                 as tag_id2_1_0__
from gift_certificates giftcertif0_
         left outer join gift_certificates_tags tags1_ on giftcertif0_.id = tags1_.gift_certificate_id
         left outer join tags tag2_ on tags1_.tag_id = tag2_.id
where giftcertif0_.id = 1;

select distinct giftcertif0_.id               as id1_0_0_,
                tag2_.id                      as id1_3_1_,
                giftcertif0_.create_date      as create_d2_0_0_,
                giftcertif0_.description      as descript3_0_0_,
                giftcertif0_.duration         as duration4_0_0_,
                giftcertif0_.last_update_date as last_upd5_0_0_,
                giftcertif0_.name             as name6_0_0_,
                giftcertif0_.price            as price7_0_0_,
                tag2_.name                    as name2_3_1_,
                tags1_.gift_certificate_id    as gift_cer1_1_0__,
                tags1_.tag_id                 as tag_id2_1_0__
from gift_certificates giftcertif0_
         left outer join gift_certificates_tags tags1_ on giftcertif0_.id = tags1_.gift_certificate_id
         left outer join tags tag2_ on tags1_.tag_id = tag2_.id;

select giftcertif0_.id               as id1_0_,
       giftcertif0_.create_date      as create_d2_0_,
       giftcertif0_.description      as descript3_0_,
       giftcertif0_.duration         as duration4_0_,
       giftcertif0_.last_update_date as last_upd5_0_,
       giftcertif0_.name             as name6_0_,
       giftcertif0_.price            as price7_0_
from gift_certificates giftcertif0_
where lower(giftcertif0_.name) like '%fi%';

select giftcertif0_.id               as id1_0_,
       giftcertif0_.create_date      as create_d2_0_,
       giftcertif0_.description      as descript3_0_,
       giftcertif0_.duration         as duration4_0_,
       giftcertif0_.last_update_date as last_upd5_0_,
       giftcertif0_.name             as name6_0_,
       giftcertif0_.price            as price7_0_
from gift_certificates giftcertif0_
where lower(giftcertif0_.name) like lower('fi');

select tag0_.id as id1_3_, tag0_.name as name2_3_
from tags tag0_
where tag0_.id = 6;

select distinct giftcertif0_.id               as id1_0_0_,
                tag2_.id                      as id1_3_1_,
                giftcertif0_.create_date      as create_d2_0_0_,
                giftcertif0_.description      as descript3_0_0_,
                giftcertif0_.duration         as duration4_0_0_,
                giftcertif0_.last_update_date as last_upd5_0_0_,
                giftcertif0_.name             as name6_0_0_,
                giftcertif0_.price            as price7_0_0_,
                tag2_.name                    as name2_3_1_,
                tags1_.gift_certificate_id    as gift_cer1_1_0__,
                tags1_.tag_id                 as tag_id2_1_0__
from gift_certificates giftcertif0_
         left outer join gift_certificates_tags tags1_ on giftcertif0_.id = tags1_.gift_certificate_id
         left outer join tags tag2_ on tags1_.tag_id = tag2_.id
where tag2_.id = 1;

select distinct tag0_.id                         as id1_3_0_,
                giftcertif2_.id                  as id1_0_1_,
                tag0_.name                       as name2_3_0_,
                giftcertif2_.create_date         as create_d2_0_1_,
                giftcertif2_.description         as descript3_0_1_,
                giftcertif2_.duration            as duration4_0_1_,
                giftcertif2_.last_update_date    as last_upd5_0_1_,
                giftcertif2_.name                as name6_0_1_,
                giftcertif2_.price               as price7_0_1_,
                giftcertif1_.tag_id              as tag_id2_1_0__,
                giftcertif1_.gift_certificate_id as gift_cer1_1_0__
from tags tag0_
         left outer join gift_certificates_tags giftcertif1_ on tag0_.id = giftcertif1_.tag_id
         left outer join gift_certificates giftcertif2_ on giftcertif1_.gift_certificate_id = giftcertif2_.id
where tag0_.id = 6;

select distinct tag0_.id as id1_3_, tag0_.name as name2_3_
from tags tag0_
         left outer join gift_certificates_tags giftcertif1_ on tag0_.id = giftcertif1_.tag_id
         left outer join gift_certificates giftcertif2_ on giftcertif1_.gift_certificate_id = giftcertif2_.id
where tag0_.id = 4
  and (giftcertif2_.id = 5);

SELECT *
FROM gift_certificates_tags;


---------------Get Gift-Certificates By Order id----------------

SELECT gc.id,
       gc.name,
       gc.description,
       gc.price,
       gc.duration,
       gc.create_date,
       gc.last_update_date
FROM gift_certificates gc
         JOIN orders_gift_certificates ogc
              ON gc.id = ogc.gift_certificate_id
WHERE ogc.order_id = 1;


SELECT *
FROM gift_certificates
WHERE id IN (2, 3);

SELECT *
FROM gift_certificates;

select distinct giftcertif0_.id               as id1_0_0_,
                tag2_.id                      as id1_4_1_,
                giftcertif0_.create_date      as create_d2_0_0_,
                giftcertif0_.description      as descript3_0_0_,
                giftcertif0_.duration         as duration4_0_0_,
                giftcertif0_.last_update_date as last_upd5_0_0_,
                giftcertif0_.name             as name6_0_0_,
                giftcertif0_.price            as price7_0_0_,
                tag2_.name                    as name2_4_1_,
                tags1_.gift_certificate_id    as gift_cer1_1_0__,
                tags1_.tag_id                 as tag_id2_1_0__
from gift_certificates giftcertif0_
         inner join gift_certificates_tags tags1_ on giftcertif0_.id = tags1_.gift_certificate_id
         inner join tags tag2_ on tags1_.tag_id = tag2_.id
order by giftcertif0_.name desc, giftcertif0_.create_date desc;

select distinct giftcertif0_.id               as id1_0_0_,
                tag2_.id                      as id1_4_1_,
                giftcertif0_.create_date      as create_d2_0_0_,
                giftcertif0_.description      as descript3_0_0_,
                giftcertif0_.duration         as duration4_0_0_,
                giftcertif0_.last_update_date as last_upd5_0_0_,
                giftcertif0_.name             as name6_0_0_,
                giftcertif0_.price            as price7_0_0_,
                tag2_.name                    as name2_4_1_,
                tags1_.gift_certificate_id    as gift_cer1_1_0__,
                tags1_.tag_id                 as tag_id2_1_0__
from gift_certificates giftcertif0_
         inner join gift_certificates_tags tags1_ on giftcertif0_.id = tags1_.gift_certificate_id
         inner join tags tag2_ on tags1_.tag_id = tag2_.id

select distinct tag0_.id as id1_4_, tag0_.name as name2_4_
from tags tag0_
         inner join gift_certificates_tags giftcertif1_ on tag0_.id = giftcertif1_.tag_id
         inner join gift_certificates giftcertif2_ on giftcertif1_.gift_certificate_id = giftcertif2_.id
where giftcertif2_.id = 3;

select distinct tag0_.id as id1_4_, tag0_.name as name2_4_
from tags tag0_
         inner join gift_certificates_tags giftcertif1_ on tag0_.id = giftcertif1_.tag_id
         inner join gift_certificates giftcertif2_ on giftcertif1_.gift_certificate_id = giftcertif2_.id
where tag0_.id = 3
  and (giftcertif2_.id is not null);






