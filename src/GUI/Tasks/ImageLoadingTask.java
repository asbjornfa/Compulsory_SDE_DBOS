package GUI.Tasks;

import javafx.concurrent.Task;
import javafx.scene.image.Image;

import java.io.File;

public class ImageLoadingTask extends Task<Image> {

    private final String filePath;

    public ImageLoadingTask(String filePath) {
        this.filePath = filePath;
    }

    @Override
    protected Image call() throws Exception {
        // Simulate loading the image from file
        Thread.sleep(1000);

        // Load the image
        Image image = new Image(new File(filePath).toURI().toString());

        return image;
    }
}