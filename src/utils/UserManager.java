package utils;

import ui.LoginRegisterDialog;

import javax.swing.*;
import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class UserManager {
    private static File USER_DATA_FILE;

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

    /* forEach method for the users
     This method reads the users.txt file line by line, splits each line into 3 parts and puts them into a
     String[] where indices 0, 1, 2 are username, password, high score. The method takes a
     Consumer<String[]> as an argument, which represents the user array. This method will be called
     with a lambda expression that will run for each user that is registered (except for admin).
     The overloaded version gets a parameter for sorting prior to performing the action.*/
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

    public static void forEach(boolean sorted, Consumer<String[]> action) {
        if (sorted) {
            ArrayList<String[]> usersToSort = new ArrayList<>();
            forEach(usersToSort::add);
            usersToSort.sort(Comparator.comparingInt((String[] user) -> -Integer.parseInt(user[2])));
            usersToSort.forEach(action);
        } else {
            forEach(action);
        }
    }

    public static void forEach(String ignoreAdmin, Consumer<String[]> action) { // a hacky solution
        if (ignoreAdmin.equals("no")) {
            try (BufferedReader reader = new BufferedReader(new FileReader(USER_DATA_FILE))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 3) {
                        action.accept(parts);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            forEach(action);
        }
    }

    public static void clearFile() {
        try (PrintWriter writer = new PrintWriter(USER_DATA_FILE)) {
            writer.print("");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static void addUser(String userCredentials) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_DATA_FILE, true))) {
            writer.write(userCredentials);
            writer.newLine();
        } catch (IOException ignored) {
        }
    }

    public static int getUserCount() {
        AtomicInteger count = new AtomicInteger();
        UserManager.forEach(u -> count.getAndIncrement());
        return count.get();
    }

    public static int getHighScoreForUser(String username) {
        AtomicInteger highScore = new AtomicInteger();
        UserManager.forEach(user -> {
            if (user[0].equals(username)) {
                highScore.set(Integer.parseInt(user[2]));
            }
        });

        return highScore.get();
    }
}
