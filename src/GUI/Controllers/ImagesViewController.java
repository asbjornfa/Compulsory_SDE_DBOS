package GUI.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ImagesViewController implements Initializable{

    public BorderPane imagesBorderPane;
    private SlideshowViewController slideshowViewController;

    // Setter for controller
    public void setSlideshowViewController(SlideshowViewController slideshowViewController) {
        this.slideshowViewController = slideshowViewController;
    }

    @FXML
    private Button addFilesBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private ImageView imageView;

    @FXML
    private Button loadImagesBtn;

    @FXML
    private MenuItem menuImages;

    @FXML
    private MenuItem menuSlideshow;

    @FXML
    private TableView<?> tblView;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Midlertidig løsning for at vise billede i ImageView..
        Image image = new Image("/Images/nature.jpg");
        imageView.setImage(image);
    }

    @FXML
    public void onClickMenuSlideshow(ActionEvent event) throws IOException {

        // Get the current stage
        Stage currentStage = (Stage) imagesBorderPane.getScene().getWindow();
        // Get its dimensions
        double width = currentStage.getWidth();
        double height = currentStage.getHeight();

        // Close the current stage
        currentStage.close();

        // Load the new scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/SlideshowView.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root, width, height)); // Set width and height for the new stage
        stage.setTitle("Image Viewer");
        stage.show();

        // Get the controller for the AddMovieView.fxml
        SlideshowViewController slideshowViewController1 = loader.getController();
        // Pass the reference to the main controller to allow communication between controllers
        slideshowViewController.setImagesViewController(this);
    }


    public void onClickMenuImages(ActionEvent event) {
    }


    public void onClickAddFilesBtn(ActionEvent event) throws IOException {
        // Load the new scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AddFilesView.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root)); // Set width and height for the new stage
        stage.setTitle("Image Viewer");
        stage.setResizable(false);
        stage.show();
    }

    public void onClickDeleteBtn(ActionEvent event) {

    }
}
