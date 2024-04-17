package DAL.DB;

import BE.Images;
import DAL.IImages;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ImageDAO_DB implements IImages {

    private MyDatabaseConnector databaseConnector;

    public ImageDAO_DB() {
        databaseConnector = new MyDatabaseConnector();
    }

    @Override
    public List<Images> getAllImages() {
        ArrayList<Images> allImages = new ArrayList<>();

        try (Connection conn = databaseConnector.getConnection();
             Statement stmt = conn.createStatement()){

            String sql = "SELECT * FROM dbo.Images";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()){

                String fileName = rs.getString("fileName");
                String fileFormat = rs.getString("fileFormat");
                String filePath = rs.getString("filePath");
                int id = rs.getInt("id");


                Images images = new Images(id,fileName,fileFormat, filePath);
                allImages.add(images);
            }
            return allImages;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Images createImage(Images images) {
        String sql =
                "INSERT INTO dbo.Images (fileName, fileFormat, filePath) VALUES (?,?,?)";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Bind parameters
            stmt.setString(1, images.getFileName());
            stmt.setString(2, images.getFileFormat());
            stmt.setString(3, images.getFilePath());

            stmt.executeUpdate();

            Images createdImage = new Images(images.getFileName(), images.getFileFormat(), images.getFilePath());

            return createdImage;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }



        @Override
    public void deleteImage(Images images) {

            String sql = "DELETE FROM dbo.Images WHERE Id = (?);";

            try (Connection conn = databaseConnector.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)){

                // Bind parameters
                stmt.setInt(1, images.getId());

                // Run the specified SQL statement
                stmt.executeUpdate();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

