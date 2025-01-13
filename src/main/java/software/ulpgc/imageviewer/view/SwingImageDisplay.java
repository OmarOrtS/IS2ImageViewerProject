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
    private int dragOffset = 0;

    public SwingImageDisplay() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startPosition = e.getX();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (Math.abs(dragOffset) > getWidth() / 3) {
                    if (isSwipeLeft() && canSwipe(swipeLeftCallback)) swipeLeftCallback.run();
                    else if (isSwipeRight() && canSwipe(swipeRightCallback)) swipeRightCallback.run();
                }
                dragOffset = 0;
                repaint();
            }

            private boolean isSwipeRight() {
                return dragOffset > 0;
            }

            private boolean canSwipe(Runnable runnable) {return runnable != null;}

            private boolean isSwipeLeft() {
                return dragOffset < 0;
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                dragOffset = e.getX() - startPosition;
                repaint();
            }

        });
    }

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
        if (bitmap == null) return;

        Resizer resizer = new Resizer(new Dimension(this.getWidth(), this.getHeight()));
        Dimension resized = resizer.resize(new Dimension(bitmap.getWidth(), bitmap.getHeight()));

        g.drawImage(bitmap, currentX(resized), currentY(resized), resized.width, resized.height, null);
    }

    private int currentY(Dimension resized) {return (this.getHeight() - resized.height) / 2;}

    private int currentX(Dimension resized) {return (this.getWidth() - resized.width) / 2 + this.dragOffset;}

    public static class Resizer {
        private final Dimension dimension;

        public Resizer(Dimension dimension) {
            this.dimension = dimension;
        }

        public Dimension resize(Dimension dimension) {
            double scale = Math.min(getWidthRatio(dimension.getWidth(), this.dimension.getWidth()),
                    getHeightRatio(dimension.getHeight(), this.dimension.getHeight()));

            return new Dimension(getNewWidth(scale), getNewHeight(scale));
        }

        private int getNewHeight(double scale) {
            return (int) Math.round(this.dimension.getHeight() * scale);
        }

        private int getNewWidth(double scale) {
            return (int) Math.round(this.dimension.getWidth() * scale);
        }

        private static double getHeightRatio(double targetHeight, double originalHeight) {
            return targetHeight / originalHeight;
        }

        private static double getWidthRatio(double targetWidth, double originalWidth) {
            return targetWidth / originalWidth;
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

