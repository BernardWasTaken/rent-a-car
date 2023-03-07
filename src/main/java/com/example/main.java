package com.example;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class main {
    
    @FXML
    Button side_profile;
    @FXML
    Button side_dashboard;
    @FXML
    Button side_settings;
    @FXML
    Pane profile_pane;
    @FXML
    Pane dashboard_pane;
    @FXML
    Pane settings_pane;
    @FXML
    TextField text_username;
    @FXML
    TextField text_password;
    @FXML
    Button login_btn;

    @FXML
    private void initialize() {
        if (profile_pane == null) {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("main.fxml"));
            loader.setController(this);
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("error");
            }
        }
    }

    @FXML
    private void profile_Click() throws IOException{
        side_profile.setId("tab-selected");
        side_dashboard.setId("tab-unselected");
        side_settings.setId("tab-unselected");

        profile_pane.setVisible(true);
        dashboard_pane.setVisible(false);
        settings_pane.setVisible(false);
    }

    @FXML
    private void login_Click() throws IOException{
        //login logic
        try {
            System.out.println("username: "+text_username.getText() + "\n" + "password: " +text_password.getText());
        } catch (Exception e) {
            System.out.println("wrong login credentials");
        }
    }

    @FXML
    private void dashboard_Click() throws IOException{
        side_profile.setId("tab-unselected");
        side_dashboard.setId("tab-selected");
        side_settings.setId("tab-unselected");

        profile_pane.setVisible(false);
        dashboard_pane.setVisible(true);
        settings_pane.setVisible(false);
    }

    @FXML
    private void settings_Click() throws IOException{
        side_profile.setId("tab-unselected");
        side_dashboard.setId("tab-unselected");
        side_settings.setId("tab-selected");

        profile_pane.setVisible(false);
        dashboard_pane.setVisible(false);
        settings_pane.setVisible(true);
    }
}
