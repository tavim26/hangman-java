# Hangman (Java)

A desktop Hangman game built with JavaFX and MySQL, made as a university project. Players create an account, choose a difficulty and a word category, and can review the history of their games.

## Features

- Sign up and log in, with passwords stored as salted PBKDF2 hashes
- Three difficulty levels: Easy, Medium and Hard (9, 6 and 3 wrong guesses allowed)
- Six word categories: Cars, Plants, Animals, Countries, Jobs, Video Games
- Games history for each player, saved in a MySQL database
- Input validation with clear error messages

## Tech stack

Java 21 · JavaFX 21 (FXML) · MySQL 8 · JDBC · Maven

## Getting started

### Prerequisites

- JDK 21
- MySQL Server 8.0 or newer

Maven does not need to be installed; the project includes the Maven Wrapper.

### 1. Create the database

```bash
sudo mysql < database/schema.sql
```

Then create a dedicated user for the application:

```sql
CREATE USER 'hangman'@'localhost' IDENTIFIED BY 'your-password';
GRANT SELECT, INSERT, UPDATE, DELETE ON hangman.* TO 'hangman'@'localhost';
```

### 2. Configure the connection

```bash
cp database/connection.properties.example database/connection.properties
```

Edit `database/connection.properties` and set the password chosen above. This file is ignored by Git.

### 3. Run

```bash
./mvnw javafx:run
```

On Windows, use `mvnw.cmd javafx:run`.

## Project structure

```
src/main/java/io/github/tavim26/hangman/
├── model/        Player, GameRecord, HangmanGame and the Difficulty/Category enums
├── database/     JDBC access (DAOs and connection)
├── service/      application logic: registration, login, password hashing, games
└── controller/   JavaFX controllers and screen navigation
src/main/resources/
├── fxml/         screen layouts
└── images/
database/         schema and connection config template
```

The game rules (`HangmanGame`) do not depend on JavaFX or the database. Controllers only call the service layer, which in turn uses the DAOs.

## License

This project is licensed under the MIT License. See [LICENSE](LICENSE) for details.
