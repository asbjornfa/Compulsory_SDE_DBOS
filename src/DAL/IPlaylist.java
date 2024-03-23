package DAL;

import BE.Images;
import BE.Playlist;

import java.util.List;

public interface IPlaylist {

    public List<Playlist> getAllPlaylist();

    public Playlist createPlaylist(Playlist playlist);

    public void deletePlaylist(Playlist playlist);

    public List<Images> getAllImagesFromPlaylist(int playlistId);

    public void addImagesToPlaylist(Images images, Playlist playlist);

    public void deleteImagesFromPlaylist(Images images, Playlist playlist);
}
