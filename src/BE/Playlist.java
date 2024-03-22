package BE;

public class Playlist {

    private int id;
    private String name;

    public Playlist(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Playlist(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}