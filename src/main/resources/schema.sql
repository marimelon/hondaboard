CREATE TABLE IF NOT EXISTS user (
    id IDENTITY,
    name CHAR NOT NULL,
    password CHAR,
    image CHAR
);

CREATE TABLE IF NOT EXISTS room(
    id IDENTITY,
    name CHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS roomuser(
    room_id INT NOT NULL,
    user_id INT NOT NULL,
    role CHAR NOT NULL,
    PRIMARY KEY(room_id, user_id)
);

Create Table IF NOT EXISTS comment(
    id IDENTITY,
    room_id INT NOT NULL,
    user_id INT NOT NULL,
    content CHAR NOT NULL,
    date TIMESTAMP NOT NULL
);

Create Table IF NOT EXISTS Event(
    id IDENTITY,
    user_id INT NOT NULL,
    title CHAR NOT NULL,
    start_at date,
    end_at date
)