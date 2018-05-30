DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (date_time, description, calories, user_id) values
  ('29.05.2015 09:00', 'Завтрак', 900, 100000),
  ('29.05.2015 14:00', 'Обед', 1000, 100000),
  ('29.05.2015 18:30', 'Ужин',  100, 100000),
  ('30.05.2015 07:00', 'Ранний завтрак', 800, 100000),
  ('30.05.2015 13:00', 'Ланч', 1000, 100000),
  ('30.05.2015 17:00', 'Обед', 1000, 100000),
  ('31.05.2015 09:00', 'Breakfast', 500, 100001),
  ('31.05.2015 15:30', 'Lunch', 1000, 100001);