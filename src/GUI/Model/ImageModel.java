package GUI.Model;

import BE.Images;
import BLL.ImageManager;
import GUI.Controllers.ImagesViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class ImageModel {

    private ObservableList<Images> imagesToBeViewed;

    private ImageManager imageManager;
    private ImagesViewController imagesViewController;

    public ImageModel() {
       imageManager = new ImageManager();

        imagesToBeViewed = FXCollections.observableArrayList();
        imagesToBeViewed.addAll(imageManager.getAllImages());
    }

    public ObservableList<Images> getObservableImages() {
        return imagesToBeViewed;
    }

    public void setImagesViewController(ImagesViewController imagesViewController) {
        this.imagesViewController = imagesViewController;
    }

    public List<Images> getSelectedImages() {
        return imagesViewController.tblViewImages.getSelectionModel().getSelectedItems();
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
