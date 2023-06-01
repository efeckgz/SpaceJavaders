package ui;

import main.Main;
import models.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class LoginRegisterDialog extends JDialog {
    public static boolean LOGGED_IN = false;
    private static File USER_DATA_FILE;
    private static String currentUsername = null;

    static {
        try {
            String path = LoginRegisterDialog.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            File jarDir = new File(path).getParentFile();
            USER_DATA_FILE = new File(jarDir, "users.txt");

            if (!USER_DATA_FILE.exists()) {
                if (!USER_DATA_FILE.createNewFile()) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Failed to create users file",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                } else {
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_DATA_FILE, true))) {
                        writer.write("admin,123,0"); // Add admin user
                        writer.newLine();
                        writer.flush(); // flush the changes immediately.
                    }
                }
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

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

    // forEach method for the users
    // This method reads the users.txt file line by line, splits each line into 3 parts and puts them into a
    // String[] where indices 0, 1, 2 are username, password, high score. The method takes a
    // Consumer<String[]> as an argument, which represents the user array. This method will be called
    // with a lambda expression that will run for each user that is registered (except for admin).
    // The overloaded version gets a parameter for sorting prior to performing the action.
    public static void forEach(Consumer<String[]> action) {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3 && !parts[0].equals("admin")) {
                    action.accept(parts);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void forEach(Consumer<String[]> action, boolean sorted) {
        if (sorted) {
            ArrayList<String[]> usersToSort = new ArrayList<>();
            forEach(usersToSort::add);
            usersToSort.sort(Comparator.comparingInt((String[] user) -> -Integer.parseInt(user[2])));
            usersToSort.forEach(action);
        } else {
            forEach(action);
        }
    }

    public static void addUser(String userCredentials) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_DATA_FILE, false))) {
            writer.write(userCredentials);
            writer.newLine();
        } catch (IOException ignored) {
        }
    }

    public static int getUserCount() {
        AtomicInteger count = new AtomicInteger();
        LoginRegisterDialog.forEach(u -> count.getAndIncrement());
        return count.get();
    }

    public static int getHighScoreForUser(String username) {
        AtomicInteger highScore = new AtomicInteger();
        LoginRegisterDialog.forEach(user -> {
            if (user[0].equals(username)) {
                highScore.set(Integer.parseInt(user[2]));
            }
        });

        return highScore.get();
    }

    public static void saveHighScore(Player player) {
        // Read the file to find the username and append the high score to the end of the appropriate line.
        // Add all the lines to a List and reconstruct the file from that list to keep it updated.

        String playerUsername = player.getUsername();
        String playerHighScoreStr = Integer.toString(player.getCurrentHighScore());
        ArrayList<String> lines = new ArrayList<>(); // read files into this

        LoginRegisterDialog.forEach(user -> {
            if (user[0].equals(playerUsername)) {
                user[2] = playerHighScoreStr;
            }
            lines.add(String.join(",", user));
        });

        // Reconstruct the file from the ArrayList
        for (String line : lines) {
            LoginRegisterDialog.addUser(line);
        }
    }

    private class SubmitButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            boolean register = registerCheckBox.isSelected();

            if (register) { // Register
                AtomicBoolean canRegister = new AtomicBoolean(false);

                // check the file for if the username already exists
                LoginRegisterDialog.forEach(user -> {
                    if (user[0].equals(username)) {
                        JOptionPane.showMessageDialog(
                                null,
                                String.format("The username %s already exists!", username),
                                "Error", JOptionPane.ERROR_MESSAGE
                        );
                    } else {
                        canRegister.set(true);
                    }
                });

                // Add the user if the username does not exist.
                if (canRegister.get()) {
                    LoginRegisterDialog.addUser(username + "," + password + "," + 0);
                    JOptionPane.showMessageDialog(
                            null,
                            String.format("Registered user %s.", username),
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }
            } else { // Login
                LoginRegisterDialog.forEach(user -> {
                    if (user[0].equals(username) && user[1].equals(password)) {
                        LoginRegisterDialog.LOGGED_IN = true;
                        LoginRegisterDialog.currentUsername = username;
                        if (username.equals("admin")) Main.debug = true; // Enable debug mode for admin user
                    } else if (user[0].equals(username)) {
                        JOptionPane.showMessageDialog(
                                null,
                                "Wrong password!",
                                "Error", JOptionPane.ERROR_MESSAGE
                        );
                    }
                });
            }
            dispose();
        }
    }
}

