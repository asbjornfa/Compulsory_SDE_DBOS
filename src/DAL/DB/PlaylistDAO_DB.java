package DAL.DB;

import BE.Image;
import BE.Playlist;
import DAL.IPlaylist;

import java.util.List;

public class PlaylistDAO_DB implements IPlaylist {

    private MyDatabaseConnector databaseConnector;

    public PlaylistDAO_DB() {
        databaseConnector = new MyDatabaseConnector();
    }

    @Override
    public List<Playlist> getAllPlaylist() {
        return null;
    }

    @Override
    public Playlist createPlaylist(Playlist playlist) {
        return null;
    }

    @Override
    public void deletePlaylist(Playlist playlist) {

    }

    @Override
    public List<Image> getAllImagesFromPlaylist(int playlistId) {
        return null;
    }

    @Override
    public void addImagesToPlaylist(Image image, Playlist playlist) {

    }

    @Override
    public void deleteImagesFromPlaylist(Image image, Playlist playlist) {

    }
}
