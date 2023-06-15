package ui

import engine.UserManager.addUser
import engine.UserManager.clearFile
import engine.UserManager.forEach
import items.Player
import main.Main
import java.awt.BorderLayout
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.util.concurrent.atomic.AtomicBoolean
import javax.swing.*

class LoginRegisterDialog(owner: JFrame?) : JDialog(owner, "Login/Register", true) {
    private val usernameField: JTextField
    private val passwordField: JPasswordField
    private val registerCheckBox: JCheckBox

    init {
        layout = BorderLayout()
        usernameField = JTextField(20)
        passwordField = JPasswordField(20)
        registerCheckBox = JCheckBox("Register")
        val submitButton = JButton("Submit")
        val cancelButton = JButton("Cancel")
        val inputPanel = JPanel(GridBagLayout())
        inputPanel.border = BorderFactory.createEmptyBorder(10, 10, 10, 10)
        val constraints = GridBagConstraints()
        constraints.anchor = GridBagConstraints.WEST
        constraints.insets = Insets(10, 10, 10, 10)
        constraints.gridx = 0
        constraints.gridy = 0
        inputPanel.add(JLabel("Username: "), constraints)
        constraints.gridx = 1
        inputPanel.add(usernameField, constraints)
        constraints.gridx = 0
        constraints.gridy = 1
        inputPanel.add(JLabel("Password: "), constraints)
        constraints.gridx = 1
        inputPanel.add(passwordField, constraints)
        val buttonPanel = JPanel()
        buttonPanel.add(registerCheckBox)
        buttonPanel.add(submitButton)
        getRootPane().defaultButton = submitButton
        buttonPanel.add(cancelButton)
        submitButton.addActionListener(SubmitButtonListener())
        cancelButton.addActionListener { e: ActionEvent? -> dispose() }
        add(inputPanel, BorderLayout.CENTER)
        add(buttonPanel, BorderLayout.SOUTH)
        pack()
        setLocationRelativeTo(owner)
    }

    private inner class SubmitButtonListener : ActionListener {
        override fun actionPerformed(e: ActionEvent) {
            val username = usernameField.text
            val password = String(passwordField.password)
            val register = registerCheckBox.isSelected
            if (register) { // Register
                val usernameExists = AtomicBoolean(false)

                // check the file for if the username already exists
                forEach { user: Array<String> ->
                    if (user[0] == username) {
                        usernameExists.set(true)
                    }
                }

                // Add the user if the username does not exist.
                if (!usernameExists.get()) {
                    addUser(String.format("%s,%s,0", username, password))
                    JOptionPane.showMessageDialog(
                            null, String.format("Registered user %s.", username),
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE
                    )
                } else {
                    JOptionPane.showMessageDialog(
                            null, String.format("The username %s already exists!", username),
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    )
                }
            } else { // Login
                /* Variable that checks if the entered password is correct and shows a dialog
                 if it is not.*/
                val correctPassword = AtomicBoolean(false)
                forEach("no") { user: Array<String> ->
                    if (user[0] == username && user[1] == password) {
                        LOGGED_IN = true
                        currentUsername = username
                        correctPassword.set(true)
                        if (username == "admin") Main.debug = true // Enable debug mode for admin user
                        dispose()
                    } else if (user[0] == username) {
                        correctPassword.set(false)
                    }
                }
                if (!correctPassword.get()) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Wrong password!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    )
                }
            }
        }
    }

    companion object {
        @JvmField
        var LOGGED_IN = false

        @JvmStatic
        var currentUsername: String? = null
            private set

        fun saveHighScore(player: Player) {
            val playerUsername = player.username
            val playerHighScoreStr = Integer.toString(player.currentHighScore)
            val lines = ArrayList<String>() // read files into this
            forEach { user: Array<String> ->
                if (user[0] == playerUsername) {
                    user[2] = playerHighScoreStr
                }
                lines.add(java.lang.String.join(",", *user))
            }

            // Clear the file before writing the updated lines
            clearFile()

            // Reconstruct the file from the ArrayList
            for (line in lines) {
                addUser(line)
            }
        }
    }
}
