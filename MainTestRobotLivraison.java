
import java.util.List;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class MainTestRobotLivraison {
    public static void main(String[] args) {

        try {
            // Étape 1 : Créer le réseau
            Réseau reseau = new Réseau();
            reseau.add_line("Tunis", "Paris", 4.5);
            reseau.add_line("Paris", "Tokyo", 6.2);
            reseau.add_line("Tunis", "Cairo", 3.0);
            reseau.add_line("Cairo", "Tokyo", 5.5);
            reseau.add_line("Tokyo", "New York", 8.1);
            reseau.add_line("Cairo", "Manilla", 7.2);
            reseau.add_line("Paris", "New York", 7.9);
            reseau.add_line("Manilla", "New York", 4.3);
            
            

            // Étape 2 : Créer le robot
            RobotLivraison robot = new RobotLivraison("EcoBot1", 0, 0);
//graph
            JFrame frame = new JFrame("Control Panel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
            frame.setSize(1200, 700);
            frame.setLayout(new BorderLayout());
            frame.setResizable(false);
            
     
            ImageIcon icon = new ImageIcon("icon.jpg");
            frame.setIconImage(icon.getImage());
            
            JTextArea screen = new JTextArea();
            screen.setEditable(false);
            screen.setBackground(Color.LIGHT_GRAY);
            screen.setBorder(BorderFactory.createTitledBorder("Screen"));
            frame.add(screen, BorderLayout.CENTER);  
            
            JPanel rightPanel = new JPanel(new GridBagLayout());
            rightPanel.setPreferredSize(new Dimension(500, 700));
            frame.add(rightPanel, BorderLayout.EAST); 
            
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.fill = GridBagConstraints.HORIZONTAL;  
            gbc.weightx = 1.0;
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            
            JTextArea output = new JTextArea(4, 20);
            output.setBorder(BorderFactory.createTitledBorder("Output"));
            output.setEditable(false);
            rightPanel.add(output, gbc);
            
            gbc.gridy++;
            JTextField input = new JTextField();
            input.setBorder(BorderFactory.createTitledBorder("Input"));
            rightPanel.add(input, gbc);
            
            gbc.gridy++;
            JPanel dirPanel = new JPanel(new GridBagLayout());
            GridBagConstraints d = new GridBagConstraints();
            JButton up = new JButton("↑");
            JButton down = new JButton("↓");
            JButton left = new JButton("←");
            JButton right = new JButton("→");
            Dimension dirSize = new Dimension(80, 40);
            up.setPreferredSize(dirSize);
            down.setPreferredSize(dirSize);
            left.setPreferredSize(dirSize);
            right.setPreferredSize(dirSize);
            d.gridx = 1;
            d.gridy = 0;
            dirPanel.add(up, d);
            d.gridx = 0;
            d.gridy = 1;
            dirPanel.add(left, d);
            d.gridx = 2;
            d.gridy = 1;
            dirPanel.add(right, d);
            d.gridx = 1;
            d.gridy = 2;
            dirPanel.add(down, d);
            rightPanel.add(dirPanel, gbc);
            
            JButton connect = new JButton("Connect");
            JButton disconnect = new JButton("Disconnect");
            JButton send = new JButton("Send ⌲");
            JButton returnButton = new JButton("Return");
            JButton doTask = new JButton("Do Task");
            
            gbc.gridy++;
            rightPanel.add(connect, gbc);
            
            gbc.gridy++;
            rightPanel.add(disconnect, gbc);
            
            gbc.gridy++;
            rightPanel.add(send, gbc);
            
            gbc.gridy++;
            rightPanel.add(returnButton, gbc);
            
            gbc.gridy++;
            rightPanel.add(doTask, gbc);
            
            gbc.gridy++;
            gbc.gridwidth = 2;
            JPanel midPanel = new JPanel(new GridLayout(1, 2, 10, 10));
            JPanel destinationsPanel = new JPanel(new GridLayout(3, 2, 5, 5));
            destinationsPanel.setBorder(BorderFactory.createTitledBorder("Destinations"));
            String[] cities = new String[]{"Paris", "Tokyo", "New York", "Tunis", "Cairo", "Manilla"};
    
            for(String city : cities) {
                JButton button = new JButton(city);
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                       robot.setDestination(button.getText());    
                    }
                });
                destinationsPanel.add(button);
            }
    
            JPanel statusPanel = new JPanel();
            statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.Y_AXIS));  
            statusPanel.setBorder(BorderFactory.createTitledBorder("Status"));
            JTextArea pkgArea = new JTextArea(3, 10);
            pkgArea.setBorder(BorderFactory.createTitledBorder("Package:"));
            pkgArea.setEditable(false);
            JTextArea delArea = new JTextArea(3, 10);
            delArea.setBorder(BorderFactory.createTitledBorder("Delivery:"));
            delArea.setEditable(false);
            statusPanel.add(pkgArea);
            statusPanel.add(delArea);
            midPanel.add(destinationsPanel);
            midPanel.add(statusPanel);
            rightPanel.add(midPanel, gbc);
            
            gbc.gridy++;
            JPanel bottomButtons = new JPanel(new GridLayout(1, 5, 5, 5));
            JButton history = new JButton("\ud83d\udcdc");
            history.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                                // Étape 5 : Affichage de l'état final

                   output.setText("--- Résumé ---\n"+robot.toString()+"\nHistorique des actions :"+robot.getHistorique());  
                }
            });
            JButton battery = new JButton("⚡");
            battery.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                   robot.recharger(100);    
                }
            });
            JButton searchButton = new JButton("⌕");
            JButton heartButton = new JButton("♥");
            JButton powerButton = new JButton("⏻");
            powerButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    
                   try{
                   robot.demarrer();}
                   catch(RobotException er){
                    //handling error

                   }

                }
            });
            bottomButtons.add(history);
            bottomButtons.add(battery);
            bottomButtons.add(searchButton);
            bottomButtons.add(heartButton);
            bottomButtons.add(powerButton);
            rightPanel.add(bottomButtons, gbc);
            
            frame.setLocationRelativeTo(null);  
            frame.setVisible(true);
            List<String> chemin = Bellmann.cheminBellman(reseau, "Paris", "New York");
            System.out.println("Chemin calculé : " + chemin);
            for (String ville : chemin) {
            int x = Math.abs(ville.hashCode() % 20);
            int y = Math.abs(ville.hashCode() % 20);
            
            robot.setDestination(ville);
            robot.FaireLivraison(x, y);
            
            }
            connect.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        robot.connecter("reseau1");
                        output.setText("Robot connecté au réseau : reseau1\n");
                    } catch (RobotException ex) {
                        output.setText("Erreur de connexion : " + ex.getMessage() + "\n");
                    }
                }
            });
            disconnect.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        robot.deconnecter();
                        output.setText("Robot déconnecté au réseau : reseau1\n"); 
                }
            });
        } catch (RobotException e) {
            System.err.println("Erreur Robot : " + e.getMessage());
        }
    }}