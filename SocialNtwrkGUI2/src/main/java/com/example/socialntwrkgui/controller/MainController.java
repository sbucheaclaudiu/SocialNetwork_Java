package com.example.socialntwrkgui.controller;

import com.example.socialntwrkgui.domain.User;
import com.example.socialntwrkgui.service.ServiceMain;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    ServiceMain service;

    User user_curent;

    @FXML
    private Button acceptaButton;

    @FXML
    private TableView<User> FriendsTableView;

    @FXML
    private Button LoginButton;

    @FXML
    private Button PrieteniButton;

    @FXML
    private Button SugestiiButton;

    @FXML
    private Button addFriendButton;

    @FXML
    private Button anuleazaCerereaButton;

    @FXML
    private Button cereriPrimiteButton;

    @FXML
    private Button cereriTrimiseButton;

    @FXML
    private Button delFriendButton;

    @FXML
    private TableColumn<User, String> firstNameColumn;

    @FXML
    private Button mesajButton;

    @FXML
    private Button refuzaButton;

    @FXML
    private TableColumn<User, String> secondNameColumn;

    @FXML
    private TableColumn<User, LocalDate> dateColumn;

    @FXML
    void handleAddFriendButton(ActionEvent event) {
        try{
            User user = FriendsTableView.getSelectionModel().getSelectedItem();
            service.addFriendship(user_curent.getID(), user.getID());
            System.out.println(user.getFirst_name());
            fillSugestii(event);
        } catch (Exception e){
            PopupMessageController.showMessage(null, Alert.AlertType.INFORMATION, "Info", e.getMessage());
        }

    }

    @FXML
    void handleAnuleazaCerereaButton(ActionEvent event) {
        try{
            User user = FriendsTableView.getSelectionModel().getSelectedItem();
            service.deleteFriendship(user_curent.getID(), user.getID());
            fillCereriTrimise(event);
        } catch (Exception e) {
            PopupMessageController.showMessage(null, Alert.AlertType.INFORMATION, "Info", e.getMessage());
        }
    }

    @FXML
    void handleCereriPrimiteButton(ActionEvent event) {
        try{
            fillCereriPrimite(event);
        }
        catch (Exception e){
            PopupMessageController.showMessage(null, Alert.AlertType.INFORMATION, "Info", e.getMessage());
        }
    }

    void fillCereriPrimite(ActionEvent event) throws Exception {
        List<User> lst_user = service.cereriPrimiteList(this.user_curent.getID());

        ObservableList<User> list = FXCollections.observableList(lst_user);

        FriendsTableView.setItems(list);
        setButtonsInvisible();
        acceptaButton.setVisible(true);
        refuzaButton.setVisible(true);
    }

    @FXML
    void handleCereriTrimiseButton(ActionEvent event) {
        try{
            fillCereriTrimise(event);
        }
        catch (Exception e){
            PopupMessageController.showMessage(null, Alert.AlertType.INFORMATION, "Info", e.getMessage());
        }


    }

    void fillCereriTrimise(ActionEvent event) throws Exception {
        List<User> lst_user = service.cereriTrimiseList(this.user_curent.getID());

        ObservableList<User> list = FXCollections.observableList(lst_user);

        FriendsTableView.setItems(list);
        setButtonsInvisible();
        anuleazaCerereaButton.setVisible(true);
    }

    @FXML
    void handleDelFriendButton(ActionEvent event) {
        try{
            User user = FriendsTableView.getSelectionModel().getSelectedItem();
            service.deleteFriendship(user_curent.getID(), user.getID());
            fillFriends(event);
        } catch (Exception e) {
            PopupMessageController.showMessage(null, Alert.AlertType.INFORMATION, "Info", e.getMessage());
        }

    }

    @FXML
    void handleLoginButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/loginView.fxml"));

        Parent root = loader.load();

        LoginController loginController = loader.getController();
        loginController.setService(service);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("SOCIAL NETWORK");

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("first_name"));
        secondNameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("second_name"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<User, LocalDate>("date"));
    }
    private void setButtonsInvisible() {
        addFriendButton.setVisible(false);
        delFriendButton.setVisible(false);
        refuzaButton.setVisible(false);
        anuleazaCerereaButton.setVisible(false);
        mesajButton.setVisible(false);
        acceptaButton.setVisible(false);
    }

    @FXML
    void handleMesajButton(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/chatView.fxml"));

        User usr_dest = FriendsTableView.getSelectionModel().getSelectedItem();
        Parent root = loader.load();

        MessageController messageController = loader.getController();
        messageController.setUserCurent(this.user_curent);
        messageController.setUserDest(usr_dest);
        messageController.setService(service);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("SOCIAL NETWORK");
        messageController.fillMessages(event);

    }

    @FXML
    void handlePrieteniButton(ActionEvent event) {
        try{
            fillFriends(event);
        }
        catch (Exception e){
            PopupMessageController.showMessage(null, Alert.AlertType.INFORMATION, "Info", e.getMessage());
        }

    }

    void fillFriends(ActionEvent event) throws Exception {
        List<User> lst_user = service.friendsList(this.user_curent.getID());

        ObservableList<User> list = FXCollections.observableList(lst_user);

        FriendsTableView.setItems(list);
        setButtonsInvisible();
        delFriendButton.setVisible(true);
        mesajButton.setVisible(true);
    }


    @FXML
    void handleRefuzaButton(ActionEvent event) {
        try{
            User user = FriendsTableView.getSelectionModel().getSelectedItem();
            service.deleteFriendship(user_curent.getID(), user.getID());
            fillCereriPrimite(event);
        } catch (Exception e) {
            PopupMessageController.showMessage(null, Alert.AlertType.INFORMATION, "Info", e.getMessage());
        }

    }

    @FXML
    void handleSugestiiButton(ActionEvent event) {
        try{
            fillSugestii(event);
        }
        catch (Exception e){
            PopupMessageController.showMessage(null, Alert.AlertType.INFORMATION, "Info", e.getMessage());
        }

    }

    void fillSugestii(ActionEvent event) throws Exception {
        List<User> lst_user = service.sugestiiList(this.user_curent.getID());

        ObservableList<User> list = FXCollections.observableList(lst_user);

        FriendsTableView.setItems(list);
        setButtonsInvisible();
        addFriendButton.setVisible(true);
    }

    @FXML
    void handleAcceptaButton(ActionEvent event){
        try{
            User user = FriendsTableView.getSelectionModel().getSelectedItem();
            service.acceptaFriendship(user_curent.getID(), user.getID());
            fillCereriPrimite(event);
        } catch (Exception e) {
            PopupMessageController.showMessage(null, Alert.AlertType.INFORMATION, "Info", e.getMessage());
        }
    }


    public void setService(ServiceMain service){
        this.service = service;
    }

    public void setUser(User u) {
        this.user_curent = u;
    }

}

