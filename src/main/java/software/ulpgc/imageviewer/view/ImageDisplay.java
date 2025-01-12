package software.ulpgc.imageviewer.view;

import software.ulpgc.imageviewer.model.Image;

public interface ImageDisplay {
    void show(Image image);
    Image image();
    void onSwipeLeft(Runnable callback);
    void onSwipeRight(Runnable callback);
}
