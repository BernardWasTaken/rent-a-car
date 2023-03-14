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
    Button cancel_btn_lg;
    @FXML
    Button apply_btn_lg;
    @FXML
    TextField surname_lg;
    @FXML
    Button logout_btn;
    @FXML
    Button side_rent;

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
    private void rent_Click() throws IOException{
        side_rent.setId("tab-selected");
        side_dashboard.setId("tab-unselected");
        side_profile.setId("tab-unselected");
        side_settings.setId("tab-unselected");

        profile_pane.setVisible(false);
        dashboard_pane.setVisible(false);
        settings_pane.setVisible(false);
    }

    private void logged_in()
    {
        side_dashboard.setVisible(true);
        side_rent.setVisible(true);

        text_username.setVisible(false);
        text_password.setVisible(false);
        login_btn.setVisible(false);

        fillData();

        profile_image_lg.setVisible(true);
        username_field_lg.setVisible(true);
        password_field_lg.setVisible(true);
        real_name_lg.setVisible(true);
        cancel_btn_lg.setVisible(true);
        apply_btn_lg.setVisible(true);
        surname_lg.setVisible(true);
        logout_btn.setVisible(true);
    }

    private void logged_out()
    {
        side_dashboard.setVisible(false);

        text_username.setVisible(true);
        text_password.setVisible(true);
        login_btn.setVisible(true);

        profile_image_lg.setVisible(false);
        username_field_lg.setVisible(false);
        password_field_lg.setVisible(false);
        real_name_lg.setVisible(false);
        cancel_btn_lg.setVisible(false);
        apply_btn_lg.setVisible(false);
        surname_lg.setVisible(false);
        logout_btn.setVisible(false);

        text_username.setText(null);
        text_password.setText(null);

        side_profile.setId("tab-selected");
        side_dashboard.setId("tab-unselected");
        side_settings.setId("tab-unselected");

        profile_pane.setVisible(true);
        dashboard_pane.setVisible(false);
        settings_pane.setVisible(false);
    }

    @FXML
    private void cancel_Click() throws IOException{
        fillData();
    }

    @FXML
    private void apply_Click() throws IOException{
        cb.updateData(
            text_username.getText(),
            username_field_lg.getText(),
            real_name_lg.getText(),
            surname_lg.getText(),
            password_field_lg.getText()
        );

        text_username.setText(username_field_lg.getText());
    }

    @FXML
    private void profile_Click() throws IOException{
        side_profile.setId("tab-selected");
        side_dashboard.setId("tab-unselected");
        side_settings.setId("tab-unselected");
        side_rent.setId("tab-unselected");

        profile_pane.setVisible(true);
        dashboard_pane.setVisible(false);
        settings_pane.setVisible(false);
    }

    @FXML
    private void logout_Click() throws IOException{
        logged_out();
    }

    private void fillData(){
        username_field_lg.setText(cb.getUserInfo(text_username.getText(), text_password.getText()).get(0));
        password_field_lg.setText(cb.getUserInfo(text_username.getText(), text_password.getText()).get(1));
        real_name_lg.setText(cb.getUserInfo(text_username.getText(), text_password.getText()).get(2));
        surname_lg.setText(cb.getUserInfo(text_username.getText(), text_password.getText()).get(3));
    }

    @FXML
    private void login_Click() throws IOException{
        try {
            if(cb.LogIn(text_username.getText(), text_password.getText()) == 1)
            {
                logged_in();
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
        side_rent.setId("tab-unselected");

        profile_pane.setVisible(false);
        dashboard_pane.setVisible(true);
        settings_pane.setVisible(false);
    }

    @FXML
    private void settings_Click() throws IOException{
        side_profile.setId("tab-unselected");
        side_dashboard.setId("tab-unselected");
        side_settings.setId("tab-selected");
        side_rent.setId("tab-unselected");

        profile_pane.setVisible(false);
        dashboard_pane.setVisible(false);
        settings_pane.setVisible(true);
    }
}
