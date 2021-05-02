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

select tags0_.gift_certificate_id as gift_cer1_2_0_,
       tags0_.tag_id              as tag_id2_2_0_,
       tag1_.id                   as id1_5_1_,
       tag1_.name                 as name2_5_1_
from gift_certificates_tags tags0_
         inner join tags tag1_ on tags0_.tag_id = tag1_.id
where tags0_.gift_certificate_id=?;

select giftcertif0_.order_id            as order_id1_4_0_,
       giftcertif0_.gift_certificate_id as gift_cer2_4_0_,
       giftcertif1_.id                  as id1_1_1_,
       giftcertif1_.create_date         as create_d2_1_1_,
       giftcertif1_.description         as descript3_1_1_,
       giftcertif1_.duration            as duration4_1_1_,
       giftcertif1_.last_update_date    as last_upd5_1_1_,
       giftcertif1_.name                as name6_1_1_,
       giftcertif1_.price               as price7_1_1_
from orders_gift_certificates giftcertif0_
         inner join gift_certificates giftcertif1_ on giftcertif0_.gift_certificate_id = giftcertif1_.id
where giftcertif0_.order_id=?;


select * from gift_certificates where id = 4975;