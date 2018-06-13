package ailatrieuphu.hungtm.com.mediaplayer.main.fragment_music.album;

public class ItemDataAlbum {
    private int sizeItems;
    private String nameAlbum;
    private String pathImageAlbum;
    private Long idAlbum;
    private String nameSinger;

    public Long getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(Long idAlbum) {
        this.idAlbum = idAlbum;
    }

    public int getSizeItems() {
        return sizeItems;
    }

    public void setSizeItems(int sizeItems) {
        this.sizeItems = sizeItems;
    }

    public String getNameAlbum() {
        return nameAlbum;
    }

    public void setNameAlbum(String nameAlbum) {
        this.nameAlbum = nameAlbum;
    }

    public String getPathImageAlbum() {
        return pathImageAlbum;
    }

    public void setPathImageAlbum(String pathImageAlbum) {
        this.pathImageAlbum = pathImageAlbum;
    }

    public String getNameSinger() {
        return nameSinger;
    }

    public void setNameSinger(String nameSinger) {
        this.nameSinger = nameSinger;
    }
}
