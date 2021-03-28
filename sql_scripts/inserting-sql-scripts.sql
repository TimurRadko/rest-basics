INSERT INTO gift_certificates (name, description, price, duration, create_date, last_update_date) VALUES
('The first', 'The first certificate', 55.0, 14, '2020-05-05', '2020-10-05'),
('The second', 'The second certificate', 35.0, 18, '2021-07-07', '2020-11-07'),
('The third', 'The third certificate', 50.0, 11, '2020-05-09', '2020-10-09'),
('The fourth', 'The fourth certificate', 14.0, 12, '2020-05-07', '2020-10-07');


INSERT INTO tags(name) VALUES ('tag1');
INSERT INTO tags(name) VALUES ('tag2');
INSERT INTO tags(name) VALUES ('tag3');
INSERT INTO tags(name) VALUES ('tag4');

INSERT INTO gift_certificates_tags(gift_certificate_id, tag_id)
VALUES (1, 1);
INSERT INTO gift_certificates_tags(gift_certificate_id, tag_id)
VALUES (1, 2);

INSERT INTO gift_certificates_tags(gift_certificate_id, tag_id)
VALUES (2, 3);
INSERT INTO gift_certificates_tags(gift_certificate_id, tag_id)
VALUES (2, 4);

INSERT INTO gift_certificates_tags(gift_certificate_id, tag_id)
VALUES (3, 1);
INSERT INTO gift_certificates_tags(gift_certificate_id, tag_id)
VALUES (3, 4);

INSERT INTO gift_certificates_tags(gift_certificate_id, tag_id)
VALUES (4, 1);
INSERT INTO gift_certificates_tags(gift_certificate_id, tag_id)
VALUES (4, 2);
INSERT INTO gift_certificates_tags(gift_certificate_id, tag_id)
VALUES (4, 4);