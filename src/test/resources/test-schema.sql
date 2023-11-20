CREATE TABLE IF NOT EXISTS movie (
    `id`          BIGINT AUTO_INCREMENT PRIMARY KEY,
    `title`       VARCHAR,
    `description` VARCHAR,
    `produced_year`        INTEGER,
    `rate` INTEGER,
    `director` VARCHAR,
    `main_actors` VARCHAR,
    `genre` VARCHAR,
    `created_date` TIMESTAMP,
    `last_modified_date` TIMESTAMP
);

