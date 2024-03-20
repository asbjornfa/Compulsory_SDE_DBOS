package DAL.DB;

import BE.Image;
import DAL.IImages;

import java.util.List;

public class ImageDAO_DB implements IImages {

    private MyDatabaseConnector databaseConnector;

    public ImageDAO_DB() {
        databaseConnector = new MyDatabaseConnector();
    }

    @Override
    public List<Image> getAllImages() {
        return null;
    }

    @Override
    public Image createImage(Image image) {
        return null;
    }

    @Override
    public Image deleteImage(Image image) {
        return null;
    }
}
