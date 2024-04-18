package GUI.Controllers;

import BE.Images;
import javafx.application.Platform;
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
            countPixels();
        }
    }

    private void countPixels() {
        if (slideshowImages != null && !slideshowImages.isEmpty()) {
            Images currentImage = slideshowImages.get(currentImageIndex);
            Image image = new Image(new File(currentImage.getFilePath()).toURI().toString());
            PixelReader pixelReader = image.getPixelReader();
            int redCount = 0;
            int greenCount = 0;
            int blueCount = 0;

            int width = (int) image.getWidth();
            int height = (int) image.getHeight();

            // Iterate over each pixel
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    Color color = pixelReader.getColor(x, y);
                    int red = (int) (color.getRed() * 255);
                    int green = (int) (color.getGreen() * 255);
                    int blue = (int) (color.getBlue() * 255);

                    // Check if the color is predominantly red, green, or blue
                    if (red > green && red > blue) {
                        redCount++;
                    } else if (green > red && green > blue) {
                        greenCount++;
                    } else if (blue > red && blue > green) {
                        blueCount++;
                    }
                }
            }

            // Update the labels
            redLbl.setText("Red Pixels: " + redCount);
            greenLbl.setText("Green Pixels: " + greenCount);
            blueLbl.setText("Blue Pixels: " + blueCount);
        }
    }

    private void startSlideshow() {
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (isPlaying) {
                    nextImage();
                }
            }
        }, 0, 10000); // Change image every 10 seconds
    }

    private void nextImage() {
        if (slideshowImages != null && !slideshowImages.isEmpty()) {
            currentImageIndex = (currentImageIndex + 1) % slideshowImages.size();
            showCurrentImage();
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
