package GUI.Controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class addFilesViewController {


    public TextField txtFileName;
    public TextField txtFileFormat;
    public TextField txtFile;
    public AnchorPane addFilesAnchorPane;

    public void onActionChoose(ActionEvent event) {
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
