package ChatClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by ruslan on 13.11.16.
 */
public class ChatClient {
    PrintWriter writer;
    BufferedReader reader;
    Socket socket;
    JTextArea chatArea;
    JTextField stringField;

    public static void main(String[] args) {
        new ChatClient().makeGui();
    }

    void makeGui() {
        JFrame frame = new JFrame("Chat Client");
        JPanel backPanel = new JPanel();
        backPanel.setBackground(Color.BLUE);
        chatArea = new JTextArea(10, 25);
        chatArea.setLineWrap(true);
        chatArea.setEditable(false);
        JScrollPane scrollBackPanel = new JScrollPane(chatArea);
        scrollBackPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollBackPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        stringField = new JTextField(25);
        JButton sendMessage = new JButton("Send");
        sendMessage.addActionListener(new SendMessageListener());
        backPanel.add(scrollBackPanel);
        backPanel.add(stringField);
        backPanel.add(sendMessage);
        makeLan();
        Thread app = new Thread(new AppMessage());
        app.start();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(backPanel);
        frame.setSize(320, 260);
        frame.setVisible(true);
    }
    void makeLan() {
        try {
            socket = new Socket("192.168.1.9", 5000);
            InputStreamReader is = new InputStreamReader(socket.getInputStream());
            reader = new BufferedReader(is);

        }
        catch (Exception e) {e.getStackTrace();}
    }
    class SendMessageListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            sendMessage(stringField.getText());
        }

        void sendMessage(String text) {
            try {
                writer = new PrintWriter(socket.getOutputStream());
                writer.print(text + "\n");
                //chatArea.append(text + "\n");
                stringField.setText("");
                writer.flush();
            } catch (Exception e) {
                e.getStackTrace();
            }
        }
    }
    class AppMessage implements Runnable {
        public void run() {
            String text;
            try {
                while ((text = reader.readLine())!=null)
                chatArea.append(text + "\n");
            }
            catch (Exception e) {}
        }
    }
}
