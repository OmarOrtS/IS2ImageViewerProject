package software.ulpgc.imageviewer;

import java.awt.*;

public class NextImageCommand implements Command {
    private final ImageDisplay imageDisplay;

    public NextImageCommand(ImageDisplay imageDisplay) {
        this.imageDisplay = imageDisplay;
    }

    @Override
    public void execute() {
        imageDisplay.show(imageDisplay.image().next());
    }
}
