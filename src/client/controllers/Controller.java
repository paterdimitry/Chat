package client.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import client.models.Network;

public class Controller {

    @FXML
    private ListView<String> listViewPerson;

    @FXML
    private ListView<String> listViewMsg;

    @FXML
    private TextField inputField;

    public Network network;

    public void setNetwork(Network network) {
        this.network = network;
    }

    private final ObservableList<String> msgList = FXCollections.observableArrayList("Добро пожаловать в чат!");

    private final ObservableList<String> prsnList = FXCollections.observableArrayList(network.USERNAME + " (Я)");

    @FXML
    void initialize() {
        listViewMsg.setItems(msgList);

        listViewPerson.setItems(prsnList);
        //реализация переноса строки внутри ячейки. решение взято с просторов Интернета
        listViewMsg.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    setMinWidth(param.getWidth());
                    setMaxWidth(param.getWidth());
                    setPrefWidth(param.getWidth());
                    setWrapText(true);
                    setText(item);
                }
            }
        });
    }

    @FXML
    void sendMsg() {
        if (!inputField.getText().isBlank()) {
            network.sendMessage(inputField.getText().trim());
            inputField.clear();
        } else {
            showAlertEmptyInput();
        }
        inputField.requestFocus();
    }

    public void sendMessageToList(String message) {
        listViewMsg.getItems().add(message);
    }

    private void showAlertEmptyInput() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка ввода");
        alert.setHeaderText("Введена пустая строка");
        alert.show();
    }

    @FXML
    void showAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("О программе");
        alert.setHeaderText("Пользовательский чат v.0.2b");
        alert.setContentText("Пользовательский текстовый чат");
        alert.show();
    }

    @FXML
    void doExit() {
        System.exit(0);
    }


}
