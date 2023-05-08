package com.example;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

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
    private Button side_profile;
    @FXML
    private Button side_dashboard;
    @FXML
    private Button side_settings;
    @FXML
    private AnchorPane profile_pane;
    @FXML
    private AnchorPane dashboard_pane;
    @FXML
    private AnchorPane settings_pane;
    @FXML
    private TextField text_username;

    @FXML
    private TextField emaik_textfield;
    @FXML
    private TextField text_password;
    @FXML
    private TextField username_field_lg;
    @FXML
    private TextField password_field_lg;
    @FXML
    private TextField real_name_lg;
    @FXML
    private Button login_btn;
    @FXML
    private ImageView profile_image_lg;
    @FXML
    private Button cancel_btn_lg;
    @FXML
    private Button apply_btn_lg;
    @FXML
    private TextField surname_lg;
    @FXML
    private Button logout_btn;
    @FXML
    private Button side_rent;
    @FXML
    private Button side_users;
    @FXML
    private AnchorPane rents_pane;
    @FXML
    private AnchorPane createrent_pane;
    @FXML
    private ComboBox car_combobox;
    @FXML
    private ComboBox user_combobox;
    @FXML
    private DatePicker fromdate_datepicker;
    @FXML
    private DatePicker todate_datepicker;
    @FXML
    private AnchorPane rents_pane_view;
    @FXML
    private AnchorPane users_pane;
    @FXML
    private AnchorPane users_pane_view;
    @FXML
    private AnchorPane createuser_pane;
    @FXML
    private TextField firstname;
    @FXML
    private TextField surname;
    @FXML
    private DatePicker birth;
    @FXML
    private ComboBox city;
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private AnchorPane cars_pane_view;
    @FXML
    private AnchorPane create_car_pane;
    @FXML
    private TextField carname_textfield;
    @FXML
    private TextField licenceplate_textfield;
    @FXML
    private ComboBox garage_combobox;
    @FXML
    private TextField kilometers_textfield;

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
        String data = jsonObject.get("data").toString();

        data = data.replace("\"", "");
        data = data.replace("[", "");
        data = data.replace("]", "");

        String[] stringData = data.split(",");


        double buttonHeight = 20;
        String test = "";
        int x = 0;
        // Iterate through the array and print each string
        for (String info : stringData) {
            String dataString = info;
            Button button = new Button(dataString);

            String[] infoArray = dataString.split("\\+");

            button.setOnMouseClicked(me -> {
                try {
                    EditRent(infoArray[0]);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

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
    public void CreateUser_Click() throws IOException {
        users_pane.setVisible(false);
        createuser_pane.setVisible(true);

        String citys = cb.GetAllCitys();
        city.getItems().clear();

        JsonElement jsonString = new JsonParser().parse(citys);
        JsonObject jsonObject = jsonString.getAsJsonObject();
        String data = jsonObject.get("data").toString();

        data = data.replace("\"", "");
data = data.replace("[", "");
data = data.replace("]", "");

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
            int success = cb.CreateUser(firstname.getText(), surname.getText(), "2022-01-01", city.getValue().toString(), emaik_textfield.getText().toString(), username.getText(), password.getText());

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

    public void CreateRent_Click() throws IOException {
        rents_pane.setVisible(false);
        createrent_pane.setVisible(true);

        String cars = cb.GetAllCars();
        String users = cb.getAllUsers();

        user_combobox.getItems().clear();
        car_combobox.getItems().clear();

        JsonElement jsonString = new JsonParser().parse(users);
        JsonObject jsonObject = jsonString.getAsJsonObject();
        String data = jsonObject.get("data").toString();

        data = data.replace("\"", "");
        data = data.replace("[", "");
        data = data.replace("]", "");

        String[] stringData = data.split(",");

        // Iterate through the array and print each string
        for (String info : stringData) {
            String dataString = info;
            user_combobox.getItems().add(dataString);
        }

        jsonString = new JsonParser().parse(cars);
        jsonObject = jsonString.getAsJsonObject();
        data = jsonObject.get("data").toString();

        data = data.replace("\"", "");
        data = data.replace("[", "");
        data = data.replace("]", "");

        stringData = data.split(",");

        // Iterate through the array and print each string
        for (String info : stringData) {
            String dataString = info;
            car_combobox.getItems().add(dataString);
        }
    }

    public void ConfirmCreateRent_Click() throws IOException {
        if (editrentbool == "0")
        {
            LocalDate selectedDate = fromdate_datepicker.getValue();
            String fromdate = selectedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            selectedDate = todate_datepicker.getValue();
            String todate = selectedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            String[] carinfo = car_combobox.getValue().toString().split("\\+");
            String[] userinfo = user_combobox.getValue().toString().split(("\\+"));

            int success = cb.CreateRent(carinfo[0], userinfo[0], fromdate, todate);

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

    public void ConfirmCreateCar_Click() throws IOException {
        if (editcarbool == "0")
        {
            String[] garage = garage_combobox.getValue().toString().split("\\+");

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
            
            String[] garage = garage_combobox.getValue().toString().split("\\+");

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

    public void EditUser(String username) throws IOException {
        createuser_pane.setVisible(true);
        users_pane.setVisible(false);

        edituserbool = username;

        System.out.println(username);

        String cities = cb.GetAllCitys();

        city.getItems().clear();

        JsonElement jsonString = new JsonParser().parse(cities);
        JsonObject jsonObject = jsonString.getAsJsonObject();
        String data = jsonObject.get("data").toString();

        data = data.replace("\"", "");
        data = data.replace("[", "");
        data = data.replace("]", "");

        String[] stringData = data.split(",");

        // Iterate through the array and print each string
        for (String node : stringData) {
            String dataString = node;
            city.getItems().add(dataString);
        }

        String user = cb.GetUser(username);

        System.out.println(user);

        jsonString = new JsonParser().parse(user);
        jsonObject = jsonString.getAsJsonObject();
        data = jsonObject.get("data").toString();

        data = data.replace("\"", "");
        data = data.replace("[", "");
        data = data.replace("]", "");

        stringData = data.split(",");

        System.out.println(stringData[0]);

        for (String node : stringData) {
            String[] array = node.toString().split("\\+");

            firstname.setText(array[0]);
            surname.setText(array[1]);

            emaik_textfield.setText(array[2]);

            this.username.setText(array[3]);
            password.setText(array[4]);
        }
    }

    public void EditRent(String id) throws IOException {
        rents_pane.setVisible(false);
        createrent_pane.setVisible(true);

        System.out.print(id);
        editrentbool = id;
        System.out.println("2");

        String rent = cb.GetRent(id);
        System.out.println("2");


        String cars = cb.GetAllCars();
        String users = cb.getAllUsers();

        user_combobox.getItems().clear();
        car_combobox.getItems().clear();
        System.out.println("2");

        JsonElement jsonString = new JsonParser().parse(users);
        JsonObject jsonObject = jsonString.getAsJsonObject();
        String data = jsonObject.get("data").toString();

        System.out.println(data);

        data = data.replace("\"", "");
        data = data.replace("[", "");
        data = data.replace("]", "");

        System.out.println(data);

        String[] stringData = data.split(",");

        // Iterate through the array and print each string
        for (String node : stringData) {
            String dataString = node;
            user_combobox.getItems().add(dataString);
        }



        jsonString = new JsonParser().parse(cars);
        jsonObject = jsonString.getAsJsonObject();
        data = jsonObject.get("data").toString();

        System.out.println(data);

        data = data.replace("\"", "");
        data = data.replace("[", "");
        data = data.replace("]", "");

        System.out.println(data);

        stringData = data.split(",");


        System.out.println(data);

        // Iterate through the array and print each string
        for (String node : stringData) {
            String dataString = node;
            car_combobox.getItems().add(dataString);
        }

        jsonString = new JsonParser().parse(rent);
        jsonObject = jsonString.getAsJsonObject();
        data = jsonObject.get("data").toString();


        System.out.println(data);

        data = data.replace("\"", "");
        data = data.replace("[", "");
        data = data.replace("]", "");

        System.out.println(data);

        stringData = data.split(",");

        System.out.println(stringData[0].toString());

        for (String node : stringData) {
            String[] array = node.toString().split("\\+");

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
        //unloadUsers();
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
        String data = jsonObject.get("data").toString();

        data = data.replace("\"", "");
        data = data.replace("[", "");
        data = data.replace("]", "");

        String[] stringData = data.split(",");

        double buttonHeight = 20;
        int x = 0;
        // Iterate through the array and print each string
        for (String node : stringData) {
            String dataString = node;
            Button button = new Button(dataString);

            String[] nodeArray = dataString.split("\\+");

            button.setOnMouseClicked(me -> {
                try {
                    EditUser(nodeArray[4]);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

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
    public void dashboard_Click() throws IOException{
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

        String data = jsonObject.get("data").toString();

        data = data.replace("\"", "");
        data = data.replace("[", "");
        data = data.replace("]", "");

        String[] stringData = data.split(",");

        double buttonHeight = 20;
        int x = 0;
        // Iterate through the array and print each string
        for (String node : stringData) {
            String dataString = node;
            Button button = new Button(dataString);

            String[] nodeArray = dataString.split("\\+");

            button.setOnMouseClicked(me -> {
                try {
                    EditCar(nodeArray[0]);
                    System.out.println(nodeArray[0].toString());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

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


    public void CancelCreateCar_Click() throws IOException {
        create_car_pane.setVisible(false);
        cars_pane_view.setVisible(true);
    }

    public void EditCar(String id) throws IOException {
        create_car_pane.setVisible(true);
        dashboard_pane.setVisible(false);

        editcarbool = id;

        String car = cb.GetCar(id);

        garage_combobox.getItems().clear();

        String garages = cb.GetAllGarages();

        JsonElement jsonString = new JsonParser().parse(garages);
        JsonObject jsonObject = jsonString.getAsJsonObject();
        String data = jsonObject.get("data").toString();

        data = data.replace("\"", "");
        data = data.replace("[", "");
        data = data.replace("]", "");

        String[] stringData = data.split(",");


        // Iterate through the array and print each string
        for (String node : stringData) {
            String dataString = node;
            garage_combobox.getItems().add(dataString);
        }

        System.out.println(garages);

        jsonString = new JsonParser().parse(car);
        jsonObject = jsonString.getAsJsonObject();
        data = jsonObject.get("data").toString();

        System.out.println(data);

        data = data.replace("\"", "");
        data = data.replace("[", "");
        data = data.replace("]", "");

        System.out.println(data);

        stringData = data.split(",");

        for (String node : stringData) {
            String[] array = node.toString().split("\\+");

            carname_textfield.setText(array[0]);
            licenceplate_textfield.setText(array[1]);

            garage_combobox.setValue(array[2]);

            System.out.println(array[3].toString());

            kilometers_textfield.setText(array[3].toString());
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

        //unloadUsers();
    }
}
