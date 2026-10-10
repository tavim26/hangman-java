CREATE DATABASE IF NOT EXISTS hangman;
USE hangman;

CREATE TABLE IF NOT EXISTS player
(
    PlayerID INT AUTO_INCREMENT PRIMARY KEY,
    Username VARCHAR(50)  NOT NULL,
    Email    VARCHAR(100) NOT NULL,
    Password VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS game
(
    GameID            INT AUTO_INCREMENT PRIMARY KEY,
    PlayerID          INT          NOT NULL,
    GameMode          VARCHAR(50)  NULL,
    Word              VARCHAR(100) NULL,
    RemainingAttempts INT          NULL,
    Status            VARCHAR(20)  NULL
);
