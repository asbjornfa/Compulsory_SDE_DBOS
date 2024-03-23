package DAL;

import BE.Images;

import java.util.List;

public interface IImages {

    public List<Images> getAllImages();

    public Images createImage(Images images);

    public void deleteImage(Images images);

}
