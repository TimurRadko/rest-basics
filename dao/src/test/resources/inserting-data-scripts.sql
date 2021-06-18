insert into gift_certificates (name, description, price, duration, create_date, last_update_date)
values (
           'BBB',
           'The first certificate',
           55.0,
           14,
           '2021-03-25 00:00:00',
           '2020-10-05 00:00:00'
       ), (
           'AAA',
           'The second certificate',
           35.0,
           18,
           '2021-07-07 00:00:00',
           '2020-11-07 00:00:00'
       ), (
           'DDD',
           'The third certificate',
           50.0,
           11,
           '2020-05-09 00:00:00',
           '2020-10-09 00:00:00'
       ), (
           'CCC',
           'The fourth certificate',
           14.0,
           12,
           '2020-05-07 00:00:00',
           '2020-10-07 00:00:00'
       );


INSERT INTO tags(name) VALUES
('tag1'),
('tag2'),
('tag3'),
('tag4');

INSERT INTO gift_certificates_tags(gift_certificate_id, tag_id) VALUES
(1, 1),
(2, 3),
(3, 4),
(4, 4);