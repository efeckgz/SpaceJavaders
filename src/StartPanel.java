import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class StartPanel extends JPanel {
    private Font font;
    private Font fontTitle;
    private Font fontSubtitle;

    private StarField starField;
    private Timer timer;

    public StartPanel() {
        setBackground(Color.BLACK);

        SwingUtilities.invokeLater(() -> {
            starField = new StarField(getWidth(), getHeight());

            timer = new Timer(1000 / 120, e -> {
                starField.animate();
                repaint();
            });
            timer.start();
        });

        try {
            //  FIX THE PATH!!!!!!!
            font = Font.createFont(Font.TRUETYPE_FONT, new File("/Users/efeacikgoz/projects/SpaceJavaders/src/Assets/upheavtt.ttf"));
            fontTitle = font.deriveFont(100f);
            fontSubtitle = font.deriveFont(32f);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        if (font != null) ge.registerFont(font);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        starField.draw(g, 0, 0);

        g.setColor(Color.WHITE);
        g.setFont(fontTitle);
        g.drawString("Space Javaders", getWidth() / 16, 300);

        g.setFont(fontSubtitle);
        g.drawString("Bytecode Battle", 315, 350);
    }
}
