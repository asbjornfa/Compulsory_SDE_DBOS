package DAL.DB;

import BE.Images;
import BE.Playlist;
import DAL.IPlaylist;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDAO_DB implements IPlaylist {

    private MyDatabaseConnector databaseConnector;

    public PlaylistDAO_DB() {
        databaseConnector = new MyDatabaseConnector();
    }

    @Override
    public List<Playlist> getAllPlaylist() {
        ArrayList<Playlist> allPlaylists = new ArrayList<>();

        try (Connection conn = databaseConnector.getConnection();
             Statement stmt = conn.createStatement()){

            String sql = "SELECT * FROM dbo.Playlist";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()){

                String name = rs.getString("name");
                int id = rs.getInt("id");


               Playlist playlist = new Playlist(id,name);
                allPlaylists.add(playlist);
            }
            return allPlaylists;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Playlist createPlaylist(Playlist playlist) {
        String sql =
                "INSERT INTO dbo.Playlist (name) VALUES (?)";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Bind parameters
            stmt.setString(1, playlist.getName());

            stmt.executeUpdate();

            Playlist createdPlaylist = new Playlist(playlist.getName());

            return createdPlaylist;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deletePlaylist(Playlist playlist) {
        String sql = "DELETE FROM dbo.Playlist WHERE Id = (?);";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){

            // Bind parameters
            stmt.setInt(1, playlist.getId());

            // Run the specified SQL statement
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<Images> getAllImagesFromPlaylist(int playlistId) {
        List<Images> allImagesInPlaylist = new ArrayList<>();

        String sql = "SELECT I.*, playlistId\n" +
                "FROM dbo.PlaylistImage\n" +
                "JOIN dbo.Images I on I.Id = PlaylistImage.imageId\n" +
                "WHERE playlistId = (?);";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, playlistId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String fileName = rs.getString("fileName");
                String fileFormat = rs.getString("fileFormat");
                int id = rs.getInt("Id");
                String filePath = rs.getString("filePath");

               Images images = new Images(id, fileName,fileFormat,filePath);
                allImagesInPlaylist.add(images);
            }
            return allImagesInPlaylist;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

        @Override
    public void addImagesToPlaylist(Images images, Playlist playlist) {
            String sql = "INSERT INTO dbo.PlaylistImage (imageId, playlistId) VALUES (?, ?);";

            try (Connection conn = databaseConnector.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                // Bind parameters
                stmt.setInt(1, images.getId());
                stmt.setInt(2, playlist.getId());


                // Run the specified SQL statement
                stmt.executeUpdate();


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

            @Override
    public void deleteImagesFromPlaylist(Images images, Playlist playlist) {
        String sql = "DELETE FROM dbo.PlaylistImage WHERE imageId = (?) and playlistId = (?);";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Bind parameters
            stmt.setInt(1, images.getId());
            stmt.setInt(2, playlist.getId());


            // Run the specified SQL statement
            stmt.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
