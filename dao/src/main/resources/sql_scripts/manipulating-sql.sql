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
where tags0_.gift_certificate_id = ?;

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
where giftcertif0_.order_id = ?;


select *
from gift_certificates
where id = 4975;

explain
select *
from gift_certificates;

explain
select tag0_.id as id1_5_, tag0_.name as name2_5_
from tags tag0_
         inner join gift_certificates_tags giftcertif1_ on tag0_.id = giftcertif1_.tag_id
         inner join gift_certificates giftcertif2_ on giftcertif1_.gift_certificate_id = giftcertif2_.id
         inner join orders_gift_certificates orders3_ on giftcertif2_.id = orders3_.gift_certificate_id
         inner join orders orders4_ on orders3_.order_id = orders4_.id
         inner join users user5_ on orders4_.user_id = user5_.id
where user5_.id = 2
group by tag0_.id, orders4_.cost
order by orders4_.cost desc, count(tag0_.id) desc;

select tag0_.id as id1_5_, tag0_.name as name2_5_
from tags tag0_
         inner join gift_certificates_tags giftcertif1_ on tag0_.id = giftcertif1_.tag_id
         inner join gift_certificates giftcertif2_ on giftcertif1_.gift_certificate_id = giftcertif2_.id
         inner join orders_gift_certificates orders3_ on giftcertif2_.id = orders3_.gift_certificate_id
         inner join orders orders4_ on orders3_.order_id = orders4_.id
         inner join users user5_ on orders4_.user_id = user5_.id
where user5_.id = 1
group by tag0_.id, orders4_.cost
order by orders4_.cost desc, count(tag0_.id) desc;

select tag0_.id as id1_5_, tag0_.name as name2_5_
from tags tag0_
         inner join gift_certificates_tags giftcertif1_ on tag0_.id = giftcertif1_.tag_id
         inner join gift_certificates giftcertif2_ on giftcertif1_.gift_certificate_id = giftcertif2_.id
         inner join orders_gift_certificates orders3_ on giftcertif2_.id = orders3_.gift_certificate_id
         inner join orders orders4_ on orders3_.order_id = orders4_.id
where orders4_.user_id = 5
group by tag0_.id
order by sum(orders4_.cost) desc;

select tag0_.id as id1_5_, tag0_.name as name2_5_
from tags tag0_
         inner join gift_certificates_tags giftcertif1_ on tag0_.id = giftcertif1_.tag_id
         inner join gift_certificates giftcertif2_ on giftcertif1_.gift_certificate_id = giftcertif2_.id
         inner join orders_gift_certificates orders3_ on giftcertif2_.id = orders3_.gift_certificate_id
         inner join orders orders4_ on orders3_.order_id = orders4_.id
where orders4_.user_id = 5
group by tag0_.id;

select tag0_.id as id1_5_, tag0_.name as name2_5_
from tags tag0_
         inner join gift_certificates_tags giftcertif1_ on tag0_.id = giftcertif1_.tag_id
         inner join gift_certificates giftcertif2_ on giftcertif1_.gift_certificate_id = giftcertif2_.id
         inner join orders_gift_certificates orders3_ on giftcertif2_.id = orders3_.gift_certificate_id
         inner join orders orders4_ on orders3_.order_id = orders4_.id
where orders4_.user_id = 5
group by tag0_.id
order by sum(orders4_.cost) desc;

explain
select tag0_.id as id1_5_, tag0_.name as name2_5_
from tags tag0_
         inner join gift_certificates_tags giftcertif1_ on tag0_.id = giftcertif1_.tag_id
         inner join gift_certificates giftcertif2_ on giftcertif1_.gift_certificate_id = giftcertif2_.id
         inner join orders_gift_certificates orders3_ on giftcertif2_.id = orders3_.gift_certificate_id
         inner join orders orders4_ on orders3_.order_id = orders4_.id
where orders4_.user_id = 1
group by tag0_.id
order by sum(orders4_.cost) desc LIMIT 1;

select tag0_.id as id1_5_, tag0_.name as name2_5_
from tags tag0_
         inner join gift_certificates_tags giftcertif1_ on tag0_.id = giftcertif1_.tag_id
         inner join gift_certificates giftcertif2_ on giftcertif1_.gift_certificate_id = giftcertif2_.id
         inner join orders_gift_certificates orders3_ on giftcertif2_.id = orders3_.gift_certificate_id
         inner join orders orders4_ on orders3_.order_id = orders4_.id
where orders4_.user_id = 1
group by tag0_.id
order by sum(orders4_.cost) desc;

select tags0_.gift_certificate_id as gift_cer1_2_0_,
       tags0_.tag_id              as tag_id2_2_0_,
       tag1_.id                   as id1_5_1_,
       tag1_.name                 as name2_5_1_
from gift_certificates_tags tags0_
         inner join tags tag1_ on tags0_.tag_id = tag1_.id
where tags0_.gift_certificate_id = ?;

select giftcertif0_.id               as id1_1_0_,
       tag3_.id                      as id1_5_1_,
       giftcertif0_.create_date      as create_d2_1_0_,
       giftcertif0_.description      as descript3_1_0_,
       giftcertif0_.duration         as duration4_1_0_,
       giftcertif0_.last_update_date as last_upd5_1_0_,
       giftcertif0_.name             as name6_1_0_,
       giftcertif0_.price            as price7_1_0_,
       tag3_.name                    as name2_5_1_,
       tags2_.gift_certificate_id    as gift_cer1_2_0__,
       tags2_.tag_id                 as tag_id2_2_0__
from gift_certificates giftcertif0_
         cross join gift_certificates giftcertif1_
         left outer join gift_certificates_tags tags2_ on giftcertif0_.id = tags2_.gift_certificate_id
         left outer join tags tag3_ on tags2_.tag_id = tag3_.id
order by giftcertif1_.name asc;

select distinct giftcertif0_.id               as id1_1_0_,
                tag2_.id                      as id1_5_1_,
                giftcertif0_.create_date      as create_d2_1_0_,
                giftcertif0_.description      as descript3_1_0_,
                giftcertif0_.duration         as duration4_1_0_,
                giftcertif0_.last_update_date as last_upd5_1_0_,
                giftcertif0_.name             as name6_1_0_,
                giftcertif0_.price            as price7_1_0_,
                tag2_.name                    as name2_5_1_,
                tags1_.gift_certificate_id    as gift_cer1_2_0__,
                tags1_.tag_id                 as tag_id2_2_0__
from gift_certificates giftcertif0_
         left outer join gift_certificates_tags tags1_ on giftcertif0_.id = tags1_.gift_certificate_id
         left outer join tags tag2_ on tags1_.tag_id = tag2_.id
order by giftcertif0_.name desc;

select tag0_.id as id1_5_, tag0_.name as name2_5_
from tags tag0_
         inner join gift_certificates_tags giftcertif1_ on tag0_.id = giftcertif1_.tag_id
         inner join gift_certificates giftcertif2_ on giftcertif1_.gift_certificate_id = giftcertif2_.id
         inner join orders_gift_certificates orders3_ on giftcertif2_.id = orders3_.gift_certificate_id
         inner join orders orders4_ on orders3_.order_id = orders4_.id
where orders4_.user_id = 5
group by tag0_.id
order by sum(orders4_.cost) desc limit 1;

















