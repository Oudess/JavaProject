import java.awt.*;
import javax.swing.*;

public class ControlRobot{
    private JTextArea screen;
    private JTextArea output;
    private JTextField input;
    private JButton up;
    private JButton down;
    private JButton left;
    private JButton right;
    private JButton connect;
    private JButton send;
    private JButton disconnect;
    private JButton doTask;
    private JButton returnButton;
    private JButton history;
    private JButton battery;
    private JButton powerButton;
    private JButton heartButton;
    private JButton searchButton;
    private JTextArea pkgArea;
    private JTextArea delArea;

    public ControlRobot() {
        JFrame frame = new JFrame("Control Panel");
        frame.setDefaultCloseOperation(3);
        frame.setSize(1200, 700);
        frame.setLayout(new BorderLayout());
        this.screen = new JTextArea();
        this.screen.setEditable(false);
        this.screen.setBackground(Color.LIGHT_GRAY);
        this.screen.setBorder(BorderFactory.createTitledBorder("Screen"));
        frame.add(this.screen, "Center");
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setPreferredSize(new Dimension(500, 700));
        frame.add(rightPanel, "East");
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = 2;
        gbc.weightx = (double)1.0F;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        this.output = new JTextArea(4, 20);
        this.output.setBorder(BorderFactory.createTitledBorder("Output"));
        this.output.setEditable(false);
        rightPanel.add(this.output, gbc);
        ++gbc.gridy;
        this.input = new JTextField();
        this.input.setBorder(BorderFactory.createTitledBorder("Input"));
        rightPanel.add(this.input, gbc);
        ++gbc.gridy;
        JPanel dirPanel = new JPanel(new GridBagLayout());
        GridBagConstraints d = new GridBagConstraints();
        this.up = new JButton("↑");
        this.down = new JButton("↓");
        this.left = new JButton("←");
        this.right = new JButton("→");
        Dimension dirSize = new Dimension(80, 40);
        this.up.setPreferredSize(dirSize);
        this.down.setPreferredSize(dirSize);
        this.left.setPreferredSize(dirSize);
        this.right.setPreferredSize(dirSize);
        d.gridx = 1;
        d.gridy = 0;
        dirPanel.add(this.up, d);
        d.gridx = 0;
        d.gridy = 1;
        dirPanel.add(this.left, d);
        d.gridx = 2;
        d.gridy = 1;
        dirPanel.add(this.right, d);
        d.gridx = 1;
        d.gridy = 2;
        dirPanel.add(this.down, d);
        rightPanel.add(dirPanel, gbc);
        this.connect = new JButton("Connect");
        this.disconnect = new JButton("Disconnect");
        this.send = new JButton("Send ⌲");
        this.returnButton = new JButton("Return");
        this.doTask = new JButton("Do Task");
        ++gbc.gridy;
        rightPanel.add(this.connect, gbc);
        ++gbc.gridy;
        rightPanel.add(this.disconnect, gbc);
        ++gbc.gridy;
        rightPanel.add(this.send, gbc);
        ++gbc.gridy;
        rightPanel.add(this.returnButton, gbc);
        ++gbc.gridy;
        rightPanel.add(this.doTask, gbc);
        ++gbc.gridy;
        gbc.gridwidth = 2;
        JPanel midPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        JPanel destinationsPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        destinationsPanel.setBorder(BorderFactory.createTitledBorder("Destinations"));
        String[] cities = new String[]{"Paris", "Tokyo", "New York", "Tunis", "Cairo", "Manilla"};

        for(String city : cities) {
            JButton button = new JButton(city);
            destinationsPanel.add(button);
        }

        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new BoxLayout(statusPanel, 1));
        statusPanel.setBorder(BorderFactory.createTitledBorder("Status"));
        this.pkgArea = new JTextArea(3, 10);
        this.pkgArea.setBorder(BorderFactory.createTitledBorder("Package:"));
        this.pkgArea.setEditable(false);
        this.delArea = new JTextArea(3, 10);
        this.delArea.setBorder(BorderFactory.createTitledBorder("Delivery:"));
        this.delArea.setEditable(false);
        statusPanel.add(this.pkgArea);
        statusPanel.add(this.delArea);
        midPanel.add(destinationsPanel);
        midPanel.add(statusPanel);
        rightPanel.add(midPanel, gbc);
        ++gbc.gridy;
        JPanel bottomButtons = new JPanel(new GridLayout(1, 5, 5, 5));
        this.history = new JButton("\ud83d\udcdc");
        this.battery = new JButton("⚡");
        this.searchButton = new JButton("⌕");
        this.heartButton = new JButton("♥");
        this.powerButton = new JButton("⏻");
        bottomButtons.add(this.history);
        bottomButtons.add(this.battery);
        bottomButtons.add(this.searchButton);
        bottomButtons.add(this.heartButton);
        bottomButtons.add(this.powerButton);
        rightPanel.add(bottomButtons, gbc);
        frame.setLocationRelativeTo((Component)null);
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        ControlRobot C= new ControlRobot();


    }
}