package ui;

import engine.UserManager;
import items.Player;
import main.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class LoginRegisterDialog extends JDialog {
    public static boolean LOGGED_IN = false;
    private static String currentUsername = null;
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JCheckBox registerCheckBox;

    public LoginRegisterDialog(JFrame owner) {
        super(owner, "Login/Register", true);
        setLayout(new BorderLayout());

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        registerCheckBox = new JCheckBox("Register");

        JButton submitButton = new JButton("Submit");
        JButton cancelButton = new JButton("Cancel");

        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        constraints.gridx = 0;
        constraints.gridy = 0;
        inputPanel.add(new JLabel("Username: "), constraints);

        constraints.gridx = 1;
        inputPanel.add(usernameField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        inputPanel.add(new JLabel("Password: "), constraints);

        constraints.gridx = 1;
        inputPanel.add(passwordField, constraints);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(registerCheckBox);
        buttonPanel.add(submitButton);
        getRootPane().setDefaultButton(submitButton);
        buttonPanel.add(cancelButton);

        submitButton.addActionListener(new SubmitButtonListener());
        cancelButton.addActionListener(e -> dispose());

        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(owner);
    }

    public static String getCurrentUsername() {
        return currentUsername;
    }

    public static void saveHighScore(Player player) {
        String playerUsername = player.getUsername();
        String playerHighScoreStr = Integer.toString(player.getCurrentHighScore());
        ArrayList<String> lines = new ArrayList<>(); // read files into this

        UserManager.forEach(user -> {
            if (user[0].equals(playerUsername)) {
                user[2] = playerHighScoreStr;
            }
            lines.add(String.join(",", user));
        });

        // Clear the file before writing the updated lines
        UserManager.clearFile();

        // Reconstruct the file from the ArrayList
        for (String line : lines) {
            UserManager.addUser(line);
        }
    }


//    public static void saveHighScore(Player player) {
//        /* Read the file to find the username and append the high score to the end of the appropriate line.
//         Add all the lines to a List and reconstruct the file from that list to keep it updated.*/
//
//        String playerUsername = player.getUsername();
//        String playerHighScoreStr = Integer.toString(player.getCurrentHighScore());
//        ArrayList<String> lines = new ArrayList<>(); // read files into this
//
//        UserManager.forEach(user -> {
//            if (user[0].equals(playerUsername)) {
//                user[2] = playerHighScoreStr;
//            }
//            lines.add(String.join(",", user));
//        });
//
//        // Reconstruct the file from the ArrayList
//        for (String line : lines) {
//            UserManager.addUser(line);
//        }
//    }

    private class SubmitButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            boolean register = registerCheckBox.isSelected();

            if (register) { // Register
                AtomicBoolean usernameExists = new AtomicBoolean(false);

                // check the file for if the username already exists
                UserManager.forEach(user -> {
                    if (user[0].equals(username)) {
                        usernameExists.set(true);
                    }
                });

                // Add the user if the username does not exist.
                if (!usernameExists.get()) {
                    UserManager.addUser(String.format("%s,%s,0", username, password));
                    JOptionPane.showMessageDialog(
                            null,
                            String.format("Registered user %s.", username),
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            String.format("The username %s already exists!", username),
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
//                AtomicBoolean canRegister = new AtomicBoolean(false);
//
//                // check the file for if the username already exists
//                UserManager.forEach(user -> {
//                    if (user[0].equals(username)) {
//                        JOptionPane.showMessageDialog(
//                                null,
//                                String.format("The username %s already exists!", username),
//                                "Error",
//                                JOptionPane.ERROR_MESSAGE
//                        );
//                    } else {
//                        canRegister.set(true);
//                    }
//                });
//
//                // Add the user if the username does not exist.
//                if (canRegister.get()) {
//                    UserManager.addUser(String.format("%s,%s,0", username, password));
//                    JOptionPane.showMessageDialog(
//                            null,
//                            String.format("Registered user %s.", username),
//                            "Success",
//                            JOptionPane.INFORMATION_MESSAGE
//                    );
//                }
            } else { // Login
                /* Variable that checks if the entered password is correct and shows a dialog
                 if it is not.*/
                AtomicBoolean correctPassword = new AtomicBoolean(false);

                UserManager.forEach("no", user -> {
                    if (user[0].equals(username) && user[1].equals(password)) {
                        LoginRegisterDialog.LOGGED_IN = true;
                        LoginRegisterDialog.currentUsername = username;
                        correctPassword.set(true);
                        if (username.equals("admin")) Main.debug = true; // Enable debug mode for admin user
                        dispose();
                    } else if (user[0].equals(username)) {
                        correctPassword.set(false);
                    }
                });

                if (!correctPassword.get()) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Wrong password!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        }
    }
}

