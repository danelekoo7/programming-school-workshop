CREATE TABLE user_group
(
    id   INT(11) AUTO_INCREMENT,
    name VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE users
(
    id            INT(11) AUTO_INCREMENT,
    username      VARCHAR(255),
    email         VARCHAR(255),
    password      VARCHAR(245),
    PRIMARY KEY (id),
    user_group_id INT(11),
    FOREIGN KEY (user_group_id) REFERENCES user_group (id) ON DELETE CASCADE
);


CREATE TABLE solution
(
    id          INT(11) AUTO_INCREMENT,
    created     DATETIME,
    updated     DATETIME,
    description TEXT,
    PRIMARY KEY (id),
    users_id    INT(11),
    FOREIGN KEY (users_id) REFERENCES users (id)
        ON DELETE CASCADE,
    exercise_id INT(11),
    FOREIGN KEY (exercise_id) REFERENCES exercise (id) ON DELETE CASCADE
);


CREATE TABLE exercise
(
    id          INT(11) AUTO_INCREMENT,
    title       VARCHAR(255),
    description TEXT,
    PRIMARY KEY (id)
);
