package server;

import server.threads.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer {

    private static final int DEFAULT_SERVER_PORT = 8888;

    public static void main(String[] args) {

        try {
            //создаем сокет сервера
            ServerSocket serverSocket = new ServerSocket(DEFAULT_SERVER_PORT);
            System.out.println("Подключение...");
            while (true) {
                //ожидаем подключения клиента и создаем сокет клиента
                Socket clientSocket = serverSocket.accept();
                System.out.println("Подключение установлено!");

                //создаем потоки ввода-вывода
                DataInputStream in = new DataInputStream(clientSocket.getInputStream());
                DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());

                //создаем и запускаем поток, ожидающий сообщения из консоли для передачи клиенту
                ServerMessage sm = new ServerMessage(out);
                sm.start();

                //создаем и запускаем поток, ожидающий сообщения клиента для обратной переправки
                WaitClientMessage wcm = new WaitClientMessage(in, out, sm);
                wcm.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}