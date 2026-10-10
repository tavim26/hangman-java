package Model;

public class Game {
    private int gameID;
    private int playerID;
    private String gameMode;
    private String word;
    private int remainingAttempts;
    private String status;


    public Game(int gameID, int playerID,String gameMode, String word, int remainingAttempts, String status) {
        this.gameID = gameID;
        this.playerID = playerID;
        this.gameMode = gameMode;
        this.word = word;
        this.remainingAttempts = remainingAttempts;
        this.status = status;
    }

    public Game(int playerID,String gameMode, String word, int remainingAttempts, String status) {
        this.playerID = playerID;
        this.gameMode = gameMode;
        this.word = word;
        this.remainingAttempts = remainingAttempts;
        this.status = status;
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getRemainingAttempts() {
        return remainingAttempts;
    }

    public void setRemainingAttempts(int remainingAttempts) {
        this.remainingAttempts = remainingAttempts;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
