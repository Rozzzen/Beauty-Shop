INSERT INTO services(id, category, price, title_en, title_ua) VALUES
(1, 'HAIRDO', 120, 'Baby Haircut', 'Дитяча Стрижка'),
(2, 'HAIRDO', 150, 'Female Haircut', 'Жіноча Стрижка'),
(3, 'HAIRDO', 200, 'Male Haircut', 'Чоловіча Стрижка'),
(4, 'HAIRDO', 80, 'Beard design', 'Оформлення бороди'),
(5, 'MAKEUP', 250, 'Day makeup', 'Денний макіяж'),
(6, 'MAKEUP', 350, 'Evening makeup', 'Вечірній макіяж'),
(7, 'MAKEUP', 500, 'Wedding makeup', 'Весільний макіяж'),
(8, 'MANICURE', 100, 'Classical manicure', 'Класичний манікюр'),
(9, 'MANICURE', 180, 'Gel varnish coating', 'Покриття гель лаком'),
(10, 'MANICURE', 260, 'Correction', 'Корекція'),
(11, 'MANICURE', 400, 'Gel extension', 'Гелеве нарощення'),
(12, 'PEDICURE', 250, 'Classical pedicure', 'Педикюр класичний'),
(13, 'PEDICURE', 130, 'Gel varnish coating', 'Покриття гель лаком'),
(14, 'PEDICURE', 180, 'Finger pedicure', 'Педикюр пальців');
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
INSERT INTO appointments(id, date_time, master_id, service_id, user_id) VALUES
(1, '2021-04-13T18:00:00', 4, 1, 1),
(2, '2021-04-13T16:00:00', 4, 2, 3),
(3, '2021-04-13T14:00:00', 4, 5, 2),
(4, '2021-04-14T14:00:00', 4, 7, 1),
(5, '2021-04-14T12:00:00', 4, 8, 3);
INSERT INTO reviews(id, created_at, rating, text, master_id, user_id) VALUES
(1, '2021-04-11', 5, 'very proffesional', 4, 6),
(2, '2021-04-11', 4, 'good service', 4, 2);