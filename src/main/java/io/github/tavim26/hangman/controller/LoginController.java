package io.github.tavim26.hangman.controller;

import io.github.tavim26.hangman.controller.SceneManager.View;
import io.github.tavim26.hangman.model.Player;
import io.github.tavim26.hangman.service.PlayerService;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

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
    private CheckBox showPasswordBox;

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
    @FXML
    private CheckBox showSignUpPasswordBox;

    private final PlayerService playerService = new PlayerService();

    // Plain text fields shown instead of the password fields when "Show password" is checked
    private TextField visiblePasswordField;
    private TextField visibleSignUpPasswordField;
    private TextField visibleRepeatPasswordField;

    @FXML
    private void initialize()
    {
        visiblePasswordField = createVisibleCopy(passwordField);
        visibleSignUpPasswordField = createVisibleCopy(signUpPasswordField);
        visibleRepeatPasswordField = createVisibleCopy(repeatPasswordField);
    }

    @FXML
    private void clickShowPassword()
    {
        setPasswordVisible(passwordField, visiblePasswordField, showPasswordBox.isSelected());
    }

    @FXML
    private void clickShowSignUpPassword()
    {
        boolean visible = showSignUpPasswordBox.isSelected();
        setPasswordVisible(signUpPasswordField, visibleSignUpPasswordField, visible);
        setPasswordVisible(repeatPasswordField, visibleRepeatPasswordField, visible);
    }

    @FXML
    private void clickSignUp()
    {
        String username = signUpUsernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = signUpPasswordField.getText();
        String repeatPassword = repeatPasswordField.getText();

        if (!playerService.isValidUsername(username))
        {
            Alerts.error("Invalid username", "Please enter a username of at most "
                    + PlayerService.MAX_USERNAME_LENGTH + " characters.");
            return;
        }

        if (!playerService.isValidEmail(email))
        {
            Alerts.error("Invalid email", "Please enter a valid email address.");
            return;
        }

        if (!playerService.isValidPassword(password))
        {
            Alerts.error("Invalid password", "The password must have at least "
                    + PlayerService.MIN_PASSWORD_LENGTH + " characters.");
            return;
        }

        if (!password.equals(repeatPassword))
        {
            Alerts.error("Passwords do not match", "Please type the same password in both fields.");
            return;
        }

        try
        {
            if (playerService.isUsernameTaken(username))
            {
                Alerts.error("Username already exists", "A player with this username already exists.");
                return;
            }

            if (playerService.isEmailTaken(email))
            {
                Alerts.error("Email already exists", "A player with this email already exists.");
                return;
            }

            playerService.register(username, email, password);
        }
        catch (SQLException e)
        {
            Alerts.databaseError(e);
            return;
        }

        Alerts.info("Account created", "Your account was created. You can now log in.");

        signUpUsernameField.clear();
        emailField.clear();
        signUpPasswordField.clear();
        repeatPasswordField.clear();

        usernameField.setText(username);
        clickLoginAccount();
    }

    @FXML
    private void clickLogIn() throws IOException
    {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty())
        {
            Alerts.error("Missing credentials", "Please enter your username and password.");
            return;
        }

        Player player;
        try
        {
            player = playerService.login(username, password);
        }
        catch (SQLException e)
        {
            Alerts.databaseError(e);
            return;
        }

        if (player == null)
        {
            Alerts.error("Log in failed", "Username and password do not match.");
            return;
        }

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

    /**
     * Creates a plain text field that shares its text with the given password field
     * and sits next to it in the layout, hidden until "Show password" is checked.
     */
    private TextField createVisibleCopy(PasswordField passwordField)
    {
        TextField copy = new TextField();
        copy.setPromptText(passwordField.getPromptText());
        copy.setFont(passwordField.getFont());
        copy.setPrefSize(passwordField.getPrefWidth(), passwordField.getPrefHeight());
        copy.textProperty().bindBidirectional(passwordField.textProperty());

        Pane parent = (Pane) passwordField.getParent();
        parent.getChildren().add(parent.getChildren().indexOf(passwordField) + 1, copy);

        setPasswordVisible(passwordField, copy, false);
        return copy;
    }

    private void setPasswordVisible(PasswordField passwordField, TextField visibleCopy, boolean visible)
    {
        passwordField.setVisible(!visible);
        passwordField.setManaged(!visible);
        visibleCopy.setVisible(visible);
        visibleCopy.setManaged(visible);
    }
}