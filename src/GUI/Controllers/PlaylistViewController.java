
package GUI.Controllers;

import BE.Images;
import BE.Playlist;
import GUI.Model.ImageModel;
import GUI.Model.PlaylistModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

    public class PlaylistViewController implements Initializable {

        @FXML
        public TableView<Playlist> playlistTblView;

        @FXML
        public TableColumn colPlaylistName;
        public MenuItem menuPlaylists;
        public Button addImageToPlaylist;
        public Label lblPlaylistName;
        public Button deleteImageFromPlaylistBtn;
        public Button loadPlaylistToSlideshowBtn;

        @FXML
        private Button addPlaylistBtn;


        @FXML
        private TableColumn colFileFormat;

        @FXML
        private TableColumn colFileName;

        @FXML
        private Button deletePlaylistBtn;

        @FXML
        private TableView<Images> imageTblView;

        @FXML
        private BorderPane playlistBorderPane;

        @FXML
        private MenuItem menuImages;

        @FXML
        private MenuItem menuSlideshow;

        private boolean isFilesShown = false;

        private SlideshowViewController slideshowViewController;
        private ImagesViewController imagesViewController;

        // Inside the class
        private ImageModel imageModel;
        private PlaylistModel playlistModel;


        @Override
        public void initialize(URL location, ResourceBundle resources) {
            loadAllPlaylists();
            loadAllImages();
            setupDoubleClickHandler();

            deleteImageFromPlaylistBtn.setVisible(false);
            lblPlaylistName.setText("");
        }


        private void handlePlaylistDoubleClick() {
            Playlist selectedPlaylist = playlistTblView.getSelectionModel().getSelectedItem();
            if (selectedPlaylist != null) {
                addImageToPlaylist.setText("Show Files");
                lblPlaylistName.setText(selectedPlaylist.getName());
                isFilesShown = true;

                // Load images for the selected playlist
                ObservableList<Images> imagesInPlaylist = playlistModel.getImagesInPlaylistsToBeViewed(selectedPlaylist);
                imageTblView.setItems(imagesInPlaylist);

                // Show deleteImageFromPlaylistBtn
                deleteImageFromPlaylistBtn.setVisible(true);
            }
        }

        private void setupDoubleClickHandler() {
            playlistTblView.setOnMouseClicked(event -> {
                if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                    handlePlaylistDoubleClick();
                }
            });
        }

        private void loadAllPlaylists() {
            colPlaylistName.setCellValueFactory(new PropertyValueFactory<>("name"));
            playlistTblView.setItems(playlistModel.getPlaylistsToBeViewed());
        }

        private void loadAllImages() {
            colFileName.setCellValueFactory(new PropertyValueFactory<>("fileName"));
            colFileFormat.setCellValueFactory(new PropertyValueFactory<>("fileFormat"));
            imageTblView.setItems(imageModel.getObservableImages());

        }

        public PlaylistViewController() {
            imageModel = new ImageModel();
            playlistModel = new PlaylistModel();
        }

        // Setter for controller
        public void setSlideshowViewController(SlideshowViewController slideshowViewController) {
            this.slideshowViewController = slideshowViewController;
        }

        // Setter for controller
        public void setImagesViewController(ImagesViewController imagesViewController) {
            this.imagesViewController = imagesViewController;
        }

        public void updatePlaylistTblView() {
            playlistTblView.refresh();
        }

        public void addPlaylistToTblView(Playlist playlist) {playlistTblView.getItems().add(playlist);
        }

        @FXML
        void onClickAddPlaylistBtn(ActionEvent event) throws IOException {
            // Load the new scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/newPlaylistView.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Add files");
            stage.setResizable(false);
            stage.show();

            // Get the controller instance after loading the FXML
            NewPlaylistController newPlaylistController = loader.getController();
            // Set the reference to ImagesViewController
            newPlaylistController.setPlaylistViewController(this);
            }


        @FXML
        void onClickDeletePlaylistBtn(ActionEvent event) {

            Playlist selectedPlaylist = playlistTblView.getSelectionModel().getSelectedItem();

                if (selectedPlaylist != null) {
                    playlistModel.deletePlaylist(selectedPlaylist);
                } else {
                    // Handle no selection error
                    System.out.println("No playlist selected!");
                }
            }


        @FXML
        void onClickMenuImages(ActionEvent event) throws IOException {

            // Get the current stage
            Stage currentStage = (Stage) playlistBorderPane.getScene().getWindow();
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
            imagesViewController.setPlaylistViewController(this);
        }

        @FXML
        void onClickMenuSlideshow(ActionEvent event) throws IOException {

            // Get the current stage
            Stage currentStage = (Stage) playlistBorderPane.getScene().getWindow();
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

            SlideshowViewController slideshowViewController = loader.getController();
            // Pass the reference to the main controller to allow communication between controllers
            slideshowViewController.setPlaylistViewController(this);
        }

        @FXML
        public void onClickMenuPlaylists(ActionEvent event) {
        }

        @FXML
        void onClickAddImageToPlaylist(ActionEvent event) {
            if (isFilesShown) {
                // Reset image table view
                imageTblView.getSelectionModel().clearSelection();

                addImageToPlaylist.setText("Add to playlist");
                deleteImageFromPlaylistBtn.setVisible(false);

                // Reset label text
                lblPlaylistName.setText("");
                imageTblView.setItems(imageModel.getObservableImages());
                isFilesShown = false;
            } else {
                Playlist selectedPlaylist = playlistTblView.getSelectionModel().getSelectedItem();
                Images selectedImage = imageTblView.getSelectionModel().getSelectedItem();

                if (selectedPlaylist != null && selectedImage != null) {
                    // Call the method in PlaylistModel to add image to playlist
                    playlistModel.addImagesToPlaylist(selectedImage, selectedPlaylist);

                    // Alert to confirm the addition
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Image Added");
                    alert.setHeaderText(null);
                    alert.setContentText("The image has been added to the selected playlist.");
                    alert.showAndWait();

                    // Clear the selection in the image table view
                    imageTblView.getSelectionModel().clearSelection();
                } else {
                    // Handle cases where either the playlist or image is not selected
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Selection Missing");
                    alert.setHeaderText(null);
                    alert.setContentText("Please select both a playlist and an image.");
                    alert.showAndWait();
                }
            }
        }

        @FXML
        public void onClickDeleteImageFromPlaylistBtn(ActionEvent event) {
            Playlist selectedPlaylist = playlistTblView.getSelectionModel().getSelectedItem();
            Images selectedImage = imageTblView.getSelectionModel().getSelectedItem();

            if (selectedPlaylist != null && selectedImage != null) {
                // Call the method in PlaylistModel to delete image from playlist
                playlistModel.deleteImagesFromPlaylist(selectedImage, selectedPlaylist);

                // Refresh image table view
                imageTblView.getItems().remove(selectedImage);

                // Alert to confirm the deletion
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Image Deleted");
                alert.setHeaderText(null);
                alert.setContentText("The image has been deleted from the playlist.");
                alert.showAndWait();
            } else {
                // Handle cases where either the playlist or image is not selected
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Selection Missing");
                alert.setHeaderText(null);
                alert.setContentText("Please select both a playlist and an image.");
                alert.showAndWait();
            }
        }

        @FXML
        void onClickLoadPlaylistToSlideshowBtn(ActionEvent event) {
            Playlist selectedPlaylist = playlistTblView.getSelectionModel().getSelectedItem();
            if (selectedPlaylist != null) {
                ObservableList<Images> imagesInPlaylist = playlistModel.getImagesInPlaylistsToBeViewed(selectedPlaylist);
                if (!imagesInPlaylist.isEmpty()) {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/SlideshowView.fxml"));
                        Parent root = loader.load();
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        stage.setTitle("Slideshow");
                        stage.show();

                        SlideshowViewController slideshowViewController = loader.getController();
                        slideshowViewController.loadImages(imagesInPlaylist);

                    } catch (IOException e) {
                        e.printStackTrace();
                        // Handle the exception
                    }
                } else {
                    // Show an alert if the playlist has no images
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Empty Playlist");
                    alert.setHeaderText(null);
                    alert.setContentText("The selected playlist has no images.");
                    alert.showAndWait();
                }
            } else {
                // Show an alert if no playlist is selected
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No Playlist Selected");
                alert.setHeaderText(null);
                alert.setContentText("Please select a playlist.");
                alert.showAndWait();
            }
        }
    }

