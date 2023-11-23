CREATE TABLE IF NOT EXISTS actor (
    id BIGSERIAL NOT NULL,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS director (
    id BIGSERIAL NOT NULL,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS genre (
    id BIGSERIAL NOT NULL,
    name VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS movie (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) CHECK (title <> ''),
    description VARCHAR(255) CHECK (description <> ''),
    produced_year INT NOT NULL CHECK (produced_year BETWEEN 1850 AND 2050),
    rate INT NOT NULL CHECK (rate BETWEEN 1 AND 5),
    created_date TIMESTAMP WITH TIME ZONE,
    last_modified_date TIMESTAMP WITH TIME ZONE
);

CREATE TABLE IF NOT EXISTS movie_directors (
    director_id BIGINT NOT NULL,
    movie_id BIGINT NOT NULL,
    FOREIGN KEY (director_id) REFERENCES director(id),
    FOREIGN KEY (movie_id) REFERENCES movie(id)
);

CREATE TABLE IF NOT EXISTS movie_genre (
    genre_id BIGINT NOT NULL,
    movie_id BIGINT NOT NULL,
    FOREIGN KEY (genre_id) REFERENCES genre(id),
    FOREIGN KEY (movie_id) REFERENCES movie(id)
);

CREATE TABLE IF NOT EXISTS movie_main_actors (
    actor_id BIGINT NOT NULL,
    movie_id BIGINT NOT NULL,
    FOREIGN KEY (actor_id) REFERENCES actor(id),
    FOREIGN KEY (movie_id) REFERENCES movie(id)
);