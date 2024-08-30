package com.sjprogramming;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class ATMGUI {
    private ATM atm = new ATM();
    private AtmOperationInterf atmOperations;

    private JFrame frame;
    private JTextArea miniStatementArea;
    private JTextField amountField;
    private JLabel balanceLabel;
    private JTextField atmNumberField;
    private JTextField pinField;
    private JTextField newPinField;
    private JButton loginButton;
    private JButton logoutButton;
    private JButton viewBalanceButton;
    private JButton withdrawButton;
    private JButton depositButton;
    private JButton viewMiniStatementButton;
    private JButton changePinButton;
    private JButton clearButton;

    public ATMGUI() {
        atmOperations = new AtmOperationImpl(atm); // Pass the same ATM instance to AtmOperationImpl

        // Initialize the JFrame and components
        frame = new JFrame("ATM Machine");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // Initialize GUI components
        initializeComponents();

        // Action listeners
        setupActionListeners();

        // Run on the Event Dispatch Thread for thread safety
        SwingUtilities.invokeLater(() -> frame.setVisible(true));
    }

    private void initializeComponents() {
        JLabel atmNumberLabel = new JLabel("ATM Number:");
        atmNumberLabel.setBounds(10, 10, 100, 25);
        frame.add(atmNumberLabel);

        atmNumberField = new JTextField();
        atmNumberField.setBounds(120, 10, 150, 25);
        frame.add(atmNumberField);

        JLabel pinLabel = new JLabel("PIN:");
        pinLabel.setBounds(10, 40, 100, 25);
        frame.add(pinLabel);

        pinField = new JTextField();
        pinField.setBounds(120, 40, 150, 25);
        frame.add(pinField);

        loginButton = new JButton("Login");
        loginButton.setBounds(280, 10, 80, 55);
        frame.add(loginButton);

        logoutButton = new JButton("Logout");
        logoutButton.setBounds(370, 10, 80, 55);
        logoutButton.setEnabled(false);
        frame.add(logoutButton);

        balanceLabel = new JLabel("Balance: $0.00");
        balanceLabel.setBounds(10, 80, 200, 25);
        frame.add(balanceLabel);

        viewBalanceButton = new JButton("View Balance");
        viewBalanceButton.setBounds(10, 110, 150, 25);
        frame.add(viewBalanceButton);

        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setBounds(10, 140, 100, 25);
        frame.add(amountLabel);

        amountField = new JTextField();
        amountField.setBounds(120, 140, 150, 25);
        frame.add(amountField);

        withdrawButton = new JButton("Withdraw");
        withdrawButton.setBounds(10, 170, 100, 25);
        frame.add(withdrawButton);

        depositButton = new JButton("Deposit");
        depositButton.setBounds(120, 170, 100, 25);
        frame.add(depositButton);

        viewMiniStatementButton = new JButton("Mini Statement");
        viewMiniStatementButton.setBounds(10, 200, 150, 25);
        frame.add(viewMiniStatementButton);

        miniStatementArea = new JTextArea();
        miniStatementArea.setBounds(170, 200, 600, 300);
        miniStatementArea.setEditable(false);
        frame.add(miniStatementArea);

        JLabel newPinLabel = new JLabel("New PIN:");
        newPinLabel.setBounds(10, 510, 100, 25);
        frame.add(newPinLabel);

        newPinField = new JTextField();
        newPinField.setBounds(120, 510, 150, 25);
        frame.add(newPinField);

        changePinButton = new JButton("Change PIN");
        changePinButton.setBounds(280, 510, 120, 25);
        frame.add(changePinButton);

        clearButton = new JButton("Clear");
        clearButton.setBounds(420, 510, 100, 25);
        frame.add(clearButton);
    }

    private void setupActionListeners() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int atmNumber = Integer.parseInt(atmNumberField.getText());
                    int pin = Integer.parseInt(pinField.getText());

                    if (atmNumber == 12345 && pin == 123) {
                        JOptionPane.showMessageDialog(frame, "Login successful!");
                        logoutButton.setEnabled(true);
                        loginButton.setEnabled(false);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Incorrect ATM Number or PIN!");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter valid ATM Number and PIN.");
                }
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atmNumberField.setText("");
                pinField.setText("");
                amountField.setText("");
                miniStatementArea.setText("");
                balanceLabel.setText("Balance: $0.00");
                newPinField.setText("");
                loginButton.setEnabled(true);
                logoutButton.setEnabled(false);
                JOptionPane.showMessageDialog(frame, "Logged out successfully!");
            }
        });

        viewBalanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atmOperations.viewBalance();
                balanceLabel.setText("Balance: $" + atm.getBalance());
            }
        });

        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double withdrawAmount = Double.parseDouble(amountField.getText());
                    atmOperations.withdrawAmount(withdrawAmount);
                    balanceLabel.setText("Balance: $" + atm.getBalance());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid amount.");
                }
            }
        });

        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double depositAmount = Double.parseDouble(amountField.getText());
                    atmOperations.depositAmount(depositAmount);
                    balanceLabel.setText("Balance: $" + atm.getBalance());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid amount.");
                }
            }
        });

        viewMiniStatementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder miniStatement = new StringBuilder();
                for (Map.Entry<Double, String> entry : atmOperations.getMiniStatement().entrySet()) {
                    miniStatement.append(entry.getKey()).append(" ").append(entry.getValue()).append("\n");
                }
                miniStatementArea.setText(miniStatement.toString());
            }
        });

        changePinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int newPin = Integer.parseInt(newPinField.getText());
                    if (newPin >= 1000 && newPin <= 9999) {
                        JOptionPane.showMessageDialog(frame, "PIN changed successfully!");
                    } else {
                        JOptionPane.showMessageDialog(frame, "PIN must be a 4-digit number.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid PIN.");
                }
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atmNumberField.setText("");
                pinField.setText("");
                amountField.setText("");
                miniStatementArea.setText("");
                balanceLabel.setText("Balance: $0.00");
                newPinField.setText("");
            }
        });
    }

    public static void main(String[] args) {
        new ATMGUI();
    }
}
