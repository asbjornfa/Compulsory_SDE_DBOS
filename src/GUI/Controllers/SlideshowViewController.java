package GUI.Controllers;

import BE.Images;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class SlideshowViewController implements Initializable {

    public MenuItem menuPlaylists;
    private List<Images> slideshowImages;
    private int currentImageIndex = 0;
    private Timer timer;
    private boolean isPlaying = false;

    public BorderPane slideshowBorderPane;

    private ImagesViewController imagesViewController;
    private PlaylistViewController playlistViewController;

    //Defines a Stage reference
    private Stage slideshowStage;

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
    private Button loadImagesBtn;

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
            Image image = new Image(new File(slideshowImages.get(currentImageIndex).getFilePath()).toURI().toString());
            imageView.setImage(image);
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
        // Pass the reference to the slideshow controller to allow communication between controllers
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
        imagePlaceholder();
    }

    public void onClickLoadImagesBtn(ActionEvent event) {
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
        // Pass the reference to the slideshow controller to allow communication between controllers
        playlistViewController.setSlideshowViewController(this);
    }
}
