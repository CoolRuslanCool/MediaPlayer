package ChatClient;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

/**
 * Created by ruslan on 14.11.16.
 */
public class ChatClient2 {
    Socket socket;
    BufferedReader reader;
    JTextArea messageArea;
    JTextField stringSend;

    public static void main(String[] args) {
        new ChatClient2().go();
    }

    void go() {
        JFrame frame = new JFrame();
        //JPanel backGraund = new JPanel();
        Box box = new Box(BoxLayout.Y_AXIS);
        messageArea = new JTextArea(10, 20);
        messageArea.setLineWrap(true);
        messageArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(messageArea);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        stringSend = new JTextField(20);
        JButton send = new JButton("send");
        send.addActionListener(new SendListener());

        box.add(scrollPane);
        box.add(stringSend);
        box.add(send);
        lan();
        Thread a = new Thread(new Inner());
        a.start();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(box);
        frame.pack();
        frame.setVisible(true);
    }

    class SendListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            try {
                PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                writer.print(stringSend.getText() + "\n");
                writer.flush();
                stringSend.setText("");
            } catch (Exception e) {
                e.getStackTrace();
            }
        }
    }

    public void lan() {
        try {
            socket = new Socket("192.168.1.9", 5000);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    class Inner implements Runnable {
        @Override
        public void run() {
//            String mess;
//            try {
//                while ((mess = reader.readLine()) != null)
//                messageArea.append(mess + "\n");
                String text;
                try {
                    while ((text = reader.readLine())!=null)
                        messageArea.append(text + "\n");
            } catch (IOException e) {
                e.getStackTrace();
            }
        }
    }
}