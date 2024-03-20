package GUI.Model;

import BE.Image;
import BLL.ImageManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ImageModel {

    private ObservableList<Image> imagesToBeViewed;

    private ImageManager imageManager;

    public ImageModel() {
       imageManager = new ImageManager();

        imagesToBeViewed = FXCollections.observableArrayList();

        imagesToBeViewed.addAll(imageManager.getAllImages());
    }

    public ObservableList<Image> getImagesToBeViewed() {
        return imagesToBeViewed;
    }

    public void createImage(String fileName, String fileFormat){
        Image image = new Image(fileName,fileFormat);

        imageManager.createImage(image);

        imagesToBeViewed.add(image);
    }

    public void deleteImage(Image image){
        imageManager.deleteImage(image);

        imagesToBeViewed.remove(image);
    }

}
