package GUI;

import BusinessLogic.PlayerLogic;
import Model.Player;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.SQLException;


public class MainController
{

    @FXML
    private TextField usernameField1;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField1;

    @FXML
    private PasswordField repeatPasswordField;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;


    @FXML
    private CheckBox showPasswordBox1;

    @FXML
    private Button signUpButton;

    @FXML
    private Button loginAccButton;

    @FXML
    private Label signUpLabel;

    @FXML
    private Label alreadyAccountLabel;

    @FXML
    private AnchorPane logInPane;

    @FXML
    private AnchorPane signUpPane;


    @FXML
    private CheckBox showPasswordBox;

    @FXML
    private Button logInButton;

    @FXML
    private Button createAccButton;

    @FXML
    private Label signInLabel;

    @FXML
    private Label registerAccountLabel;


    PlayerLogic playerLogic;

    public MainController()
    {
        playerLogic = new PlayerLogic();
    }


    @FXML
    public void clickShowPassword()
    {

    }


    @FXML
    public void clickShowPassword1()
    {


    }

    @FXML
    public void clickSignUp() throws SQLException {
        String username = usernameField1.getText();
        String password = passwordField1.getText();
        String repeatPassword = repeatPasswordField.getText();
        String email = emailField.getText();

        if(password.equals(repeatPassword) == false)
        {
            showAlert(Alert.AlertType.ERROR, "Passwords do not match", "Passwords do not match");
            return;
        }

        Player newPlayer = new Player(username, email, password);

        if(playerLogic.usernameValidator(username) == false)
        {
            showAlert(Alert.AlertType.ERROR, "Invalid username", "Please enter a valid username");
            return;
        }

        if(playerLogic.passwordValidator(password) == false)
        {
            showAlert(Alert.AlertType.ERROR, "Invalid password", "Please enter your password");
            return;
        }

        if(playerLogic.emailValidator(email) == false)
        {
            showAlert(Alert.AlertType.ERROR, "Invalid email", "Please enter a valid email address");
            return;
        }

        if(playerLogic.alreadyExists(newPlayer) == 1)
        {
            showAlert(Alert.AlertType.ERROR, "Username Already Exists", "A player with this username already exists");
            return;
        }

        if(playerLogic.alreadyExists(newPlayer) == 2)
        {
            showAlert(Alert.AlertType.ERROR, "Email Already Exists", "A player with this email already exists");
            return;
        }


        if(playerLogic.playerValidator(newPlayer) == false)
        {
            showAlert(Alert.AlertType.ERROR, "Invalid player", "This is not a valid player");
            return;
        }


        playerLogic.addPlayer(newPlayer);
        showAlert(Alert.AlertType.CONFIRMATION, "Player Added", "Player added successfully");

    }




    @FXML
    public void clickLogIn() throws SQLException, IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();


        if(playerLogic.usernameValidator(username) == false)
        {
            showAlert(Alert.AlertType.ERROR, "Invalid username", "Please enter a valid username");
            return;
        }

        if(playerLogic.passwordValidator(password) == false)
        {
            showAlert(Alert.AlertType.ERROR, "Invalid password", "Please enter your password");
            return;
        }

        Player currentPlayer = playerLogic.gePlayerLogin(username, password);

        if(currentPlayer== null)
        {
            showAlert(Alert.AlertType.ERROR, "Player does not exist", "Username and password do not match");
            return;
        }


        showAlert(Alert.AlertType.CONFIRMATION,"Log In Successful", "Log In Successful");
        //ce se intampla cand logarea merge
        SceneManager.changeSceneWithUserData("/SceneBuilder/player.fxml",632,435,currentPlayer);


    }




    @FXML
    public void clickLoginAccount()
    {
        logInPane.setVisible(true);
        signUpPane.setVisible(false);

    }

    @FXML
    public void clickCreateAccount()
    {
        logInPane.setVisible(false);
        signUpPane.setVisible(true);
    }



    private void showAlert(Alert.AlertType alertType, String title, String message)
    {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);

        alert.setContentText(message);
        alert.showAndWait();
    }




}

