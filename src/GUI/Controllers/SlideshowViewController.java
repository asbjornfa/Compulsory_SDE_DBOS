package GUI.Controllers;

import BE.Images;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.scene.image.PixelReader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class SlideshowViewController implements Initializable {

    @FXML
    private MenuItem menuPlaylists;
    @FXML
    private BorderPane slideshowBorderPane;
    @FXML
    private Label blueLbl;
    @FXML
    private Button clearBtn;
    @FXML
    private Label currentlyLbl;
    @FXML
    private Label greenLbl;
    @FXML
    private ImageView imageView;
    @FXML
    private Button leftSkipBtn;
    @FXML
    private MenuItem menuImages;
    @FXML
    private MenuItem menuSlideshow;
    @FXML
    private Button playPauseBtn;
    @FXML
    private Label redLbl;
    @FXML
    private Button rightSkipBtn;

    private ImagesViewController imagesViewController;
    private PlaylistViewController playlistViewController;

    //Defines a Stage reference
    private Stage slideshowStage;

    private List<Images> slideshowImages;
    private int currentImageIndex = 0;
    private Timer timer;
    private boolean isPlaying = false;

    /**
     * Declares a Task object to handle asynchronous operations for the slideshow.
     * Using a Task allows for executing background tasks without blocking the UI thread.
     * This specific task is responsible for loading and displaying images in the slideshow.
     * By declaring it at the class level, it can be accessed and reused across methods.
     */
    private Task<Void> slideshowTask;

    // Setter for controller
    public void setImagesViewController(ImagesViewController imagesViewController) {
        this.imagesViewController = imagesViewController;
    }

    public void setPlaylistViewController(PlaylistViewController playlistViewController){
        this.playlistViewController = playlistViewController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imagePlaceholder();
        currentlyLbl.setText("");
        redLbl.setText("");
        greenLbl.setText("");
        blueLbl.setText("");
    }

    public void imagePlaceholder(){
        Image placeholder = new Image("/Images/no-image-is-selected.jpg");
        imageView.setImage(placeholder);
    }

    public void loadImages(List<Images> images) {
        slideshowImages = images;
        showCurrentImage();
        startSlideshow();
    }

    private void showCurrentImage() {
        if (slideshowImages != null && !slideshowImages.isEmpty()) {
            Images currentImage = slideshowImages.get(currentImageIndex);
            Image image = new Image(new File(currentImage.getFilePath()).toURI().toString());
            imageView.setImage(image);
            // Update currentlyLbl on the JavaFX application thread
            Platform.runLater(() -> {
                currentlyLbl.setText("Currently displaying: " + currentImage.getFileName());
            });
            countPixels(image);
        }
    }

    private void countPixels(Image image) {
        // Define a Task to perform pixel counting
        Task<Void> countTask = new Task<>() {
            @Override
            protected Void call() {
                // Initialize arrays to hold pixel counts for red, green, and blue
                final int[] redCount = {0};
                final int[] greenCount = {0};
                final int[] blueCount = {0};

                // Get the PixelReader to read pixel values from the image
                PixelReader pixelReader = image.getPixelReader();
                int width = (int) image.getWidth();
                int height = (int) image.getHeight();

                // Iterate over each pixel in the image
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        // Get the color of the current pixel
                        Color color = pixelReader.getColor(x, y);
                        int red = (int) (color.getRed() * 255);
                        int green = (int) (color.getGreen() * 255);
                        int blue = (int) (color.getBlue() * 255);

                        // Check if the color is predominantly red, green, or blue
                        if (red > green && red > blue) {
                            redCount[0]++;
                        } else if (green > red && green > blue) {
                            greenCount[0]++;
                        } else if (blue > red && blue > green) {
                            blueCount[0]++;
                        }
                    }
                }

                // Update the UI with pixel count labels on the JavaFX application thread
                Platform.runLater(() -> {
                    redLbl.setText("Red Pixels: " + redCount[0]);
                    greenLbl.setText("Green Pixels: " + greenCount[0]);
                    blueLbl.setText("Blue Pixels: " + blueCount[0]);
                });

                return null; // Return null since this Task doesn't return any value
            }
        };

        // Start the Task in a new thread
        new Thread(countTask).start();
    }

    /**
     * Starts the slideshow by scheduling a Timer task to change images at regular intervals.
     * If there's an existing timer, it cancels it before creating a new one.
     */
    private void startSlideshow() {
        // Check if there's an existing timer and cancel it
        if (timer != null) {
            timer.cancel();
        }
        // Create a new timer
        timer = new Timer();
        // Schedule a task to run at fixed intervals
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // Check if the slideshow is playing, then proceed to the next image
                if (isPlaying) {
                    nextImage();
                }
            }
        }, 0, 10000); // Change image every 10 seconds
    }

    /**
     * Displays the next image in the slideshow.
     * If there are slideshow images available, it advances to the next image index and
     * loads and displays the new image asynchronously in a separate task to avoid UI blocking.
     */
    private void nextImage() {
        // Check if there are slideshow images available and the list is not empty
        if (slideshowImages != null && !slideshowImages.isEmpty()) {
            // Advance to the next image index in a circular manner
            currentImageIndex = (currentImageIndex + 1) % slideshowImages.size();
            // Create a new task to load and display the current image
            slideshowTask = new Task<>() {
                @Override
                protected Void call() {
                    // Load and display the current image asynchronously
                    showCurrentImage();
                    return null;
                }
            };
            // Start a new thread to execute the slideshow task
            new Thread(slideshowTask).start();
        }
    }

    @FXML
    public void onClickMenuImages(ActionEvent event) throws IOException {

        // Get the current stage
        Stage currentStage = (Stage) slideshowBorderPane.getScene().getWindow();
        // Get its dimensions
        double width = currentStage.getWidth();
        double height = currentStage.getHeight();

        // Close the current stage
        currentStage.close();

        // Load the new scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ImagesView.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root, width, height)); // Set width and height for the new stage
        stage.setTitle("Image Viewer");
        stage.show();

        ImagesViewController imagesViewController = loader.getController();
        imagesViewController.setSlideshowViewController(this);
    }

    @FXML
    public void onClickMenuSlideshow(ActionEvent event) {
    }

    public void onClickLeftSkipBtn(ActionEvent event) {
        currentImageIndex = (currentImageIndex - 1 + slideshowImages.size()) % slideshowImages.size();
        showCurrentImage();
    }

    public void onClickPlayPauseBtn(ActionEvent event) {
        isPlaying = !isPlaying;
        playPauseBtn.setText(isPlaying ? "⏸" : "▶");
    }

    public void onClickRightSkipBtn(ActionEvent event) {
        nextImage();
    }

    public void onClickClearBtn(ActionEvent event) {
        currentlyLbl.setText("");
        redLbl.setText("");
        greenLbl.setText("");
        blueLbl.setText("");
        imagePlaceholder();
    }


    public void onClickMenuPlaylists(ActionEvent event) throws IOException {
        // Get the current stage
        Stage currentStage = (Stage) slideshowBorderPane.getScene().getWindow();
        // Get its dimensions
        double width = currentStage.getWidth();
        double height = currentStage.getHeight();

        // Close the current stage
        currentStage.close();

        // Load the new scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/PlaylistView.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root, width, height)); // Set width and height for the new stage
        stage.setTitle("Image Viewer");
        stage.show();

        PlaylistViewController playlistViewController = loader.getController();
        playlistViewController.setSlideshowViewController(this);
    }
}
