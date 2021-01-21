package server.threads;

import java.io.DataOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class ServerMessage extends Thread {

    DataOutputStream out;
    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    Scanner scanner;

    public ServerMessage(DataOutputStream out) {
        this.out = out;
        this.setDaemon(true);
    }

    @Override
    public void run() {
        while (true) {
            System.out.print("Введите cерверное сообщение: ");
            scanner = new Scanner(System.in);
            String serverMsg = scanner.next();
            //берем сообщение из консоли и отправляем в поток out
            try {
                 out.writeUTF(dateFormat.format(new Date()) + " server: " + serverMsg);
            } catch (IOException e) {
                System.err.println(e);

            }
        }
    }

}
