package view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

import model.NetworkTarget;
import model.Sound;

/**
 * Start window which allows the selection of different modes and options
 * 
 */
@SuppressWarnings("serial")
public class WelcomeWindow extends JFrame {

    private JFrame frame;
    private JPanel panel;
    private JLabel tankLogo;
    private JLabel singlePlayer;
    private JLabel multiPlayer;
    
    private JLabel nameLabel;
    private JTextField nameTextField;
    
    private JLabel serverIPLabel;
    private JTextField serverIPTextField;
    
    private JLabel serverPortLabel;
    private JTextField serverPortTextField;
   

    public WelcomeWindow() {
        setIconImage(new ImageIcon("src/resources/TankHunters.png").getImage());
    	setTitle("Intro");
        frame = this;
        panel = new JPanel();
        
        Sound.play("game");

        add(panel);
        tankLogo = new JLabel(new ImageIcon("src/resources/free-tank-logo.png"));
        tankLogo.setBounds(0, 0, 600, 600);
       
        panel.setOpaque(true);
        panel.setLayout(null);
        panel.setBounds(getBounds());
        
        multiPlayer = new JLabel();
        multiPlayer.setText("Join Game");
        multiPlayer.setBounds(350, 100, 310, 40);
       
        multiPlayer.setForeground(Color.blue);
        multiPlayer.setFont(new Font("Serif", Font.BOLD, 24));
        multiPlayer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                multiPlayer.setForeground(Color.RED);
                multiPlayer.setFont(new Font("Serif", Font.BOLD, 26));

            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                multiPlayer.setForeground(Color.blue);
                multiPlayer.setFont(new Font("Serif", Font.BOLD, 24));
            }

            public void mousePressed(java.awt.event.MouseEvent evt) {
            	Sound.stop();
          	   	frame.dispose();
          	   	new GameWindow(new NetworkTarget(serverIPTextField.getText(), new Integer(serverPortTextField.getText())), 1);
            }
        }); 
        
        singlePlayer = new JLabel();
        singlePlayer.setText("Create Game");
        singlePlayer.setBounds(350, 140, 210, 40);
        singlePlayer.setForeground(Color.blue);
        singlePlayer.setFont(new Font("Serif", Font.BOLD, 24));
        singlePlayer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                singlePlayer.setForeground(Color.RED);
                singlePlayer.setFont(new Font("Serif", Font.BOLD, 26));

            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                singlePlayer.setForeground(Color.blue);
                singlePlayer.setFont(new Font("Serif", Font.BOLD, 24));
            }

            public void mousePressed(java.awt.event.MouseEvent evt) {
            	  Sound.stop();
           	   frame.dispose();
               new GameWindow(new NetworkTarget(serverIPTextField.getText(), new Integer(serverPortTextField.getText()),nameTextField.getText()));
            }
        });

      
       
         
        nameLabel = new JLabel();
        nameLabel.setText("Name:");
        nameLabel.setBounds(50, 120, 100, 40);
        nameLabel.setForeground(Color.blue);
        nameLabel.setFont(new Font("Serif", Font.BOLD, 24)); 
        
       nameTextField = new JTextField();
       nameTextField.setText("Evgheni");
       nameTextField.setBounds(120, 120, 150, 40);
       nameTextField.setForeground(Color.red);
       nameTextField.setFont(new Font("Serif", Font.BOLD, 24));
       
       serverIPLabel = new JLabel();
       serverIPLabel.setText("IP:");
       serverIPLabel.setBounds(50, 160, 100, 40);
       serverIPLabel.setForeground(Color.blue);
       serverIPLabel.setFont(new Font("Serif", Font.BOLD, 24)); 
       
       serverIPTextField = new JTextField();
       serverIPTextField.setText("127.0.0.1");
       serverIPTextField.setBounds(120, 160, 150, 40);
       serverIPTextField.setForeground(Color.red);
       serverIPTextField.setFont(new Font("Serif", Font.BOLD, 24));
       
       
       serverPortLabel = new JLabel();
       serverPortLabel.setText("Port:");
       serverPortLabel.setBounds(50, 200, 100, 40);
       serverPortLabel.setForeground(Color.blue);
       serverPortLabel.setFont(new Font("Serif", Font.BOLD, 24)); 
       
       serverPortTextField = new JTextField();
       serverPortTextField.setText("8080");
       serverPortTextField.setBounds(120, 200, 55, 40);
       serverPortTextField.setForeground(Color.red);
       serverPortTextField.setFont(new Font("Serif", Font.BOLD, 24));
           

       
        panel.add(nameLabel);  
        panel.add(nameTextField);
        
        panel.add(serverIPLabel);  
        panel.add(serverIPTextField);
        
        panel.add(serverPortLabel);  
        panel.add(serverPortTextField);
        
        
        panel.add(singlePlayer);
        panel.add(multiPlayer);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        panel.add(tankLogo);
        setBounds(300, 100, 600, 600);
        setVisible(true);
    
        nameLabel.requestFocusInWindow();
    }
    
    public static void main(String []args){
    	new WelcomeWindow();	
    }
}
