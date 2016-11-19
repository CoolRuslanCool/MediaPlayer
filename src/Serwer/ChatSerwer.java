package Serwer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * Created by ruslan on 13.11.16.
 */
public class ChatSerwer {
    ArrayList <PrintWriter> clientChat;

    public static void main(String[] args) {

        new ChatSerwer().makeSerwer();
    }

    class ClientChat implements Runnable {
        BufferedReader reader;
        Socket clientSocket;

        ClientChat(Socket socket) {
            try {
                clientSocket = socket;
                reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            } catch (Exception e) {
            }
        }

        public void run() {
            String message;
            try {
                while ((message = reader.readLine()) != null) {
                    tellAll(message);
                    System.out.println("message inputed");
                }
            } catch (Exception e) {
            }
        }
    }

    void tellAll(String message) {
        Iterator it = clientChat.iterator();
        while (it.hasNext()) {
            try {
                PrintWriter writer = (PrintWriter) it.next();
                writer.print(message + "\n");
                writer.flush();
                System.out.println("message send");

            } catch (Exception e) {
            }
        }
    }

    public void makeSerwer() {
        clientChat = new ArrayList<>();

        try {
            ServerSocket server = new ServerSocket(5000);
            while (true) {
                Socket clientSocket = server.accept();
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
//                sort(writer);
                clientChat.add(writer);

                Thread t = new Thread(new ClientChat(clientSocket));
                t.start();
                System.out.println("connect");
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

//    void sort(PrintWriter writer) {
//        Iterator it = clientChat.iterator();
//                while (it.hasNext()) {
//            PrintWriter printWriter = (PrintWriter) it.next();
//            if (printWriter.equals(writer)) {
//                clientChat.remove(printWriter);
//                clientChat.add(writer);
//            }
//            else clientChat.add(writer);
//                    if(it == null) clientChat.add(writer);
//                }
//    }
}