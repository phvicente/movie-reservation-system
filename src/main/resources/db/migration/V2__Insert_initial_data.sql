INSERT INTO role (name) VALUES ('ROLE_ADMIN');
INSERT INTO role (name) VALUES ('ROLE_USER');

-- Password: admin
INSERT INTO user_account (username, password, email, role_id)
VALUES (
           'admin',
           '$2y$10$UgEMA19YR1yjOlTfu9xJ5eFkzZolMywt.yaZtx9GE6L8MZ1crrGsG',
           'admin@example.com',
           (SELECT id FROM role WHERE name = 'ROLE_ADMIN')
       );

-- Password: user1
INSERT INTO user_account (username, password, email, role_id)
VALUES (
           'user1',
           '$2y$10$AIW7fZgmWEW.D4kfGJ2D/uG9Lj8GQD0.F21vVFE6enS3Y1J.S3Br2',
           'user1@example.com',
           (SELECT id FROM role WHERE name = 'ROLE_USER')
       );

-- Password: user2
INSERT INTO user_account (username, password, email, role_id)
VALUES (
           'user2',
           '$2y$10$MCMcxFzS5yWO1hzXusYrmeITFwJZXEYjnUbD6DjREdv6vdjcE21MO',
           'user2@example.com',
           (SELECT id FROM role WHERE name = 'ROLE_USER')
       );

-- Inserindo filmes adicionais
INSERT INTO movie (title, description, genre, poster_image)
VALUES
    ('The Matrix Reloaded', 'The second installment in the Matrix trilogy.', 'Action/Sci-Fi', 'matrix_reloaded.jpg'),
    ('Inception', 'A skilled thief leads a team into people dreams to steal secrets.', 'Sci-Fi/Thriller', 'inception.jpg');

-- Inserindo showtimes para os filmes
-- Inserindo showtimes para 'The Matrix Reloaded' (movie_id = 1)
INSERT INTO showtime (start_time, end_time, movie_id)
VALUES
    ('2024-10-01 18:00:00', '2024-10-01 20:30:00', (SELECT id FROM movie WHERE title = 'The Matrix Reloaded')),
    ('2024-10-02 21:00:00', '2024-10-02 23:30:00', (SELECT id FROM movie WHERE title = 'The Matrix Reloaded'));

-- Inserindo showtimes para 'Inception' (movie_id = 2)
INSERT INTO showtime (start_time, end_time, movie_id)
VALUES
    ('2024-10-03 17:00:00', '2024-10-03 19:30:00', (SELECT id FROM movie WHERE title = 'Inception')),
    ('2024-10-04 20:00:00', '2024-10-04 22:30:00', (SELECT id FROM movie WHERE title = 'Inception'));

-- Inserindo assentos para cada showtime
-- Supondo que os IDs dos showtimes inseridos sejam 1 a 4
-- Inserindo assentos para o primeiro showtime
INSERT INTO seat (seat_number, reserved, showtime_id)
VALUES
    ('A1', FALSE, (SELECT id FROM showtime WHERE start_time = '2024-10-01 18:00:00')),
    ('A2', FALSE, (SELECT id FROM showtime WHERE start_time = '2024-10-01 18:00:00'));

-- Inserindo assentos para o segundo showtime
INSERT INTO seat (seat_number, reserved, showtime_id)
VALUES
    ('B1', FALSE, (SELECT id FROM showtime WHERE start_time = '2024-10-02 21:00:00')),
    ('B2', FALSE, (SELECT id FROM showtime WHERE start_time = '2024-10-02 21:00:00'));

-- Inserindo assentos para o terceiro showtime
INSERT INTO seat (seat_number, reserved, showtime_id)
VALUES
    ('C1', FALSE, (SELECT id FROM showtime WHERE start_time = '2024-10-03 17:00:00')),
    ('C2', FALSE, (SELECT id FROM showtime WHERE start_time = '2024-10-03 17:00:00'));

-- Inserindo assentos para o quarto showtime
INSERT INTO seat (seat_number, reserved, showtime_id)
VALUES
    ('D1', FALSE, (SELECT id FROM showtime WHERE start_time = '2024-10-04 20:00:00')),
    ('D2', FALSE, (SELECT id FROM showtime WHERE start_time = '2024-10-04 20:00:00'));

-- Inserindo reservas para testes
-- Reservas serão associadas a assentos específicos e usuários existentes

-- Inserindo primeira reserva para 'admin' (user_id = 1) no primeiro showtime
-- Reservando assentos 'A1' e 'A2'

-- Atualizando os assentos para reservados
UPDATE seat
SET reserved = TRUE
WHERE seat_number IN ('A1', 'A2')
  AND showtime_id = (SELECT id FROM showtime WHERE start_time = '2024-10-01 18:00:00');

-- Inserindo a reserva
INSERT INTO reservation (user_id, showtime_id, reservation_time, cancelled_at)
VALUES (
           (SELECT id FROM user_account WHERE username = 'admin'),
           (SELECT id FROM showtime WHERE start_time = '2024-10-01 18:00:00'),
           NOW(),
           NULL
       );

-- Obter o ID da reserva recém-criada
-- Supondo que a reserva inserida seja a ID 1

-- Inserindo entradas na tabela de associação 'reservation_seat'
INSERT INTO reservation_seat (reservation_id, seat_id)
VALUES
    (
        (SELECT id FROM reservation WHERE user_id = (SELECT id FROM user_account WHERE username = 'admin') AND showtime_id = (SELECT id FROM showtime WHERE start_time = '2024-10-01 18:00:00') ORDER BY id DESC LIMIT 1),
    (SELECT id FROM seat WHERE seat_number = 'A1' AND showtime_id = (SELECT id FROM showtime WHERE start_time = '2024-10-01 18:00:00'))
    ),
    (
        (SELECT id FROM reservation WHERE user_id = (SELECT id FROM user_account WHERE username = 'admin') AND showtime_id = (SELECT id FROM showtime WHERE start_time = '2024-10-01 18:00:00') ORDER BY id DESC LIMIT 1),
        (SELECT id FROM seat WHERE seat_number = 'A2' AND showtime_id = (SELECT id FROM showtime WHERE start_time = '2024-10-01 18:00:00'))
    );

-- Inserindo segunda reserva para 'user1' (user_id = 2) no terceiro showtime
-- Reservando assentos 'C1' e 'C2'

-- Atualizando os assentos para reservados
UPDATE seat
SET reserved = TRUE
WHERE seat_number IN ('C1', 'C2')
  AND showtime_id = (SELECT id FROM showtime WHERE start_time = '2024-10-03 17:00:00');

-- Inserindo a reserva
INSERT INTO reservation (user_id, showtime_id, reservation_time, cancelled_at)
VALUES (
           (SELECT id FROM user_account WHERE username = 'user1'),
           (SELECT id FROM showtime WHERE start_time = '2024-10-03 17:00:00'),
           NOW(),
           NULL
       );

-- Inserindo entradas na tabela de associação 'reservation_seat'
INSERT INTO reservation_seat (reservation_id, seat_id)
VALUES
    (
        (SELECT id FROM reservation WHERE user_id = (SELECT id FROM user_account WHERE username = 'user1') AND showtime_id = (SELECT id FROM showtime WHERE start_time = '2024-10-03 17:00:00') ORDER BY id DESC LIMIT 1),
    (SELECT id FROM seat WHERE seat_number = 'C1' AND showtime_id = (SELECT id FROM showtime WHERE start_time = '2024-10-03 17:00:00'))
    ),
    (
        (SELECT id FROM reservation WHERE user_id = (SELECT id FROM user_account WHERE username = 'user1') AND showtime_id = (SELECT id FROM showtime WHERE start_time = '2024-10-03 17:00:00') ORDER BY id DESC LIMIT 1),
        (SELECT id FROM seat WHERE seat_number = 'C2' AND showtime_id = (SELECT id FROM showtime WHERE start_time = '2024-10-03 17:00:00'))
    );

-- Inserindo terceira reserva para 'user2' (user_id = 3) no segundo showtime
-- Reservando assentos 'B1' e 'B2'

-- Atualizando os assentos para reservados
UPDATE seat
SET reserved = TRUE
WHERE seat_number IN ('B1', 'B2')
  AND showtime_id = (SELECT id FROM showtime WHERE start_time = '2024-10-02 21:00:00');

-- Inserindo a reserva
INSERT INTO reservation (user_id, showtime_id, reservation_time, cancelled_at)
VALUES (
           (SELECT id FROM user_account WHERE username = 'user2'),
           (SELECT id FROM showtime WHERE start_time = '2024-10-02 21:00:00'),
           NOW(),
           NULL
       );

-- Inserindo entradas na tabela de associação 'reservation_seat'
INSERT INTO reservation_seat (reservation_id, seat_id)
VALUES
    (
        (SELECT id FROM reservation WHERE user_id = (SELECT id FROM user_account WHERE username = 'user2') AND showtime_id = (SELECT id FROM showtime WHERE start_time = '2024-10-02 21:00:00') ORDER BY id DESC LIMIT 1),
    (SELECT id FROM seat WHERE seat_number = 'B1' AND showtime_id = (SELECT id FROM showtime WHERE start_time = '2024-10-02 21:00:00'))
    ),
    (
        (SELECT id FROM reservation WHERE user_id = (SELECT id FROM user_account WHERE username = 'user2') AND showtime_id = (SELECT id FROM showtime WHERE start_time = '2024-10-02 21:00:00') ORDER BY id DESC LIMIT 1),
        (SELECT id FROM seat WHERE seat_number = 'B2' AND showtime_id = (SELECT id FROM showtime WHERE start_time = '2024-10-02 21:00:00'))
    );

-- Inserindo quarta reserva para 'user2' (user_id = 3) no quarto showtime
-- Reservando assentos 'D1' e 'D2'

-- Atualizando os assentos para reservados
UPDATE seat
SET reserved = TRUE
WHERE seat_number IN ('D1', 'D2')
  AND showtime_id = (SELECT id FROM showtime WHERE start_time = '2024-10-04 20:00:00');

-- Inserindo a reserva
INSERT INTO reservation (user_id, showtime_id, reservation_time, cancelled_at)
VALUES (
           (SELECT id FROM user_account WHERE username = 'user2'),
           (SELECT id FROM showtime WHERE start_time = '2024-10-04 20:00:00'),
           NOW(),
           NULL
       );

-- Inserindo entradas na tabela de associação 'reservation_seat'
INSERT INTO reservation_seat (reservation_id, seat_id)
VALUES
    (
        (SELECT id FROM reservation WHERE user_id = (SELECT id FROM user_account WHERE username = 'user2') AND showtime_id = (SELECT id FROM showtime WHERE start_time = '2024-10-04 20:00:00') ORDER BY id DESC LIMIT 1),
    (SELECT id FROM seat WHERE seat_number = 'D1' AND showtime_id = (SELECT id FROM showtime WHERE start_time = '2024-10-04 20:00:00'))
    ),
    (
        (SELECT id FROM reservation WHERE user_id = (SELECT id FROM user_account WHERE username = 'user2') AND showtime_id = (SELECT id FROM showtime WHERE start_time = '2024-10-04 20:00:00') ORDER BY id DESC LIMIT 1),
        (SELECT id FROM seat WHERE seat_number = 'D2' AND showtime_id = (SELECT id FROM showtime WHERE start_time = '2024-10-04 20:00:00'))
    );