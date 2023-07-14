CREATE TABLE user_system (
    id          INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    login       VARCHAR(40),
    name        VARCHAR(80),
    password    VARCHAR(255),
    email       VARCHAR(150),
    active      SMALLINT
);

INSERT INTO user_system(login, name, password, email, active)
    VALUES ('lucas', 'lucas', '$2a$10$QvB3hnum78HhnqgCSafosOnhFoqplYys.bgw66bvBfAOx5T13nOKW', 'lucneves@gmail.com', 1);

CREATE TABLE role (
    id      INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name    VARCHAR(80)
);

INSERT INTO role(name)
    VALUES ('ROLE_ADMIN');

CREATE TABLE user_has_role (
    user_id     INT NOT NULL,
    role_id     INT NOT NULL,
    CONSTRAINT user_role_use_fk FOREIGN KEY (user_id) REFERENCES user_system(id),
    CONSTRAINT user_role_role_fk FOREIGN KEY (role_id) REFERENCES role(id)
);

INSERT INTO user_has_role(user_id, role_id) VALUES (1,1);