package BE;

public class Images {

    private int id;
    private String fileName;
    private String fileFormat;
    private String filePath;

    public Images(int id, String fileName, String fileFormat, String filePath) {
        this.id = id;
        this.fileName = fileName;
        this.fileFormat = fileFormat;
        this.filePath = filePath;
    }

    public Images(String fileName, String fileFormat, String filePath) {
        this.fileName = fileName;
        this.fileFormat = fileFormat;
        this.filePath = filePath;
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

    public String getFilePath() {
        return filePath;
    }
}
