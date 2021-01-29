package client.models;

import client.controllers.Controller;
import javafx.application.Platform;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Network {

    private static final String DEFAULT_SERVER_HOST = "localhost";
    private static final int DEFAULT_SERVER_PORT = 8888;
    public static final String USERNAME = "Дмитрий";

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    private final String host;
    private final int port;

    public Network(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public Network() {
        this.host = DEFAULT_SERVER_HOST;
        this.port = DEFAULT_SERVER_PORT;
    }

    public void connect() {
        try {
            socket = new Socket(host, port);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Соединение не установлено!");
        }
    }

    //метод отправки сообщения. вызывается из контроллера
    public void sendMessage(String message){
        message = USERNAME + ": " + message;
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            System.out.println("Ошибка отправки сообщения");
        }
    }

    //ожидание сообщения в непрерывном потоке
    public void waitMessage(Controller controller) {
        Thread thread = new Thread(() -> {
            try {
                while (true) {
                    //принимаем сообщение из потока in и отправляем на контроллер для добавления в ListView
                    String message = in.readUTF();
                    //класс Platform применен для согласования потоков ожидания сообщения и FX-приложения.
                    // (взято из Интернета)
                    Platform.runLater(() -> controller.sendMessageToList(message));
                }
            } catch (IOException e) {
                System.out.println("Ошибка соединения");
            }
        });
        thread.setDaemon(true);
        thread.start();
    }
}
