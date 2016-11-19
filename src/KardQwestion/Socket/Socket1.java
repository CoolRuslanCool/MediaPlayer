package KardQwestion.Socket;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

/**
 * Created by ruslan on 10.11.16.
 */
public class Socket1 {
    String s;
    public static void main(String[] args) {
        Socket1 socket1 = new Socket1();
        socket1.s = "sss";
        try {
            BufferedReader stream = new BufferedReader(new FileReader("/home/ruslan/Рабочий стол/MediaPlayer/src/sss.txt"));
            socket1.s = stream.readLine();
            stream.close();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("/home/ruslan/Рабочий стол/MediaPlayer/src/2.ser"));
            objectOutputStream.writeObject(socket1);
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("/home/ruslan/Рабочий стол/MediaPlayer/src/2.ser"));
            socket1 = (Socket1) objectInputStream.readObject();

        } catch (Exception e) {
            e.getMessage();
        }System.out.println(socket1.s);
    }
}