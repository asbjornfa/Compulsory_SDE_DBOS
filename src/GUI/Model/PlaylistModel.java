package GUI.Model;

import BE.Playlist;
import BLL.PlaylistManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PlaylistModel {

    private ObservableList<Playlist> playlistsToBeViewed;

    private PlaylistManager playlistManager;

    public PlaylistModel() {
        playlistManager = new PlaylistManager();

        playlistsToBeViewed = FXCollections.observableArrayList();

        playlistsToBeViewed.addAll(playlistManager.getAllPlaylist());
    }

    public ObservableList<Playlist> getPlaylistsToBeViewed() {
        return playlistsToBeViewed;
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

}


