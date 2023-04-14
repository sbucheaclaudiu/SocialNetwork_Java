package com.example.socialntwrkgui.controller;

import com.example.socialntwrkgui.domain.Message;
import com.example.socialntwrkgui.domain.User;
import com.example.socialntwrkgui.service.ServiceMain;
import com.example.socialntwrkgui.utils.events.MessageEntityChangeEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TimerTask;
import java.util.Timer;

public class MessageController implements Initializable {
    ServiceMain service;

    User user_curent;

    User user_dest;

    private ObservableList<String> messages;
    @FXML
    private ListView<String> messageView;

    @FXML
    private Button goBackButton;

    @FXML
    private TextField messageField;

    @FXML
    private Button sendButton;

    @FXML
    void handleGoBackButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/mainView.fxml"));

        Parent root = loader.load();

        MainController mainController = loader.getController();
        mainController.setUser(this.user_curent);
        mainController.setService(service);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("SOCIAL NETWORK");
    }

    @FXML
    void handleSendButton(ActionEvent event) throws Exception {
        String formatMessage = user_curent.getFirstName() + ": " + messageField.getText();
        messages.add(formatMessage);
        service.addMessage(user_curent.getID(), user_dest.getID(), formatMessage);
        messageField.clear();
    }

    void fillMessages(ActionEvent event) throws Exception {
        List<String> msg = this.service.getMessages(user_curent.getID(), user_dest.getID());
        messages = FXCollections.observableList(msg);
        messageView.setItems(messages);
    }

    void fill(){
        List<String> msg = this.service.getMessages(user_curent.getID(), user_dest.getID());
        messages = FXCollections.observableList(msg);
        messageView.setItems(messages);
    }

    void refresh(){
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                fill();
            }
        }, 0, 5000);
    }


    public void initialize(URL location, ResourceBundle resources) {
        messages = FXCollections.observableArrayList();
    }

    public void setService(ServiceMain service){
        this.service = service;
        refresh();
    }

    public void setUserCurent(User u) {
        this.user_curent = u;
    }

    public void setUserDest(User u) {
        this.user_dest = u;
    }
}
