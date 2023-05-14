import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class LoginRegisterDialog extends JDialog {
    private static boolean loggedIn = false;
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

        // Create the input panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        // Add components to the input panel
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

        // Create the button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(registerCheckBox);
        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);

        // Add action listeners to buttons
        submitButton.addActionListener(new SubmitButtonListener());
        cancelButton.addActionListener(e -> dispose());

        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(owner);
    }

    public static boolean getLoggedIn() {
        return LoginRegisterDialog.loggedIn;
    }

    public static void setLoggedIn(boolean loggedIn) {
        LoginRegisterDialog.loggedIn = loggedIn;
    }

    private class SubmitButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            boolean register = registerCheckBox.isSelected();

            //JSONObject users = new JSONObject();
            File file = new File("users.json");

            if (register) {
                // Register the user
                // Add logic to store the username/password as a new user
                System.out.println("Registering user: " + username);
                setLoggedIn(true);
            } else {
                // Login
                // Check if the inputted username/password combo matches an existing user
                System.out.println("Logging in user: " + username);
                setLoggedIn(true);
            }

            // Close the dialog
            dispose();
        }
    }
}
