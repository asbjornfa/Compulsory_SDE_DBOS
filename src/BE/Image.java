package BE;

public class Image {

    private int id;
    private String fileName;
    private String fileFormat;

    public Image(int id, String fileName, String fileFormat) {
        this.id = id;
        this.fileName = fileName;
        this.fileFormat = fileFormat;
    }

    public Image(String fileName, String fileFormat) {
        this.fileName = fileName;
        this.fileFormat = fileFormat;
    }

    public int getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileFormat() {
        return fileFormat;
    }

}
