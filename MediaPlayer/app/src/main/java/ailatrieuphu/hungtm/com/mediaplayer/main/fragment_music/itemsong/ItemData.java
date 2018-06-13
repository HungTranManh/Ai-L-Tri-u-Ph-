package ailatrieuphu.hungtm.com.mediaplayer.main.fragment_music.itemsong;

public class ItemData {
    private String pathImage;
    private String path;
    private String nameSong;
    private String nameSinger;
    private Long time;
    private Long idAlbum;

    public Long getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(Long idAlbum) {
        this.idAlbum = idAlbum;
    }

    public String getPathImage() {
        return pathImage;
    }

    public void setPathImage(String idImage) {
        this.pathImage = idImage;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getNameSong() {
        return nameSong;
    }

    public void setNameSong(String nameSong) {
        this.nameSong = nameSong;
    }

    public String getNameSinger() {
        return nameSinger;
    }

    public void setNameSinger(String nameSinger) {
        this.nameSinger = nameSinger;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
