package GUI.Model;

import BE.Images;
import BLL.ImageManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ImageModel {

    private ObservableList<Images> imagesToBeViewed;

    private ImageManager imageManager;

    public ImageModel() {
       imageManager = new ImageManager();

        imagesToBeViewed = FXCollections.observableArrayList();

        imagesToBeViewed.addAll(imageManager.getAllImages());
    }

    public ObservableList<Images> getObservableImages() {
        return imagesToBeViewed;
    }

    public void createImage(String fileName, String fileFormat, String filePath){
        Images images = new Images(fileName,fileFormat,filePath);

        imageManager.createImage(images);

        imagesToBeViewed.add(images);
    }

    public void deleteImage(Images images){
        imageManager.deleteImage(images);

        imagesToBeViewed.remove(images);
    }

}
