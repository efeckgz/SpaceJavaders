import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
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

    public static void traverseUsers(Consumer<String[]> action) {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    action.accept(parts);
                }
            }
        } catch (IOException ignored) {

        }
    }

    public static int getHighScoreForUser(String username) {
        AtomicInteger highScore = new AtomicInteger();
        LoginRegisterDialog.traverseUsers(user -> {
            highScore.set(Integer.parseInt(user[2]));
        });

        return highScore.get();
//        try (BufferedReader reader = new BufferedReader(new FileReader(USER_DATA_FILE))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                String[] parts = line.split(",");
//                if (parts.length == 3 && parts[0].equals(username)) {
//                    return Integer.parseInt(parts[2]);
//                }
//            }
//        } catch (IOException ignored) {
//        }
//
//        return 0; // This won't happen.
    }

    public static void saveHighScore(Player player) {
        // Read the file to find the username and append the high score to the end of the appropriate line.
        // Add all the lines to a List and reconstruct the file from that list to keep it updated.

        String playerUsername = player.getUsername();
        String playerHighScoreStr = Integer.toString(player.getCurrentHighScore());
        ArrayList<String> lines = new ArrayList<>(); // read files into this

        try (BufferedReader reader = new BufferedReader(new FileReader(USER_DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3 && parts[0].equals(playerUsername)) {
                    parts[2] = playerHighScoreStr;
                    line = String.join(",", parts);
                }
                lines.add(line); // reconstruct the file as lines into this ArrayList
            }
        } catch (IOException ignored) {
        }

        // Reconstruct the file using the ArrayList
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_DATA_FILE, false))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException ignored) {
        }
    }

    private class SubmitButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            boolean register = registerCheckBox.isSelected();

            if (register) { // Register
                boolean canRegister = false;
                // Open the file for reading to check if the entered credentials already exist
                try (BufferedReader reader = new BufferedReader(new FileReader(USER_DATA_FILE))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(",");
                        if (parts.length == 3 && parts[0].equals(username)) {
                            JOptionPane.showMessageDialog(
                                    null,
                                    String.format("The username %s already exists!", username),
                                    "Error", JOptionPane.ERROR_MESSAGE
                            );
                            return; // If the username already exists return
                        } else {
                            canRegister = true;
                        }
                    }
                } catch (IOException ignored) {

                }

                // Open the file for writing to Register if registering is allowed
                if (canRegister) {
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_DATA_FILE, true))) {
                        writer.write(username + "," + password + "," + 0);
                        writer.newLine();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            } else { // Login
                // Open the file for reading when Logging in
                try (BufferedReader reader = new BufferedReader(new FileReader(USER_DATA_FILE))) {
                    String line;
                    while ((line = reader.readLine()) != null) { // Read the lines until there is no line to read
                        String[] parts = line.split(",");
                        if (parts.length == 3 && parts[0].equals(username) && parts[1].equals(password)) {
                            LoginRegisterDialog.LOGGED_IN = true;
                            LoginRegisterDialog.currentUsername = username;
                            if (username.equals("admin")) Main.debug = true; // Enable debug mode for admin
                            break;
                        }
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            dispose();
        }
    }
}

