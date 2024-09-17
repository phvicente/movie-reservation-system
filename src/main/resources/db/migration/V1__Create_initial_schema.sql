CREATE TABLE role (
                      id BIGSERIAL PRIMARY KEY,
                      name VARCHAR(50) NOT NULL UNIQUE
);


CREATE TABLE user_account (
                              id BIGSERIAL PRIMARY KEY,
                              username VARCHAR(50) NOT NULL UNIQUE,
                              password VARCHAR(255) NOT NULL,
                              email VARCHAR(100) NOT NULL UNIQUE,
                              role_id BIGINT NOT NULL,
                              FOREIGN KEY (role_id) REFERENCES role(id)
);


CREATE TABLE movie (
                       id BIGSERIAL PRIMARY KEY,
                       title VARCHAR(100) NOT NULL,
                       description TEXT,
                       genre VARCHAR(50),
                       poster_image VARCHAR(255)
);


CREATE TABLE showtime (
                          id BIGSERIAL PRIMARY KEY,
                          start_time TIMESTAMP NOT NULL,
                          end_time TIMESTAMP NOT NULL,
                          movie_id BIGINT NOT NULL,
                          FOREIGN KEY (movie_id) REFERENCES movie(id)
);


CREATE TABLE seat (
                      id BIGSERIAL PRIMARY KEY,
                      seat_number VARCHAR(10) NOT NULL,
                      reserved BOOLEAN NOT NULL,
                      showtime_id BIGINT NOT NULL,
                      FOREIGN KEY (showtime_id) REFERENCES showtime(id)
);

ALTER TABLE seat
    ADD CONSTRAINT unique_seat_per_showtime UNIQUE (seat_number, showtime_id);


CREATE TABLE reservation (
                             id BIGSERIAL PRIMARY KEY,
                             user_id BIGINT NOT NULL,
                             showtime_id BIGINT NOT NULL,
                             reservation_time TIMESTAMP NOT NULL DEFAULT NOW(),
                             cancelled_at TIMESTAMP,
                             FOREIGN KEY (user_id) REFERENCES user_account(id),
                             FOREIGN KEY (showtime_id) REFERENCES showtime(id)
);

CREATE TABLE reservation_seat (
                                  reservation_id BIGINT NOT NULL,
                                  seat_id BIGINT NOT NULL,
                                  PRIMARY KEY (reservation_id, seat_id),
                                  FOREIGN KEY (reservation_id) REFERENCES reservation(id),
                                  FOREIGN KEY (seat_id) REFERENCES seat(id)
);

ALTER TABLE reservation_seat
    ADD CONSTRAINT unique_seat_reservation UNIQUE (seat_id);

CREATE INDEX idx_user_account_role_id ON user_account(role_id);
CREATE INDEX idx_showtime_movie_id ON showtime(movie_id);
CREATE INDEX idx_seat_showtime_id ON seat(showtime_id);
CREATE INDEX idx_reservation_user_id ON reservation(user_id);
CREATE INDEX idx_reservation_showtime_id ON reservation(showtime_id);

