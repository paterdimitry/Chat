package server.threads;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WaitClientMessage extends Thread {
    DataInputStream in;
    DataOutputStream out;
    ServerMessage sm;
    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    public WaitClientMessage(DataInputStream in, DataOutputStream out, ServerMessage sm) {
        this.in = in;
        this.out = out;
        this.sm = sm;
        this.setDaemon(true);
    }

    @Override
    public void run() {
        try {
            while (true) {
                //берем сообщение их потока in, добавляем серверную дату (время) и отправляем обратно клиенту
                String message = in.readUTF();
                message = dateFormat.format(new Date()) + " " + message;
                out.writeUTF(message);
            }
        } catch (IOException e) {
            //в случае отключения клиента выведется сообщение об этом и закроются лишние потоки
            System.out.println("Соединение прервано");
            sm.stop();
            this.stop();
        }
    }
}
