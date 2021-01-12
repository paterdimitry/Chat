package sample;

import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class Controller {

    @FXML
    private ListView<String> listViewPerson;

    @FXML
    private ListView<String> listViewMsg;

    @FXML
    private TextField inputField;

    //создаем переменную с нашим именем/ником.
    public String username = "Дмитрий";

    private final ObservableList<String> msgList = FXCollections.observableArrayList("Добро пожаловать в чат!");

    private final ObservableList<String> prsnList = FXCollections.observableArrayList(username + " (Я)");

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
        String message = inputField.getText().trim();
        if (message.length() != 0) {
            sendMessageToList(message);
        } else {
            showAlertEmptyInput();
        }
        inputField.clear();
        inputField.requestFocus();
    }

    private void sendMessageToList(String message) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
        //создаем строку из текущей даты по заданному формату, имени пользователя и текста сообщения
        message = dateFormat.format(new Date()) + " " + username + ": " + message;
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
        alert.setHeaderText("Пользовательский чат v.01b");
        alert.setContentText("Пользовательский текстовый чат");
        alert.show();
    }

    @FXML
    void doExit() {
        System.exit(0);
    }


}
