package GUI.Controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class addFilesViewController {


    public TextField txtFileName;
    public TextField txtFileFormat;
    public TextField txtFile;
    public AnchorPane addFilesAnchorPane;

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
            txtFile.setText((file.getName()));
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
        Stage stage = (Stage) addFilesAnchorPane.getScene().getWindow();
        stage.close();
    }

    public void onActionSave(ActionEvent event) {



        Stage stage = (Stage) addFilesAnchorPane.getScene().getWindow();
        stage.close();
    }
}
