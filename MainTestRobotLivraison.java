
import java.awt.*;
import java.awt.event.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.swing.*;

public class MainTestRobotLivraison {
    public static void main(String[] args) {

        try {
            // Étape 1 : Créer le réseau
            Réseau reseau = new Réseau();
            reseau.add_line("New York", "Tokyo", 4.8);
        reseau.add_line("New York", "Manilla", 2);
        reseau.add_line("Tokyo", "Paris", 10.12);
        reseau.add_line("Tokyo", "Manilla", 5);
        reseau.add_line("Manilla", "Cairo", 3.58);
        reseau.add_line("Cairo", "Paris", 4);
        reseau.add_line("Paris", "Tunis", 11);
            Map<String, Point> cityPositions = new HashMap<>();
            cityPositions.put("New York", new Point(25, 260));
            cityPositions.put("Tokyo", new Point(175, 160));
            cityPositions.put("Manilla", new Point(170, 465));
            cityPositions.put("Cairo", new Point(325, 360));
            cityPositions.put("Paris", new Point(475, 260));
            cityPositions.put("Tunis", new Point(625, 260));
            
            

            // Étape 2 : Créer le robot
            RobotLivraison robot = new RobotLivraison("EcoBot1", 0, 0);
//graph
            JFrame frame = new JFrame("Control Panel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
            frame.setSize(1200, 700);
            frame.setLayout(new BorderLayout());
            frame.setResizable(false);
            JPanel statusPanel = new JPanel();
            statusPanel.setLayout(new GridLayout(2,2, 5, 5));  
            statusPanel.setBorder(BorderFactory.createTitledBorder("Status"));
            JTextArea pkgArea = new JTextArea(3, 5);
            pkgArea.setBorder(BorderFactory.createTitledBorder("Package:"));
            pkgArea.setEditable(false);
            JTextArea delArea = new JTextArea(3, 5);
            delArea.setBorder(BorderFactory.createTitledBorder("Delivery:"));
            delArea.setEditable(false);
            JTextArea BatteryArea = new JTextArea(3, 5);
            BatteryArea.setBorder(BorderFactory.createTitledBorder("Battery:"));
            BatteryArea.setEditable(false);
            JTextArea PositionArea = new JTextArea(3, 5);
            PositionArea.setBorder(BorderFactory.createTitledBorder("Position:"));
            PositionArea.setEditable(false);
            
     
            ImageIcon icon = new ImageIcon("icon.jpg");
            frame.setIconImage(icon.getImage());
            
            Screen screen = new Screen();
            screen.setBackground(new Color(162,228,184));//(170,219,30)
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
            doTask.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(robot.getEtat()){
                        
                        new Thread(() -> {
                            try {
                                String dest=input.getText();
                                ArrayList<String> chemin = robot.effectuerTache(reseau, dest);
                                screen.setVisible(true);
                                pkgArea.setText( robot.getColisActuel());
                                delArea.setText( robot.getDelivery());
                                SwingUtilities.invokeLater(() ->{
                                    output.setText("Shortest path from Tunis: " + chemin + "\n");
                                    
                            }
                                );
                                TimeUnit.SECONDS.sleep(2);
                                for (String city : chemin) {
                                    Point point = cityPositions.get(city);
                                    if (point != null) {
                                        // Update text and robot position on the Swing thread
                                        SwingUtilities.invokeLater(() -> {
                                            output.setText("Currently in: " + city + "\n");
                                            screen.setPosition(point);
                                            BatteryArea.setText(robot.energie+"%");
                                            PositionArea.setText("Position: ("+screen.x+","+screen.y+")");
                                            
                                        });
                        
                                        TimeUnit.SECONDS.sleep(2); // Wait before moving to next city
                                    }
                                }
                                pkgArea.setText( "Dropped!");
                                delArea.setText( "Delivered!");
                                output.setText("Delivery completed! in: "+ dest+ "\n");
                                robot.setDelivery(false);
                                robot.setColisActuel(0);
                                screen.setVisible(false);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                SwingUtilities.invokeLater(() ->
                                    output.setText("Erreur: " + ex.getMessage())
                                );
                            }
                        }).start();
                    }else{
                        output.setText("Robot is not powered up!\n");
                    }
                }
            });
            
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
                       if(robot.getEtat()){
                        robot.setDestination(button.getText());
                       input.setText(button.getText());
                       output.setText("Destination set to: " + button.getText() + "\n");
                       }else{
                           output.setText("Robot is not powered up!\n");
                       }
                           
                    }
                });
                destinationsPanel.add(button);
            }
            send.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        if(robot.getEtat()){
                        robot.envoyerDonnees(input.getText());
                        output.setText("Data sent successfully!");}
                        else{
                            output.setText("Robot is not powered up!\n");
                        }
                    } catch (RobotException e1) {
                        // TODO Auto-generated catch block
                        output.setText(e1.getMessage());
                    }

                }
            });
    
            
            statusPanel.add(pkgArea);
            statusPanel.add(delArea);
            statusPanel.add(BatteryArea);
            statusPanel.add(PositionArea);
            midPanel.add(destinationsPanel);
            midPanel.add(statusPanel);
            rightPanel.add(midPanel, gbc);
            left.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    
                 

                    try {
                        if(robot.getEtat()){
                        robot.cosommerEnergie(5);
                        PositionArea.setText("Position: ("+screen.x+","+screen.y+")");
                        BatteryArea.setText(robot.energie+"%");
                        screen.deplacer(-10, 0);}
                        else{
                            output.setText("Robot is not powered up!\n");
                        }
                    } catch (EnergieInsuffisanteException ex) {
                        output.setText("Erreur de déplacement : " + ex.getMessage() + "\n");
                    }
                 
                    
            }});
            right.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    
                 
                    try {
                        if(robot.getEtat()){
                        robot.cosommerEnergie(5);
                        PositionArea.setText("Position: ("+screen.x+","+screen.y+")");
                        BatteryArea.setText(robot.energie+"%");
                        screen.deplacer(10, 0);}
                        else{
                            output.setText("Robot is not powered up!\n");
                        }
                    
                    } catch (EnergieInsuffisanteException ex) {
                        output.setText("Erreur de déplacement : " + ex.getMessage() + "\n");
                    }
                 
            }});
            up.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    
                 
                    try {
                        if(robot.getEtat()){
                        screen.deplacer(0, -10);
                        PositionArea.setText("Position: ("+screen.x+","+screen.y+")");
                        BatteryArea.setText(robot.energie+"%");
                        robot.cosommerEnergie(5);}
                        else{
                            output.setText("Robot is not powered up!\n");
                        }
                } catch (EnergieInsuffisanteException ex) {
                
                    output.setText("Erreur de déplacement : " + ex.getMessage() + "\n");
                } 
            }});
            down.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    
                 
                    try {
                        if(robot.getEtat()){
                        robot.cosommerEnergie(5);
                        PositionArea.setText("Position: ("+screen.x+","+screen.y+")");
                        BatteryArea.setText(robot.energie+"%");
                        screen.deplacer(0, 10);}
                        else{
                            output.setText("Robot is not powered up!\n");
                        }
                    } catch (EnergieInsuffisanteException ex) {
                        output.setText("Erreur de déplacement : " + ex.getMessage() + "\n");
                    }
                 
                    
            }});
            returnButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    
                    try {
                        if(robot.getEtat()){
                        screen.setPosition(new Point(625, 260));
                        robot.setDestination("Tunis");
                        PositionArea.setText("Position: ("+screen.x+","+screen.y+")");
                        BatteryArea.setText(robot.energie+"%");
                        delArea.setText( robot.getDelivery());
                        pkgArea.setText( robot.getColisActuel());
                        robot.deplacer(625, 260);}
                        else{
                            output.setText("Robot is not powered up!\n");
                        }
                    } catch (RobotException ex) {
                        output.setText("Erreur de retour : " + ex.getMessage() + "\n");
                    }
                }
            });
    
            
            gbc.gridy++;
            JPanel bottomButtons = new JPanel(new GridLayout(1, 5, 5, 5));
            JButton history = new JButton("\ud83d\udcdc");
            history.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                                // Étape 5 : Affichage de l'état final
                    if(robot.getEtat()){
                        output.setText("--- Résumé ---\n"+robot.toString()+"\nHistorique des actions :"+robot.getHistorique());}
                    else{
                        output.setText("Robot is not powered up!\n");
                    }
                }
            });
            JButton battery = new JButton("⚡");
            battery.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    
                   robot.recharger(25); 
                   BatteryArea.setText( robot.energie + "%");  }
                    
            });
            JButton searchButton = new JButton("⌕");
            JButton heartButton = new JButton("♥");
            JButton powerButton = new JButton("⏻");
            searchButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        if(robot.getEtat())
                        Desktop.getDesktop().browse(URI.create("file:///C:/Users/MSI/Desktop/project/JavaProject.html"));
                        else{
                            output.setText("Robot is not powered up!\n");
                        }
                    } catch (Exception ex) {
                        output.setText("Erreur d'ouverture du lien : " + ex.getMessage() + "\n");
                    }
                    
                }
            });
            powerButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    
                   try{
                    if(robot.getEtat() == true){
                        robot.arreter();
                        output.setText("");
                        output.setText("Robot arrêté\n");}
                    else{
                        robot.demarrer();
                        output.setText("");
                        output.setText("Robot démarré\n");
                        
                    }
                    BatteryArea.setText(robot.energie+"%");
                    screen.powerUP();
                    PositionArea.setText("Position: (625, 260)");
                    }catch(RobotException er){
                        output.setText("");
                        output.setText("Erreur de démarrage : " + er.getMessage() + "\n");
                    }

                   }

                });
            connect.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        if(robot.getEtat()){
                            String reseau=input.getText();
                        robot.connecter(reseau);
                        output.setText("Robot connecté au réseau : "+reseau+"\n");
                        BatteryArea.setText(robot.energie+"%");}
                        else{
                            output.setText("Robot is not powered up!\n");
                        }
                    } catch (RobotException ex) {
                        output.setText("Erreur de connexion : " + ex.getMessage() + "\n");
                    }
                }
            });
            disconnect.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(robot.getEtat()){
                        robot.deconnecter();
                        output.setText("");
                        output.setText("Robot déconnecté\n");}
                    else{
                        output.setText("Robot is not powered up!\n");
                    } 
                }
            });
            heartButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(robot.getEtat()){
                        robot.ajouterHistorique("Robot en mode cœur !");
                        screen.setHeart();
                        output.setForeground(Color.RED);
                        output.setText("❤️ ❤️ ❤️ ❤️ ❤️ ❤️ ❤️ ❤️ ❤️ ❤️\n");}
                    else{
                        output.setText("Robot is not powered up!\n");
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
           
            
        } catch (RobotException e) {
            System.err.println("Erreur Robot : " + e.getMessage());
        }
    }}