
    package GUI.Controllers;

import BE.Images;
import BE.Playlist;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

    public class PlaylistViewController {

        @FXML
        public TableView<Playlist> playlistTblView;

        @FXML
        public TableColumn colPlaylistName;

        @FXML
        private Button addBtn;

        @FXML
        private TableColumn colFileFormat;

        @FXML
        private TableColumn colFileName;

        @FXML
        private Button deleteBtn;

        @FXML
        private TableView<Images> imageTblView;

        @FXML
        private BorderPane imagesBorderPane;

        @FXML
        private MenuItem menuImages;

        @FXML
        private MenuItem menuSlideshow;



        @FXML
        void onClickAddBtn(ActionEvent event) {

        }

        @FXML
        void onClickDeleteBtn(ActionEvent event) {

        }

        @FXML
        void onClickMenuImages(ActionEvent event) {

        }

        @FXML
        void onClickMenuSlideshow(ActionEvent event) {

        }

    }

