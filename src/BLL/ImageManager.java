package BLL;

import BE.Image;
import DAL.DB.ImageDAO_DB;
import DAL.IImages;

import java.util.List;

public class ImageManager {

    private IImages imageDAO;

    public ImageManager() {
        imageDAO = new ImageDAO_DB();
    }

    public List<Image> getAllImages(){
        return imageDAO.getAllImages();
    }

    public Image createImage(Image newImage){
        return imageDAO.createImage(newImage);
    }

    public void deleteImage(Image image){
        imageDAO.deleteImage(image);
    }

}
