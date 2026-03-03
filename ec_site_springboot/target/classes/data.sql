-- USERS テーブルが存在しなければ作成
CREATE TABLE IF NOT EXISTS USERS (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

-- ユーザーを挿入（重複時は更新）
MERGE INTO USERS (username, password) KEY (username)
VALUES ('testuser', 'pass123');

-- 追加ユーザー例
MERGE INTO USERS (username, password) KEY (username)
VALUES ('alice', 'alicepass');
MERGE INTO USERS (username, password) KEY (username)
VALUES ('bob', 'bobpass');