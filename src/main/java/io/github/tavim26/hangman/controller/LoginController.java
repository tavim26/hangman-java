package io.github.tavim26.hangman.controller;

import io.github.tavim26.hangman.controller.SceneManager.View;
import io.github.tavim26.hangman.model.Player;
import io.github.tavim26.hangman.service.PlayerService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController
{
    @FXML
    private AnchorPane logInPane;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    @FXML
    private AnchorPane signUpPane;
    @FXML
    private TextField signUpUsernameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField signUpPasswordField;
    @FXML
    private PasswordField repeatPasswordField;

    private final PlayerService playerService = new PlayerService();

    @FXML
    private void clickShowPassword()
    {
    }

    @FXML
    private void clickShowPassword1()
    {
    }

    @FXML
    private void clickSignUp() throws SQLException
    {
        String username = signUpUsernameField.getText();
        String email = emailField.getText();
        String password = signUpPasswordField.getText();
        String repeatPassword = repeatPasswordField.getText();

        if (!password.equals(repeatPassword))
        {
            showAlert(Alert.AlertType.ERROR, "Passwords do not match", "Passwords do not match");
            return;
        }

        if (!playerService.isValidUsername(username))
        {
            showAlert(Alert.AlertType.ERROR, "Invalid username", "Please enter a valid username");
            return;
        }

        if (!playerService.isValidPassword(password))
        {
            showAlert(Alert.AlertType.ERROR, "Invalid password", "Please enter your password");
            return;
        }

        if (!playerService.isValidEmail(email))
        {
            showAlert(Alert.AlertType.ERROR, "Invalid email", "Please enter a valid email address");
            return;
        }

        if (playerService.isUsernameTaken(username))
        {
            showAlert(Alert.AlertType.ERROR, "Username Already Exists", "A player with this username already exists");
            return;
        }

        if (playerService.isEmailTaken(email))
        {
            showAlert(Alert.AlertType.ERROR, "Email Already Exists", "A player with this email already exists");
            return;
        }

        playerService.register(new Player(username, email, password));
        showAlert(Alert.AlertType.CONFIRMATION, "Player Added", "Player added successfully");
    }

    @FXML
    private void clickLogIn() throws SQLException, IOException
    {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (!playerService.isValidUsername(username))
        {
            showAlert(Alert.AlertType.ERROR, "Invalid username", "Please enter a valid username");
            return;
        }

        if (!playerService.isValidPassword(password))
        {
            showAlert(Alert.AlertType.ERROR, "Invalid password", "Please enter your password");
            return;
        }

        Player player = playerService.login(username, password);

        if (player == null)
        {
            showAlert(Alert.AlertType.ERROR, "Player does not exist", "Username and password do not match");
            return;
        }

        showAlert(Alert.AlertType.CONFIRMATION, "Log In Successful", "Log In Successful");

        DashboardController dashboard = SceneManager.show(View.DASHBOARD);
        dashboard.setPlayer(player);
    }

    @FXML
    private void clickLoginAccount()
    {
        logInPane.setVisible(true);
        signUpPane.setVisible(false);
    }

    @FXML
    private void clickCreateAccount()
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