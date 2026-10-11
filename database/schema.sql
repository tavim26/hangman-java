CREATE DATABASE IF NOT EXISTS hangman;
USE hangman;

CREATE TABLE IF NOT EXISTS player
(
    PlayerID INT AUTO_INCREMENT PRIMARY KEY,
    Username VARCHAR(50)  NOT NULL UNIQUE,
    Email    VARCHAR(100) NOT NULL UNIQUE,
    Password VARCHAR(100) NOT NULL
    );

CREATE TABLE IF NOT EXISTS game
(
    GameID            INT AUTO_INCREMENT PRIMARY KEY,
    PlayerID          INT          NOT NULL,
    Difficulty        VARCHAR(20)  NOT NULL,
    Category          VARCHAR(50)  NOT NULL,
    Word              VARCHAR(100) NOT NULL,
    RemainingAttempts INT          NOT NULL,
    Status            VARCHAR(10)  NOT NULL,
    FOREIGN KEY (PlayerID) REFERENCES player (PlayerID) ON DELETE CASCADE
    );