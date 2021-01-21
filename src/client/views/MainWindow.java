package client.views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import client.controllers.Controller;
import client.models.Network;

public class MainWindow extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainWindow.class.getResource("/client/fxml/MainWindow.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Чат");
        primaryStage.setScene(new Scene(root));
        primaryStage.setMinWidth(660);
        primaryStage.setMinHeight(530);
        primaryStage.show();

        Network network = new Network();
        network.connect();

        Controller controller = loader.getController();
        controller.setNetwork(network);
        network.waitMessage(controller);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
