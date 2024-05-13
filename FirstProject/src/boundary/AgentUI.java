package boundary;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AgentUI extends JFrame {
	private int userId;
    
    public AgentUI(int userId) {
    	this.userId = userId;
        setTitle("Agent Function");
        setSize(400, 300); // Adjust the size as necessary
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        
        setLayout(null);
        
        JLabel titleLabel = new JLabel("Agent Function Dashboard", SwingConstants.CENTER);
        titleLabel.setBounds(50, 10, 300, 30);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 10, 10)); 
        panel.setBounds(50, 50, 300, 150); 

        JButton viewPropertiesButton = new JButton("View Properties");
        JButton viewRatesButton = new JButton("View Rate");
        JButton viewReviewsButton = new JButton("View Review");
        JButton logoutButton = new JButton("Logout");
        
        viewPropertiesButton.setFont(new Font("Arial", Font.PLAIN, 14));
        viewRatesButton.setFont(new Font("Arial", Font.PLAIN, 14));
        viewReviewsButton.setFont(new Font("Arial", Font.PLAIN, 14));
        logoutButton.setFont(new Font("Arial", Font.PLAIN, 14));

        viewPropertiesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	AgentViewPropertyUI agentViewProperty = new AgentViewPropertyUI(userId);
            	agentViewProperty.setVisible(true);
                 dispose();
            }
        });
        
        viewRatesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	AgentViewRatingUI avrUI = new AgentViewRatingUI(userId, AgentUI.this.getX(), AgentUI.this.getY());
            }
        });

        viewReviewsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	AgentViewReviewUI avReviewUI = new AgentViewReviewUI(userId, AgentUI.this.getX(), AgentUI.this.getY());
            }
        });
        
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	AgentUI.this.dispose();
            	LoginUI loginUI = new LoginUI();
            }
        });
        
        panel.add(viewPropertiesButton);
        panel.add(viewRatesButton);
        panel.add(viewReviewsButton);
        panel.add(logoutButton);

        add(titleLabel);
        add(panel);
        
        setVisible(true);
    }
}