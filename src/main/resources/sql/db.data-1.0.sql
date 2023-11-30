--liquibase formatted sql

--changeset aziz:2
INSERT INTO Actor (id, first_name, last_name) VALUES
                                                  (1, 'Leonardo', 'DiCaprio'),
                                                  (2, 'Kate', 'Winslet'),
                                                  (3, 'Billy', 'Zane'),
                                                  (4, 'Kathy', 'Bates'),
                                                  (5, 'Frances', 'Fisher'),
                                                  (6, 'Tim', 'Robbins'),
                                                  (7, 'Morgan', 'Freeman'),
                                                  (8, 'Bob', 'Gunton'),
                                                  (9, 'William', 'Sandler'),
                                                  (10, 'Clancy', 'Brown'),
                                                  (11, 'Marlon', 'Brando'),
                                                  (12, 'Al', 'Pacino'),
                                                  (13, 'James', 'Caan'),
                                                  (14, 'Richard S.', 'Castellano'),
                                                  (15, 'Robert', 'Duvall');

INSERT INTO Director (id, first_name, last_name) VALUES (1, 'James', 'Cameron'), (2, 'Frank', 'Darabont'), (3, 'Francis', 'Coppola');

INSERT INTO Genre (id, name) VALUES (1, 'Drama'), (2, 'Romance'), (3, 'Crime');


INSERT INTO movie (id, title, description, produced_year, rate, created_date, last_modified_date) VALUES
    (1, 'Titanic', 'A seventeen-year-old aristocrat falls in love with a kind but poor artist aboard the luxurious, ill-fated R.M.S. Titanic.', 1997, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 'The Shawshank Redemption', 'Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.', 1994, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, 'The Godfather', 'The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.', 1972, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO movie_directors (director_id, movie_id) VALUES (1, 1), (2, 2), (3, 3);

INSERT INTO movie_genre (genre_id, movie_id) VALUES (1, 1), (1, 2), (3, 3);

INSERT INTO movie_main_actors (actor_id, movie_id) VALUES (1, 1),
                                                          (2, 1),
                                                          (3, 1),
                                                          (4, 1),
                                                          (5, 1),
                                                          (6, 2),
                                                          (7, 2),
                                                          (8, 2),
                                                          (9, 2),
                                                          (10, 2),
                                                          (11, 3),
                                                          (12, 3),
                                                          (13, 3),
                                                          (14, 3),
                                                          (15, 3);