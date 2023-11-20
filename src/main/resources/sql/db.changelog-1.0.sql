--liquibase formatted sql

--changeset aziz:1
CREATE TABLE IF NOT EXISTS movie (
    id          BIGSERIAL PRIMARY KEY,
    title       TEXT    NOT NULL CHECK (title <> ''),
    description TEXT    NOT NULL CHECK (description <> ''),
    produced_year INTEGER NOT NULL CHECK (year BETWEEN 1850 AND 2050),
    rate INTEGER NOT NULL CHECK (rate BETWEEN 1 AND 5),
    director TEXT NOT NULL CHECK (director <> ''),
    main_actors TEXT NOT NULL CHECK (main_actors <> ''),
    genre TEXT NOT NULL CHECK (genre <> ''),
    created_date TIMESTAMP,
    last_modified_date TIMESTAMP
);