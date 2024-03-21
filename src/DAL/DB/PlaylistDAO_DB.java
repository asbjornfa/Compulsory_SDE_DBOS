package DAL.DB;

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
    public Playlist deletePlaylist(Playlist playlist) {
        return null;
    }
}
