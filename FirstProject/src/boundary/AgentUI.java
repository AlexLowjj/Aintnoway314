package boundary;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AgentUI extends JFrame {
    
    public AgentUI() {
        setTitle("Agent Function");
        setSize(400, 300); // Adjust the size as necessary
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        
        setLayout(null);
        
        JLabel titleLabel = new JLabel("Agent Function Dashboard", SwingConstants.CENTER);
        titleLabel.setBounds(50, 10, 300, 30);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1, 10, 10)); 
        panel.setBounds(50, 50, 300, 100); 

        JButton viewPropertiesButton = new JButton("View Properties");
        JButton viewRatesReviewsButton = new JButton("View Rate and Review");
        
        viewPropertiesButton.setFont(new Font("Arial", Font.PLAIN, 14));
        viewRatesReviewsButton.setFont(new Font("Arial", Font.PLAIN, 14));
        
        viewPropertiesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	AgentViewProperty agentViewProperty = new AgentViewProperty();
            	agentViewProperty.setVisible(true);
                 dispose();
            }
        });
        
        viewRatesReviewsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(AgentUI.this, "Rate and Review View selected.");
            }
        });
        
        panel.add(viewPropertiesButton);
        panel.add(viewRatesReviewsButton);

        add(titleLabel);
        add(panel);
        
        setVisible(true);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AgentUI();
            }
        });
    }
}
