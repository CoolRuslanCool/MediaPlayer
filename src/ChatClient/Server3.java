package ChatClient;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by ruslan on 14.11.16.
 */
public class Server3 {
    ArrayList clients;

    public static void main(String[] args) {
        new Server3().go();
    }
    class Client implements Runnable {
        BufferedReader reader;
        Socket socketClient;
        public Client(Socket socket) {
            socketClient = socket;
            try {
                reader = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
            } catch (Exception e) {e.getStackTrace();}
        }

        @Override
        public void run() {
            String mes;
            try {
                while ((mes = reader.readLine()) != null) {
                    tellAll(mes);
                }
            } catch (Exception e) {e.getStackTrace();}
        }
    }
    void tellAll(String message) {
        Iterator it = clients.iterator();
        while (it.hasNext()) {
            PrintWriter writer = (PrintWriter) it.next();
            writer.println(message);
            writer.flush();
        }
    }
    void go() {
        clients = new ArrayList();
        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            while (true) {
                Socket soc = serverSocket.accept();
                System.out.println(soc);
                PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(soc.getOutputStream())));
                clients.add(writer);
                Thread a = new Thread(new Client(soc));
                a.start();
            }
        }catch (Exception e) {e.getStackTrace();}
    }
}
