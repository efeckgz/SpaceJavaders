package abstracts;

import constants.GameConstants;
import utils.TimeManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public abstract class GameView extends JPanel implements GameConstants {
    private StarField starField;

    public GameView() {
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
        TimeManager.startTimer(1000 / GAME_FPS, e -> updateComponent());
    }

    public void updateComponent() {
        revalidate();
        repaint();
    }

    // Override this method to do screen specific drawing
    protected abstract void drawItems(Graphics g);

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        starField.draw(g, 0, 0);
        drawItems(g);
    }
}
