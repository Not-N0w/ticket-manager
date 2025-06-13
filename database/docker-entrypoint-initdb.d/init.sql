ALTER SYSTEM SET listen_addresses = '*';


CREATE TABLE roles (
                       user_id SERIAL,
                       name VARCHAR(50) UNIQUE NOT NULL CHECK (name <> ''),
                       PRIMARY KEY(user_id, name)
);

CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR(30) NOT NULL UNIQUE CHECK (username <> ''),
                       password TEXT NOT NULL CHECK (password <> ''),
                       first_name VARCHAR(255) NOT NULL,
                       last_name VARCHAR(255) NOT NULL,
                       status VARCHAR(20) NOT NULL
);

CREATE TABLE user_roles (
                            user_id INTEGER NOT NULL,
                            role VARCHAR(50) NOT NULL,
                            PRIMARY KEY (user_id, role),
                            FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE locations (
                           id SERIAL PRIMARY KEY,
                           x FLOAT NOT NULL,
                           y FLOAT NOT NULL,
                           z BIGINT NOT NULL
);

CREATE TABLE coordinates (
                             id SERIAL PRIMARY KEY,
                             x INTEGER NOT NULL CHECK (x > -47),
                             y FLOAT NOT NULL CHECK (y > -69)
);

CREATE TABLE persons (
                         id SERIAL PRIMARY KEY,
                         birthday DATE NOT NULL,
                         weight DOUBLE PRECISION NOT NULL CHECK (weight > 0),
                         passport_id VARCHAR(30) NOT NULL CHECK (passport_id <> ''),
                         location_id INTEGER NOT NULL,
                         FOREIGN KEY (location_id) REFERENCES locations(id)
                             ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE tickets (
                         id SERIAL PRIMARY KEY,
                         name VARCHAR(30) NOT NULL,
                         creation_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         coordinates_id INTEGER NOT NULL,
                         price INTEGER NOT NULL CHECK (price > 0),
                         refundable BOOLEAN NOT NULL,
                         ticket_type VARCHAR(20) NOT NULL,
                         person_id INTEGER NOT NULL,
                         user_id INTEGER NOT NULL,
                         FOREIGN KEY (coordinates_id) REFERENCES coordinates(id)
                             ON UPDATE CASCADE ON DELETE CASCADE,
                         FOREIGN KEY (person_id) REFERENCES persons(id)
                             ON UPDATE CASCADE ON DELETE CASCADE,
                         FOREIGN KEY (user_id) REFERENCES users(id)
                             ON UPDATE CASCADE ON DELETE CASCADE
);

