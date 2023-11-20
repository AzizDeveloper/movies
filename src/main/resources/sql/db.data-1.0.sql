--liquibase formatted sql

--changeset aziz:2
INSERT INTO movie (id, title, description, produced_year, rate, director, main_actors, genre, created_date, last_modified_date)
VALUES
    (1, 'Titanic', 'A seventeen-year-old aristocrat falls in love with a kind but poor artist aboard the luxurious, ill-fated R.M.S. Titanic.',
     1997, 5, 'James Cameron', 'Leonardo DiCaprio, Kate Winslet, Billy Zane, Kathy Bates, Frances Fisher', 'Drama', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 'The Shawshank Redemption', 'Over the course of several years, two convicts form a friendship, seeking consolation and, eventually, redemption through basic compassion.',
     1994, 4, 'Frank Darabont', 'Tim Robbins, Morgan Freeman, Bob Gunton, William Sandler, Clancy Brown', 'Drama', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, 'The Godfather', 'Don Vito Corleone, head of a mafia family, decides to hand over his empire to his youngest son Michael. However, his decision unintentionally puts the lives of his loved ones in grave danger.',
     1972, 3, 'Francis Ford Coppola', 'Marlon Brando, Al Pacino, James Caan, Richard S. Castellano, Robert Duvall', 'Crime', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);