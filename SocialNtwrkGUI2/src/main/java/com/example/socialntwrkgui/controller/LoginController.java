package com.example.socialntwrkgui.controller;

import com.example.socialntwrkgui.domain.Friendship;
import com.example.socialntwrkgui.domain.User;
import com.example.socialntwrkgui.exceptions.LackException;
import com.example.socialntwrkgui.service.ServiceMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.List;

public class LoginController {
    ServiceMain service;

    @FXML
    private TextField firstNameField;

    @FXML
    private Button loginButton;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField secondNameField;

    @FXML
    void handleLoginButton(ActionEvent event) throws IOException {
        String firstName = firstNameField.getText();
        String secondName = secondNameField.getText();
        String password = passwordField.getText();
        AtomicBoolean found= new AtomicBoolean(false);

        List<User> lst = this.service.getAllUsers();
        User user_princ = null;
        int ok = 1;

        for(User u : lst){
            if(u.getFirstName().equals(firstName) && u.getSecondName().equals(secondName) && u.getPassword().equals(password)) {
                user_princ = u;
                ok = 0;
            }
        }

        if(ok == 0){
            PopupMessageController.showMessage(null, Alert.AlertType.INFORMATION, "Info", "LOGIN MERS CU SUCCES!\nSalut, " + user_princ.getFirst_name() + " " + user_princ.getSecondName()+"!");
            //openMainPage(user_princ, event);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/mainView.fxml"));

            Parent root = loader.load();

            MainController mainController = loader.getController();
            mainController.setUser(user_princ);
            mainController.setService(service);

            firstNameField.clear();
            secondNameField.clear();
            passwordField.clear();

            Scene scene = new Scene(root);
            Stage secondStage = new Stage();
            secondStage.setScene(scene);
            secondStage.show();
            secondStage.setTitle("SOCIAL NETWORK");
        }
        else {
            try {
                int ok1 = this.service.verifyName(firstName, secondName);
                if(ok1 == 1) throw new LackException("dde");
                List<User> lst3 = this.service.getAllUsers();
                List<Long> lst_id = new ArrayList<>();
                for(User m : lst3){
                    lst_id.add(m.getID());
                }
                Long max = Collections.max(lst_id, null);
                User user = new User(max+2, firstName, secondName, password);
                this.service.addUser(max+2, firstName, secondName, password);
                PopupMessageController.showMessage(null, Alert.AlertType.INFORMATION, "Info", "CONT NOU CREAT CU SUCCES!\nSalut, " + user.getFirst_name() + " " + user.getSecondName()+"!");
                //openMainPage(user, event);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/mainView.fxml"));

                Parent root = loader.load();

                MainController mainController = loader.getController();
                mainController.setUser(user);
                mainController.setService(service);

                firstNameField.clear();
                secondNameField.clear();
                passwordField.clear();

                Scene scene = new Scene(root);
                Stage secondStage = new Stage();
                secondStage.setScene(scene);
                secondStage.show();
                secondStage.setTitle("SOCIAL NETWORK");
            }
            catch (LackException e){
                PopupMessageController.showMessage(null, Alert.AlertType.INFORMATION, "Info", "Parola introdusa gresit!");
            }
        }
    }

    private void openMainPage(User u, ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/mainView.fxml"));

        Parent root = loader.load();

        MainController mainController = loader.getController();
        mainController.setUser(u);
        mainController.setService(service);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("SOCIAL NETWORK");
    }

    public void setService(ServiceMain service){
        this.service = service;
    }

}
