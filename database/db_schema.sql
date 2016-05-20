DROP DATABASE IF EXISTS cstore_db;
CREATE DATABASE cstore_db;
USE cstore_db;

CREATE TABLE IF NOT EXISTS accounts (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    first_name NVARCHAR(32),
    last_name NVARCHAR(32),
    hashed_password NVARCHAR(256) NOT NULL,
    email NVARCHAR(64) UNIQUE NOT NULL,
    display_name NVARCHAR(32) NOT NULL,
    img_src NVARCHAR(128),
    fb_link NVARCHAR(128),
    twitter_link NVARCHAR(128),
    google_plus_link NVARCHAR(128),
    country NVARCHAR(32),
    city NVARCHAR(32),
    about_me NVARCHAR(1024),
    type ENUM ('ADMIN', 'USER', 'GUEST'),
    is_banned ENUM ('0', '1')
);

CREATE TABLE IF NOT EXISTS post (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    account_id INT,
    img_src NVARCHAR(128),
    youtube_link NVARCHAR(1024),
    title NVARCHAR(64),
    description NVARCHAR(1024),
    post_time DATETIME,

    FOREIGN KEY (account_id) REFERENCES accounts(id)
);

CREATE TABLE IF NOT EXISTS follow (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    follower_id INT,
    following_id INT,

    FOREIGN KEY (follower_id) REFERENCES accounts(id),
    FOREIGN KEY (following_id) REFERENCES accounts(id)
);

CREATE TABLE IF NOT EXISTS tag (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    tag NVARCHAR(32)
);

CREATE TABLE IF NOT EXISTS post_tag (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    post_id INT,
    tag_id INT,

    FOREIGN KEY (post_id) REFERENCES post(id),
    FOREIGN KEY (tag_id) REFERENCES tag(id)
);

CREATE TABLE IF NOT EXISTS plus (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    post_id INT,
    account_id INT,

    FOREIGN KEY (post_id) REFERENCES post(id),
    FOREIGN KEY (account_id) REFERENCES accounts(id)
);

CREATE TABLE IF NOT EXISTS minus (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    post_id INT,
    account_id INT,

    FOREIGN KEY (post_id) REFERENCES post(id),
    FOREIGN KEY (account_id) REFERENCES accounts(id)
);

CREATE TABLE IF NOT EXISTS comment (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    post_id INT,
    account_id INT,
    comment_text NVARCHAR(1024),
    add_time DATETIME,

    FOREIGN KEY (post_id) REFERENCES post(id),
    FOREIGN KEY (account_id) REFERENCES accounts(id)
)