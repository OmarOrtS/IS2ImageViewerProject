package software.ulpgc.imageviewer.view;

import software.ulpgc.imageviewer.model.Image;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SwingImageDisplay extends JPanel implements ImageDisplay {
    private Image image;
    private BufferedImage bitmap;
    private Runnable swipeLeftCallback;
    private Runnable swipeRightCallback;
    private int startPosition;

    public SwingImageDisplay() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {startPosition = e.getX();}

            @Override
            public void mouseReleased(MouseEvent e) {
                int endPosition = e.getX();
                if (isSwipeLeft(endPosition)) runIfNotNull(swipeLeftCallback);
                else if (isSwipeRight(endPosition) ) runIfNotNull(swipeRightCallback);
            }

            private boolean isSwipeRight(int endPosition) {return endPosition - startPosition > 50;}

            private void runIfNotNull(Runnable runnable) {runnable.run();}

            private boolean isSwipeLeft(int endPosition) {return startPosition - endPosition > 50;}
        });
    }

    @Override
    public void show(Image image) {
        this.image = image;
        this.bitmap = load(image.name());
        this.repaint();
    }

    @Override
    public Image image() {return image;}

    @Override
    public void onSwipeLeft(Runnable callback) {
        this.swipeLeftCallback = callback;
    }

    @Override
    public void onSwipeRight(Runnable callback) {
        this.swipeRightCallback = callback;
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        Resizer resizer = new Resizer(new Dimension(this.getWidth(), this.getHeight()));
        Dimension resized = resizer.resize(new Dimension(bitmap.getWidth(), bitmap.getHeight()));
        int x = (this.getWidth() - (int) resized.getWidth()) / 2;
        int y = (this.getHeight() - (int) resized.getHeight()) / 2;
        g.drawImage(bitmap, x, y, resized.width, resized.height, null);
    }

    public static class Resizer {
        private final Dimension dimension;

        public Resizer(Dimension dimension) {
            this.dimension = dimension;
        }

        public Dimension resize(Dimension dimension) {
            double scale = Math.min(getWidthRatio(dimension.getWidth(),
                    this.dimension.getWidth()), getHeightRatio(dimension.getHeight(), this.dimension.getHeight()));

            return new Dimension(getNewWidth(scale), getNewHeight(scale));
        }

        private int getNewHeight(double scale) { return (int) Math.round(this.dimension.getHeight() * scale);}

        private int getNewWidth(double scale) { return (int) Math.round(this.dimension.getWidth() * scale);}

        private static double getHeightRatio(double targetHeight, double originalHeight) { return targetHeight / originalHeight;}

        private static double getWidthRatio(double targetWidth, double originalWidth) { return targetWidth / originalWidth;}
    }

    private BufferedImage load(String name) {
        try {
            return ImageIO.read(new File(name));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
