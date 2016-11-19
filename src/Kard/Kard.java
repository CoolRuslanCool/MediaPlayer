package Kard;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicOptionPaneUI;
import java.awt.*;

/**
 * Created by ruslan on 05.11.16.
 */
public class Kard {
    public static void main(String[] args) {
        JFrame frame = new JFrame("fffff");
        JPanel panelBack = new JPanel(new BorderLayout(10,10));
        panelBack.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        Box box = new Box(BoxLayout.Y_AXIS);
        box.setBackground(Color.BLUE);
        for (int a = 0;a<15;a++) {
            box.add(new Label("readf"+a));
        }
        panelBack.add(BorderLayout.WEST,box);
        //GridLayout layout = new GridLayout(15,15,0,0);
        JPanel panelFront = new JPanel(new GridLayout(15,15));
        for (int a = 0;a<225;a++) {
            panelFront.add(new JCheckBox());
        }
        panelBack.add(BorderLayout.CENTER,panelFront);
        Box boxButton = new Box(BoxLayout.Y_AXIS);
        JButton start = new JButton("start");
        JButton stop = new JButton("stop");
        boxButton.add(start);
        boxButton.add(stop);
        panelBack.add(BorderLayout.EAST,boxButton);
        JMenuBar barMenu = new JMenuBar();
        JMenu menu = new JMenu("menu1");

        JMenuItem start1 = new JMenuItem("start");
        menu.add(start1);

        barMenu.add(menu);
        frame.setJMenuBar(barMenu);
        frame.getContentPane().add(panelBack);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
