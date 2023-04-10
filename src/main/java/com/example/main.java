package com.example;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.ObjectMapper;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class main {
    
    connectionBase cb = new connectionBase();



    @FXML
    Button side_profile;
    @FXML
    Button side_dashboard;
    @FXML
    Button side_settings;
    @FXML
    AnchorPane profile_pane;
    @FXML
    AnchorPane dashboard_pane;
    @FXML
    AnchorPane settings_pane;
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
    Button side_users;
    @FXML
    AnchorPane rents_pane;
    @FXML
    AnchorPane createrent_pane;
    ComboBox car_combobox;
    ComboBox user_combobox;
    DatePicker fromdate_datepicker;
    DatePicker todate_datepicker;
    AnchorPane rents_pane_view;
    @FXML
    AnchorPane users_pane;

    @FXML
    private void initialize() {
        if (profile_pane == null) {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/main.fxml"));
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
        side_users.setId("tab-unselected");

        profile_pane.setVisible(false);
        dashboard_pane.setVisible(false);
        settings_pane.setVisible(false);
        users_pane.setVisible(false);
        rents_pane.setVisible(true);

        rents_pane.getChildren().clear();

        rents_pane_view.getChildren().clear();

        String rents = cb.GetAllRents();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(rents);

        // Extract "data" field value
        JsonNode dataArray = jsonNode.get("data");
        double buttonHeight = 20;
        int x = 0;
        // Iterate through the array and print each string
        if (dataArray.isArray()) {
            for (JsonNode node : dataArray) {
                String dataString = node.asText();
                Button button = new Button(dataString);
            
                // Set the ID of the button
                button.setId("tab-selected");
                
                // Set the width and height of the button
                button.setPrefWidth(rents_pane_view.getWidth());
                button.setPrefHeight(buttonHeight);
                
                // Set the position of the button below the previous button
                button.setLayoutY(buttonHeight * x);
                
                // Add the button to the AnchorPane
                users_pane.getChildren().add(button);

                x++;
            }
        }
    }

    int editrentbool = 0;
    private void CreateRent_Click() throws IOException {
        rents_pane.setVisible(false);
        createrent_pane.setVisible(true);

        String cars = cb.GetAllCars();
        String users = cb.getAllUsers();

        user_combobox.getItems().clear();
        car_combobox.getItems().clear();


        String users = cb.GetAllRents();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(rents);

        // Extract "data" field value
        JsonNode dataArray = jsonNode.get("data");

        // Iterate through the array and print each string
        if (dataArray.isArray()) {
            for (JsonNode node : dataArray) {
                String dataString = node.asText();
                user_combobox.getItems().add(dataString);
            }
        }
        
        String cars = cb.GetAllCars();

        objectMapper = new ObjectMapper();
        jsonNode = objectMapper.readTree(cars);

        // Extract "data" field value
        dataArray = jsonNode.get("data");

        // Iterate through the array and print each string
        if (dataArray.isArray()) {
            for (JsonNode node : dataArray) {
                String dataString = node.asText();
                car_combobox.getItems().add(dataString);
            }
        }
    }

    private void ConfirmCreateRent_Click() throws IOException {
        if (editrentbool == 0)
        {
            LocalDate selectedDate = fromdate_datepicker.getValue();
            String fromdate = selectedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            selectedDate = todate_datepicker.getValue();
            String todate = selectedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            int success = cb.CreateRent(car_combobox.getValue().toString(), user_combobox.getValue().toString(), fromdate, todate);

            if (success == -1)
            {
                
            }
            else
            {
                rents_pane.setVisible(true);
                createrent_pane.setVisible(false);
            }
        }
        else{
            LocalDate selectedDate = fromdate_datepicker.getValue();
            String fromdate = selectedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            selectedDate = todate_datepicker.getValue();
            String todate = selectedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            int success = cb.edit(car_combobox.getValue().toString(), user_combobox.getValue().toString(), fromdate, todate);

            if (success == -1)
            {
                
            }
            else
            {
                rents_pane.setVisible(true);
                createrent_pane.setVisible(false);
            }
        }
    }

    private void CalcelCreateRent_Click() throws IOException {
        rents_pane.setVisible(true);
        createrent_pane.setVisible(false);
    }

    private void addUsers() throws IOException{
        users_pane.prefWidthProperty().bind(App.scene.widthProperty());
        users_pane.prefHeightProperty().bind(App.scene.heightProperty());

        // Create an array of button texts
        String[] buttonTexts = {"Button 1", "Button 2", "Button 3"};

        // Calculate the height of each button based on the number of buttons and the height of the anchor pane
        double buttonHeight = 20;

        // Loop through the button texts and create a new button for each one
        for (int i = 0; i < buttonTexts.length; i++) {
            Button button = new Button(buttonTexts[i]);

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Handle button click event
                    JButton clickedButton = (JButton) e.getSource();
                    String[] text = clickedButton.getText().split("+");
                    EditRent(text[0]);
                }
            });
            
            // Set the ID of the button
            button.setId("tab-selected");
            
            // Set the width and height of the button
            button.setPrefWidth(users_pane.getWidth());
            button.setPrefHeight(buttonHeight);
            
            // Set the position of the button below the previous button
            button.setLayoutY(buttonHeight * i);
            
            // Add the button to the AnchorPane
            users_pane.getChildren().add(button);
        }
    }

    public void EditRent(String id)
    {
        String rent = cb.GetRent(id);

        String cars = cb.GetAllCars();
        String users = cb.getAllUsers();

        user_combobox.getItems().clear();
        car_combobox.getItems().clear();


        String users = cb.GetAllRents();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(rents);

        // Extract "data" field value
        JsonNode dataArray = jsonNode.get("data");

        // Iterate through the array and print each string
        if (dataArray.isArray()) {
            for (JsonNode node : dataArray) {
                String dataString = node.asText();
                user_combobox.getItems().add(dataString);
            }
        }
        
        String cars = cb.GetAllCars();

        objectMapper = new ObjectMapper();
        jsonNode = objectMapper.readTree(cars);

        // Extract "data" field value
        dataArray = jsonNode.get("data");

        // Iterate through the array and print each string
        if (dataArray.isArray()) {
            for (JsonNode node : dataArray) {
                String dataString = node.asText();
                car_combobox.getItems().add(dataString);
            }
        }

        objectMapper = new ObjectMapper();
        jsonNode = objectMapper.readTree(rent);

        // Extract "data" field value
        dataArray = jsonNode.get("data");

        if (dataArray.isArray()) {
            for (JsonNode node : dataArray) {
                String[] array = dataArray.toString().split("+");

                DateTimeFormatter formatedate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate fromdate = LocalDate.parse(array[3], formatedate);

                fromdate_datepicker.setValue(fromdate);
                
                LocalDate todate = LocalDate.parse(array[4], formatedate);

                todate_datepicker.setValue(todate);
            }
        }
    }
    private void logged_in()
    {
        side_dashboard.setVisible(true);
        side_rent.setVisible(true);
        side_users.setVisible(true);

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
        side_rent.setVisible(false);
        side_users.setVisible(false);


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
        side_rent.setId("tab-unselected");
        side_users.setId("tab-unselected");

        profile_pane.setVisible(true);
        dashboard_pane.setVisible(false);
        settings_pane.setVisible(false);
        rents_pane.setVisible(false);
        users_pane.setVisible(false);
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
        side_users.setId("tab-unselected");

        profile_pane.setVisible(true);
        dashboard_pane.setVisible(false);
        settings_pane.setVisible(false);
        rents_pane.setVisible(false);
        users_pane.setVisible(false);

        unloadUsers();
    }

    @FXML
    private void logout_Click() throws IOException{
        logged_out();
        unloadUsers();
    }

    @FXML
    private void users_Click() throws IOException{

        side_profile.setId("tab-unselected");
        side_dashboard.setId("tab-unselected");
        side_settings.setId("tab-unselected");
        side_rent.setId("tab-unselected");
        side_users.setId("tab-selected");

        profile_pane.setVisible(false);
        dashboard_pane.setVisible(false);
        settings_pane.setVisible(false);
        rents_pane.setVisible(false);
        users_pane.setVisible(true);

        addUsers();
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
        side_users.setId("tab-unselected");

        profile_pane.setVisible(false);
        dashboard_pane.setVisible(true);
        settings_pane.setVisible(false);
        rents_pane.setVisible(false);
        users_pane.setVisible(false);

        unloadUsers();
    }

    @FXML
    private void settings_Click() throws IOException{
        side_profile.setId("tab-unselected");
        side_dashboard.setId("tab-unselected");
        side_settings.setId("tab-selected");
        side_rent.setId("tab-unselected");
        side_users.setId("tab-unselected");

        profile_pane.setVisible(false);
        dashboard_pane.setVisible(false);
        settings_pane.setVisible(true);
        rents_pane.setVisible(false);
        users_pane.setVisible(false);

        unloadUsers();
    }
}
