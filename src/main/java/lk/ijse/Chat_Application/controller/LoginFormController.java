package lk.ijse.Chat_Application.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFormController {
    @FXML
    private TextField txtName;
    static String userName;
    @FXML
    private JFXButton btnLogin;


    @FXML
    void btnLoginOnAction(ActionEvent event) throws IOException {
        userName = txtName.getText();

        if (userName != null && !userName.isEmpty()) {
            txtName.clear();
            Stage stage = new Stage();
            stage.setScene(new Scene(FXMLLoader.load(LoginFormController.class.getResource("/view/client_form.fxml"))));
            stage.close();
            stage.centerOnScreen();
            stage.setTitle("Chat App");
            stage.show();
        }else {
            new Alert(Alert.AlertType.CONFIRMATION,"Enter your name").show();
        }
    }



    @FXML
    void enterNameOnAction(ActionEvent event) throws IOException {
        userName = txtName.getText();
        if (userName != null && !userName.isEmpty()) {
            txtName.clear();
            Stage stage = new Stage();
            stage.setScene(new Scene(FXMLLoader.load(LoginFormController.class.getResource("/view/client_form.fxml"))));
            stage.close();
            stage.centerOnScreen();
            stage.setTitle("Chat App");
            stage.show();
        }else {
            new Alert(Alert.AlertType.CONFIRMATION,"Enter your name").show();
        }

    }

    @FXML
    void logOut(MouseEvent event) {
        System.exit(0);

    }
}
