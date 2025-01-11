package software.ulpgc.imageviewer.model;

public interface Image {
    String name();
    Image next();
    Image prev();
}
