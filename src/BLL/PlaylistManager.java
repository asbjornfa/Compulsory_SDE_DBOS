package BLL;

import BE.Playlist;
import DAL.DB.PlaylistDAO_DB;
import DAL.IPlaylist;

import java.util.List;

public class PlaylistManager {

    private IPlaylist playlistDAO;

    public PlaylistManager() {
        playlistDAO = new PlaylistDAO_DB();
    }

    public List<Playlist> getAllPlaylist(){
        return playlistDAO.getAllPlaylist();
    }

    public Playlist createPlaylist(Playlist newPlaylist){
        return playlistDAO.createPlaylist(newPlaylist);
    }

    public void deletePlaylist(Playlist playlist){
        playlistDAO.deletePlaylist(playlist);
    }

}
