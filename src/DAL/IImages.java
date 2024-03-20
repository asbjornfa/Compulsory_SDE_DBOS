package DAL;

import BE.Image;

import java.util.List;

public interface IImages {

    public List<Image> getAllImages();

    public Image createImage(Image image);

    public Image deleteImage(Image image);

}
