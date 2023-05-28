import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URISyntaxException;

public class LoginRegisterDialog extends JDialog {
    public static boolean LOGGED_IN = false;
    private static File USER_DATA_FILE;
    private static String currentUsername = null;
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JCheckBox registerCheckBox;

    public LoginRegisterDialog(JFrame owner) {
        super(owner, "Login/Register", true);
        setLayout(new BorderLayout());

        try {
            String path = LoginRegisterDialog.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            File jarDir = new File(path).getParentFile();
            USER_DATA_FILE = new File(jarDir, "users.txt");

            if (!USER_DATA_FILE.exists()) {
                USER_DATA_FILE.createNewFile();
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

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
                        if (parts.length == 2 && parts[0].equals(username)) {
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
                        writer.write(username + "," + password);
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
                        if (parts.length == 2 && parts[0].equals(username) && parts[1].equals(password)) {
                            LoginRegisterDialog.LOGGED_IN = true;
                            LoginRegisterDialog.currentUsername = username;
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

