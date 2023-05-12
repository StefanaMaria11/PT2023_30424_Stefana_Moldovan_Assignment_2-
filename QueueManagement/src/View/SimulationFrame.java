package View;

import BusinessLogic.SimulationManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimulationFrame {
    public static JFrame frame = new JFrame("Queue Management");
    private JPanel panel = new JPanel();
    public Color color = new Color(175,238,238);

    public void SimulationFrame(){
        frame.setSize(700,700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().setLayout(null);
        frame.setVisible(true);

        frame.getContentPane().setBackground( color );

        JLabel label1 = new JLabel("QUEUE MANAGEMENT");
        label1.setBounds(180,20,500,30);
        label1.setFont(new Font ("Century Gothic", Font.BOLD,25));
        frame.add(label1);



        JLabel clientsLabel = new JLabel("Clients: ");
        JTextField noClients = new JTextField();
        clientsLabel.setBounds(120, 80, 500, 30);
        clientsLabel.setFont(new Font ("Century Gothic", Font.BOLD,20));
        noClients.setBounds(200, 85, 100, 25);
        frame.add(clientsLabel);
        frame.add(noClients);

        JLabel queuesLabel = new JLabel("Queues: ");
        JTextField noQueues = new JTextField();
        queuesLabel.setBounds(120, 110, 500, 30);
        queuesLabel.setFont(new Font ("Century Gothic", Font.BOLD,20));
        noQueues.setBounds(205, 115, 100, 25);
        frame.add(queuesLabel);
        frame.add(noQueues);

        JLabel simulLabel = new JLabel("Max simulation time: ");
        JTextField simulInterval = new JTextField();
        simulLabel.setBounds(120, 140, 500, 30);
        simulLabel.setFont(new Font ("Century Gothic", Font.BOLD,20));
        simulInterval.setBounds(330, 145, 100, 25);
        frame.add(simulLabel);
        frame.add(simulInterval);

        JLabel minArrLabel = new JLabel("Min arrival time: ");
        JTextField minArrTime = new JTextField();
        minArrLabel.setBounds(120, 170, 500, 30);
        minArrLabel.setFont(new Font ("Century Gothic", Font.BOLD,20));
        minArrTime.setBounds(280, 175, 100, 25);
        frame.add(minArrLabel);
        frame.add(minArrTime);

        JLabel maxArrLabel = new JLabel("Max arrival time: ");
        JTextField maxArrTime = new JTextField();
        maxArrLabel.setBounds(120, 200, 500, 30);
        maxArrLabel.setFont(new Font ("Century Gothic", Font.BOLD,20));
        maxArrTime.setBounds(300, 205, 100, 25);
        frame.add(maxArrLabel);
        frame.add(maxArrTime);

        JLabel minSerLabel = new JLabel("Min service time: ");
        JTextField minSerTime = new JTextField();
        minSerLabel.setBounds(120, 230, 500, 30);
        minSerLabel.setFont(new Font ("Century Gothic", Font.BOLD,20));
        minSerTime.setBounds(290, 235, 100, 25);
        frame.add(minSerLabel);
        frame.add(minSerTime);

        JLabel maxSerLabel = new JLabel("Max service time: ");
        JTextField maxSerTime = new JTextField();
        maxSerLabel.setBounds(120, 260, 500, 30);
        maxSerLabel.setFont(new Font ("Century Gothic", Font.BOLD,20));
        maxSerTime.setBounds(300, 265, 100, 25);
        frame.add(maxSerLabel);
        frame.add(maxSerTime);

        JButton startButton = new JButton("Start simulation");
        startButton.setBounds(200, 320, 200, 30);
        startButton.setFont(new Font ("Century Gothic", Font.PLAIN,20));
        startButton.setBackground(new Color(224,255,255));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get user input from text fields
                int noOfClients = Integer.parseInt(noClients.getText());
                int noOfServers = Integer.parseInt(noQueues.getText());
                int timeLimit = Integer.parseInt(simulInterval.getText());
                int minArrivalTime = Integer.parseInt(minArrTime.getText());
                int maxArrivalTime = Integer.parseInt(maxArrTime.getText());
                int minProcessingTime = Integer.parseInt(minSerTime.getText());
                int maxProcessingTime = Integer.parseInt(maxSerTime.getText());

                // Create SimulationManager object with user input
                SimulationManager manager = new SimulationManager();
                manager.noOfClients = noOfClients;
                manager.noOfServers = noOfServers;
                manager.timeLimit = timeLimit;
                manager.minArrivalTime = minArrivalTime;
                manager.maxArrivalTime = maxArrivalTime;
                manager.minProcessingTime = minProcessingTime;
                manager.maxProcessingTime = maxProcessingTime;

                // Start simulation
                Thread simThread = new Thread(manager);
                simThread.start();
            }
        });
        frame.add(startButton);

        frame.revalidate();
        frame.repaint();

    }
}
