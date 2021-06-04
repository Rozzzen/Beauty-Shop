INSERT INTO favours(id, category, price) VALUES
(1, 'HAIRDO', 120),
(2, 'HAIRDO', 150),
(3, 'HAIRDO', 200),
(4, 'HAIRDO', 80),
(5, 'MAKEUP', 250),
(6, 'MAKEUP', 350),
(7, 'MAKEUP', 500),
(8, 'MANICURE', 100),
(9, 'MANICURE', 180),
(10, 'MANICURE', 260),
(11, 'MANICURE', 400),
(12, 'PEDICURE', 250),
(13, 'PEDICURE', 130),
(14, 'PEDICURE', 180);
INSERT INTO favour_translation(id, language, text, favour_id) VALUES
(1, 'EN', 'Baby Haircut', 1),          (2, 'UK', 'Дитяча Стрижка', 1),
(3, 'EN', 'Female Haircut', 2),        (4, 'UK', 'Жіноча Стрижка', 2),
(5, 'EN', 'Male Haircut', 3),          (6, 'UK', 'Чоловіча Стрижка', 3),
(7, 'EN', 'Beard design', 4),          (8, 'UK', 'Оформлення бороди', 4),
(9, 'EN', 'Day makeup', 5),            (10, 'UK', 'Денний макіяж', 5),
(11, 'EN', 'Evening makeup', 6),       (12, 'UK', 'Вечірній макіяж', 6),
(13, 'EN', 'Wedding makeup', 7),       (14, 'UK', 'Весільний макіяж', 7),
(15, 'EN', 'Classical manicure', 8),   (16, 'UK', 'Класичний манікюр', 8),
(17, 'EN', 'Gel varnish coating', 9),  (18, 'UK', 'Покриття гель лаком', 9),
(19, 'EN', 'Correction', 10),          (20, 'UK', 'Корекція', 10),
(21, 'EN', 'Gel extension', 11),       (22, 'UK', 'Гелеве нарощення', 11),
(23, 'EN', 'Classical pedicure', 12),  (24, 'UK', 'Педикюр класичний', 12),
(25, 'EN', 'Gel varnish coating', 13), (26, 'UK', 'Покриття гель лаком', 13),
(27, 'EN', 'Finger pedicure', 14),     (28, 'UK', 'Педикюр пальців', 14);
INSERT INTO master_ratings(id, average_rating, rating_count) VALUES
(1, 4.5, 10),
(2, 5, 6),
(3, 3, 15),
(4, 4.3, 10),
(5, 4.8, 6);
INSERT INTO users(id, email, name, surname, role, password) VALUES
(1,'master1@gmail.com', 'Aleksandra', 'Bryuhanda', 'MASTER', '1'),
(2,'master2@gmail.com', 'Olga', 'Bryl', 'MASTER', '2'),
(3,'master3@gmail.com', 'Nikolaj', 'Bojko', 'MASTER', '3'),
(4,'master4@gmail.com', 'Yuliya', 'Averbuh', 'MASTER', '4'),
(5,'master5@gmail.com', 'Egor', 'Pleskachev', 'MASTER', '5'),
(6,'max2001zh@gmail.com', 'Maksym', 'Zhuk', 'USER', 'qwe'),
(7,'admin@gmail.com', 'Admin', '1', 'ADMIN', 'qwe');
INSERT INTO masters(id, image, rating_id, user_id) VALUES
(1, 'master1.jpg', 1, 1),
(2, 'master2.jpg', 2, 2),
(4, 'master4.jpg', 4, 4),
(5, 'master5.jpg', 5, 5);
INSERT INTO masters(id, rating_id, user_id) VALUES
(3, 3, 3);
INSERT INTO master_specialities(master_id, specialities) VALUES
(1, 'HAIRDO'),
(1, 'MAKEUP'),
(2, 'PEDICURE'),
(2, 'MANICURE'),
(3, 'HAIRDO'),
(4, 'HAIRDO'),
(4, 'MANICURE'),
(4, 'PEDICURE'),
(4, 'MAKEUP'),
(5, 'HAIRDO'),
(5, 'MANICURE');
INSERT INTO appointments(id, timeslot, master_id, favour_id, user_id) VALUES
(1, '2021-04-15T18:00:00', 4, 1, 1),
(2, '2021-04-15T16:00:00', 4, 2, 3),
(3, '2021-04-16T16:00:00', 4, 5, 2),
(4, '2021-04-16T14:00:00', 4, 7, 1),
(5, '2021-04-16T12:00:00', 4, 8, 3);
INSERT INTO reviews(id, created_at, rating, text, master_id, user_id) VALUES
(1, '2021-04-11', 5, 'very proffesional', 4, 6),
(2, '2021-04-11', 4, 'good service', 4, 2);