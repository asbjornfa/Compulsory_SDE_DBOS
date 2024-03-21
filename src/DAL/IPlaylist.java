package DAL;

import BE.Playlist;

import java.util.List;

public interface IPlaylist {

    public List<Playlist> getAllPlaylist();

    public Playlist createPlaylist(Playlist playlist);

    public Playlist deletePlaylist(Playlist playlist);

}
