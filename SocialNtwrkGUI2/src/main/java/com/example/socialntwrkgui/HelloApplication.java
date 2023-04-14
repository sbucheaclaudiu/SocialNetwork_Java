package com.example.socialntwrkgui;


import com.example.socialntwrkgui.controller.LoginController;
import com.example.socialntwrkgui.domain.Message;
import com.example.socialntwrkgui.repositoryDB.DataBaseRepoMessage;
import com.example.socialntwrkgui.service.ServiceFriendship;
import com.example.socialntwrkgui.service.ServiceMain;
import com.example.socialntwrkgui.service.ServiceMessage;
import com.example.socialntwrkgui.service.ServiceUser;
import com.example.socialntwrkgui.validators.MessageValidator;
import com.example.socialntwrkgui.validators.UserValidator;
import com.example.socialntwrkgui.validators.FriendshipValidator;
import com.example.socialntwrkgui.repositoryDB.DataBaseRepoFriendship;
import com.example.socialntwrkgui.repositoryDB.DataBaseRepoUser;
import com.example.socialntwrkgui.domain.User;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class HelloApplication extends Application {

    ServiceMain service;

    @Override
    public void start(Stage stage) throws IOException {
        UserValidator user_validator = new UserValidator();
        FriendshipValidator friendship_validator = new FriendshipValidator();
        MessageValidator message_validator = new MessageValidator();

        DataBaseRepoUser repo_user = new DataBaseRepoUser("socialnetwork", "sbucheaclaudiu","fcbarcelona", "users", user_validator);
        DataBaseRepoFriendship repo_friendship = new DataBaseRepoFriendship("socialnetwork", "sbucheaclaudiu","fcbarcelona", "friendships", friendship_validator);
        DataBaseRepoMessage repo_message = new DataBaseRepoMessage("socialnetwork", "sbucheaclaudiu","fcbarcelona", " messages", message_validator);

        ServiceUser srv_user = new ServiceUser(repo_user);
        ServiceFriendship srv_friendship = new ServiceFriendship(repo_friendship);
        ServiceMessage srv_message = new ServiceMessage(repo_message);

        ServiceMain srv = new ServiceMain(srv_user, srv_friendship, srv_message);
        this.service = srv;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/loginView.fxml"));
        Parent root = fxmlLoader.load();
        LoginController loginController = fxmlLoader.getController();
        loginController.setService(service);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("SOCIAL NETWORK");
    }

    public static void main(String[] args) {
        launch();
    }

}