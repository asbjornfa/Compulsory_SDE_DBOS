package GUI.Model;

import BE.Images;
import BE.Playlist;
import BLL.PlaylistManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class PlaylistModel {

    private ObservableList<Playlist> playlistsToBeViewed;
    private ObservableList<Images> imagesInPlaylistsToBeViewed;

    private PlaylistManager playlistManager;

    public PlaylistModel() {
        playlistManager = new PlaylistManager();

        playlistsToBeViewed = FXCollections.observableArrayList();
        playlistsToBeViewed.addAll(playlistManager.getAllPlaylist());

        imagesInPlaylistsToBeViewed = FXCollections.observableArrayList();
    }

    public ObservableList<Playlist> getPlaylistsToBeViewed() {
        return playlistsToBeViewed;
    }


    public ObservableList<Images> getImagesInPlaylistsToBeViewed(Playlist playlist) {
        int playlistId = playlist.getId();
        List<Images> imagesList = playlistManager.getAllImagesFromPlaylist(playlistId);
        imagesInPlaylistsToBeViewed.setAll(imagesList);
        return imagesInPlaylistsToBeViewed;
    }


    public void createPlaylist(String name){
        Playlist playlist = new Playlist(name);

        playlistManager.createPlaylist(playlist);

        playlistsToBeViewed.add(playlist);
    }

    public void deletePlaylist(Playlist playlist){
        playlistManager.deletePlaylist(playlist);

        playlistsToBeViewed.remove(playlist);
    }

    public List<Images> getAllImagesFromPlaylist(int playlistId) {
        return playlistManager.getAllImagesFromPlaylist(playlistId);
    }

    public void addImagesToPlaylist(Images images, Playlist playlist) {
        playlistManager.addImagesToPlaylist(images,playlist);
    }

    public void deleteImagesFromPlaylist(Images images, Playlist playlist){
        playlistManager.deleteImagesFromPlaylist(images, playlist);
    }
}


