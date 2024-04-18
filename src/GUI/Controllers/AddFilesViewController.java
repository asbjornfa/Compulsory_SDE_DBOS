package GUI.Controllers;

import BE.Images;
import GUI.Model.ImageModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class AddFilesViewController {

    @FXML
    private TextField txtFileName;
    @FXML
    private TextField txtFileFormat;
    @FXML
    private TextField txtFile;
    @FXML
    private AnchorPane addFilesAnchorPane;

    private ImagesViewController imagesViewController;

    private ImageModel imageModel;

    public AddFilesViewController() {
        imageModel = new ImageModel();
    }

    public void setImagesViewController(ImagesViewController imagesViewController){
        this.imagesViewController = imagesViewController;
    }

    public void onActionChoose(ActionEvent event) {
        Stage stage = new Stage();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.setInitialDirectory(new File("Data/Images"));
        fileChooser.getExtensionFilters().addAll(
                //Determine which file extensions is allowed while inserting a file.
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));

        File file = fileChooser.showOpenDialog(stage);

        if (file != null){
            txtFile.setText(("Data/Images/" + file.getName()));

            // Extracting file name without extension
            String fileName = file.getName();
            int lastDotIndex = fileName.lastIndexOf('.');
            if (lastDotIndex > 0) {
                fileName = fileName.substring(0, lastDotIndex);
            }
            txtFileName.setText(fileName);

            // Extracting file extension
            String fileExtension = "";
            int lastIndex = file.getName().lastIndexOf('.');
            if (lastIndex > 0) {
                fileExtension = file.getName().substring(lastIndex + 1);
            }
            txtFileFormat.setText(fileExtension);
        }
    }

    public void onActionCancel(ActionEvent event) {
        closeStage();
    }


    public void onActionSave(ActionEvent event) {
        String fileName = txtFileName.getText();
        String fileFormat = txtFileFormat.getText();
        String filePath = txtFile.getText();

        if (!fileName.isEmpty() && !fileFormat.isEmpty() && !filePath.isEmpty()) {
            Images images = new Images(fileName, fileFormat, filePath);

            imageModel.createImage(fileName, fileFormat, filePath);

            if (imagesViewController!=null) {
                imagesViewController.addImagesToTblView(images);
                imagesViewController.updateImageTblView();
            }
        } else {
            // Show an alert indicating that all fields are required
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Incomplete Fields");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all the required fields.");
            alert.showAndWait();
        }
        closeStage();
    }

    private void closeStage() {
        Stage stage = (Stage) addFilesAnchorPane.getScene().getWindow();
        stage.close();
    }

}
