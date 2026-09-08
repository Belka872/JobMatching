CREATE TABLE IF NOT EXISTS vacancies
(
    name       VARCHAR(255) UNIQUE NOT NULL PRIMARY KEY,
    company    VARCHAR(255)        NOT NULL,
    tags       TEXT[] NOT NULL,
    experience INTEGER             NOT NULL
);

CREATE TABLE IF NOT EXISTS users
(
    name       VARCHAR(255) UNIQUE NOT NULL PRIMARY KEY,
    skills     TEXT[] NOT NULL,
    experience INTEGER             NOT NULL
);