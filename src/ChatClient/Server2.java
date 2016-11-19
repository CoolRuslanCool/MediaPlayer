package ChatClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by ruslan on 14.11.16.
 */
public class Server2 {
    ArrayList clientList;

    public static void main(String[] args) {
        new Server2().lan();
    }
    class Client implements Runnable {
        Socket socketClient;
        BufferedReader reader;

        public Client(Socket socket) {
            socketClient = socket;
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (Exception e) {
                e.getStackTrace();
            }
        }
        @Override
        public void run() {
            String mess;
            try {
                while ((mess = reader.readLine()) != null) {
                    tellAll(mess);
                }
            } catch (Exception e) {e.getStackTrace();}
        }
    }
    void lan() {
        clientList = new ArrayList();

        try {
            ServerSocket server = new ServerSocket(5000);
            while (true) {
                Socket socket = server.accept();
                PrintWriter writer = new PrintWriter(socket.getOutputStream());
                clientList.add(writer);
                Thread a = new Thread(new Client(socket));
                a.start();
            }
        } catch (Exception e) {e.getStackTrace();}
    }
    void tellAll(String message) {
        Iterator it = clientList.iterator();
        try {
            while (it.hasNext()) {
                PrintWriter writer = (PrintWriter) it.next();
                writer.println(message);
                writer.flush();
            }
        } catch (Exception e) {e.getStackTrace();}
    }
}
