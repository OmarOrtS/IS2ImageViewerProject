package software.ulpgc.imageviewer.presenter;

import software.ulpgc.imageviewer.control.Command;
import software.ulpgc.imageviewer.view.ImageDisplay;

import java.util.HashMap;
import java.util.Map;

public class ImagePresenter {
    private final ImageDisplay imageDisplay;
    private final Map<String, Command> commands;

    public ImagePresenter(ImageDisplay imageDisplay) {
        this.imageDisplay = imageDisplay;
        this.commands = new HashMap<>();
        configureView();
    }

    private void configureView() {
        this.imageDisplay.onSwipeLeft(this::nextImage);
        this.imageDisplay.onSwipeRight(this::previousImage);
    }


    private void nextImage() {commands.get(">").execute();}

    private void previousImage() {commands.get("<").execute();}

    public void add(String name, Command command) {commands.put(name, command);}

    public Map<String, Command> commands() {return commands;}
}
