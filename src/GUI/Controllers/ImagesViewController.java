package GUI.Controllers;

import BE.Images;
import GUI.Model.ImageModel;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.swing.event.ChangeListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ImagesViewController implements Initializable{

    @FXML
    public BorderPane imagesBorderPane;
    @FXML
    public TableColumn colFileName;
    @FXML
    public TableColumn colFileFormat;


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
    private TableView<Images> tblViewImages;

    private AddFilesViewController addFilesViewController;
    private SlideshowViewController slideshowViewController;
    private ImageModel imageModel;


    // Setter for controller
    public void setSlideshowViewController(SlideshowViewController slideshowViewController) {
        this.slideshowViewController = slideshowViewController;
    }

    public ImagesViewController() {
        imageModel = new ImageModel();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
         imagePlaceholder();

        tblViewImages.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String filePath = newValue.getFilePath();
                Image image = new Image(new File(filePath).toURI().toString());
                imageView.setImage(image);
            } else {
                // If no row is selected, display the placeholder image
                imagePlaceholder();
            }
        });
        
        colFileName.setCellValueFactory(new PropertyValueFactory<>("fileName"));
        colFileFormat.setCellValueFactory(new PropertyValueFactory<>("fileFormat"));

        // Set the table items from the observable list in the model
        tblViewImages.setItems(imageModel.getObservableImages());

    }

    public void imagePlaceholder(){
        Image placeholder = new Image("/Images/no-image-is-selected.jpg");
        imageView.setImage(placeholder);
    }

    public void updateImageTblView(){
        tblViewImages.refresh();
    }

    public void addImagesToTblView(Images images) {
        tblViewImages.getItems().add(images);
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
        stage.setScene(new Scene(root));
        stage.setTitle("Add files");
        stage.setResizable(false);
        stage.show();

        // Get the controller instance after loading the FXML
        AddFilesViewController addFilesViewController = loader.getController();
        // Set the reference to ImagesViewController
        addFilesViewController.setImagesViewController(this);
    }

    public void onClickDeleteBtn(ActionEvent event) {
        Images selectedImage = tblViewImages.getSelectionModel().getSelectedItem();

        if (selectedImage != null) {
            imageModel.deleteImage(selectedImage);
        }
    }
}
