package GUI.Controllers;

import BE.Playlist;
import GUI.Model.PlaylistModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewPlaylistController {

    @FXML
    private TextField txtPlaylistName;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;

    private PlaylistViewController playlistViewController;
    private PlaylistModel playlistModel;

    public NewPlaylistController() {
        playlistModel = new PlaylistModel();
    }

    public void setPlaylistViewController(PlaylistViewController playlistViewController){
        this.playlistViewController = playlistViewController;
    }
    public void onActionSavePlaylist(ActionEvent event) {
        String playlistName = txtPlaylistName.getText().trim();
        if (!playlistName.isEmpty()) {
            Playlist playlist = new Playlist(playlistName);
            playlistModel.createPlaylist(playlistName);

            if (playlistViewController!=null) {
                playlistViewController.addPlaylistToTblView(playlist);
                playlistViewController.updatePlaylistTblView();
            }
            closeStage();
        } else {
            System.out.println("Playlist name cannot be empty.");
        }
    }

    public void onActionCancel(ActionEvent event) {
        closeStage();
    }

    private void closeStage() {
        Stage stage = (Stage) btnSave.getScene().getWindow();
        stage.close();
    }
}


