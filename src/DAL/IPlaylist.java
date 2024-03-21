package DAL;

import BE.Image;
import BE.Playlist;
import BLL.PlaylistManager;

import java.util.List;

public interface IPlaylist {

    public List<Playlist> getAllPlaylist();

    public Playlist createPlaylist(Playlist playlist);

    public void deletePlaylist(Playlist playlist);

    public List<Image> getAllImagesFromPlaylist(int playlistId);

    public void addImagesToPlaylist(Image image, Playlist playlist);

    public void deleteImagesFromPlaylist(Image image, Playlist playlist);
}
