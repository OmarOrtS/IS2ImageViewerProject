package software.ulpgc.imageviewer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SwingImageDisplay extends JPanel implements ImageDisplay {
    private Image image;
    private BufferedImage bitmap;

    @Override
    public void show(Image image) {
        this.image = image;
        this.bitmap = load(image.name());
        this.repaint();
    }

    @Override
    public Image image() {
        return image;
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        Resizer resizer = new Resizer(new Dimension(this.getWidth(), this.getHeight()));
        Dimension resized = resizer.resize(new Dimension(bitmap.getWidth(), bitmap.getHeight()));
        int x = (this.getWidth() - bitmap.getWidth()) / 2;
        int y = (bitmap.getHeight() - bitmap.getHeight()) / 2;
        g.drawImage(bitmap, x, y, null);
    }

    public static class Resizer {
        private final Dimension dimension;

        public Resizer(Dimension dimension) {
            this.dimension = dimension;
        }

        public Dimension resize(Dimension dimension) {
            return null;
        }
    }

    private BufferedImage load(String name) {
        try {
            return ImageIO.read(new File(name));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
