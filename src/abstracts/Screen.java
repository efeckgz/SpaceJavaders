package abstracts;

import constants.GameConstants;
import threads.StarField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;

public abstract class Screen extends JPanel implements GameConstants {
    // An abstract class that provides basic functionality that all the screens in the game need.
    // It initializes the star field, automatically updates itself and provides a method for drawing items.

    private StarField starField; // Every screen has the star field drawn and animated.
    private Timer timer;

    // A buffer to draw all items on to.
    // All the game objects will be drawn onto this buffer
    // and then this buffer will be drawn onto the screen.
    private BufferedImage buffer;

    public Screen() {
        // Constructor code inside this method. This pattern allows inheritors
        // to accept constructor parameters.
        initialize();
    }

    private void initialize() {
        setFocusable(true);
        requestFocusInWindow();
        setBackground(Color.BLACK);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                if (starField == null) {
                    starField = new StarField(getWidth(), getHeight());
                    starField.start();
                }
            }
        });

        timer = new Timer((int) GAME_UPDATE_RATE, e -> updateComponent());
    }

    public void startTimer() {
        timer.start();
    }

    public void stopTimer() {
        if (timer.isRunning()) timer.stop();
    }

    public void updateComponent() {
        // revalidate();
        repaint();
    }

    // Override this method to do screen specific drawing
    // This gets called in JPanel.paintComponent().
    protected abstract void drawItems(Graphics g);

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Create the buffer in the draw method to make sure the getWidth() and getHeight() methods
        // do not return 0. Check if it is null before drawing to not initialize a new buffer for every render.
        if (buffer == null) buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D bufferGraphics = buffer.createGraphics(); // graphics for the buffer

        starField.draw(bufferGraphics, 0, 0);
        drawItems(bufferGraphics);

        bufferGraphics.dispose();

        g.drawImage(buffer, 0, 0, null);
    }
}
