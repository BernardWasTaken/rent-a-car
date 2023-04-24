package com.example;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.event.Event;
import javafx.event.EventHandler;
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

    Gson gson = new Gson();

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
    AnchorPane users_pane_view;
    AnchorPane createuser_pane;
    TextField firstname;
    TextField surname;
    DatePicker birth;
    ComboBox city;
    TextField username;
    TextField password;
    AnchorPane cars_pane_view;
    AnchorPane create_car_pane;
    TextField carname_textfield;
    TextField licenceplate_textfield;
    ComboBox garage_combobox;
    TextField kilometers_textfield;

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

        rents_pane_view.getChildren().clear();

        String rents = cb.GetAllRents();

        JsonElement jsonString = new JsonParser().parse(rents);
        JsonObject jsonObject = jsonString.getAsJsonObject();
        String data = jsonObject.get("data").getAsString();
        String[] dataArray = data.split(",");


        double buttonHeight = 20;
        String test = "";
        int x = 0;
        // Iterate through the array and print each string
        for (String info : dataArray) {
            String dataString = info;
            Button button = new Button(dataString);

            String[] infoArray = dataString.split(" + ");

            button.onClick = Function()
            {
                EditRent(infoArray[0]);
            }

            // Set the ID of the button
            button.setId("tab-selected");

            // Set the width and height of the button
            button.setPrefWidth(rents_pane_view.getWidth());
            button.setPrefHeight(buttonHeight);

            // Set the position of the button below the previous button
            button.setLayoutY(buttonHeight * x);

            // Add the button to the AnchorPane
            rents_pane_view.getChildren().add(button);

            x++;
        }
    }
    String edituserbool = "0";
    private void CreateUser_Click() throws IOException {
        users_pane.setVisible(false);
        createuser_pane.setVisible(true);

        String citys = cb.GetAllCitys();
        city.getItems().clear();

        JsonElement jsonString = new JsonParser().parse(citys);
        JsonObject jsonObject = jsonString.getAsJsonObject();
        String data = jsonObject.get("data").getAsString();
        String[] stringData = data.split(",");

        // Iterate through the array and print each string
        for (String info : stringData) {
            String dataString = info;
            city.getItems().add(dataString);
        }
    }

    public void CancelCreateUser_Click() throws IOException {
        users_pane.setVisible(true);
        createuser_pane.setVisible(false);

        edituserbool = "0";
    }

    public void ConfirmCreateUser_Click() throws IOException {
        if (edituserbool == "0")
        {
            LocalDate selectedDate = birth.getValue();
            String birth = selectedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            int success = cb.CreateUser(firstname.getText(), surname.getText(), birth, city.getValue().toString(), "zamenjaj", username.getText(), password.getText());

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

            int success = cb.EditUser(edituserbool, firstname.getText(), surname.getText(), username.getText(), password.getText());

            if (success == -1)
            {
                
            }
            else
            {
                users_pane.setVisible(true);
                createuser_pane.setVisible(false);
            }
        }
    }

    String editrentbool = "0";
    public void CancelCreateRent_Click() throws IOException {
        rents_pane.setVisible(true);
        createrent_pane.setVisible(false);

        editrentbool = "0";
    }

    private void CreateRent_Click() throws IOException {
        rents_pane.setVisible(false);
        createrent_pane.setVisible(true);

        String cars = cb.GetAllCars();
        String users = cb.getAllUsers();

        user_combobox.getItems().clear();
        car_combobox.getItems().clear();

        JsonElement jsonString = new JsonParser().parse(users);
        JsonObject jsonObject = jsonString.getAsJsonObject();
        String data = jsonObject.get("data").getAsString();
        String[] stringData = data.split(",");

        // Iterate through the array and print each string
        for (String info : stringData) {
            String dataString = info;
            user_combobox.getItems().add(dataString);
        }

        jsonString = new JsonParser().parse(cars);
        jsonObject = jsonString.getAsJsonObject();
        data = jsonObject.get("data").getAsString();
        stringData = data.split(",");

        // Iterate through the array and print each string
        for (String info : stringData) {
            String dataString = info;
            car_combobox.getItems().add(dataString);
        }
    }

    private void ConfirmCreateRent_Click() throws IOException {
        if (editrentbool == "0")
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

            int success = cb.EditRent(editrentbool, car_combobox.getValue().toString(), user_combobox.getValue().toString(), fromdate, todate);

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

    private void ConfirmCreateCar_Click() throws IOException {
        if (editcarbool == "0")
        {
            String[] garage = garage_combobox.getValue().toString().split(" + ");

            int success = cb.CreateCar(carname_textfield.getText(), licenceplate_textfield.getText(), garage[0], kilometers_textfield.getText());

            if (success == -1)
            {
                
            }
            else
            {
                dashboard_pane.setVisible(true);
                create_car_pane.setVisible(false);
            }
        }
        else{
            
            String[] garage = garage_combobox.getValue().toString().split(" + ");

            int success = cb.EditCar(editcarbool, carname_textfield.getText(), licenceplate_textfield.getText(), garage[0], kilometers_textfield.getText());

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

    public void EditUser(String username)
    {
        edituserbool = username;

        String cities = cb.GetAllCitys();

        city.getItems().clear();

        JsonElement jsonString = new JsonParser().parse(cities);
        JsonObject jsonObject = jsonString.getAsJsonObject();
        String data = jsonObject.get("data").getAsString();
        String[] stringData = data.split(",");

        // Iterate through the array and print each string
        for (String node : stringData) {
            String dataString = node;
            city.getItems().add(dataString);
        }

        String user = cb.GetUser(username);

        jsonString = new JsonParser().parse(user);
        jsonObject = jsonString.getAsJsonObject();
        data = jsonObject.get("data").getAsString();
        stringData = data.split(",");

        for (String node : stringData) {
            String[] array = node.toString().split(" + ");

            firstname.setText(array[1]);
            surname.setText(array[2]);

            DateTimeFormatter formatedate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate birthday = LocalDate.parse(array[3], formatedate);

            birth.setValue(birthday);

            this.username.setText(array[4]);
            password.setText(array[5]);
        }
    }

    public void EditRent(String id)
    {
        editrentbool = id;

        String rent = cb.GetRent(id);

        String cars = cb.GetAllCars();
        String users = cb.getAllUsers();

        user_combobox.getItems().clear();
        car_combobox.getItems().clear();

        JsonElement jsonString = new JsonParser().parse(users);
        JsonObject jsonObject = jsonString.getAsJsonObject();
        String data = jsonObject.get("data").getAsString();
        String[] stringData = data.split(",");

        // Iterate through the array and print each string
        for (String node : stringData) {
            String dataString = node;
            user_combobox.getItems().add(dataString);
        }

        jsonString = new JsonParser().parse(cars);
        jsonObject = jsonString.getAsJsonObject();
        data = jsonObject.get("data").getAsString();
        stringData = data.split(",");

        // Iterate through the array and print each string
        for (String node : stringData) {
            String dataString = node;
            car_combobox.getItems().add(dataString);
        }

        jsonString = new JsonParser().parse(rent);
        jsonObject = jsonString.getAsJsonObject();
        data = jsonObject.get("data").getAsString();
        stringData = data.split(",");

        for (String node : stringData) {
            String[] array = node.toString().split(" + ");

            DateTimeFormatter formatedate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate fromdate = LocalDate.parse(array[3], formatedate);

            fromdate_datepicker.setValue(fromdate);

            LocalDate todate = LocalDate.parse(array[4], formatedate);

            todate_datepicker.setValue(todate);
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

        users_pane_view.getChildren().clear();

        String rents = cb.GetAllUsers();
        JsonElement jsonString = new JsonParser().parse(rents);
        JsonObject jsonObject = jsonString.getAsJsonObject();
        String data = jsonObject.get("data").getAsString();
        String[] stringData = data.split(",");

        double buttonHeight = 20;
        int x = 0;
        // Iterate through the array and print each string
        for (String node : stringData) {
            String dataString = node;
            Button button = new Button(dataString);

            String[] nodeArray = dataString.split(" + ");

            button.onClick = Function()
            {
                EditUser(nodeArray[0]);
            }

            // Set the ID of the button
            button.setId("tab-selected");

            // Set the width and height of the button
            button.setPrefWidth(rents_pane_view.getWidth());
            button.setPrefHeight(buttonHeight);

            // Set the position of the button below the previous button
            button.setLayoutY(buttonHeight * x);

            // Add the button to the AnchorPane
            users_pane_view.getChildren().add(button);

            x++;
        }
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

    String editcarbool = "0";
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

        cars_pane_view.getChildren().clear();

        String cars = cb.GetAllCars();
        JsonElement jsonString = new JsonParser().parse(cars);
        JsonObject jsonObject = jsonString.getAsJsonObject();
        String data = jsonObject.get("data").getAsString();
        String[] stringData = data.split(",");

        double buttonHeight = 20;
        int x = 0;
        // Iterate through the array and print each string
        for (String node : stringData) {
            String dataString = node;
            Button button = new Button(dataString);

            String[] nodeArray = dataString.split(" + ");

            button.onClick() = Function()
            {
                EditCar(nodeArray[0]);
            }

            // Set the ID of the button
            button.setId("tab-selected");

            // Set the width and height of the button
            button.setPrefWidth(rents_pane_view.getWidth());
            button.setPrefHeight(buttonHeight);

            // Set the position of the button below the previous button
            button.setLayoutY(buttonHeight * x);

            // Add the button to the AnchorPane
            cars_pane_view.getChildren().add(button);

            x++;
        }
    }

    public void EditCar(String id)
    {
        editcarbool = id;

        String car = cb.GetCar(id);

        garage_combobox.getItems().clear();

        String garages = cb.GetAllGarages();

        JsonElement jsonString = new JsonParser().parse(garages);
        JsonObject jsonObject = jsonString.getAsJsonObject();
        String data = jsonObject.get("data").getAsString();
        String[] stringData = data.split(",");

        // Iterate through the array and print each string
        for (String node : stringData) {
            String dataString = node;
            garage_combobox.getItems().add(dataString);
        }

        jsonString = new JsonParser().parse(garages);
        jsonObject = jsonString.getAsJsonObject();
        data = jsonObject.get("data").getAsString();
        stringData = data.split(",");

        for (String node : stringData) {
            String[] array = node.toString().split(" + ");

            carname_textfield.setText(array[1]);
            licenceplate_textfield.setText(array[2]);

            garage_combobox.setValue(array[3]);

            kilometers_textfield.setText(array[4]);
        }
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
