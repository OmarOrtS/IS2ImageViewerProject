package software.ulpgc.imageviewer.view;

import software.ulpgc.imageviewer.presenter.ImagePresenter;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private ImageDisplay imageDisplay;
    private final ImagePresenter presenter;



    public MainFrame() {
        setTitle("Image Viewer");
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        add(createImageDisplay());
        add(createToolbar(), BorderLayout.SOUTH);
        this.presenter = new ImagePresenter(imageDisplay);
    }

    private Component createToolbar() {
        JPanel panel = new JPanel();
        panel.add(createButton("<"));
        panel.add(createButton(">"));
        return panel;
    }

    private Component createButton(String label) {
        JButton button = new JButton(label);
        button.addActionListener(e -> presenter.commands().get(label).execute());
        return button;
    }

    private Component createImageDisplay() {
        SwingImageDisplay display = new SwingImageDisplay();
        this.imageDisplay = display;
        return display;
    }


    public ImageDisplay imageDisplay() {
        return imageDisplay;
    }

    public ImagePresenter getPresenter() {return presenter;}
}
