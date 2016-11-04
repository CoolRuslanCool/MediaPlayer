import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by ruslan on 04.11.16.
 */
public class MediaPlayer {
    ArrayList<JCheckBox> checkBoxes;
    String[] instrum = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16"};
    int[] instrNum = {35,42,46,38,49,39,50,60,70,72,64,56,58,47,67,63};
    Sequencer sequencer;
    Sequence sequence;
    Track track;
    JFrame frame;
    JPanel panel;

    public static void main(String[] args) {
        MediaPlayer player = new MediaPlayer();
        player.creatFrame2();
    }
    void creatFrame2() {
        frame = new JFrame("Player");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BorderLayout layout = new BorderLayout();
        JPanel panel1 = new JPanel(layout);
        panel1.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        checkBoxes = new ArrayList<>();
        Box buttonBox = new Box(BoxLayout.Y_AXIS);

        JButton start = new JButton("start");
        start.addActionListener(new StartButton());
        buttonBox.add(start);
        JButton stop = new JButton("stop");
        stop.addActionListener(new StopButton());
        buttonBox.add(stop);
        JButton tempUp = new JButton("tempUp");
        tempUp.addActionListener(new TempUpButton());
        buttonBox.add(tempUp);
        JButton tempDown= new JButton("tempDown");
        tempDown.addActionListener(new TempDownButton());
        buttonBox.add(tempDown);
        JButton allClear = new JButton("Clear all check");
        allClear.addActionListener(new AllClearButton());
        buttonBox.add(allClear);
        Box nameInstrum = new Box(BoxLayout.Y_AXIS);
        for (int a=0;a<instrum.length;a++) {
            Label label = new Label(instrum[a]);
            nameInstrum.add(label);
        }
        panel1.add(BorderLayout.WEST,nameInstrum);
        panel1.add(BorderLayout.EAST,buttonBox);
        frame.getContentPane().add(panel1);
        GridLayout gridLayout = new GridLayout(16,16);
        gridLayout.setVgap(1);
        gridLayout.setHgap(2);
        panel = new JPanel(gridLayout);
        panel.setBackground(Color.CYAN);
        panel1.setBackground(Color.RED);
        nameInstrum.setBackground(Color.magenta);
        buttonBox.setBackground(Color.magenta);
        panel1.add(BorderLayout.CENTER,panel);
        for (int a=0;a<256;a++) {
            JCheckBox c = new JCheckBox();
            c.setSelected(false);
            checkBoxes.add(c);
            panel.add(c);
        }
        setUpMidi();
        frame.setBounds(50,50,300,300);
        frame.pack();
        frame.setVisible(true);
    }
    void setUpMidi() {
        try {
            sequencer = MidiSystem.getSequencer();
            sequence = new Sequence(Sequence.PPQ, 4);
            track = sequence.createTrack();
            sequencer.setTempoInBPM(120);
            sequencer.open();
        }
        catch (Exception e) { e.getStackTrace(); }
    }
    MidiEvent makeEvent(int a1, int a2, int a3, int a4, int a5) {
        MidiEvent event = null;
        try {
            event = new MidiEvent(new ShortMessage(a1, a2, a3, a4), a5);
        }
        catch (Exception e) { e.getStackTrace(); }
        return event;
    }
    void makeTracks (int[] list) {
        for (int a=0;a<list.length;a++) {
            int key = list[a];
            if (key != 0) {
                track.add(makeEvent(144,9,key,100,a));
                track.add(makeEvent(128,9,key,100,a+1));
            }
        }
    }
    void makeTrackAndStart() {
        int[] trackList = null;
        sequence.deleteTrack(track);
        track = sequence.createTrack();
        for (int a=0;a<16;a++) {
            trackList = new int[16];
            int key = instrNum[a];
            for (int j = 0;j<16;j++) {
                JCheckBox jc = checkBoxes.get(j+(16*a));
                if (jc.isSelected())
                    trackList[j] = key;
                else
                    trackList[j] = 0;
            }
            makeTracks(trackList);
            track.add(makeEvent(176,1,127,0,16));
        }
        track.add(makeEvent(192,9,1,0,15));
        try {
            sequencer.setSequence(sequence);
            sequencer.setLoopCount(sequencer.LOOP_CONTINUOUSLY);
            sequencer.start();
            sequencer.setTempoInBPM(120);
        }
        catch (Exception e) { e.getStackTrace(); }
    }




    class StartButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        makeTrackAndStart();
        }
    }
    class StopButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        sequencer.stop();
        }
    }
    class TempUpButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        float temp = sequencer.getTempoFactor();
            sequencer.setTempoFactor((float)(temp*1.03));
        }
    }
    class TempDownButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        float temp = sequencer.getTempoFactor();
            sequencer.setTempoFactor((float)(temp*0.97));
        }
    }
    class AllClearButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (JCheckBox ch: checkBoxes)
            ch.setSelected(false);
        }

    }

}
