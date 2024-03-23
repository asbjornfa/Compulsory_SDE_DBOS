package BLL;

import BE.Images;
import DAL.DB.ImageDAO_DB;
import DAL.IImages;

import java.util.List;

public class ImageManager {

    private IImages imageDAO;

    public ImageManager() {
        imageDAO = new ImageDAO_DB();
    }

    public List<Images> getAllImages(){
        return imageDAO.getAllImages();
    }

    public Images createImage(Images newImages){
        return imageDAO.createImage(newImages);
    }

    public void deleteImage(Images images){
        imageDAO.deleteImage(images);
    }

}
