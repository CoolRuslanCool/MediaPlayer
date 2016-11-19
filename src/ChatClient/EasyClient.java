package ChatClient;

import jdk.management.resource.internal.inst.InitInstrumentation;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by ruslan on 14.11.16.
 */
public class EasyClient {
    static Socket s;
    public static void main(String[] args) throws Exception{
            s = new Socket("192.168.1.9", 5000);
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Thread a = new Thread(new In());
        a.start();
        while (true) {
            String string = reader.readLine();
            writer.print(string + "\n");
            writer.flush();
        }
    }
    public static class In  implements Runnable {
        public void run() {
            try {
                while (true) {
                    BufferedReader reader1 = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    System.out.println(reader1.readLine());
                }
            } catch (Exception e) {e.getStackTrace();}
        }
    }
}
