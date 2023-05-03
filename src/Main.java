import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Use native macOS menu bar if possible
        try {
            System.setProperty("apple.laf.useScreenMenuBar", "true");
        } catch (Exception e) {
            // Do nothing if the property cannot be set
        }

        JFrame frame = new JFrame("SpaceJavaders"); // Frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar(); // Create the menu bar

        // Creating & adding menus
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        // Creating & adding menu items
        JMenuItem openItem = new JMenuItem("Open");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem exitItem = new JMenuItem("Exit");
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        // Setting frame properties
        frame.setJMenuBar(menuBar);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}