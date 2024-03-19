package GUI.Controllers;

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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SlideshowViewController implements Initializable {

    private ImagesViewController imagesViewController;

    //Defines a Stage reference
    private Stage slideshowStage;

    // Setter for controller
    public void setImagesViewController(ImagesViewController imagesViewController) {
        this.imagesViewController = imagesViewController;
    }

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Midlertidig løsning for at vise billede i ImageView..
        Image image = new Image("/Images/nature.jpg");
        imageView.setImage(image);
    }

    @FXML
    public void onClickMenuImages(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ImagesView.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Image Viewer");
        stage.show();

        // Get the controller for the AddMovieView.fxml
        ImagesViewController imagesViewController = loader.getController();
        // Pass the reference to the main controller to allow communication between controllers
        imagesViewController.setSlideshowViewController(this);



    }



    @FXML
    public void onClickMenuSlideshow(ActionEvent event) {

    }


}
