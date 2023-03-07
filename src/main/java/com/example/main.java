package com.example;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class main {
    
    connectionBase cb = new connectionBase();

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
    TextField username_field_lg;
    @FXML
    TextField password_field_lg;
    @FXML
    TextField real_name_lg;
    @FXML
    Button login_btn;
    @FXML
    ImageView profile_image_lg;

    @FXML
    private void initialize() {
        if (profile_pane == null) {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("main.fxml"));
            loader.setController(this);
            try {
                cb.connect();
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
        try {
            if(cb.LogIn(text_username.getText(), text_password.getText()) == 1)
            {
                side_dashboard.setVisible(true);

                text_username.setVisible(false);
                text_password.setVisible(false);
                login_btn.setVisible(false);



                username_field_lg.setText(cb.getUserInfo(text_username.getText(), text_password.getText()).get(0));
                password_field_lg.setText(cb.getUserInfo(text_username.getText(), text_password.getText()).get(1));
                real_name_lg.setText(cb.getUserInfo(text_username.getText(), text_password.getText()).get(2));



                profile_image_lg.setVisible(true);
                username_field_lg.setVisible(true);
                password_field_lg.setVisible(true);
                real_name_lg.setVisible(true);
            }
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
